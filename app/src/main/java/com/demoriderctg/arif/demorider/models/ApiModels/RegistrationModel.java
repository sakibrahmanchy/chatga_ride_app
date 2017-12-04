package com.demoriderctg.arif.demorider.models.ApiModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Arif on 12/3/2017.
 */

public class RegistrationModel {

    public String getSuccess() {
        return success;
    }



    @SerializedName("success")
    private String success;

    @SerializedName("success")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;

    @SerializedName("email")
    private String email;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("deviceToken")
    private String deviceToken;
    @SerializedName("gender")
    private String gender;
    @SerializedName("birthDate")
    private String birthDate;
    public RegistrationModel(String success,String firstName, String lastName, String email, String phoneNumber, String deviceToken, String gender, String birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.deviceToken = deviceToken;
        this.gender = gender;
        this.birthDate = birthDate;
        this.success=success;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthDate() {
        return birthDate;
    }


}
