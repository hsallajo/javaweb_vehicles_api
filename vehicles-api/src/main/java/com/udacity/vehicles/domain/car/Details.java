package com.udacity.vehicles.domain.car;

import javax.annotation.Generated;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "body",
        "model",
        "manufacturer",
        "numberOfDoors",
        "fuelType",
        "engine",
        "mileage",
        "modelYear",
        "productionYear",
        "externalColor"
})

/**
 * Declares the additional detail variables for each Car object,
 * along with related methods for access and setting.
 */

@Generated("jsonschema2pojo")
@Embeddable
public class Details {

    @NotBlank
    @JsonProperty("body")
    private String body;

    @NotBlank
    @JsonProperty("model")
    private String model;

    @NotNull
    @ManyToOne
    @JsonProperty("manufacturer")
    private Manufacturer manufacturer;

    @JsonProperty("numberOfDoors")
    private Integer numberOfDoors;

    @JsonProperty("fuelType")
    private String fuelType;

    @JsonProperty("engine")
    private String engine;

    @JsonProperty("mileage")
    private Integer mileage;

    @JsonProperty("modelYear")
    private Integer modelYear;

    @JsonProperty("productionYear")
    private Integer productionYear;

    @JsonProperty("externalColor")
    private String externalColor;

    @JsonProperty("body")
    public String getBody() {
        return body;
    }

    @JsonProperty("body")
    public void setBody(String body) {
        this.body = body;
    }

    @JsonProperty("model")
    public String getModel() {
        return model;
    }

    @JsonProperty("model")
    public void setModel(String model) {
        this.model = model;
    }

    @JsonProperty("manufacturer")
    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    @JsonProperty("manufacturer")
    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    @JsonProperty("numberOfDoors")
    public Integer getNumberOfDoors() {
        return numberOfDoors;
    }

    @JsonProperty("numberOfDoors")
    public void setNumberOfDoors(Integer numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    @JsonProperty("fuelType")
    public String getFuelType() {
        return fuelType;
    }

    @JsonProperty("fuelType")
    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    @JsonProperty("engine")
    public String getEngine() {
        return engine;
    }

    @JsonProperty("engine")
    public void setEngine(String engine) {
        this.engine = engine;
    }

    @JsonProperty("mileage")
    public Integer getMileage() {
        return mileage;
    }

    @JsonProperty("mileage")
    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    @JsonProperty("modelYear")
    public Integer getModelYear() {
        return modelYear;
    }

    @JsonProperty("modelYear")
    public void setModelYear(Integer modelYear) {
        this.modelYear = modelYear;
    }

    @JsonProperty("productionYear")
    public Integer getProductionYear() {
        return productionYear;
    }

    @JsonProperty("productionYear")
    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    @JsonProperty("externalColor")
    public String getExternalColor() {
        return externalColor;
    }

    @JsonProperty("externalColor")
    public void setExternalColor(String externalColor) {
        this.externalColor = externalColor;
    }

}
