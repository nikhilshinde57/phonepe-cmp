package com.phonepe.cmp.model.db;

import com.phonepe.cmp.enums.CabStatus;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class CabHistory {
    private int cabId;
    private String cabNumber;
    private CabStatus status;
    private LocalDateTime timestamp;
}
