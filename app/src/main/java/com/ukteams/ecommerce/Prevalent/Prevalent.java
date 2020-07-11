package com.ukteams.ecommerce.Prevalent;

import com.ukteams.ecommerce.Model.Users;

public class Prevalent {

    //this class helps to retrieve the details of the user especially when he wants to reset his credentials
    // on forgot password and remember me features on LoginActivity

    public static Users currentOnlineUser;  //taken from Users Activity under Model package bcoz its where the login credentials are kept (in the Model)

    //use these two parameters to store user credentials information
    public static final String UserPhoneKey = "UserPhone";
    public static final String UserPasswordKey = "UserPassword";

}
