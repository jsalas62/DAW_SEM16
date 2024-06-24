package com.reservatec.backendreservatec.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter @Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id_usuario")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "nombres", nullable = false)
    private String nombres;

    @Column(name = "codigo_tecsup", nullable = false)
    private String codigoTecsup;

    @ManyToOne
    @JoinColumn(name = "fk_id_rol", nullable = false)
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "fk_id_estado", nullable = false)
    private Estado estado;

    @ManyToOne
    @JoinColumn(name = "fk_id_carrera")
    private Carrera carrera;
}
