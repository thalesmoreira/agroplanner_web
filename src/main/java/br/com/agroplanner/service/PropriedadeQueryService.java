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

import br.com.agroplanner.domain.Propriedade;
import br.com.agroplanner.domain.*; // for static metamodels
import br.com.agroplanner.repository.PropriedadeRepository;
import br.com.agroplanner.service.dto.PropriedadeCriteria;
import br.com.agroplanner.service.dto.PropriedadeDTO;
import br.com.agroplanner.service.mapper.PropriedadeMapper;

/**
 * Service for executing complex queries for {@link Propriedade} entities in the database.
 * The main input is a {@link PropriedadeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PropriedadeDTO} or a {@link Page} of {@link PropriedadeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PropriedadeQueryService extends QueryService<Propriedade> {

    private final Logger log = LoggerFactory.getLogger(PropriedadeQueryService.class);

    private final PropriedadeRepository propriedadeRepository;

    private final PropriedadeMapper propriedadeMapper;

    public PropriedadeQueryService(PropriedadeRepository propriedadeRepository, PropriedadeMapper propriedadeMapper) {
        this.propriedadeRepository = propriedadeRepository;
        this.propriedadeMapper = propriedadeMapper;
    }

    /**
     * Return a {@link List} of {@link PropriedadeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PropriedadeDTO> findByCriteria(PropriedadeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Propriedade> specification = createSpecification(criteria);
        return propriedadeMapper.toDto(propriedadeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PropriedadeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PropriedadeDTO> findByCriteria(PropriedadeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Propriedade> specification = createSpecification(criteria);
        return propriedadeRepository.findAll(specification, page)
            .map(propriedadeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PropriedadeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Propriedade> specification = createSpecification(criteria);
        return propriedadeRepository.count(specification);
    }

    /**
     * Function to convert PropriedadeCriteria to a {@link Specification}.
     */
    private Specification<Propriedade> createSpecification(PropriedadeCriteria criteria) {
        Specification<Propriedade> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Propriedade_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Propriedade_.nome));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Propriedade_.descricao));
            }
            if (criteria.getLocalidade() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocalidade(), Propriedade_.localidade));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Propriedade_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
