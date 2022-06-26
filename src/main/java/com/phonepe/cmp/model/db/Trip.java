package com.phonepe.cmp.model.db;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.phonepe.cmp.enums.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Trip {
    private int id;
    private int riderId;
    private int cabId;
    private int cityId;
    private Location source;
    private Location destination;
    @Setter
    private TripStatus status;
    @Setter
    private double price;
    private LocalDateTime startTime;
    @Setter
    private LocalDateTime endTime;

    public Trip(int id, int riderId, int cabId, int cityId, Location source, Location destination, double price, LocalDateTime startTime) {
        this.id = id;
        this.riderId = riderId;
        this.cabId = cabId;
        this.cityId = cityId;
        this.source = source;
        this.destination = destination;
        this.status = TripStatus.BOOKED;
        this.price = price;
        this.startTime = startTime;
    }
}
