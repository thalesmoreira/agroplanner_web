package br.com.agroplanner.service;

import br.com.agroplanner.service.dto.PropriedadeCaracteristicaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link br.com.agroplanner.domain.PropriedadeCaracteristica}.
 */
public interface PropriedadeCaracteristicaService {

    /**
     * Save a propriedadeCaracteristica.
     *
     * @param propriedadeCaracteristicaDTO the entity to save.
     * @return the persisted entity.
     */
    PropriedadeCaracteristicaDTO save(PropriedadeCaracteristicaDTO propriedadeCaracteristicaDTO);

    /**
     * Get all the propriedadeCaracteristicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PropriedadeCaracteristicaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" propriedadeCaracteristica.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PropriedadeCaracteristicaDTO> findOne(Long id);

    /**
     * Delete the "id" propriedadeCaracteristica.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
