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

import br.com.agroplanner.domain.PropriedadeContratada;
import br.com.agroplanner.domain.*; // for static metamodels
import br.com.agroplanner.repository.PropriedadeContratadaRepository;
import br.com.agroplanner.service.dto.PropriedadeContratadaCriteria;
import br.com.agroplanner.service.dto.PropriedadeContratadaDTO;
import br.com.agroplanner.service.mapper.PropriedadeContratadaMapper;

/**
 * Service for executing complex queries for {@link PropriedadeContratada} entities in the database.
 * The main input is a {@link PropriedadeContratadaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PropriedadeContratadaDTO} or a {@link Page} of {@link PropriedadeContratadaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PropriedadeContratadaQueryService extends QueryService<PropriedadeContratada> {

    private final Logger log = LoggerFactory.getLogger(PropriedadeContratadaQueryService.class);

    private final PropriedadeContratadaRepository propriedadeContratadaRepository;

    private final PropriedadeContratadaMapper propriedadeContratadaMapper;

    public PropriedadeContratadaQueryService(PropriedadeContratadaRepository propriedadeContratadaRepository, PropriedadeContratadaMapper propriedadeContratadaMapper) {
        this.propriedadeContratadaRepository = propriedadeContratadaRepository;
        this.propriedadeContratadaMapper = propriedadeContratadaMapper;
    }

    /**
     * Return a {@link List} of {@link PropriedadeContratadaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PropriedadeContratadaDTO> findByCriteria(PropriedadeContratadaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PropriedadeContratada> specification = createSpecification(criteria);
        return propriedadeContratadaMapper.toDto(propriedadeContratadaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PropriedadeContratadaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PropriedadeContratadaDTO> findByCriteria(PropriedadeContratadaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PropriedadeContratada> specification = createSpecification(criteria);
        return propriedadeContratadaRepository.findAll(specification, page)
            .map(propriedadeContratadaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PropriedadeContratadaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PropriedadeContratada> specification = createSpecification(criteria);
        return propriedadeContratadaRepository.count(specification);
    }

    /**
     * Function to convert PropriedadeContratadaCriteria to a {@link Specification}.
     */
    private Specification<PropriedadeContratada> createSpecification(PropriedadeContratadaCriteria criteria) {
        Specification<PropriedadeContratada> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PropriedadeContratada_.id));
            }
            if (criteria.getDataInicial() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataInicial(), PropriedadeContratada_.dataInicial));
            }
            if (criteria.getDataFinal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataFinal(), PropriedadeContratada_.dataFinal));
            }
            if (criteria.getQuantidadeCabecas() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantidadeCabecas(), PropriedadeContratada_.quantidadeCabecas));
            }
            if (criteria.getValorContratado() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorContratado(), PropriedadeContratada_.valorContratado));
            }
            if (criteria.getFormaPagamento() != null) {
                specification = specification.and(buildSpecification(criteria.getFormaPagamento(), PropriedadeContratada_.formaPagamento));
            }
            if (criteria.getParcelas() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getParcelas(), PropriedadeContratada_.parcelas));
            }
            if (criteria.getValorParcela() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorParcela(), PropriedadeContratada_.valorParcela));
            }
            if (criteria.getPropriedadeId() != null) {
                specification = specification.and(buildSpecification(criteria.getPropriedadeId(),
                    root -> root.join(PropriedadeContratada_.propriedade, JoinType.LEFT).get(Propriedade_.id)));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(PropriedadeContratada_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
