package net.hosain.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class HttpToFileRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer://foo?period=10s&delay=2s")
                        .id("remoteResourceRoute")
                        .to("http://{{remote.host}}/file.txt")
                        .to("file:{{output.dir.path}}?fileName=output.txt");
    }
}
