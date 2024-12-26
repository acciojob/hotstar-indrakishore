package com.driver.services;

import com.driver.EntryDto.WebSeriesEntryDto;
import com.driver.model.ProductionHouse;
import com.driver.model.WebSeries;
import com.driver.repository.ProductionHouseRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebSeriesService {

    @Autowired
    WebSeriesRepository webSeriesRepository;

    @Autowired
    ProductionHouseRepository productionHouseRepository;


    public Integer addWebSeries(WebSeriesEntryDto webSeriesEntryDto) throws Exception {

        // Check if the series name is already present in the database
        if (webSeriesRepository.findBySeriesName(webSeriesEntryDto.getSeriesName()) != null) {
            throw new Exception("Series is already present");
        }

        // Check if the production house ID exists in the database
        ProductionHouse productionHouse = productionHouseRepository.findById(webSeriesEntryDto.getProductionHouseId())
                .orElseThrow(() -> new Exception("Production house not found"));

        // Convert DTO to entity and set the production house
        WebSeries webSeries = convertDtoToEntity(webSeriesEntryDto);
        webSeries.setProductionHouse(productionHouse);

        // Save the web series
        webSeriesRepository.save(webSeries);

        // Update the ratings of the production house
        List<WebSeries> webSeriesList = webSeriesRepository.findByProductionHouseId(productionHouse.getId());
        double averageRating = webSeriesList.stream().mapToDouble(WebSeries::getRating).average().orElse(0.0);
        productionHouse.setRatings(averageRating);

        // Save the updated production house
        productionHouseRepository.save(productionHouse);

        return webSeries.getId();
    }

    private WebSeries convertDtoToEntity(WebSeriesEntryDto webSeriesEntryDto) {
        WebSeries webSeries = new WebSeries();
        webSeries.setSeriesName(webSeriesEntryDto.getSeriesName());
        webSeries.setRating(webSeriesEntryDto.getRating());
        ProductionHouse productionHouse = productionHouseRepository.findById(webSeriesEntryDto.getProductionHouseId()).get();
        webSeries.setProductionHouse(productionHouse);
        return webSeries;
    }

}
