package com.ukteams.ecommerce.Model;

public class Users {

//    private String name,password, phone, image, address;
    public String name,password, phone, image, address;

   //create your constructor with no parameters
    public Users() {

    }


    //create your constructor with  parameters
    public Users(String name, String password, String phone, String image, String address) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.image = image;
        this.address = address;
    }


    //set the getter and setter for your parameters


   //name parameter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //password parameter
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    //phone parameter
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }




    //image parameter
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    //address parameter
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
