package com.udacity.vehicles;

import com.udacity.vehicles.domain.CarResponse;
import com.udacity.vehicles.domain.CarsResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class VehiclesApiApplicationTests {

    public final String REQUEST_JSON_CHEVROLET = "{\n" +
            "   \"condition\":\"USED\",\n" +
            "   \"details\":{\n" +
            "      \"body\":\"sedan\",\n" +
            "      \"model\":\"Impala\",\n" +
            "      \"manufacturer\":{\n" +
            "         \"code\":101,\n" +
            "         \"name\":\"Chevrolet\"\n" +
            "      },\n" +
            "      \"numberOfDoors\":4,\n" +
            "      \"fuelType\":\"Gasoline\",\n" +
            "      \"engine\":\"3.6L V6\",\n" +
            "      \"mileage\":32280,\n" +
            "      \"modelYear\":2018,\n" +
            "      \"productionYear\":2018,\n" +
            "      \"externalColor\":\"white\"\n" +
            "   },\n" +
            "   \"location\":{\n" +
            "      \"lat\":40.73061,\n" +
            "      \"lon\":-73.935242\n" +
            "   }\n" +
            "}";

    public final String REQUEST_JSON_FORD = "{\n" +
            "   \"condition\":\"NEW\",\n" +
            "   \"details\":{\n" +
            "      \"body\":\"pickup\",\n" +
            "      \"model\":\"F-150\",\n" +
            "      \"manufacturer\":{\n" +
            "         \"code\":102,\n" +
            "         \"name\":\"Ford\"\n" +
            "      },\n" +
            "      \"numberOfDoors\":4,\n" +
            "      \"fuelType\":\"Gasoline\",\n" +
            "      \"engine\":\"4.6L V8\",\n" +
            "      \"mileage\":10100,\n" +
            "      \"modelYear\":2020,\n" +
            "      \"productionYear\":2020,\n" +
            "      \"externalColor\":\"Red\"\n" +
            "   },\n" +
            "   \"location\":{\n" +
            "      \"lat\":37.7749,\n" +
            "      \"lon\":122.4194\n" +
            "   }\n" +
            "}";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() {
    }

    public void addCars() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> ent = new HttpEntity<String>(REQUEST_JSON_CHEVROLET,headers);
        ResponseEntity<String> ans = this.restTemplate.exchange("http://localhost:" + port + "/cars"
                , HttpMethod.POST
                , ent
                , String.class);

        HttpEntity<String> ent2 = new HttpEntity<String>(REQUEST_JSON_FORD,headers);
        ResponseEntity<String> ans2 = this.restTemplate.exchange("http://localhost:" + port + "/cars"
                , HttpMethod.POST
                , ent2
                , String.class);

        System.out.println("Answer: " + ans.getStatusCode());
        System.out.println("Answer(2): " + ans2.getStatusCode());

    }

    @Test
    public void getAllCars(){

        /*add cars*/
        addCars();

        /*request cars*/
        ResponseEntity<CarsResponse> res = this.restTemplate.getForEntity("http://localhost:" + port + "/cars/", CarsResponse.class);
        assertThat(res.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(res.getBody().getEmbeddedCarList().getCarList().size(), equalTo(2));

    }

    @Test
    public void getPrice(){
        addCars();

        ResponseEntity<CarResponse> res = this.restTemplate.getForEntity("http://localhost:" + port + "/cars/2", CarResponse.class);
        assertThat(res.getStatusCode(), equalTo(HttpStatus.OK));

        assertThat(res.getBody().getDetails().getModel(), equalTo("F-150"));
        assertThat(res.getBody().getPrice(), notNullValue());
    }

}
