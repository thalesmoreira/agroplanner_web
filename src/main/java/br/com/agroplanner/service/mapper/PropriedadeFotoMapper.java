package br.com.agroplanner.service.mapper;

import br.com.agroplanner.domain.*;
import br.com.agroplanner.service.dto.PropriedadeFotoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PropriedadeFoto} and its DTO {@link PropriedadeFotoDTO}.
 */
@Mapper(componentModel = "spring", uses = {PropriedadeMapper.class})
public interface PropriedadeFotoMapper extends EntityMapper<PropriedadeFotoDTO, PropriedadeFoto> {

    @Mapping(source = "propriedade.id", target = "propriedadeId")
    @Mapping(source = "propriedade.nome", target = "propriedadeNome")
    PropriedadeFotoDTO toDto(PropriedadeFoto propriedadeFoto);

    @Mapping(source = "propriedadeId", target = "propriedade")
    PropriedadeFoto toEntity(PropriedadeFotoDTO propriedadeFotoDTO);

    default PropriedadeFoto fromId(Long id) {
        if (id == null) {
            return null;
        }
        PropriedadeFoto propriedadeFoto = new PropriedadeFoto();
        propriedadeFoto.setId(id);
        return propriedadeFoto;
    }
}
