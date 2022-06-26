package com.phonepe.cmp.model.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public abstract class Person implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String contact;

}
