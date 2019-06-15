package br.com.agroplanner.service.impl;

import br.com.agroplanner.service.Propriedade_caracteristicaService;
import br.com.agroplanner.domain.Propriedade_caracteristica;
import br.com.agroplanner.repository.Propriedade_caracteristicaRepository;
import br.com.agroplanner.service.dto.Propriedade_caracteristicaDTO;
import br.com.agroplanner.service.mapper.Propriedade_caracteristicaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Propriedade_caracteristica}.
 */
@Service
@Transactional
public class Propriedade_caracteristicaServiceImpl implements Propriedade_caracteristicaService {

    private final Logger log = LoggerFactory.getLogger(Propriedade_caracteristicaServiceImpl.class);

    private final Propriedade_caracteristicaRepository propriedade_caracteristicaRepository;

    private final Propriedade_caracteristicaMapper propriedade_caracteristicaMapper;

    public Propriedade_caracteristicaServiceImpl(Propriedade_caracteristicaRepository propriedade_caracteristicaRepository, Propriedade_caracteristicaMapper propriedade_caracteristicaMapper) {
        this.propriedade_caracteristicaRepository = propriedade_caracteristicaRepository;
        this.propriedade_caracteristicaMapper = propriedade_caracteristicaMapper;
    }

    /**
     * Save a propriedade_caracteristica.
     *
     * @param propriedade_caracteristicaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Propriedade_caracteristicaDTO save(Propriedade_caracteristicaDTO propriedade_caracteristicaDTO) {
        log.debug("Request to save Propriedade_caracteristica : {}", propriedade_caracteristicaDTO);
        Propriedade_caracteristica propriedade_caracteristica = propriedade_caracteristicaMapper.toEntity(propriedade_caracteristicaDTO);
        propriedade_caracteristica = propriedade_caracteristicaRepository.save(propriedade_caracteristica);
        return propriedade_caracteristicaMapper.toDto(propriedade_caracteristica);
    }

    /**
     * Get all the propriedade_caracteristicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Propriedade_caracteristicaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Propriedade_caracteristicas");
        return propriedade_caracteristicaRepository.findAll(pageable)
            .map(propriedade_caracteristicaMapper::toDto);
    }


    /**
     * Get one propriedade_caracteristica by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Propriedade_caracteristicaDTO> findOne(Long id) {
        log.debug("Request to get Propriedade_caracteristica : {}", id);
        return propriedade_caracteristicaRepository.findById(id)
            .map(propriedade_caracteristicaMapper::toDto);
    }

    /**
     * Delete the propriedade_caracteristica by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Propriedade_caracteristica : {}", id);
        propriedade_caracteristicaRepository.deleteById(id);
    }
}
