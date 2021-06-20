package com.udacity.vehicles.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "self"
})
@Generated("jsonschema2pojo")
public class Links__1 {

    @JsonProperty("self")
    private Links_Self__1 self;

    @JsonProperty("self")
    public Links_Self__1 getLinks_Self() {
        return self;
    }

    @JsonProperty("self")
    public void setLinks_Self(Links_Self__1 self) {
        this.self = self;
    }

}
