package net.hosain.route;

import org.apache.camel.CamelContext;
import org.apache.camel.api.management.mbean.ManagedRouteMBean;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

import static com.jayway.awaitility.Awaitility.await;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpToFileRouteIT {

    @Autowired
    private CamelContext camelContext;

    private File outFile;

    @After
    public void cleanup() {
        try {
            outFile.delete();

        } catch (Exception ignored) {
        }
    }

    @Test
    public void testRouteRequestsResourcesAndWritesResponseToFile() throws Exception {
        //given
        outFile = new File("target/output.txt");

        //when
        await().atMost(10, SECONDS).until(() -> getTotalExchanges() == 1);

        //then
        assertThat(outFile.exists()).isTrue();
        assertThat(new String(readAllBytes(get("target/output.txt")))).isEqualTo("hello\n");
    }

    private long getTotalExchanges() throws Exception {
        return camelContext.getManagedRoute("remoteResourceRoute", ManagedRouteMBean.class).getExchangesTotal();
    }
}