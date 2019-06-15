package br.com.agroplanner.service;

import br.com.agroplanner.service.dto.CaracteristicaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link br.com.agroplanner.domain.Caracteristica}.
 */
public interface CaracteristicaService {

    /**
     * Save a caracteristica.
     *
     * @param caracteristicaDTO the entity to save.
     * @return the persisted entity.
     */
    CaracteristicaDTO save(CaracteristicaDTO caracteristicaDTO);

    /**
     * Get all the caracteristicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CaracteristicaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" caracteristica.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CaracteristicaDTO> findOne(Long id);

    /**
     * Delete the "id" caracteristica.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
