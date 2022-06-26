package com.phonepe.cmp.response;

import com.phonepe.cmp.model.db.Trip;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class BookCabResponse {

    Trip trip;
    private String error;

}
