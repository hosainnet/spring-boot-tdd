package net.hosain.controller;

import net.hosain.service.ApiService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ApiControllerTest {

    private ApiController apiController;
    private ApiService mockApiService;

    @Before
    public void setup() {
        mockApiService = mock(ApiService.class);
        apiController = new ApiController(mockApiService);
    }

    @Test
    public void testGetVehicles() {
        //given
        when(mockApiService.getAllVehicles()).thenReturn("mock");

        //when
        String response = apiController.getVehicles();

        //then
        verify(mockApiService).getAllVehicles();
        assertThat(response).isEqualTo("mock");
    }
}
