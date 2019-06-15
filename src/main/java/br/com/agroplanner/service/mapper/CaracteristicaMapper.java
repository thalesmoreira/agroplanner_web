package br.com.agroplanner.service.mapper;

import br.com.agroplanner.domain.*;
import br.com.agroplanner.service.dto.CaracteristicaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Caracteristica} and its DTO {@link CaracteristicaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CaracteristicaMapper extends EntityMapper<CaracteristicaDTO, Caracteristica> {



    default Caracteristica fromId(Long id) {
        if (id == null) {
            return null;
        }
        Caracteristica caracteristica = new Caracteristica();
        caracteristica.setId(id);
        return caracteristica;
    }
}
