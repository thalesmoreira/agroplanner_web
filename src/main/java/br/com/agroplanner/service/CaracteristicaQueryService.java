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

import br.com.agroplanner.domain.Caracteristica;
import br.com.agroplanner.domain.*; // for static metamodels
import br.com.agroplanner.repository.CaracteristicaRepository;
import br.com.agroplanner.service.dto.CaracteristicaCriteria;
import br.com.agroplanner.service.dto.CaracteristicaDTO;
import br.com.agroplanner.service.mapper.CaracteristicaMapper;

/**
 * Service for executing complex queries for {@link Caracteristica} entities in the database.
 * The main input is a {@link CaracteristicaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CaracteristicaDTO} or a {@link Page} of {@link CaracteristicaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CaracteristicaQueryService extends QueryService<Caracteristica> {

    private final Logger log = LoggerFactory.getLogger(CaracteristicaQueryService.class);

    private final CaracteristicaRepository caracteristicaRepository;

    private final CaracteristicaMapper caracteristicaMapper;

    public CaracteristicaQueryService(CaracteristicaRepository caracteristicaRepository, CaracteristicaMapper caracteristicaMapper) {
        this.caracteristicaRepository = caracteristicaRepository;
        this.caracteristicaMapper = caracteristicaMapper;
    }

    /**
     * Return a {@link List} of {@link CaracteristicaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CaracteristicaDTO> findByCriteria(CaracteristicaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Caracteristica> specification = createSpecification(criteria);
        return caracteristicaMapper.toDto(caracteristicaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CaracteristicaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CaracteristicaDTO> findByCriteria(CaracteristicaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Caracteristica> specification = createSpecification(criteria);
        return caracteristicaRepository.findAll(specification, page)
            .map(caracteristicaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CaracteristicaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Caracteristica> specification = createSpecification(criteria);
        return caracteristicaRepository.count(specification);
    }

    /**
     * Function to convert CaracteristicaCriteria to a {@link Specification}.
     */
    private Specification<Caracteristica> createSpecification(CaracteristicaCriteria criteria) {
        Specification<Caracteristica> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Caracteristica_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Caracteristica_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Caracteristica_.descricao));
            }
        }
        return specification;
    }
}
