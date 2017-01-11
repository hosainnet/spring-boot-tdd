package net.hosain.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetVehicles() {
        String response = restTemplate.getForObject("/api/vehicles", String.class);
        assertThat(response).isEqualTo("real");
    }
}