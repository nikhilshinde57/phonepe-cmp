package com.phonepe.cmp.controller;

import com.phonepe.cmp.model.db.Rider;
import com.phonepe.cmp.request.rider.CreateRiderRequest;
import com.phonepe.cmp.service.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/riders")
public class RiderController {

    @Autowired
    RiderService riderService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Rider getRiderById(@PathVariable Long id) {
        return riderService.getRiderById(id);
    }

    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public List<Rider> getAllRiders() {
        return riderService.getAllRiders();
    }

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Rider createDriver(
            @Valid @RequestBody @NotNull CreateRiderRequest createRiderRequest) {
        return riderService.createRider(createRiderRequest);
    }
}
