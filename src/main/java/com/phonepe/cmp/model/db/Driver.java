package com.phonepe.cmp.model.db;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Driver extends Person {
    private int id;

    public Driver(String firstName, String lastName, String email, String contact, int id) {
        super(firstName, lastName, email, contact);
        this.id = id;
    }
}
