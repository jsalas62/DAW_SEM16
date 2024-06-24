package com.reservatec.backendreservatec.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "campo")
@Getter @Setter
public class Campo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id_campo")
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Integer aforo;

    @ManyToOne
    @JoinColumn(name = "fk_id_estado", nullable = false)
    private Estado estado;
}
