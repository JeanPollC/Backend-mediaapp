package com.mitocode.model;


import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idPatient;

    @Column(nullable = false, length = 70)
    private String firstName;

    @Column(nullable = false, length = 70)
    private String lastName;

    @Column(nullable = false, length = 80)
    private String dni;

    @Column(nullable = false, length = 150)
    private String address;

    @Column(nullable = false, length = 10)
    private String phone;

    @Column(nullable = false, length = 55)
    private String email;
}