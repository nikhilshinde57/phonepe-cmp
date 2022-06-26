package com.phonepe.cmp.request.cab;

import com.phonepe.cmp.constants.ErrorMessageConstants;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateCabRequest {

    @NotNull(message = ErrorMessageConstants.CAB_ID_NOT_NULL)
    private int cabId;
    @NotNull(message = ErrorMessageConstants.CITY_ID_NOT_NULL)
    private int cityId;
}
