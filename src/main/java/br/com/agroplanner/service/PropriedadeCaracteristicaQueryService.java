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

import br.com.agroplanner.domain.PropriedadeCaracteristica;
import br.com.agroplanner.domain.*; // for static metamodels
import br.com.agroplanner.repository.PropriedadeCaracteristicaRepository;
import br.com.agroplanner.service.dto.PropriedadeCaracteristicaCriteria;
import br.com.agroplanner.service.dto.PropriedadeCaracteristicaDTO;
import br.com.agroplanner.service.mapper.PropriedadeCaracteristicaMapper;

/**
 * Service for executing complex queries for {@link PropriedadeCaracteristica} entities in the database.
 * The main input is a {@link PropriedadeCaracteristicaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PropriedadeCaracteristicaDTO} or a {@link Page} of {@link PropriedadeCaracteristicaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PropriedadeCaracteristicaQueryService extends QueryService<PropriedadeCaracteristica> {

    private final Logger log = LoggerFactory.getLogger(PropriedadeCaracteristicaQueryService.class);

    private final PropriedadeCaracteristicaRepository propriedadeCaracteristicaRepository;

    private final PropriedadeCaracteristicaMapper propriedadeCaracteristicaMapper;

    public PropriedadeCaracteristicaQueryService(PropriedadeCaracteristicaRepository propriedadeCaracteristicaRepository, PropriedadeCaracteristicaMapper propriedadeCaracteristicaMapper) {
        this.propriedadeCaracteristicaRepository = propriedadeCaracteristicaRepository;
        this.propriedadeCaracteristicaMapper = propriedadeCaracteristicaMapper;
    }

    /**
     * Return a {@link List} of {@link PropriedadeCaracteristicaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PropriedadeCaracteristicaDTO> findByCriteria(PropriedadeCaracteristicaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PropriedadeCaracteristica> specification = createSpecification(criteria);
        return propriedadeCaracteristicaMapper.toDto(propriedadeCaracteristicaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PropriedadeCaracteristicaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PropriedadeCaracteristicaDTO> findByCriteria(PropriedadeCaracteristicaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PropriedadeCaracteristica> specification = createSpecification(criteria);
        return propriedadeCaracteristicaRepository.findAll(specification, page)
            .map(propriedadeCaracteristicaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PropriedadeCaracteristicaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PropriedadeCaracteristica> specification = createSpecification(criteria);
        return propriedadeCaracteristicaRepository.count(specification);
    }

    /**
     * Function to convert Propriedade_caracteristicaCriteria to a {@link Specification}.
     */
    private Specification<PropriedadeCaracteristica> createSpecification(PropriedadeCaracteristicaCriteria criteria) {
        Specification<PropriedadeCaracteristica> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PropriedadeCaracteristica_.id));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), PropriedadeCaracteristica_.value));
            }
            if (criteria.getPropriedadeId() != null) {
                specification = specification.and(buildSpecification(criteria.getPropriedadeId(),
                    root -> root.join(PropriedadeCaracteristica_.propriedade, JoinType.LEFT).get(Propriedade_.id)));
            }
            if (criteria.getCaracteristicaId() != null) {
                specification = specification.and(buildSpecification(criteria.getCaracteristicaId(),
                    root -> root.join(PropriedadeCaracteristica_.caracteristica, JoinType.LEFT).get(Caracteristica_.id)));
            }
        }
        return specification;
    }
}
