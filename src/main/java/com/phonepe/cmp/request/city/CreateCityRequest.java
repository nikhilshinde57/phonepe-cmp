package com.phonepe.cmp.request.city;

import com.phonepe.cmp.constants.ErrorMessageConstants;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateCityRequest {

    @NotNull(message = ErrorMessageConstants.CITY_NAME_NOT_NULL)
    @NotBlank(message = ErrorMessageConstants.CITY_NAME_NOT_BLANK)
    private String name;
}
