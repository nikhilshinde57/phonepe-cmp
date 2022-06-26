package com.phonepe.cmp.service;

import com.phonepe.cmp.constants.ErrorMessageConstants;
import com.phonepe.cmp.enums.CabStatus;
import com.phonepe.cmp.enums.TripStatus;
import com.phonepe.cmp.model.db.Cab;
import com.phonepe.cmp.model.db.Trip;
import com.phonepe.cmp.request.trip.CreateTripRequest;
import com.phonepe.cmp.request.trip.UpdateTripRequest;
import com.phonepe.cmp.service.exception.EntityNotFoundException;
import com.phonepe.cmp.service.exception.InvalidOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

@Service
public class TripService {
    public static final Logger LOGGER = LoggerFactory.getLogger(CityService.class);
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    private static int nextTripIdToCreate = 1;
    @Autowired
    CabService cabService;
    private List<Trip> tripList;

    @Autowired
    public TripService() {
        this.tripList = new ArrayList<>();
    }

    public Trip getTripById(Long id) {
        return tripList.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessageConstants.TRIP_BY_ID_NOT_FOUND));
    }

    public List<Trip> getAllTrips() {
        return tripList;
    }

    public List<Trip> getTripsByCabIds(List<Integer> cabIds) {
        return tripList.stream().filter(t -> cabIds.stream().allMatch(id -> id == t.getId())).collect(Collectors.toList());
    }

    public Trip createTip(CreateTripRequest createTripRequest) {
        //Check that is there any trip ongoing with given cabId
        Trip isOnGoingTripWithGiveCabId = tripList.stream().filter(t -> t.getStatus().equals(TripStatus.IN_PROGRESS) && t.getCabId() == createTripRequest.getCabId()).findFirst().orElse(null);
        if (isOnGoingTripWithGiveCabId != null) {
            throw new InvalidOperationException(ErrorMessageConstants.TRIP_DUPLICATE_WITH_CAB_NUMBER);
        }

        try {
            lock.writeLock().lock();
            Trip newTrip = new Trip(nextTripIdToCreate, createTripRequest.getRiderId(), createTripRequest.getCabId(), createTripRequest.getCityId(), createTripRequest.getSource(),
                    createTripRequest.getDestination(), createTripRequest.getPrice(), LocalDateTime.now());

            tripList.add(newTrip);
            nextTripIdToCreate++;
            return newTrip;
        } finally {
            lock.writeLock().unlock();
        }

    }

    public Trip updateTripStatus(int tripId, TripStatus statusToUpdate) {
        if (statusToUpdate == TripStatus.BOOKED) {
            throw new InvalidOperationException(ErrorMessageConstants.TRIP_STATUS_CANNOT_UPDATE);
        }
        try {
            lock.writeLock().lock();
            Trip tripToUpdate = tripList.stream().filter(t -> t.getId() == tripId).findFirst().orElseThrow(() -> new EntityNotFoundException(ErrorMessageConstants.TRIP_BY_ID_NOT_FOUND));
            tripToUpdate.setStatus(statusToUpdate);
            return tripToUpdate;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public Trip updateTrip(UpdateTripRequest updateTripRequest) {
        Trip tripToUpdate = tripList.stream().filter(t -> t.getId() == updateTripRequest.getTripId()).findFirst().orElseThrow(() -> new EntityNotFoundException(ErrorMessageConstants.TRIP_BY_ID_NOT_FOUND));

        if (tripToUpdate.getStatus() == TripStatus.IN_PROGRESS && updateTripRequest.getStatus() == TripStatus.BOOKED) {
            throw new InvalidOperationException(ErrorMessageConstants.TRIP_STATUS_CANNOT_UPDATE);
        } else if (tripToUpdate.getStatus() == TripStatus.FINISHED) {
            throw new InvalidOperationException(ErrorMessageConstants.TRIP_STATUS_CANNOT_UPDATE_FROM_FINISHED);
        }
        try {
            lock.writeLock().lock();
            if (updateTripRequest.getDestination() != null) {
                tripToUpdate.setDestination(updateTripRequest.getDestination());
            }
            if (updateTripRequest.getSource() != null) {
                tripToUpdate.setSource(updateTripRequest.getSource());
            }
            if (updateTripRequest.getStatus() == TripStatus.FINISHED) {
                tripToUpdate.setStatus(TripStatus.FINISHED);
                tripToUpdate.setEndTime(LocalDateTime.now());
                Cab cabToFree = cabService.getCabById(tripToUpdate.getCabId());
                cabToFree.setState(CabStatus.IDLE);
            }
            if (updateTripRequest.getStatus() != null) {
                tripToUpdate.setStatus(updateTripRequest.getStatus());
            }
            return tripToUpdate;

        } finally {
            lock.writeLock().unlock();
        }
    }

}
