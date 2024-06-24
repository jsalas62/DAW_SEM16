package com.reservatec.backendreservatec.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "estado")
@Getter @Setter
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id_estado")
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column
    private String descripcion;
}
