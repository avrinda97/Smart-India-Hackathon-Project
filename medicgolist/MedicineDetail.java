package com.example.shubhanshu.medicgolist;

import java.util.ArrayList;


public class MedicineDetail {
    private String MedicineName;
    private String Price;
    private String Description;
    private int id;
    String ingredients;
    String amt;

    private int upvote;



    private int downvote;


    MedicineDetail(String MedicineName,String Price,String Description,int id){
        this.id=id;
        this.Price=Price;
        this.Description=Description;
        this.MedicineName=MedicineName;
    }
    MedicineDetail(String MedicineName,String Price,String Description,int id,int upvote,int downvote,String ingredients,String amt){
        this.id=id;
        this.Price=Price;
        this.Description=Description;
        this.MedicineName=MedicineName;
        this.upvote=upvote;
        this.downvote=downvote;
        this.ingredients=ingredients;
        this.amt=amt;
    }


    public int getUpvote() {
        return upvote;
    }

    public int getDownvote() {
        return downvote;
    }
    public String getMedicineName() {
        return MedicineName;
    }

    public String getPrice() {
        return Price;
    }

    public String getDescription() {
        return Description;
    }

    public int getId() {
        return id;
    }

    public String getIngredients(){
        return ingredients;
    }
    public String getAmt() {
        return amt;
    }


}
