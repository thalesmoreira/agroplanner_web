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

import br.com.agroplanner.domain.Propriedade_caracteristica;
import br.com.agroplanner.domain.*; // for static metamodels
import br.com.agroplanner.repository.Propriedade_caracteristicaRepository;
import br.com.agroplanner.service.dto.Propriedade_caracteristicaCriteria;
import br.com.agroplanner.service.dto.Propriedade_caracteristicaDTO;
import br.com.agroplanner.service.mapper.Propriedade_caracteristicaMapper;

/**
 * Service for executing complex queries for {@link Propriedade_caracteristica} entities in the database.
 * The main input is a {@link Propriedade_caracteristicaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Propriedade_caracteristicaDTO} or a {@link Page} of {@link Propriedade_caracteristicaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class Propriedade_caracteristicaQueryService extends QueryService<Propriedade_caracteristica> {

    private final Logger log = LoggerFactory.getLogger(Propriedade_caracteristicaQueryService.class);

    private final Propriedade_caracteristicaRepository propriedade_caracteristicaRepository;

    private final Propriedade_caracteristicaMapper propriedade_caracteristicaMapper;

    public Propriedade_caracteristicaQueryService(Propriedade_caracteristicaRepository propriedade_caracteristicaRepository, Propriedade_caracteristicaMapper propriedade_caracteristicaMapper) {
        this.propriedade_caracteristicaRepository = propriedade_caracteristicaRepository;
        this.propriedade_caracteristicaMapper = propriedade_caracteristicaMapper;
    }

    /**
     * Return a {@link List} of {@link Propriedade_caracteristicaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Propriedade_caracteristicaDTO> findByCriteria(Propriedade_caracteristicaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Propriedade_caracteristica> specification = createSpecification(criteria);
        return propriedade_caracteristicaMapper.toDto(propriedade_caracteristicaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link Propriedade_caracteristicaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Propriedade_caracteristicaDTO> findByCriteria(Propriedade_caracteristicaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Propriedade_caracteristica> specification = createSpecification(criteria);
        return propriedade_caracteristicaRepository.findAll(specification, page)
            .map(propriedade_caracteristicaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(Propriedade_caracteristicaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Propriedade_caracteristica> specification = createSpecification(criteria);
        return propriedade_caracteristicaRepository.count(specification);
    }

    /**
     * Function to convert Propriedade_caracteristicaCriteria to a {@link Specification}.
     */
    private Specification<Propriedade_caracteristica> createSpecification(Propriedade_caracteristicaCriteria criteria) {
        Specification<Propriedade_caracteristica> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Propriedade_caracteristica_.id));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), Propriedade_caracteristica_.value));
            }
            if (criteria.getPropriedadeId() != null) {
                specification = specification.and(buildSpecification(criteria.getPropriedadeId(),
                    root -> root.join(Propriedade_caracteristica_.propriedade, JoinType.LEFT).get(Propriedade_.id)));
            }
            if (criteria.getCaracteristicaId() != null) {
                specification = specification.and(buildSpecification(criteria.getCaracteristicaId(),
                    root -> root.join(Propriedade_caracteristica_.caracteristica, JoinType.LEFT).get(Caracteristica_.id)));
            }
        }
        return specification;
    }
}
