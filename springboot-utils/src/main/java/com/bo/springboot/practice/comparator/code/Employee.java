package com.bo.springboot.practice.comparator.code;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class Employee {
    private String name;
    private int age;
    private double salary;
    private long mobile;

    // constructors, getters & setters
}