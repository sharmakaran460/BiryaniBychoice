package com.example.test.Model;

import android.util.Base64;

public class FoodModel {

    int foodid;
    String foodName;
    String foodCat;
    String foodDes;
    String food_imag_url;
    byte[] image;
    int foodPrice;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public FoodModel() {
    }

    public FoodModel(int foodid, String foodName, String foodCat, String foodDes, int foodPrice , String food_imag_url) {
        this.foodid = foodid;
        this.foodName = foodName;
        this.foodCat = foodCat;
        this.foodDes = foodDes;
        this.foodPrice = foodPrice;
        this.food_imag_url = food_imag_url;
    }

    public int getFoodid() {
        return foodid;
    }

    public void setFoodid(int foodid) {
        this.foodid = foodid;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodCat() {
        return foodCat;
    }

    public void setFoodCat(String foodCat) {
        this.foodCat = foodCat;
    }

    public String getFoodDes() {
        return foodDes;
    }

    public void setFoodDes(String foodDes) {
        this.foodDes = foodDes;
    }

    public int getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(int foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFood_imag_url() {
        return food_imag_url;
    }

    public void setFood_imag_url(String food_imag_url) {
        String imageData[] = food_imag_url.split(",");
        System.out.println("ye data hai split hone k bad........................."+imageData[0]);
        try{
            this.food_imag_url =imageData[1];
            setImage(Base64.decode(this.food_imag_url,Base64.DEFAULT));
            System.out.println("value at one "+this.food_imag_url);
        }catch (ArrayIndexOutOfBoundsException e)
        {
            e.getStackTrace();
        }

        //this.food_imag_url = food_imag_url;


    }

    @Override
    public String toString() {
        return "FoodModel{" +
                "foodid=" + foodid +
                ", foodName='" + foodName + '\'' +
                ", foodCat='" + foodCat + '\'' +
                ", foodDes='" + foodDes + '\'' +
                ", foodPrice=" + foodPrice +
                '}';
    }
}
