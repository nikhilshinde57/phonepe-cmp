package com.phonepe.cmp.request.trip;

import com.phonepe.cmp.constants.ErrorMessageConstants;
import com.phonepe.cmp.enums.TripStatus;
import com.phonepe.cmp.model.db.Location;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateTripRequest {

    @NotNull(message = ErrorMessageConstants.TRIP_ID_NOT_NULL)
    private int tripId;
    private Location source;
    private Location destination;
    private TripStatus status;

    public UpdateTripRequest(int id, TripStatus status) {
        this.tripId = id;
        this.status = status;
    }
}
