package com.phonepe.cmp.service;

import com.phonepe.cmp.constants.ErrorMessageConstants;
import com.phonepe.cmp.model.db.Rider;
import com.phonepe.cmp.request.rider.CreateRiderRequest;
import com.phonepe.cmp.service.exception.EntityAlreadyExistsException;
import com.phonepe.cmp.service.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class RiderService {
    public static final Logger LOGGER = LoggerFactory.getLogger(CityService.class);
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    private static int nextRiderIdToCreate = 1;
    List<Rider> riderList;

    @Autowired
    public RiderService() {
        this.riderList = new ArrayList<>();
    }

    public Rider getRiderById(Long id) {
        return riderList.stream()
                .filter(d -> d.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessageConstants.RIDER_BY_ID_NOT_FOUND));
    }

    public List<Rider> getAllRiders() {
        return riderList;
    }

    public Rider createRider(CreateRiderRequest createRiderRequest) {
        Rider existingRider = riderList.stream().filter(c -> c.getEmail().equals(createRiderRequest.getContact()) || c.getContact().equals(createRiderRequest.getContact())).findFirst().orElse(null);
        if (existingRider != null) {
            throw new EntityAlreadyExistsException(ErrorMessageConstants.DRIVER_DUPLICATE_EMAIL_OR_CONTACT);
        }
        try {
            lock.writeLock().lock();
            Rider newRider = new Rider(createRiderRequest.getFirstName(), createRiderRequest.getLastName(), createRiderRequest.getEmail(), createRiderRequest.getContact(), nextRiderIdToCreate);
            riderList.add(newRider);
            nextRiderIdToCreate++;
            return newRider;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
