package com.phonepe.cmp.request.cab;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CabStatisticsRequest {
    private int cabId;
    private LocalDateTime from;
    private LocalDateTime to;
}
