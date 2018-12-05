package com.steam.appseg2105;



public class Availability {
    //The name of the service and every day of the week
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;
    private String sunday;
    public Availability( String monday, String tuesday, String wednesday, String thursday, String friday, String saturday, String sunday) {
        if(monday!=null){
            this.monday = monday;
        }if (tuesday!=null){
            this.tuesday = tuesday;
        }if (wednesday!=null){
            this.wednesday = wednesday;
        }if (thursday!=null){
            this.thursday = thursday;
        }if (friday!=null){
            this.friday = friday;
        }if (saturday!=null){
            this.saturday = saturday;
        }if (sunday!=null){
            this.sunday = sunday;
        }

    }


    public String getMonday() {
        return monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public String getFriday() {
        return friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public String getSunday() {
        return sunday;
    }

    public void setMonday(String monday) {     this.monday = monday; }

    public void setTuesday(String tuesday) {     this.tuesday = tuesday; }

    public void setWednesday(String wednesday) {     this.wednesday = wednesday; }

    public void setThursday(String thursday) { this.thursday = thursday; }

    public void setFriday(String friday) { this.friday = friday; }

    public void setSaturday(String saturday) { this.saturday = saturday; }

    public void setSunday(String sunday) { this.sunday = sunday; }
}
