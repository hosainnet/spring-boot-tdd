package net.hosain.route;

import com.confluex.mock.http.MockHttpServer;
import org.apache.camel.CamelContext;
import org.apache.camel.api.management.mbean.ManagedRouteMBean;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static com.confluex.mock.http.matchers.HttpMatchers.get;
import static com.jayway.awaitility.Awaitility.await;
import static java.nio.file.Files.readAllBytes;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpToFileRouteIT {

    @Autowired
    private CamelContext camelContext;

    private File outFile;
    private MockHttpServer mockHttpServer;

    @Before
    public void setup() {
        mockHttpServer = new MockHttpServer(9090);
        mockHttpServer.respondTo(get("/file.txt")).withBody("mock response");
    }

    @After
    public void cleanup() {
        try {
            outFile.delete();

        } catch (Exception ignored) {
        }
        mockHttpServer.stop();
    }

    @Test
    public void testRouteRequestsResourcesAndWritesResponseToFile() throws Exception {
        //given
        outFile = new File("target/output.txt");

        //when
        await().atMost(10, SECONDS).until(() -> getTotalExchanges() == 1);

        //then
        assertThat(mockHttpServer.getRequests().size()).isEqualTo(1);
        assertThat(mockHttpServer.getRequests().get(0).getPath()).isEqualTo("/file.txt");

        assertThat(outFile.exists()).isTrue();
        assertThat(readFileContent(outFile.getPath())).isEqualTo("mock response");
    }

    private long getTotalExchanges() throws Exception {
        return camelContext.getManagedRoute("remoteResourceRoute", ManagedRouteMBean.class).getExchangesTotal();
    }

    private String readFileContent(String path) throws IOException {
        return new String(readAllBytes(Paths.get(path)));
    }
}