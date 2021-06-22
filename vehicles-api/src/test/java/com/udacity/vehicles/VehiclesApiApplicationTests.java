package com.udacity.vehicles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.vehicles.domain.Car;
import com.udacity.vehicles.domain.CarResponse;
import com.udacity.vehicles.domain.CarsResponse;
import com.udacity.vehicles.domain.car.Details;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger log = LoggerFactory.getLogger(VehiclesApiApplicationTests.class);

    @Test
    public void contextLoads() {
    }

    @Test
    public void addCar() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> ent = new HttpEntity<String>(REQUEST_JSON_CHEVROLET, headers);
        ResponseEntity<String> ans = this.restTemplate.exchange("http://localhost:" + port + "/cars"
                , HttpMethod.POST
                , ent
                , String.class);

        assertThat(ans.getStatusCode(), equalTo(HttpStatus.CREATED));

    }

    @Test
    public void updateCar() {

        addCar();

        ResponseEntity<CarResponse> aCar = this.restTemplate.getForEntity("http://localhost:" + port + "/cars/1", CarResponse.class);

        /* modify the car */
        Details oriDetails = aCar.getBody().getDetails();
        Details updatedDetails = oriDetails;
        updatedDetails.setExternalColor("yellow");

        Car updatedCar = new Car();
        updatedCar.setDetails(updatedDetails);
        updatedCar.setCondition(aCar.getBody().getCondition());
        updatedCar.setCreatedAt(aCar.getBody().getCreatedAt());
        updatedCar.setPrice(aCar.getBody().getPrice());
        updatedCar.setId(aCar.getBody().getId());
        updatedCar.setLinks(aCar.getBody().getLinks());
        updatedCar.setLocation(aCar.getBody().getLocation());
        updatedCar.setModifiedAt(aCar.getBody().getModifiedAt());

        /* update the car */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> ent = new HttpEntity<>(objToJson(updatedCar), headers);
        ResponseEntity<String> ans = this.restTemplate.exchange("http://localhost:" + port + "/cars/1"
                , HttpMethod.PUT
                , ent
                , String.class);


        ResponseEntity<CarResponse> res = this.restTemplate.getForEntity("http://localhost:" + port + "/cars/1", CarResponse.class);
        assertThat(res.getBody().getDetails().getExternalColor(), equalTo("yellow"));

    }

    @Test
    public void deleteCar(){

        addCars();

        this.restTemplate.delete("http://localhost:" + port + "/cars/1");

        ResponseEntity<CarsResponse> res = this.restTemplate.getForEntity("http://localhost:" + port + "/cars/", CarsResponse.class);
        assertThat(res.getBody().getEmbeddedCarList().getCarList().size(), equalTo(1));

    }

    @Test
    public void getAllCars() {

        /* adds (2) cars */
        addCars();

        /*request cars*/
        ResponseEntity<CarsResponse> res = this.restTemplate.getForEntity("http://localhost:" + port + "/cars/", CarsResponse.class);
        assertThat(res.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(res.getBody().getEmbeddedCarList().getCarList().size(), equalTo(2));

    }

    @Test
    public void getPrice() {
        addCars();

        ResponseEntity<CarResponse> res = this.restTemplate.getForEntity("http://localhost:" + port + "/cars/2", CarResponse.class);
        assertThat(res.getStatusCode(), equalTo(HttpStatus.OK));

        assertThat(res.getBody().getDetails().getModel(), equalTo("F-150"));
        assertThat(res.getBody().getPrice(), notNullValue());
    }

    public void addCars() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> ent = new HttpEntity<String>(REQUEST_JSON_CHEVROLET, headers);
        ResponseEntity<String> ans = this.restTemplate.exchange("http://localhost:" + port + "/cars"
                , HttpMethod.POST
                , ent
                , String.class);

        HttpEntity<String> ent2 = new HttpEntity<String>(REQUEST_JSON_FORD, headers);
        ResponseEntity<String> ans2 = this.restTemplate.exchange("http://localhost:" + port + "/cars"
                , HttpMethod.POST
                , ent2
                , String.class);

    }

    public String objToJson(Car aCar) {

        String objJson = null;
        try {
            objJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(aCar);
        } catch (JsonProcessingException e) {
            log.debug("ResponseEntity<CarResponse> object to Json conversion failed.", e);
        }
        System.out.println("json object: " + objJson);
        return objJson;
    }

}
