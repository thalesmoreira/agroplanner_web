package br.com.agroplanner.service.mapper;

import br.com.agroplanner.domain.*;
import br.com.agroplanner.service.dto.PropriedadeCaracteristicaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PropriedadeCaracteristica} and its DTO {@link PropriedadeCaracteristicaDTO}.
 */
@Mapper(componentModel = "spring", uses = {PropriedadeMapper.class, CaracteristicaMapper.class})
public interface PropriedadeCaracteristicaMapper extends EntityMapper<PropriedadeCaracteristicaDTO, PropriedadeCaracteristica> {

    @Mapping(source = "propriedade.id", target = "propriedadeId")
    @Mapping(source = "propriedade.nome", target = "propriedadeNome")
    @Mapping(source = "caracteristica.id", target = "caracteristicaId")
    @Mapping(source = "caracteristica.nome", target = "caracteristicaNome")
    PropriedadeCaracteristicaDTO toDto(PropriedadeCaracteristica propriedadeCaracteristica);

    @Mapping(source = "propriedadeId", target = "propriedade")
    @Mapping(source = "caracteristicaId", target = "caracteristica")
    PropriedadeCaracteristica toEntity(PropriedadeCaracteristicaDTO propriedadeCaracteristicaDTO);

    default PropriedadeCaracteristica fromId(Long id) {
        if (id == null) {
            return null;
        }
        PropriedadeCaracteristica propriedadeCaracteristica = new PropriedadeCaracteristica();
        propriedadeCaracteristica.setId(id);
        return propriedadeCaracteristica;
    }
}
