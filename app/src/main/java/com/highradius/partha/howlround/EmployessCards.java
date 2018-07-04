package com.highradius.partha.howlround;

public class EmployessCards {
    String department;
    String designation;
    String email;
    String image_url;
    Long level;
    String name;
    Long positive;
    Long negative;

    public EmployessCards() {
    }

    public EmployessCards(String department, String designation, String email, String image_url, Long level, String name, Long positive, Long negative) {
        this.department = department;
        this.designation = designation;
        this.email = email;
        this.image_url = image_url;
        this.level = level;
        this.name = name;
        this.positive = positive;
        this.negative = negative;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPositive() {
        return positive;
    }

    public void setPositive(Long positive) {
        this.positive = positive;
    }

    public Long getNegative() {
        return negative;
    }

    public void setNegative(Long negative) {
        this.negative = negative;
    }
}