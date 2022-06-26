package com.phonepe.cmp.service;

import com.phonepe.cmp.constants.ErrorMessageConstants;
import com.phonepe.cmp.model.db.City;
import com.phonepe.cmp.model.db.Trip;
import com.phonepe.cmp.request.city.CreateCityRequest;
import com.phonepe.cmp.request.city.GetMostDemandedCitiesRequest;
import com.phonepe.cmp.response.GetMostDemandedCitiesResponse;
import com.phonepe.cmp.service.exception.BadRequestException;
import com.phonepe.cmp.service.exception.EntityAlreadyExistsException;
import com.phonepe.cmp.service.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

@Service
@Slf4j
@Component
public class CityService {

    public static final Logger LOGGER = LoggerFactory.getLogger(CityService.class);
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    private static int nextCityIdToCreate = 1;
    @Autowired
    TripService tripService;
    private List<City> cityList;

    @Autowired
    public CityService() {
        this.cityList = new ArrayList<>();
    }

    public City getCityById(int id) {
        return cityList.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessageConstants.CITY_BY_ID_NOT_FOUND));
    }

    public List<City> getAllCities() {
        return cityList;
    }

    public City getCityByName(String name) {
        return cityList.stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessageConstants.CITY_BY_NAME_NOT_FOUND));
    }

    public List<GetMostDemandedCitiesResponse> getMostDemandedCities(GetMostDemandedCitiesRequest getMostDemandedCitiesRequest) throws BadRequestException {
        List<Trip> tripsList = tripService.getAllTrips();
        if (getMostDemandedCitiesRequest.isByDateRange()) {
            if (getMostDemandedCitiesRequest.getFrom() == null || getMostDemandedCitiesRequest.getTo() == null) {
                throw new BadRequestException(ErrorMessageConstants.START_AND_DATE_REQUIRED);
            }
            List<Trip> filteredListByDateRange = tripsList.stream().filter(t -> t.getEndTime().isAfter(getMostDemandedCitiesRequest.getFrom()) &&
                    t.getEndTime().isBefore(getMostDemandedCitiesRequest.getTo())).collect(Collectors.toList());
            tripsList = filteredListByDateRange;
        }
        //for byTripCount the logic will be same;
        Map<Integer, Integer> cityToTripCountMap = new HashMap<>();
        tripsList.forEach(t -> {
            cityToTripCountMap.putIfAbsent(t.getCityId(), 1);
            cityToTripCountMap.put(t.getCityId(), cityToTripCountMap.get(t.getCityId()) + 1);
        });
        LinkedHashMap<Integer, Integer> descSortedCityToTripCountMap = new LinkedHashMap<>();
        cityToTripCountMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> descSortedCityToTripCountMap.put(x.getKey(), x.getValue()));
        List<GetMostDemandedCitiesResponse> responses = new ArrayList<>();
        descSortedCityToTripCountMap.entrySet().forEach(e -> {
            City city = getCityById(e.getKey());
            responses.add(new GetMostDemandedCitiesResponse(city.getId(), city.getName(), e.getValue()));
        });
        return responses;


    }

    public City createCity(CreateCityRequest createCityRequest) {
        City existingCity = cityList.stream().filter(c -> c.getName().equals(createCityRequest.getName())).findFirst().orElse(null);
        if (existingCity != null) {
            throw new EntityAlreadyExistsException(ErrorMessageConstants.CITY_DUPLICATE_NAME);
        }
        try {
            lock.writeLock().lock();
            City newCity = new City(nextCityIdToCreate, createCityRequest.getName());
            cityList.add(newCity);
            nextCityIdToCreate++;
            return newCity;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
