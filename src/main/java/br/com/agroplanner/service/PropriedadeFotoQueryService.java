package br.com.agroplanner.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import br.com.agroplanner.domain.PropriedadeFoto;
import br.com.agroplanner.domain.*; // for static metamodels
import br.com.agroplanner.repository.PropriedadeFotoRepository;
import br.com.agroplanner.service.dto.PropriedadeFotoCriteria;
import br.com.agroplanner.service.dto.PropriedadeFotoDTO;
import br.com.agroplanner.service.mapper.PropriedadeFotoMapper;

/**
 * Service for executing complex queries for {@link PropriedadeFoto} entities in the database.
 * The main input is a {@link PropriedadeFotoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PropriedadeFotoDTO} or a {@link Page} of {@link PropriedadeFotoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PropriedadeFotoQueryService extends QueryService<PropriedadeFoto> {

    private final Logger log = LoggerFactory.getLogger(PropriedadeFotoQueryService.class);

    private final PropriedadeFotoRepository propriedadeFotoRepository;

    private final PropriedadeFotoMapper propriedadeFotoMapper;

    public PropriedadeFotoQueryService(PropriedadeFotoRepository propriedadeFotoRepository, PropriedadeFotoMapper propriedadeFotoMapper) {
        this.propriedadeFotoRepository = propriedadeFotoRepository;
        this.propriedadeFotoMapper = propriedadeFotoMapper;
    }

    /**
     * Return a {@link List} of {@link PropriedadeFotoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PropriedadeFotoDTO> findByCriteria(PropriedadeFotoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PropriedadeFoto> specification = createSpecification(criteria);
        return propriedadeFotoMapper.toDto(propriedadeFotoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PropriedadeFotoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PropriedadeFotoDTO> findByCriteria(PropriedadeFotoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PropriedadeFoto> specification = createSpecification(criteria);
        return propriedadeFotoRepository.findAll(specification, page)
            .map(propriedadeFotoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PropriedadeFotoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PropriedadeFoto> specification = createSpecification(criteria);
        return propriedadeFotoRepository.count(specification);
    }

    /**
     * Function to convert PropriedadeFotoCriteria to a {@link Specification}.
     */
    private Specification<PropriedadeFoto> createSpecification(PropriedadeFotoCriteria criteria) {
        Specification<PropriedadeFoto> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PropriedadeFoto_.id));
            }
            if (criteria.getPropriedadeId() != null) {
                specification = specification.and(buildSpecification(criteria.getPropriedadeId(),
                    root -> root.join(PropriedadeFoto_.propriedade, JoinType.LEFT).get(Propriedade_.id)));
            }
        }
        return specification;
    }
}
