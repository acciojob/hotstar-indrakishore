package com.driver.EntryDto;

import com.driver.model.SubscriptionType;



public class SubscriptionEntryDto {

    private int userId;
    private SubscriptionType subscriptionType;
    private int noOfScreensSubscribed;

    public SubscriptionEntryDto(int userId, SubscriptionType subscriptionType, int noOfScreensSubscribed) {
        this.userId = userId;
        this.subscriptionType = subscriptionType;
        this.noOfScreensSubscribed = noOfScreensSubscribed;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public int getNoOfScreensSubscribed() {
        return noOfScreensSubscribed;
    }

    public void setNoOfScreensSubscribed(int noOfScreensSubscribed) {
        this.noOfScreensSubscribed = noOfScreensSubscribed;
    }
}
