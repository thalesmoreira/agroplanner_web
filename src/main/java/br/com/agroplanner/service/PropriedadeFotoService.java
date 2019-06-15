package br.com.agroplanner.service;

import br.com.agroplanner.service.dto.PropriedadeFotoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link br.com.agroplanner.domain.PropriedadeFoto}.
 */
public interface PropriedadeFotoService {

    /**
     * Save a propriedadeFoto.
     *
     * @param propriedadeFotoDTO the entity to save.
     * @return the persisted entity.
     */
    PropriedadeFotoDTO save(PropriedadeFotoDTO propriedadeFotoDTO);

    /**
     * Get all the propriedadeFotos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PropriedadeFotoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" propriedadeFoto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PropriedadeFotoDTO> findOne(Long id);

    /**
     * Delete the "id" propriedadeFoto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
