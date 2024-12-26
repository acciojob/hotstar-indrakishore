package com.driver.services;


import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.repository.SubscriptionRepository;
import com.driver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserRepository userRepository;

    public Integer buySubscription(SubscriptionEntryDto subscriptionEntryDto) {
        Subscription subscription = convertDtoToEntity(subscriptionEntryDto);
        int totalAmount = calculateTotalAmount(subscription.getSubscriptionType(), subscription.getNoOfScreensSubscribed());
        subscription.setTotalAmountPaid(totalAmount);
        subscriptionRepository.save(subscription);
        return totalAmount;
    }

    private int calculateTotalAmount(SubscriptionType subscriptionType, int noOfScreensSubscribed) {
        int basePrice;
        int screenPrice;

        switch (subscriptionType) {
            case BASIC:
                basePrice = 500;
                screenPrice = 200;
                break;
            case PRO:
                basePrice = 800;
                screenPrice = 250;
                break;
            case ELITE:
                basePrice = 1000;
                screenPrice = 350;
                break;
            default:
                throw new IllegalArgumentException("Unknown subscription type: " + subscriptionType);
        }

        return basePrice + screenPrice * noOfScreensSubscribed;
    }

    private Subscription convertDtoToEntity(SubscriptionEntryDto subscriptionEntryDto) {
        Subscription subscription = new Subscription();
        subscription.setSubscriptionType(subscriptionEntryDto.getSubscriptionType());
        subscription.setNoOfScreensSubscribed(subscriptionEntryDto.getNoOfScreensSubscribed());
        subscription.setStartSubscriptionDate(new Date());
        User user = userRepository.findById(subscriptionEntryDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        subscription.setUser(user);
        return subscription;
    }

    public Integer upgradeSubscription(Integer userId) throws Exception {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        Subscription subscription = user.getSubscription();
        SubscriptionType subscriptionType = subscription.getSubscriptionType();

        if (subscription.getTotalAmountPaid() == 0) {
            throw new Exception("Current subscription not paid for");
        }

        if (subscriptionType.equals(SubscriptionType.ELITE)) {
            throw new Exception("Already the best Subscription");
        }

        SubscriptionType newSubscriptionType = SubscriptionType.values()[subscriptionType.ordinal() + 1];
        subscription.setSubscriptionType(newSubscriptionType);
        int newTotalAmount = calculateTotalAmount(newSubscriptionType, subscription.getNoOfScreensSubscribed());
        int difference = newTotalAmount - subscription.getTotalAmountPaid();
        subscription.setTotalAmountPaid(newTotalAmount);
        subscriptionRepository.save(subscription);
        return difference;
    }

    public Integer calculateTotalRevenueOfHotstar() {
        if (subscriptionRepository == null) {
            throw new IllegalStateException("Repository not initialized");
        }
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        return subscriptions.stream().mapToInt(Subscription::getTotalAmountPaid).sum();
    }
}
