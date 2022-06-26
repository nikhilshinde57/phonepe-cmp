package com.phonepe.cmp.service;

import com.phonepe.cmp.constants.ErrorMessageConstants;
import com.phonepe.cmp.enums.CabStatus;
import com.phonepe.cmp.enums.TripStatus;
import com.phonepe.cmp.model.db.Cab;
import com.phonepe.cmp.model.db.City;
import com.phonepe.cmp.model.db.Trip;
import com.phonepe.cmp.request.cab.BookCabRequest;
import com.phonepe.cmp.request.cab.CabStatisticsRequest;
import com.phonepe.cmp.request.cab.CreateCabRequest;
import com.phonepe.cmp.request.cab.UpdateCabRequest;
import com.phonepe.cmp.request.trip.CreateTripRequest;
import com.phonepe.cmp.request.trip.UpdateTripRequest;
import com.phonepe.cmp.response.BookCabResponse;
import com.phonepe.cmp.service.exception.BadRequestException;
import com.phonepe.cmp.service.exception.EntityAlreadyExistsException;
import com.phonepe.cmp.service.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

@Service
public class CabService {

    public static final Logger LOGGER = LoggerFactory.getLogger(CityService.class);
    public static final String EMPTY_STRING = "";
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    private static int nextCabIdToCreate = 1;
    @Autowired
    DriverService driverService;
    @Autowired
    CityService cityService;
    @Autowired
    TripService tripService;
    List<Cab> cabList;

    @Autowired
    public CabService() {
        this.cabList = new ArrayList<>();
    }

    public Cab getCabById(int id) throws EntityNotFoundException {
        return cabList.stream()
                .filter(d -> d.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessageConstants.CAB_BY_ID_NOT_FOUND));
    }

    public List<Cab> getAllCabs() {
        return cabList;
    }

    public Cab createCab(CreateCabRequest createCabRequest) {
        Cab existingCab = cabList.stream().filter(c -> c.getNumber().equals(createCabRequest.getNumber())).findFirst().orElse(null);
        if (existingCab != null) {
            throw new EntityAlreadyExistsException(ErrorMessageConstants.CAB_DUPLICATE_CAB_NUMBER);
        }
        driverService.getDriverById(createCabRequest.getDriverId());
        cityService.getCityById(createCabRequest.getCityId());
        try {
            lock.writeLock().lock();
            Cab newCab = new Cab(nextCabIdToCreate, createCabRequest.getNumber(), createCabRequest.getType(), CabStatus.IDLE, createCabRequest.getDriverId(), createCabRequest.getCityId());
            cabList.add(newCab);
            nextCabIdToCreate++;
            return newCab;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public Cab updateCab(UpdateCabRequest updateCabRequest) {
        try {
            lock.writeLock().lock();
            Cab cabToUpdate = cabList.stream().filter(c -> c.getId() == updateCabRequest.getCabId()).findFirst().orElseThrow(() -> new EntityNotFoundException(ErrorMessageConstants.CAB_BY_ID_NOT_FOUND));
            City cityToUpdate = cityService.getCityById(updateCabRequest.getCityId());
            cabToUpdate.setCityId(cityToUpdate.getId());
            return cabToUpdate;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public BookCabResponse bookCab(BookCabRequest bookCabRequest) {
        try {
            lock.writeLock().lock();
            List<Cab> availableCabList = cabList.stream().filter(c -> c.getState().equals(CabStatus.IDLE) && c.getCityId() == bookCabRequest.getCityId()).collect(Collectors.toList());
            if (availableCabList.isEmpty()) {
                return new BookCabResponse(null, ErrorMessageConstants.CAB_NOT_AVAILABLE);
            }
            List<Trip> tripLisOfIdleCabsByAvbCabIds = tripService.getTripsByCabIds(availableCabList.stream().map(Cab::getId).collect(Collectors.toList()));

            //Cab having past booking history now choose the cab who has remained idle the most time
            if (!tripLisOfIdleCabsByAvbCabIds.isEmpty()) {
                //sort by trip end time and get idle cab who finished trip very first
                tripLisOfIdleCabsByAvbCabIds.sort(Comparator.comparing(Trip::getEndTime));
            }
            //means all available cabs don't have any ride
            Cab assignedCab = availableCabList.get(0);
            assignedCab.setState(CabStatus.ON_TRIP);
            //Trip is Created with status as booked
            Trip tripCreated = tripService.createTip(prepareCreateTripRequest(bookCabRequest, assignedCab));
            //For simplicity on booking we are marking the trip as ongoing
            //tripService.updateTripStatus(tripCreated.getId(),TripStatus.IN_PROGRESS);
            tripService.updateTrip(new UpdateTripRequest(tripCreated.getId(), TripStatus.IN_PROGRESS));
            return new BookCabResponse(tripCreated, EMPTY_STRING);
        } finally {
            lock.writeLock().unlock();
        }
    }


    public long getCabIdleStatistics(CabStatisticsRequest cabStatisticsRequest) throws BadRequestException {
        if (cabStatisticsRequest.getTo().isBefore(cabStatisticsRequest.getFrom())) {
            throw new BadRequestException(ErrorMessageConstants.FROM_DATE_SHOULD_BE_LESS_THAN_TO_DATE);
        }
        Cab cab = getCabById(cabStatisticsRequest.getCabId());
        List<Trip> tripListOfGivenCabId = tripService.getTripsByCabIds(new ArrayList<Integer>() {{
            add(cabStatisticsRequest.getCabId());
        }}).stream().filter(t -> t.getStatus().equals(TripStatus.FINISHED)).collect(Collectors.toList());
        if (tripListOfGivenCabId.isEmpty() && cab.getState().equals(CabStatus.IDLE)) {
            //no rides
            if (cab.getStartTime().isAfter(cabStatisticsRequest.getFrom()) && cab.getEndTime().isBefore(cabStatisticsRequest.getTo())) {
                return Duration.between(cabStatisticsRequest.getTo(), cab.getStartTime()).toHours();
            }
        }
        tripListOfGivenCabId.sort(Comparator.comparing(Trip::getEndTime));
        Trip previousTrip = null;
        long duration = 0;
        // e.g. Trips ->9-10,10.30-11.30 (DATE-rage 9-12)
        for (Trip trip : tripListOfGivenCabId) {
            if (trip.getEndTime().isBefore(cabStatisticsRequest.getFrom())) {
                continue;
            }
            if (previousTrip == null) {
                previousTrip = trip;
                continue;
            } else {
                if (trip.getEndTime().isBefore(cabStatisticsRequest.getTo())) {
                    duration += (Duration.between(trip.getStartTime(), previousTrip.getEndTime()).toHours());
                    previousTrip = trip;
                    continue;
                }
            }
        }
        if (previousTrip != null && previousTrip.getEndTime().isBefore(cabStatisticsRequest.getTo())) {
            duration += (Duration.between(cabStatisticsRequest.getTo(), previousTrip.getEndTime()).toMillis());
        }
        return duration;
    }

    private CreateTripRequest prepareCreateTripRequest(BookCabRequest bookCabRequest, Cab cab) {
        CreateTripRequest createTripRequest = new CreateTripRequest();
        createTripRequest.setCabId(cab.getId());
        createTripRequest.setCityId(cab.getCityId());
        createTripRequest.setRiderId(bookCabRequest.getRiderId());
        createTripRequest.setSource(bookCabRequest.getSource());
        createTripRequest.setDestination(bookCabRequest.getDestination());
        createTripRequest.setPrice(29);
        return createTripRequest;
    }
}
