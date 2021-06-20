package com.udacity.vehicles.domain;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "self",
        "cars"
})
@Generated("jsonschema2pojo")
public class Links {

    @JsonProperty("self")
    private Self self;
    @JsonProperty("cars")
    private Cars cars;

    @JsonProperty("self")
    public Self getSelf() {
        return self;
    }

    @JsonProperty("self")
    public void setSelf(Self self) {
        this.self = self;
    }

    @JsonProperty("cars")
    public Cars getCars() {
        return cars;
    }

    @JsonProperty("cars")
    public void setCars(Cars cars) {
        this.cars = cars;
    }

}
