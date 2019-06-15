package br.com.agroplanner.service;

import br.com.agroplanner.service.dto.Propriedade_caracteristicaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link br.com.agroplanner.domain.Propriedade_caracteristica}.
 */
public interface Propriedade_caracteristicaService {

    /**
     * Save a propriedade_caracteristica.
     *
     * @param propriedade_caracteristicaDTO the entity to save.
     * @return the persisted entity.
     */
    Propriedade_caracteristicaDTO save(Propriedade_caracteristicaDTO propriedade_caracteristicaDTO);

    /**
     * Get all the propriedade_caracteristicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Propriedade_caracteristicaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" propriedade_caracteristica.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Propriedade_caracteristicaDTO> findOne(Long id);

    /**
     * Delete the "id" propriedade_caracteristica.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
