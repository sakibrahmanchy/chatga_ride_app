package com.demoriderctg.arif.demorider.models.ApiModels.LoginModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sakib Rahman on 12/12/2017.
 */

public class LoginData {

    @SerializedName("user_id")
    public String userId;

    @SerializedName("client_id")
    public String clientId;

    @SerializedName("first_name")
    public String firstName;

    @SerializedName("last_name")
    public String lastName;

    @SerializedName("email")
    public String email;

    @SerializedName("phone")
    public String phone;

    @SerializedName("birth_date")
    public String birthDate;

    @SerializedName("gender")
    public String gender;

    @SerializedName("device_token")
    public String deviceToken;

    @SerializedName("avatar")
    public String avatar;

    @SerializedName("rating")
    public float rating;

    @SerializedName("referral_code")
    public String referralCode;

    public LoginData(String firstName, String lastName, String deviceToken, String birthDate, String gender, String userId,
                     String clientId, String phone, String email,String avatar, float rating, String referralCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.deviceToken = deviceToken;
        this.birthDate = birthDate;
        this.gender = gender;
        this.userId = userId;
        this.clientId =clientId;
        this.deviceToken = deviceToken;
        this.phone = phone;
        this.email = email;
        this.rating = rating;
        this.referralCode = referralCode;
    }

    public LoginData(){}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }
}
