package com.steam.appseg2105;

public class Rating {
    private double rating;
    private String comment;
    public Rating(double rating, String comment){
        this.rating = rating;
        this.comment = comment;
    }
    public double getRating(){
        return rating;
    }
    public String getComment(){
        return comment;
    }
    public void setRating(int rating){
        this.rating = rating;
    }
}
