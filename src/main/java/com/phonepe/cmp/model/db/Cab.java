package com.phonepe.cmp.model.db;

import com.phonepe.cmp.enums.CabStatus;
import com.phonepe.cmp.enums.VehicleType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class Cab extends Vehicle {
    @Setter
    private CabStatus state;
    @Setter
    private int driverId;
    @Setter
    private int cityId;

    @Setter
    private LocalDateTime startTime;
    @Setter
    private LocalDateTime endTime;

    public Cab(int id, String number, VehicleType type, CabStatus state, int driverId, int cityId) {
        super(id, number, type);
        this.state = state;
        this.driverId = driverId;
        this.cityId = cityId;
        this.startTime = LocalDateTime.now();
    }
}
