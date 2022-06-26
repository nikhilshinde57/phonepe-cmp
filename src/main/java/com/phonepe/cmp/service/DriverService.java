package com.phonepe.cmp.service;

import com.phonepe.cmp.constants.ErrorMessageConstants;
import com.phonepe.cmp.model.db.Driver;
import com.phonepe.cmp.request.driver.CreateDriverRequest;
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
public class DriverService {
    public static final Logger LOGGER = LoggerFactory.getLogger(CityService.class);
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    private static int nextDriverIdToCreate = 1;
    List<Driver> driverList;

    @Autowired
    public DriverService() {
        this.driverList = new ArrayList<>();
    }

    public Driver getDriverById(int id) {
        return driverList.stream()
                .filter(d -> d.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessageConstants.DRIVER_BY_ID_NOT_FOUND));
    }

    public List<Driver> getAllDrivers() {
        return driverList;
    }

    public Driver createDriver(CreateDriverRequest createDriverRequest) {
        Driver existingDriver = driverList.stream().filter(c -> c.getEmail().equals(createDriverRequest.getContact()) || c.getContact().equals(createDriverRequest.getContact())).findFirst().orElse(null);
        if (existingDriver != null) {
            throw new EntityAlreadyExistsException(ErrorMessageConstants.DRIVER_DUPLICATE_EMAIL_OR_CONTACT);
        }
        try {
            lock.writeLock().lock();
            Driver newDriver = new Driver(createDriverRequest.getFirstName(), createDriverRequest.getLastName(), createDriverRequest.getEmail(), createDriverRequest.getContact(), nextDriverIdToCreate);
            driverList.add(newDriver);
            nextDriverIdToCreate++;
            return newDriver;
        } finally {
            lock.writeLock().unlock();
        }
    }

}
