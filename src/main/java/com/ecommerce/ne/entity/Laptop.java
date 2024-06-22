package com.ecommerce.ne.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
@Entity
@Data
@AllArgsConstructor
public class Laptop {
    @Id
    @GeneratedValue
    private int lapId;
    @NotBlank(message = "Brand must not be null")
    @Column(nullable = false, length = 100)
    private String brand;
    @NotBlank(message = "sn must not be null")
    @Column(nullable = false, length = 100)
    private String sn;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnore
    private Student student;

    public Laptop() {

    }
    public Laptop(String brand,String sn, Student student) {
        super();
        this.brand = brand;
        this.sn = sn;
        this.student = student;
    }
}

