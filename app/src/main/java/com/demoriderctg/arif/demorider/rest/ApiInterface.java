package com.demoriderctg.arif.demorider.rest;

/**
 * Created by Sakib Rahman on 11/18/2017.
 */

import com.demoriderctg.arif.demorider.models.ApiModels.AuthToken;
import com.demoriderctg.arif.demorider.models.ApiModels.RegistrationModel;
import com.demoriderctg.arif.demorider.models.ApiModels.User;
import com.demoriderctg.arif.demorider.models.ApiModels.UserCheckResponse;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.FormUrlEncoded;


public interface ApiInterface {

    @GET("chaatga_rider/api/v1/users/")
    Call<ArrayList<User>> getAllUsers(@Header("Authorization") String authHeader, @Header("Accept") String authType);

    @GET("api/v1/user_exists")
    Call<UserCheckResponse> checkUser(@Query("phoneNumber") String phoneNumber);

    @GET("api/v1/client/")
    Call<User> getUserDetails(@Query("phoneNumber") String phoneNumber, @Header("Authorization") String authHeader);

    @POST("oauth/token")
    @FormUrlEncoded
    Call<AuthToken> getAuthToken(@Field("client_id") String clientId,
                                 @Field("client_secret") String clientSecret,
                                 @Field("grant_type") String grantType,
                                 @Field("username") String username,
                                 @Field("password") String password);

    @GET("/access_token")
    Call<AuthToken> getAccessToken(@Query("phone_number") String phoneNumber,
                                   @Query("client_id") String clientId,
                                   @Query("client_secret") String clientSecret);

    @POST("api/v1/client")
    @FormUrlEncoded
    Call<RegistrationModel> signUpClient(@Field("first_name") String firstName,
                                 @Field("last_name") String lastName,
                                 @Field("email") String email,
                                 @Field("phone_number") String phoneNumber,
                                 @Field("password") String password,
                                 @Field("device_token") String deviceToken,
                                 @Field("birth_date") String birthDate,
                                 @Field("gender") String gender);

//    @POST("/api/v1/client?")
//    @FormUrlEncoded
//    Call<RegistrationModel> signUpClient2();

}