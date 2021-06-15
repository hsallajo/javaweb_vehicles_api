package com.shu.pricesmicroservice;

import com.shu.pricesmicroservice.model.PriceResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class PricesMicroServiceApplicationTests {

    //@LocalServerPort
    private int port = 8762;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    public void getAllPrices(){

        ResponseEntity<PriceResponse> res = this.restTemplate.getForEntity("http://localhost:" + port + "/prices", PriceResponse.class);

        Assertions.assertEquals(res.getBody().getEmbedded().getPrices().size(), 5);
        Assertions.assertEquals(res.getStatusCode(), HttpStatus.OK);

    }

}
