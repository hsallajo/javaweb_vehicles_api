package com.udacity.vehicles;

import com.udacity.vehicles.domain.CarResponse;
import com.udacity.vehicles.domain.CarsResponse;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.Details;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.Location;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
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

        /*add a car*/
        addCars();

        /*request cars*/
        ResponseEntity<CarsResponse> res = this.restTemplate.getForEntity("http://localhost:" + port + "/cars/", CarsResponse.class);
        assertThat(res.getStatusCode(), equalTo(HttpStatus.OK));
        System.out.println("Response: " + res.getBody());

        List<Car> list = res.getBody().getEmbeddedCarList().getCarList();

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
            System.out.println(list.get(i).getDetails().getModel());
            System.out.println(list.get(i).getDetails().getModelYear());
            System.out.println(list.get(i).getDetails().getEngine());
            System.out.println(list.get(i).getPrice());
            System.out.println("\n");
        }

    }

    @Test
    public void getPrice(){
        /*add a car*/
        addCars();

        ResponseEntity<CarResponse> res = this.restTemplate.getForEntity("http://localhost:" + port + "/cars/2", CarResponse.class);
        assertThat(res.getStatusCode(), equalTo(HttpStatus.OK));
        System.out.println("Response: " + res.getBody());

        System.out.println("Looking for price for id: " + res.getBody().getId());
        System.out.println("Model: " + res.getBody().getDetails().getModel());
        System.out.println("Price: " + res.getBody().getPrice() + "$");


    }


    private Car getAnotherCar() {
        Car car = new Car();
        car.setLocation(new Location(37.7749, 122.4194));
        Details details = new Details();
        Manufacturer manufacturer = new Manufacturer(101, "Ford");
        details.setManufacturer(manufacturer);
        details.setModel("F-150");
        details.setMileage(10100);
        details.setExternalColor("Red");
        details.setBody("pickup");
        details.setEngine("4.6L V8");
        details.setFuelType("Gasoline");
        details.setModelYear(2021);
        details.setProductionYear(2020);
        details.setNumberOfDoors(4);
        car.setDetails(details);
        car.setCondition(Condition.USED);
        return car;
    }
}
