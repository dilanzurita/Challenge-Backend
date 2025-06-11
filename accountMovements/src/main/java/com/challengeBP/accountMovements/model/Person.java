package com.challengeBP.accountMovements.model;

import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Person {
    private String name;
    private String gender;
    private String identification;
    private String address;
    private String phone;
}
