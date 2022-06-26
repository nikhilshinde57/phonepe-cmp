package com.phonepe.cmp.controller;

import com.phonepe.cmp.model.db.City;
import com.phonepe.cmp.request.city.CreateCityRequest;
import com.phonepe.cmp.request.city.GetMostDemandedCitiesRequest;
import com.phonepe.cmp.response.GetMostDemandedCitiesResponse;
import com.phonepe.cmp.service.CityService;
import com.phonepe.cmp.service.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/city")
public class CityController {

    @Autowired
    CityService cityService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public City getCityById(@PathVariable int id) {
        return cityService.getCityById(id);
    }

    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public List<City> getAllCabs() {
        return cityService.getAllCities();
    }

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public City createCity(
            @Valid @RequestBody @NotNull CreateCityRequest createCityRequest) {
        return cityService.createCity(createCityRequest);
    }

    @PostMapping(value = "/most-demanded")
    @ResponseStatus(HttpStatus.OK)
    public List<GetMostDemandedCitiesResponse> getMostDemandedCities(@Valid @RequestBody GetMostDemandedCitiesRequest getMostDemandedCitiesRequest) throws BadRequestException {
        return cityService.getMostDemandedCities(getMostDemandedCitiesRequest);
    }

}
