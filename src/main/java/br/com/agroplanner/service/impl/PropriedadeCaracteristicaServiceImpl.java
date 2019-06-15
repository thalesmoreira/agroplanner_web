package br.com.agroplanner.service.impl;

import br.com.agroplanner.service.PropriedadeCaracteristicaService;
import br.com.agroplanner.domain.PropriedadeCaracteristica;
import br.com.agroplanner.repository.PropriedadeCaracteristicaRepository;
import br.com.agroplanner.service.dto.PropriedadeCaracteristicaDTO;
import br.com.agroplanner.service.mapper.PropriedadeCaracteristicaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PropriedadeCaracteristica}.
 */
@Service
@Transactional
public class PropriedadeCaracteristicaServiceImpl implements PropriedadeCaracteristicaService {

    private final Logger log = LoggerFactory.getLogger(PropriedadeCaracteristicaServiceImpl.class);

    private final PropriedadeCaracteristicaRepository propriedadeCaracteristicaRepository;

    private final PropriedadeCaracteristicaMapper propriedadeCaracteristicaMapper;

    public PropriedadeCaracteristicaServiceImpl(PropriedadeCaracteristicaRepository propriedadeCaracteristicaRepository, PropriedadeCaracteristicaMapper propriedadeCaracteristicaMapper) {
        this.propriedadeCaracteristicaRepository = propriedadeCaracteristicaRepository;
        this.propriedadeCaracteristicaMapper = propriedadeCaracteristicaMapper;
    }

    /**
     * Save a propriedadeCaracteristica.
     *
     * @param propriedadeCaracteristicaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PropriedadeCaracteristicaDTO save(PropriedadeCaracteristicaDTO propriedadeCaracteristicaDTO) {
        log.debug("Request to save PropriedadeCaracteristica : {}", propriedadeCaracteristicaDTO);
        PropriedadeCaracteristica propriedadeCaracteristica = propriedadeCaracteristicaMapper.toEntity(propriedadeCaracteristicaDTO);
        propriedadeCaracteristica = propriedadeCaracteristicaRepository.save(propriedadeCaracteristica);
        return propriedadeCaracteristicaMapper.toDto(propriedadeCaracteristica);
    }

    /**
     * Get all the propriedadeCaracteristicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PropriedadeCaracteristicaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PropriedadeCaracteristicas");
        return propriedadeCaracteristicaRepository.findAll(pageable)
            .map(propriedadeCaracteristicaMapper::toDto);
    }


    /**
     * Get one propriedadeCaracteristica by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PropriedadeCaracteristicaDTO> findOne(Long id) {
        log.debug("Request to get PropriedadeCaracteristica : {}", id);
        return propriedadeCaracteristicaRepository.findById(id)
            .map(propriedadeCaracteristicaMapper::toDto);
    }

    /**
     * Delete the propriedadeCaracteristica by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PropriedadeCaracteristica : {}", id);
        propriedadeCaracteristicaRepository.deleteById(id);
    }
}
