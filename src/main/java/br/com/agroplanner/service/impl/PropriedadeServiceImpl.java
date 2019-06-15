package br.com.agroplanner.service.impl;

import br.com.agroplanner.service.PropriedadeService;
import br.com.agroplanner.domain.Propriedade;
import br.com.agroplanner.repository.PropriedadeRepository;
import br.com.agroplanner.service.dto.PropriedadeDTO;
import br.com.agroplanner.service.mapper.PropriedadeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Propriedade}.
 */
@Service
@Transactional
public class PropriedadeServiceImpl implements PropriedadeService {

    private final Logger log = LoggerFactory.getLogger(PropriedadeServiceImpl.class);

    private final PropriedadeRepository propriedadeRepository;

    private final PropriedadeMapper propriedadeMapper;

    public PropriedadeServiceImpl(PropriedadeRepository propriedadeRepository, PropriedadeMapper propriedadeMapper) {
        this.propriedadeRepository = propriedadeRepository;
        this.propriedadeMapper = propriedadeMapper;
    }

    /**
     * Save a propriedade.
     *
     * @param propriedadeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PropriedadeDTO save(PropriedadeDTO propriedadeDTO) {
        log.debug("Request to save Propriedade : {}", propriedadeDTO);
        Propriedade propriedade = propriedadeMapper.toEntity(propriedadeDTO);
        propriedade = propriedadeRepository.save(propriedade);
        return propriedadeMapper.toDto(propriedade);
    }

    /**
     * Get all the propriedades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PropriedadeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Propriedades");
        return propriedadeRepository.findAll(pageable)
            .map(propriedadeMapper::toDto);
    }


    /**
     * Get one propriedade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PropriedadeDTO> findOne(Long id) {
        log.debug("Request to get Propriedade : {}", id);
        return propriedadeRepository.findById(id)
            .map(propriedadeMapper::toDto);
    }

    /**
     * Delete the propriedade by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Propriedade : {}", id);
        propriedadeRepository.deleteById(id);
    }
}
