package com.steam.appseg2105;

public class Service {
    private String serviceTitle;
    private double hourlyRate;
    public Service(String serviceTitle,double hourlyRate){
        this.serviceTitle = serviceTitle;
        this.hourlyRate = hourlyRate;
    }
    public void setHourlyRate(double hourlyRate){
        this.hourlyRate = hourlyRate;
    }
    public String getServiceTitle(){
        return serviceTitle;
    }
    public double getHourlyRate(){
        return hourlyRate;
    }
}
