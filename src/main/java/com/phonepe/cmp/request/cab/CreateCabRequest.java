package com.phonepe.cmp.request.cab;

import com.phonepe.cmp.constants.ErrorMessageConstants;
import com.phonepe.cmp.enums.VehicleType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateCabRequest {

    @NotNull(message = ErrorMessageConstants.CAB_NUMBER_NOT_NULL)
    @NotBlank(message = ErrorMessageConstants.CAB_NUMBER_NOT_BLANK)
    @Length(min = 8, max = 8, message = ErrorMessageConstants.CAB_NUMBER_NOT_PROPER)
    private String number;

    @NotNull(message = ErrorMessageConstants.CAB_TYPE_NOT_NULL)
    private VehicleType type;

    @NotNull(message = ErrorMessageConstants.DRIVER_ID_NOT_NULL)
    private int driverId;

    @NotNull(message = ErrorMessageConstants.CITY_ID_NOT_NULL)
    private int cityId;

}
