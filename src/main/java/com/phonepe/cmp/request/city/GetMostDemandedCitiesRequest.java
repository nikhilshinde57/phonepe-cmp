package com.phonepe.cmp.request.city;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetMostDemandedCitiesRequest {
    private boolean byTripCount;
    private boolean byDateRange;
    private LocalDateTime from;
    private LocalDateTime to;

}
