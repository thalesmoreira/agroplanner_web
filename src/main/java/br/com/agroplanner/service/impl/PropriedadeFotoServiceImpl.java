package br.com.agroplanner.service.impl;

import br.com.agroplanner.service.PropriedadeFotoService;
import br.com.agroplanner.domain.PropriedadeFoto;
import br.com.agroplanner.repository.PropriedadeFotoRepository;
import br.com.agroplanner.service.dto.PropriedadeFotoDTO;
import br.com.agroplanner.service.mapper.PropriedadeFotoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PropriedadeFoto}.
 */
@Service
@Transactional
public class PropriedadeFotoServiceImpl implements PropriedadeFotoService {

    private final Logger log = LoggerFactory.getLogger(PropriedadeFotoServiceImpl.class);

    private final PropriedadeFotoRepository propriedadeFotoRepository;

    private final PropriedadeFotoMapper propriedadeFotoMapper;

    public PropriedadeFotoServiceImpl(PropriedadeFotoRepository propriedadeFotoRepository, PropriedadeFotoMapper propriedadeFotoMapper) {
        this.propriedadeFotoRepository = propriedadeFotoRepository;
        this.propriedadeFotoMapper = propriedadeFotoMapper;
    }

    /**
     * Save a propriedadeFoto.
     *
     * @param propriedadeFotoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PropriedadeFotoDTO save(PropriedadeFotoDTO propriedadeFotoDTO) {
        log.debug("Request to save PropriedadeFoto : {}", propriedadeFotoDTO);
        PropriedadeFoto propriedadeFoto = propriedadeFotoMapper.toEntity(propriedadeFotoDTO);
        propriedadeFoto = propriedadeFotoRepository.save(propriedadeFoto);
        return propriedadeFotoMapper.toDto(propriedadeFoto);
    }

    /**
     * Get all the propriedadeFotos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PropriedadeFotoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PropriedadeFotos");
        return propriedadeFotoRepository.findAll(pageable)
            .map(propriedadeFotoMapper::toDto);
    }


    /**
     * Get one propriedadeFoto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PropriedadeFotoDTO> findOne(Long id) {
        log.debug("Request to get PropriedadeFoto : {}", id);
        return propriedadeFotoRepository.findById(id)
            .map(propriedadeFotoMapper::toDto);
    }

    /**
     * Delete the propriedadeFoto by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PropriedadeFoto : {}", id);
        propriedadeFotoRepository.deleteById(id);
    }
}
