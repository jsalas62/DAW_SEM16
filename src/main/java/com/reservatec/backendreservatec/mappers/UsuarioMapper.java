package com.reservatec.backendreservatec.mappers;

import com.reservatec.backendreservatec.domains.UsuarioTO;
import com.reservatec.backendreservatec.entities.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "nombres", target = "nombres")
    @Mapping(source = "codigoTecsup", target = "codigoTecsup")
    @Mapping(source = "rol", target = "rol")
    @Mapping(source = "estado", target = "estado")
    @Mapping(source = "carrera", target = "carrera")
    Usuario toEntity(UsuarioTO usuarioTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "nombres", target = "nombres")
    @Mapping(source = "codigoTecsup", target = "codigoTecsup")
    @Mapping(source = "rol", target = "rol")
    @Mapping(source = "estado", target = "estado")
    @Mapping(source = "carrera", target = "carrera")
    UsuarioTO toTO(Usuario usuario);


}