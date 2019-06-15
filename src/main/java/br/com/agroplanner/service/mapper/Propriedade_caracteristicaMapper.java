package br.com.agroplanner.service.mapper;

import br.com.agroplanner.domain.*;
import br.com.agroplanner.service.dto.Propriedade_caracteristicaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Propriedade_caracteristica} and its DTO {@link Propriedade_caracteristicaDTO}.
 */
@Mapper(componentModel = "spring", uses = {PropriedadeMapper.class, CaracteristicaMapper.class})
public interface Propriedade_caracteristicaMapper extends EntityMapper<Propriedade_caracteristicaDTO, Propriedade_caracteristica> {

    @Mapping(source = "propriedade.id", target = "propriedadeId")
    @Mapping(source = "propriedade.nome", target = "propriedadeNome")
    @Mapping(source = "caracteristica.id", target = "caracteristicaId")
    @Mapping(source = "caracteristica.nome", target = "caracteristicaNome")
    Propriedade_caracteristicaDTO toDto(Propriedade_caracteristica propriedade_caracteristica);

    @Mapping(source = "propriedadeId", target = "propriedade")
    @Mapping(source = "caracteristicaId", target = "caracteristica")
    Propriedade_caracteristica toEntity(Propriedade_caracteristicaDTO propriedade_caracteristicaDTO);

    default Propriedade_caracteristica fromId(Long id) {
        if (id == null) {
            return null;
        }
        Propriedade_caracteristica propriedade_caracteristica = new Propriedade_caracteristica();
        propriedade_caracteristica.setId(id);
        return propriedade_caracteristica;
    }
}
