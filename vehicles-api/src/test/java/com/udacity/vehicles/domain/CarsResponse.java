package com.udacity.vehicles.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "_embedded",
        "_links"
})
@Generated("jsonschema2pojo")
public class CarsResponse {

    @JsonProperty("_embedded")
    private EmbeddedCarList embeddedCarList;
    @JsonProperty("_links")
    private Links__1 links;

    @JsonProperty("_embedded")
    public EmbeddedCarList getEmbeddedCarList() {
        return embeddedCarList;
    }

    @JsonProperty("_embedded")
    public void setEmbeddedCarList(EmbeddedCarList embeddedCarList) {
        this.embeddedCarList = embeddedCarList;
    }

    @JsonProperty("_links")
    public Links__1 getCarList_Links() {
        return links;
    }

    @JsonProperty("_links")
    public void setCarList_Links(Links__1 links) {
        this.links = links;
    }

}
