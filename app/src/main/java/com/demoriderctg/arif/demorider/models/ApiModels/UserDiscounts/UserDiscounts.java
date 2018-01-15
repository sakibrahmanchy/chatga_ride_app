package com.demoriderctg.arif.demorider.models.ApiModels.UserDiscounts;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SakibRahman on 1/15/2018.
 */

public class UserDiscounts {

    @SerializedName("id")
    private  int discountId;
    @SerializedName("client_id")
    private int clientId;
    @SerializedName("start_time")
    private String startTime;
    @SerializedName("end_time")
    private String endTime;
    @SerializedName("discount_percentage")
    private double discountPercentage;
    @SerializedName("discount_amount")
    private double discountAmount;
    @SerializedName("promotion_code")
    private String promotionCode;
    @SerializedName("max_use_time")
    private  int maxUseTime;
    @SerializedName("max_discount_amount")
    private double maxDiscountAmount;

    public UserDiscounts(int discountId, int clientId, String startTime, String endTime, double discountPercentage, double discountAmount, String promotionCode, int maxUseTime, double maxDiscountAmount) {
        this.discountId = discountId;
        this.clientId = clientId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.discountPercentage = discountPercentage;
        this.discountAmount = discountAmount;
        this.promotionCode = promotionCode;
        this.maxUseTime = maxUseTime;
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public int getMaxUseTime() {
        return maxUseTime;
    }

    public void setMaxUseTime(int maxUseTime) {
        this.maxUseTime = maxUseTime;
    }

    public double getMaxDiscountAmount() {
        return maxDiscountAmount;
    }

    public void setMaxDiscountAmount(double maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }
}
