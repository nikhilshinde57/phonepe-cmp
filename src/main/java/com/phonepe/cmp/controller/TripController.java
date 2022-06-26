package com.phonepe.cmp.controller;

import com.phonepe.cmp.model.db.Trip;
import com.phonepe.cmp.request.trip.UpdateTripRequest;
import com.phonepe.cmp.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    @Autowired
    TripService tripService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Trip getTripById(@PathVariable Long id) {
        return tripService.getTripById(id);
    }

    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public List<Trip> getAllTrip() {
        return tripService.getAllTrips();
    }

    @PatchMapping(value = "/{id}")
    public Trip updateTrip(
            @PathVariable Long id,
            @NotNull @Valid @RequestBody UpdateTripRequest updateTripRequest) {
        return tripService.updateTrip(updateTripRequest);
    }
}
