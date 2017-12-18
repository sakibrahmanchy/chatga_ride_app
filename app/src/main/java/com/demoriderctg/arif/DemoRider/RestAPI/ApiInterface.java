package com.demoriderctg.arif.DemoRider.RestAPI;

/**
 * Created by Sakib Rahman on 11/18/2017.
 */

import com.demoriderctg.arif.DemoRider.Model.ApiModels.AccessTokenModels.AuthToken;
import com.demoriderctg.arif.DemoRider.Model.ApiModels.LoginModels.LoginModel;
import com.demoriderctg.arif.DemoRider.Model.ApiModels.RegistrationModels.RegistrationModel;
import com.demoriderctg.arif.DemoRider.Model.ApiModels.User;
import com.demoriderctg.arif.DemoRider.Model.ApiModels.UserCheckResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("chaatga_rider/api/v1/users/")
    Call<ArrayList<User>> getAllUsers(@Header("Authorization") String authHeader, @Header("Accept") String authType);

    @GET("api/v1/user_exists")
    Call<UserCheckResponse> checkUser(@Query("phoneNumber") String phoneNumber);

    @GET("access_token")
    Call<AuthToken> getAccessToken(@Query("phone_number") String phoneNumber,
                                   @Query("client_id") String clientId,
                                   @Query("client_secret") String clientSecret
    );

    @POST("api/v1/login")
    @FormUrlEncoded
    Call<LoginModel> loginUser(@Header("Authorization") String authHeader,
                               @Field("phone_number") String phoneNumber,
                               @Field("device_token") String deviceToken
    );

    @POST("api/v1/client")
    @FormUrlEncoded
    Call<RegistrationModel> signUpClient(@Field("first_name") String firstName,
                                         @Field("last_name") String lastName,
                                         @Field("email") String email,
                                         @Field("phone_number") String phoneNumber,
                                         @Field("password") String password,
                                         @Field("device_token") String deviceToken,
                                         @Field("birth_date") String birthDate,
                                         @Field("gender") String gender
    );
}