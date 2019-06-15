package br.com.agroplanner.service.mapper;

import br.com.agroplanner.domain.*;
import br.com.agroplanner.service.dto.PropriedadeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Propriedade} and its DTO {@link PropriedadeDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PropriedadeMapper extends EntityMapper<PropriedadeDTO, Propriedade> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    PropriedadeDTO toDto(Propriedade propriedade);

    @Mapping(source = "userId", target = "user")
    Propriedade toEntity(PropriedadeDTO propriedadeDTO);

    default Propriedade fromId(Long id) {
        if (id == null) {
            return null;
        }
        Propriedade propriedade = new Propriedade();
        propriedade.setId(id);
        return propriedade;
    }
}
