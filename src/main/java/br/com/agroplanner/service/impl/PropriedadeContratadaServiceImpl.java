package br.com.agroplanner.service.impl;

import br.com.agroplanner.service.PropriedadeContratadaService;
import br.com.agroplanner.domain.PropriedadeContratada;
import br.com.agroplanner.repository.PropriedadeContratadaRepository;
import br.com.agroplanner.service.dto.PropriedadeContratadaDTO;
import br.com.agroplanner.service.mapper.PropriedadeContratadaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PropriedadeContratada}.
 */
@Service
@Transactional
public class PropriedadeContratadaServiceImpl implements PropriedadeContratadaService {

    private final Logger log = LoggerFactory.getLogger(PropriedadeContratadaServiceImpl.class);

    private final PropriedadeContratadaRepository propriedadeContratadaRepository;

    private final PropriedadeContratadaMapper propriedadeContratadaMapper;

    public PropriedadeContratadaServiceImpl(PropriedadeContratadaRepository propriedadeContratadaRepository, PropriedadeContratadaMapper propriedadeContratadaMapper) {
        this.propriedadeContratadaRepository = propriedadeContratadaRepository;
        this.propriedadeContratadaMapper = propriedadeContratadaMapper;
    }

    /**
     * Save a propriedadeContratada.
     *
     * @param propriedadeContratadaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PropriedadeContratadaDTO save(PropriedadeContratadaDTO propriedadeContratadaDTO) {
        log.debug("Request to save PropriedadeContratada : {}", propriedadeContratadaDTO);
        PropriedadeContratada propriedadeContratada = propriedadeContratadaMapper.toEntity(propriedadeContratadaDTO);
        propriedadeContratada = propriedadeContratadaRepository.save(propriedadeContratada);
        return propriedadeContratadaMapper.toDto(propriedadeContratada);
    }

    /**
     * Get all the propriedadeContratadas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PropriedadeContratadaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PropriedadeContratadas");
        return propriedadeContratadaRepository.findAll(pageable)
            .map(propriedadeContratadaMapper::toDto);
    }


    /**
     * Get one propriedadeContratada by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PropriedadeContratadaDTO> findOne(Long id) {
        log.debug("Request to get PropriedadeContratada : {}", id);
        return propriedadeContratadaRepository.findById(id)
            .map(propriedadeContratadaMapper::toDto);
    }

    /**
     * Delete the propriedadeContratada by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PropriedadeContratada : {}", id);
        propriedadeContratadaRepository.deleteById(id);
    }
}
