package com.rsakin.getaxi.locationproviderservice.repo;


import com.rsakin.getaxi.locationproviderservice.model.Location;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends ElasticsearchRepository<Location, String> {

    Optional<Location> findByUserId(int userId);

}