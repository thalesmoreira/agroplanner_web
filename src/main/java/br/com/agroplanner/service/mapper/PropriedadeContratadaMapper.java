package br.com.agroplanner.service.mapper;

import br.com.agroplanner.domain.*;
import br.com.agroplanner.service.dto.PropriedadeContratadaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PropriedadeContratada} and its DTO {@link PropriedadeContratadaDTO}.
 */
@Mapper(componentModel = "spring", uses = {PropriedadeMapper.class, UserMapper.class})
public interface PropriedadeContratadaMapper extends EntityMapper<PropriedadeContratadaDTO, PropriedadeContratada> {

    @Mapping(source = "propriedade.id", target = "propriedadeId")
    @Mapping(source = "propriedade.nome", target = "propriedadeNome")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    PropriedadeContratadaDTO toDto(PropriedadeContratada propriedadeContratada);

    @Mapping(source = "propriedadeId", target = "propriedade")
    @Mapping(source = "userId", target = "user")
    PropriedadeContratada toEntity(PropriedadeContratadaDTO propriedadeContratadaDTO);

    default PropriedadeContratada fromId(Long id) {
        if (id == null) {
            return null;
        }
        PropriedadeContratada propriedadeContratada = new PropriedadeContratada();
        propriedadeContratada.setId(id);
        return propriedadeContratada;
    }
}
