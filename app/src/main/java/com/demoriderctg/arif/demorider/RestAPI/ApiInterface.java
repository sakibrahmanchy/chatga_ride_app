package com.demoriderctg.arif.demorider.RestAPI;

/**
 * Created by Sakib Rahman on 11/18/2017.
 */

import com.demoriderctg.arif.demorider.models.ApiModels.AccessTokenModels.AuthToken;
import com.demoriderctg.arif.demorider.models.ApiModels.DateTimeModel.DateTimeResponse;
import com.demoriderctg.arif.demorider.models.ApiModels.DeviceTokenModels.UpdateDeviceTokenData;
import com.demoriderctg.arif.demorider.models.ApiModels.LoginModels.LoginData;
import com.demoriderctg.arif.demorider.models.ApiModels.LoginModels.LoginModel;
import com.demoriderctg.arif.demorider.models.ApiModels.NotificationModels.NotificationResponse;
import com.demoriderctg.arif.demorider.models.ApiModels.Rating.RateDriver;
import com.demoriderctg.arif.demorider.models.ApiModels.Rating.Rating;
import com.demoriderctg.arif.demorider.models.ApiModels.RegistrationModels.RegistrationModel;
import com.demoriderctg.arif.demorider.models.ApiModels.RideHistory.ClientHistory;
import com.demoriderctg.arif.demorider.models.ApiModels.RideHistory.ClientHistoryResponse;
import com.demoriderctg.arif.demorider.models.ApiModels.RideHistory.RideHistoryResponse;
import com.demoriderctg.arif.demorider.models.ApiModels.User;
import com.demoriderctg.arif.demorider.models.ApiModels.UserCheckResponse;
import com.demoriderctg.arif.demorider.models.ApiModels.UserDiscounts.ApplyPromoCodeResponse;
import com.demoriderctg.arif.demorider.models.ApiModels.UserDiscounts.UserDiscountResponse;
import com.demoriderctg.arif.demorider.models.ApiModels.UserDiscounts.UserDiscounts;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.FormUrlEncoded;


public interface ApiInterface {

    @GET("chaatga_rider/api/v1/users/")
    Call<ArrayList<User>> getAllUsers(@Header("Authorization") String authHeader, @Header("Accept") String authType);

    @GET("api/v1/user_exists")
    Call<UserCheckResponse> checkUser(@Query("phoneNumber") String phoneNumber);


    @GET("access_token")
    Call<AuthToken> getAccessToken(@Query("phone_number") String phoneNumber,
                                   @Query("client_id") String clientId,
                                   @Query("client_secret") String clientSecret);

    @POST("api/v1/user/device_token")
    @FormUrlEncoded
    Call<UpdateDeviceTokenData> updateDeviceToken(@Header("Authorization") String authHeader,
                                                  @Field("phone_number") String phoneNumber,
                                                  @Field("device_token") String deviceToken);

    @POST("api/v1/login")
    @FormUrlEncoded
    Call<LoginModel> loginUser(  @Header("Authorization") String authHeader,
                                 @Field("phone_number") String phoneNumber,
                                 @Field("device_token") String deviceToken);

    @POST("api/v1/client")
    @FormUrlEncoded
    Call<RegistrationModel> signUpClient(@Field("first_name") String firstName,
                                         @Field("last_name") String lastName,
                                         @Field("email") String email,
                                         @Field("phone_number") String phoneNumber,
                                         @Field("password") String password,
                                         @Field("device_token") String deviceToken,
                                         @Field("birth_date") String birthDate);

    @GET("api/v1/client/history/specific")
    Call<ClientHistory> getClientSpecificHistory(@Header("Authorization") String authHeader,
                                                 @Query("history_id") int history_id);

    @POST("api/v1/ride/history")
    @FormUrlEncoded
    Call<RideHistoryResponse> createRideHistory(@Field("clientId") int clientId,
                                                @Field("riderId") int riderId,
                                                @Field("start_time") String startTime,
                                                @Field("end_time") String endTime,
                                                @Field("pick_point_latitude") String pickPointLat,
                                                @Field("pick_point_longitude") String pickPointLon,
                                                @Field("destination_point_latitude") String destinationPointLat,
                                                @Field("destination_point_longitude") String destinationPointLon,
                                                @Field("initial_approx_cost") String initialApproxCost);

    @GET("api/v1/user_discounts")
    Call<UserDiscountResponse> getUserDiscounts(@Header("Authorization") String authHeader, @Query("user_id") String userId);

    @GET("api/v1/date_time")
    Call<DateTimeResponse> getDateTime();

    @GET("api/v1/client/history")
    Call<ClientHistoryResponse>getClientHistory(@Header("Authorization") String authHeader,
                                                @Query("client_id") String client_id);

    @POST("api/v1/client/promo_code/apply")
    @FormUrlEncoded
    Call<ApplyPromoCodeResponse>applyPromoCode(@Header("Authorization") String authHeader,
                                               @Field("promo_code") String promoCode,
                                               @Field("client_id") String clientId);
    @POST("api/v1/rate/rider")
    @FormUrlEncoded
    Call<RateDriver>rateRider(@Header("Authorization") String authHeader,
                          @Field("history_id") int historyId,
                          @Field("rating") double ratingPoint);

    @POST("api/v1/rider/rating")
    @FormUrlEncoded
    Call<Rating>getRiderRating(@Header("Authorization") String authHeader,
                              @Field("rider_id") int riderId);

    @POST("api/v1/client/rating")
    @FormUrlEncoded
    Call<Rating>getClientRating(@Header("Authorization") String authHeader,
                               @Field("client_id") int clientId);

    @GET("api/v1/client/notifications")
    Call<NotificationResponse> getClientNotifications(@Header("Authorization") String authHeader);

    @POST("api/v1/client/update_profile")
    @Multipart
    Call<LoginModel> updateClientProfile(@Header("Authorization") String authHeader,
                                         @Part("client_id") RequestBody client_id,
                                         @Part("first_name") RequestBody first_name,
                                         @Part("last_name") RequestBody last_name,
                                         @Part("gender") RequestBody gender,
                                         @Part("email") RequestBody email,
                                         @Part MultipartBody.Part avatar,
                                         @Part("phone") RequestBody phone);

}