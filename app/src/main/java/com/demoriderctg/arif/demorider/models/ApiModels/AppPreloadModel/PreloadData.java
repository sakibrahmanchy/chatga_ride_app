package com.demoriderctg.arif.demorider.models.ApiModels.AppPreloadModel;

import com.demoriderctg.arif.demorider.UserInformation;
import com.demoriderctg.arif.demorider.models.ApiModels.AppSettingModels.AppSettings;
import com.demoriderctg.arif.demorider.models.ApiModels.LatLongBound;
import com.demoriderctg.arif.demorider.models.ApiModels.LoginModels.LoginData;
import com.demoriderctg.arif.demorider.models.ApiModels.NewsCardModels.NewsCard;
import com.demoriderctg.arif.demorider.models.RideCancelModels.RideCancelReason;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by SakibRahman on 3/21/2018.
 */

public class PreloadData {

    @SerializedName("clientInformations")
    LoginData userInformations;
    @SerializedName("newsCards")
    ArrayList<NewsCard> newsCards;
    @SerializedName("latLongBounds")
    ArrayList<LatLongBound> latLongBounds;
    @SerializedName("appSettings")
    AppSettings appSettings;
    @SerializedName("rideCancelReasons")
    ArrayList<RideCancelReason> rideCancelReasons;

    public PreloadData(LoginData userInformations,
                       ArrayList<NewsCard> newsCards,
                       ArrayList<LatLongBound> latLongBounds,
                       AppSettings appSettings,
                       ArrayList<RideCancelReason> rideCancelReasons) {
        this.userInformations = userInformations;
        this.newsCards = newsCards;
        this.latLongBounds = latLongBounds;
        this.appSettings = appSettings;
        this.rideCancelReasons = rideCancelReasons;
    }

    public LoginData getUserInformations() {
        return userInformations;
    }

    public void setUserInformations(LoginData userInformations) {
        this.userInformations = userInformations;
    }

    public ArrayList<NewsCard> getNewsCards() {
        return newsCards;
    }

    public void setNewsCards(ArrayList<NewsCard> newsCards) {
        this.newsCards = newsCards;
    }

    public ArrayList<LatLongBound> getLatLongBounds() {
        return latLongBounds;
    }

    public void setLatLongBounds(ArrayList<LatLongBound> latLongBounds) {
        this.latLongBounds = latLongBounds;
    }

    public AppSettings getAppSettings() {
        return appSettings;
    }

    public void setAppSettings(AppSettings appSettings) {
        this.appSettings = appSettings;
    }

    public ArrayList<RideCancelReason> getRideCancelReasons() {
        return rideCancelReasons;
    }

    public void setRideCancelReasons(ArrayList<RideCancelReason> rideCancelReasons) {
        this.rideCancelReasons = rideCancelReasons;
    }
}
