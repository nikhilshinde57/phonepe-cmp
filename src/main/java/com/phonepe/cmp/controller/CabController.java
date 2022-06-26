package com.phonepe.cmp.controller;

import com.phonepe.cmp.model.db.Cab;
import com.phonepe.cmp.request.cab.BookCabRequest;
import com.phonepe.cmp.request.cab.CabStatisticsRequest;
import com.phonepe.cmp.request.cab.CreateCabRequest;
import com.phonepe.cmp.request.cab.UpdateCabRequest;
import com.phonepe.cmp.response.BookCabResponse;
import com.phonepe.cmp.service.CabService;
import com.phonepe.cmp.service.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/cabs")
public class CabController {

    @Autowired
    CabService cabService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cab getCabById(@PathVariable int id) {
        return cabService.getCabById(id);
    }

    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public List<Cab> getAllCabs() {
        return cabService.getAllCabs();
    }

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Cab createCab(
            @Valid @RequestBody CreateCabRequest createCabRequest) {
        return cabService.createCab(createCabRequest);
    }

    @PostMapping(value = "/book")
    @ResponseStatus(HttpStatus.CREATED)
    public BookCabResponse bookCab(
            @Valid @RequestBody @NotNull BookCabRequest bookCabRequest) {
        return cabService.bookCab(bookCabRequest);
    }

    @PostMapping(value = "/{id}/statistics/idle")
    @ResponseStatus(HttpStatus.CREATED)
    public long getCabIdleStatistics(
            @Valid @RequestBody @NotNull CabStatisticsRequest cabStatisticsRequest) throws BadRequestException {
        return cabService.getCabIdleStatistics(cabStatisticsRequest);
    }

    @PatchMapping(value = "/{id}")
    public Cab updateCab(@PathVariable Long id, @NotNull @Valid @RequestBody UpdateCabRequest updateCabRequest) {
        return cabService.updateCab(updateCabRequest);
    }

}
