package com.reservatec.backendreservatec.services.impl;

import com.reservatec.backendreservatec.entities.Horario;
import com.reservatec.backendreservatec.repositories.HorarioRepository;
import com.reservatec.backendreservatec.services.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorarioServiceImpl implements HorarioService {


    private final HorarioRepository horarioRepository;

    public HorarioServiceImpl(HorarioRepository horarioRepository) {
        this.horarioRepository = horarioRepository;
    }

    @Override
    public List<Horario> findAllHorarios() {
        return horarioRepository.findAll();
    }
}