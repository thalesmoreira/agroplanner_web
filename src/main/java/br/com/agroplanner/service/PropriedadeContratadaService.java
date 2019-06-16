package br.com.agroplanner.service;

import br.com.agroplanner.service.dto.PropriedadeContratadaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link br.com.agroplanner.domain.PropriedadeContratada}.
 */
public interface PropriedadeContratadaService {

    /**
     * Save a propriedadeContratada.
     *
     * @param propriedadeContratadaDTO the entity to save.
     * @return the persisted entity.
     */
    PropriedadeContratadaDTO save(PropriedadeContratadaDTO propriedadeContratadaDTO);

    /**
     * Get all the propriedadeContratadas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PropriedadeContratadaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" propriedadeContratada.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PropriedadeContratadaDTO> findOne(Long id);

    /**
     * Delete the "id" propriedadeContratada.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
