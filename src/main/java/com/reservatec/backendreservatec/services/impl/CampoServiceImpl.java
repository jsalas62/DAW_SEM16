package com.reservatec.backendreservatec.services.impl;

import com.reservatec.backendreservatec.entities.Campo;
import com.reservatec.backendreservatec.repositories.CampoRepository;
import com.reservatec.backendreservatec.services.CampoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampoServiceImpl implements CampoService {

    private final CampoRepository campoRepository;


    @Autowired
    public CampoServiceImpl(
            CampoRepository campoRepository) {
        this.campoRepository = campoRepository;
    }

    @Override
    public List<Campo> findAllCampos() {
        return campoRepository.findAll();
    }
}