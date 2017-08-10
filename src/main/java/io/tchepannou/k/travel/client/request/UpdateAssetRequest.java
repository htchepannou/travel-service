package io.tchepannou.k.travel.client.request;

import javax.validation.constraints.NotNull;

public class UpdateAssetRequest {
    @NotNull
    private String immatriculationNumber;

    private String model;
    private Integer year;
    private Integer capacity;

    public String getImmatriculationNumber() {
        return immatriculationNumber;
    }

    public void setImmatriculationNumber(final String immatriculationNumber) {
        this.immatriculationNumber = immatriculationNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(final String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(final Integer year) {
        this.year = year;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(final Integer capacity) {
        this.capacity = capacity;
    }
}
