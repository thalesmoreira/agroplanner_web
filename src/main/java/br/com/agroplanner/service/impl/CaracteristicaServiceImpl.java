package br.com.agroplanner.service.impl;

import br.com.agroplanner.service.CaracteristicaService;
import br.com.agroplanner.domain.Caracteristica;
import br.com.agroplanner.repository.CaracteristicaRepository;
import br.com.agroplanner.service.dto.CaracteristicaDTO;
import br.com.agroplanner.service.mapper.CaracteristicaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Caracteristica}.
 */
@Service
@Transactional
public class CaracteristicaServiceImpl implements CaracteristicaService {

    private final Logger log = LoggerFactory.getLogger(CaracteristicaServiceImpl.class);

    private final CaracteristicaRepository caracteristicaRepository;

    private final CaracteristicaMapper caracteristicaMapper;

    public CaracteristicaServiceImpl(CaracteristicaRepository caracteristicaRepository, CaracteristicaMapper caracteristicaMapper) {
        this.caracteristicaRepository = caracteristicaRepository;
        this.caracteristicaMapper = caracteristicaMapper;
    }

    /**
     * Save a caracteristica.
     *
     * @param caracteristicaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CaracteristicaDTO save(CaracteristicaDTO caracteristicaDTO) {
        log.debug("Request to save Caracteristica : {}", caracteristicaDTO);
        Caracteristica caracteristica = caracteristicaMapper.toEntity(caracteristicaDTO);
        caracteristica = caracteristicaRepository.save(caracteristica);
        return caracteristicaMapper.toDto(caracteristica);
    }

    /**
     * Get all the caracteristicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CaracteristicaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Caracteristicas");
        return caracteristicaRepository.findAll(pageable)
            .map(caracteristicaMapper::toDto);
    }


    /**
     * Get one caracteristica by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CaracteristicaDTO> findOne(Long id) {
        log.debug("Request to get Caracteristica : {}", id);
        return caracteristicaRepository.findById(id)
            .map(caracteristicaMapper::toDto);
    }

    /**
     * Delete the caracteristica by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Caracteristica : {}", id);
        caracteristicaRepository.deleteById(id);
    }
}
