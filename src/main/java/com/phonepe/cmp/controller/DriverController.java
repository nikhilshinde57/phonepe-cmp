package com.phonepe.cmp.controller;

import com.phonepe.cmp.model.db.Driver;
import com.phonepe.cmp.request.driver.CreateDriverRequest;
import com.phonepe.cmp.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    @Autowired
    DriverService driverService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Driver getDriverById(@PathVariable int id) {
        return driverService.getDriverById(id);
    }

    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public List<Driver> getAllCabs() {
        return driverService.getAllDrivers();
    }

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Driver createDriver(
            @Valid @RequestBody @NotNull CreateDriverRequest createDriverRequest) {
        return driverService.createDriver(createDriverRequest);
    }
}
