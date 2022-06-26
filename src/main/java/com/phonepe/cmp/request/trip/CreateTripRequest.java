package com.phonepe.cmp.request.trip;

import com.phonepe.cmp.constants.ErrorMessageConstants;
import com.phonepe.cmp.model.db.Location;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateTripRequest {

    @NotNull(message = ErrorMessageConstants.RIDER_ID_NOT_NULL)
    private int riderId;
    @NotNull(message = ErrorMessageConstants.CAB_ID_NOT_NULL)
    private int cabId;
    @NotNull(message = ErrorMessageConstants.CITY_ID_NOT_NULL)
    private int cityId;
    private Location source;
    private Location destination;
    private double price;
}
