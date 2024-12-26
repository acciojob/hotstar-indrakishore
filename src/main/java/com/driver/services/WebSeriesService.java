package com.driver.services;

import com.driver.EntryDto.WebSeriesEntryDto;
import com.driver.model.ProductionHouse;
import com.driver.model.WebSeries;
import com.driver.repository.ProductionHouseRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// src/main/java/com/driver/services/WebSeriesService.java

@Service
public class WebSeriesService {

    @Autowired
    WebSeriesRepository webSeriesRepository;

    @Autowired
    ProductionHouseRepository productionHouseRepository;

    public Integer addWebSeries(WebSeriesEntryDto webSeriesEntryDto) throws Exception {
        if (webSeriesRepository.findBySeriesName(webSeriesEntryDto.getSeriesName()) != null) {
            throw new Exception("Series is already present");
        }

        ProductionHouse productionHouse = productionHouseRepository.findById(webSeriesEntryDto.getProductionHouseId())
                .orElseThrow(() -> new Exception("Production house not found"));

        WebSeries webSeries = convertDtoToEntity(webSeriesEntryDto);
        webSeries.setProductionHouse(productionHouse);
        webSeries = webSeriesRepository.save(webSeries);

        List<WebSeries> webSeriesList = webSeriesRepository.findByProductionHouseId(productionHouse.getId());
        double averageRating = webSeriesList.stream().mapToDouble(WebSeries::getRating).average().orElse(0.0);
        productionHouse.setRatings(averageRating);
        productionHouseRepository.save(productionHouse);

        return webSeries.getId();
    }

    private WebSeries convertDtoToEntity(WebSeriesEntryDto webSeriesEntryDto) {
        WebSeries webSeries = new WebSeries();
        webSeries.setSeriesName(webSeriesEntryDto.getSeriesName());
        webSeries.setRating(webSeriesEntryDto.getRating());
        return webSeries;
    }
}
