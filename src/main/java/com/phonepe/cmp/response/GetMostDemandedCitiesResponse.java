package com.phonepe.cmp.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GetMostDemandedCitiesResponse {
    private int cityId;
    private String cityName;
    private int tripCount;

}
