package com.reservatec.backendreservatec.mappers;

import com.reservatec.backendreservatec.domains.ReservaTO;
import com.reservatec.backendreservatec.entities.Reserva;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface ReservaMapper {

    ReservaMapper INSTANCE = Mappers.getMapper(ReservaMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "usuario", target = "usuario")
    @Mapping(source = "campo", target = "campo")
    @Mapping(source = "fecha", target = "fecha")
    @Mapping(source = "horario", target = "horario")
    @Mapping(source = "comentario", target = "comentario")
    @Mapping(source = "estado", target = "estado")
    Reserva toEntity(ReservaTO reservaTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "usuario", target = "usuario")
    @Mapping(source = "campo", target = "campo")
    @Mapping(source = "fecha", target = "fecha")
    @Mapping(source = "horario", target = "horario")
    @Mapping(source = "comentario", target = "comentario")
    @Mapping(source = "estado", target = "estado")
    ReservaTO toTO(Reserva reserva);



}