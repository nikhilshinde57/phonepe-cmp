package com.phonepe.cmp.request.cab;

import com.phonepe.cmp.constants.ErrorMessageConstants;
import com.phonepe.cmp.model.db.Location;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BookCabRequest {
    @NotNull(message = ErrorMessageConstants.CITY_ID_NOT_NULL)
    private int cityId;
    @NotNull(message = ErrorMessageConstants.RIDER_ID_NOT_NULL)
    private int riderId;

    private Location source;
    private Location destination;

}
