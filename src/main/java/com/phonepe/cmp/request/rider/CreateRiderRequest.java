package com.phonepe.cmp.request.rider;

import com.phonepe.cmp.constants.ErrorMessageConstants;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateRiderRequest {
    @NotNull(message = ErrorMessageConstants.FIRST_NAME_NOT_NULL)
    @NotBlank(message = ErrorMessageConstants.DRIVER_NAME_NOT_BLANK)
    private String firstName;

    @NotNull(message = ErrorMessageConstants.LAST_NAME_NOT_NULL)
    @NotBlank(message = ErrorMessageConstants.LAST_NAME_NOT_BLANK)
    private String lastName;

    @Email(message = ErrorMessageConstants.EMAIL_ID_NOT_PROPER)
    @NotNull(message = ErrorMessageConstants.EMAIL_NOT_NULL)
    @NotBlank(message = ErrorMessageConstants.EMAIL_NOT_BLANK)
    private String email;

    @NotNull(message = ErrorMessageConstants.CONTACT_NOT_NULL)
    @NotBlank(message = ErrorMessageConstants.CONTACT_NOT_BLANK)
    @Length(min = 10, max = 15, message = ErrorMessageConstants.CONTACT_NOT_PROPER)
    private String contact;
}
