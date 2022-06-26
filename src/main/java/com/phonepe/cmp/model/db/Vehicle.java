package com.phonepe.cmp.model.db;

import com.phonepe.cmp.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public abstract class Vehicle {
    @Setter
    private int id;
    private String number;
    @Setter
    private VehicleType type;
}
