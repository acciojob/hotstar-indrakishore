//package com.driver.test;
//
//import com.driver.EntryDto.ProductionHouseEntryDto;
//import com.driver.EntryDto.SubscriptionEntryDto;
//import com.driver.EntryDto.WebSeriesEntryDto;
//import com.driver.model.ProductionHouse;
//import com.driver.model.Subscription;
//import com.driver.model.SubscriptionType;
//import com.driver.model.User;
//import com.driver.model.WebSeries;
//import com.driver.repository.ProductionHouseRepository;
//import com.driver.repository.SubscriptionRepository;
//import com.driver.repository.UserRepository;
//import com.driver.repository.WebSeriesRepository;
//import com.driver.services.ProductionHouseService;
//import com.driver.services.SubscriptionService;
//import com.driver.services.UserService;
//import com.driver.services.WebSeriesService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.stubbing.OngoingStubbing;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.when;
//
//
//@ExtendWith(MockitoExtension.class)
//public class TestCases {
//
//    @Mock
//    UserRepository userRepository;
//
//    @Mock
//    WebSeriesRepository webSeriesRepository;
//
//    @Mock
//    SubscriptionRepository subscriptionRepository;
//
//    @Mock
//    ProductionHouseRepository productionHouseRepository;
//
//    @InjectMocks
//    UserService userService;
//
//    @InjectMocks
//    WebSeriesService webSeriesService;
//
//    @InjectMocks
//    SubscriptionService subscriptionService;
//
//    @InjectMocks
//    ProductionHouseService productionHouseService;
//
//    @Test
//    public void testAddUser(){
//        User user = new User();
//        user.setAge(20);
//        user.setName("John");
//        user.setSubscription(SubscriptionType.BASIC);
//        when(userRepository.save(any(User.class))).thenReturn(user);
//        assertEquals(20, userService.addUser(user));
//    }
//
//    @Test
//    public void testGetAvailableCountOfWebSeriesViewable(){
//        User user = new User();
//        user.setAge(20);
//        user.setName("John");
//        user.setSubscriptionType(SubscriptionType.BASIC);
//        when(userRepository.findById(1)).thenReturn(Optional.of(user));
//        List<WebSeries> webSeriesList = new ArrayList<>();
//        WebSeries webSeries1 = new WebSeries();
//        webSeries1.setAgeLimit(18);
//        WebSeries webSeries2 = new WebSeries();
//        webSeries2.setAgeLimit(20);
//        webSeriesList.add(webSeries1);
//        webSeriesList.add(webSeries2);
//        when(webSeriesRepository.findAll()).thenReturn(webSeriesList);
//        assertEquals(1, userService.getAvailableCountOfWebSeriesViewable(1));
//    }
//
//    @Test
//    public void testBuySubscription(){
//        SubscriptionEntryDto subscriptionEntryDto = new SubscriptionEntryDto();
//        subscriptionEntryDto.setNoOfScreensSubscribed(2);
//        subscriptionEntryDto.setSubscriptionType(SubscriptionType.BASIC);
//        Subscription subscription = new Subscription();
//        subscription.setNoOfScreensSubscribed(2);
//        subscription.setSubscriptionType(SubscriptionType.BASIC);
//        subscription.setTotalAmountPaid(900);
//        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscription);
//        assertEquals(900, subscriptionService.buySubscription(subscriptionEntryDto));
//    }
//
//    @Test
//    public void testAddWebSeries(){
//        WebSeriesEntryDto webSeriesEntryDto = new WebSeriesEntryDto();
//        webSeriesEntryDto.setAgeLimit(18);
//        webSeriesEntryDto.setProductionHouseId(1);
//        webSeriesEntryDto.setRatings(4);
//        WebSeries webSeries = new WebSeries();
//        webSeries.setAgeLimit(18);
//        webSeries.setProductionHouseId(1);
//}
//    @Test
//    public void testAddProductionHouse(){
//        ProductionHouseEntryDto productionHouseEntryDto = new ProductionHouseEntryDto();
//        productionHouseEntryDto.setProductionHouseName("XYZ");
//        ProductionHouse productionHouse = new ProductionHouse();
//        productionHouse.setProductionHouseName("XYZ");
//        when(productionHouseRepository.save(any(ProductionHouse.class))).thenReturn(productionHouse);
//        assertEquals("XYZ", productionHouseService.addProductionHouse(productionHouseEntryDto));
//    }
//}
//
