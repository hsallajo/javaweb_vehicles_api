package com.udacity.vehicles.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.udacity.vehicles.domain.car.Car;

import javax.annotation.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "carList"
})
@Generated("jsonschema2pojo")
public class EmbeddedCarList {

    @JsonProperty("carList")
    private List<Car> carList = null;

    @JsonProperty("carList")
    public List<Car> getCarList() {
        return carList;
    }

    @JsonProperty("carList")
    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }

}
