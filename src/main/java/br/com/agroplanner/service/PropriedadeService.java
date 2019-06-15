package br.com.agroplanner.service;

import br.com.agroplanner.service.dto.PropriedadeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link br.com.agroplanner.domain.Propriedade}.
 */
public interface PropriedadeService {

    /**
     * Save a propriedade.
     *
     * @param propriedadeDTO the entity to save.
     * @return the persisted entity.
     */
    PropriedadeDTO save(PropriedadeDTO propriedadeDTO);

    /**
     * Get all the propriedades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PropriedadeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" propriedade.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PropriedadeDTO> findOne(Long id);

    /**
     * Delete the "id" propriedade.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
