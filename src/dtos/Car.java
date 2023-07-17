package dtos;

import enums.CrossLightEnum;

import java.util.Date;

public class Car {
String id;
Date arrivalTime;
CrossLightEnum crossLight;

    public Car(String id, Date arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
    }

    public Car(String id, Date arrivalTime, CrossLightEnum crossLight) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.crossLight = crossLight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CrossLightEnum getCrossLight() {
        return crossLight;
    }

    public void setCrossLight(CrossLightEnum crossLight) {
        this.crossLight = crossLight;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
