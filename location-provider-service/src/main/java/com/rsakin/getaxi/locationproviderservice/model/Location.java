package com.rsakin.getaxi.locationproviderservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "locations")
//@Setting(settingPath = "es-config/elastic-analyzer.json")
@Data
public class Location {

    @Id
    private String id;
    int userId;
    long latitude;
    long longitude;

}
