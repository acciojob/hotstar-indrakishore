package com.driver.services;


import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.EntryDto.SubscriptionRequestDto;
import com.driver.exceptions.AlreadyEliteSubscriptionException;
import com.driver.exceptions.SubscriptionNotPaidException;
import com.driver.exceptions.UserNotFoundException;
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
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

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

        return basePrice + (screenPrice * noOfScreensSubscribed);
    }

    private Subscription convertDtoToEntity(SubscriptionEntryDto subscriptionEntryDto) {
        Subscription subscription = new Subscription();
        subscription.setSubscriptionType(subscriptionEntryDto.getSubscriptionType());
        subscription.setNoOfScreensSubscribed(subscriptionEntryDto.getNoOfScreensSubscribed());
        subscription.setStartSubscriptionDate(new Date());
        User user = userRepository.findById(subscriptionEntryDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + subscriptionEntryDto.getUserId()));
        subscription.setUser(user);
        return subscription;
    }

    public Integer upgradeSubscription(Integer userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        Subscription subscription = user.getSubscription();
        if (subscription == null) {
            throw new IllegalArgumentException("User does not have a subscription");
        }

        SubscriptionType subscriptionType = subscription.getSubscriptionType();

        if (subscription.getTotalAmountPaid() == 0) {
            throw new SubscriptionNotPaidException("Current subscription not paid for");
        }

        if (subscriptionType.equals(SubscriptionType.ELITE)) {
            throw new AlreadyEliteSubscriptionException("Already the best subscription");
        }

        // Upgrade to next subscription
        SubscriptionType newSubscriptionType = SubscriptionType.values()[subscriptionType.ordinal() + 1];
        subscription.setSubscriptionType(newSubscriptionType);

        // Calculate new total amount
        int newTotalAmount = calculateTotalAmount(newSubscriptionType, subscription.getNoOfScreensSubscribed());
        int difference = newTotalAmount - subscription.getTotalAmountPaid();

        // Set the new total amount paid
        subscription.setTotalAmountPaid(newTotalAmount);

        // Save subscription after update
        subscriptionRepository.save(subscription);

        return difference;
    }


    public Integer calculateTotalRevenueOfHotstar() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        return subscriptions.stream().mapToInt(Subscription::getTotalAmountPaid).sum();
    }

    public Subscription createSubscription(SubscriptionRequestDto request) {
        Subscription subscription = new Subscription();
        subscription.setUser(userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + request.getUserId())));
        subscription.setSubscriptionType(request.getSubscriptionType());
        subscription.setNoOfScreensSubscribed(request.getNoOfScreensSubscribed());
        subscription.setStartSubscriptionDate(new Date());
        subscription.setTotalAmountPaid(calculateTotalAmount(request.getSubscriptionType(), request.getNoOfScreensSubscribed()));
        subscriptionRepository.save(subscription);
        return subscription;
    }
}
