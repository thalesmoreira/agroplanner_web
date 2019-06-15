package br.com.agroplanner.web.rest;

import br.com.agroplanner.service.PropriedadeService;
import br.com.agroplanner.web.rest.errors.BadRequestAlertException;
import br.com.agroplanner.service.dto.PropriedadeDTO;
import br.com.agroplanner.service.dto.PropriedadeCriteria;
import br.com.agroplanner.service.PropriedadeQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.agroplanner.domain.Propriedade}.
 */
@RestController
@RequestMapping("/api")
public class PropriedadeResource {

    private final Logger log = LoggerFactory.getLogger(PropriedadeResource.class);

    private static final String ENTITY_NAME = "propriedade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PropriedadeService propriedadeService;

    private final PropriedadeQueryService propriedadeQueryService;

    public PropriedadeResource(PropriedadeService propriedadeService, PropriedadeQueryService propriedadeQueryService) {
        this.propriedadeService = propriedadeService;
        this.propriedadeQueryService = propriedadeQueryService;
    }

    /**
     * {@code POST  /propriedades} : Create a new propriedade.
     *
     * @param propriedadeDTO the propriedadeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new propriedadeDTO, or with status {@code 400 (Bad Request)} if the propriedade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/propriedades")
    public ResponseEntity<PropriedadeDTO> createPropriedade(@Valid @RequestBody PropriedadeDTO propriedadeDTO) throws URISyntaxException {
        log.debug("REST request to save Propriedade : {}", propriedadeDTO);
        if (propriedadeDTO.getId() != null) {
            throw new BadRequestAlertException("A new propriedade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PropriedadeDTO result = propriedadeService.save(propriedadeDTO);
        return ResponseEntity.created(new URI("/api/propriedades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /propriedades} : Updates an existing propriedade.
     *
     * @param propriedadeDTO the propriedadeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated propriedadeDTO,
     * or with status {@code 400 (Bad Request)} if the propriedadeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the propriedadeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/propriedades")
    public ResponseEntity<PropriedadeDTO> updatePropriedade(@Valid @RequestBody PropriedadeDTO propriedadeDTO) throws URISyntaxException {
        log.debug("REST request to update Propriedade : {}", propriedadeDTO);
        if (propriedadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PropriedadeDTO result = propriedadeService.save(propriedadeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, propriedadeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /propriedades} : get all the propriedades.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of propriedades in body.
     */
    @GetMapping("/propriedades")
    public ResponseEntity<List<PropriedadeDTO>> getAllPropriedades(PropriedadeCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Propriedades by criteria: {}", criteria);
        Page<PropriedadeDTO> page = propriedadeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /propriedades/count} : count all the propriedades.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/propriedades/count")
    public ResponseEntity<Long> countPropriedades(PropriedadeCriteria criteria) {
        log.debug("REST request to count Propriedades by criteria: {}", criteria);
        return ResponseEntity.ok().body(propriedadeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /propriedades/:id} : get the "id" propriedade.
     *
     * @param id the id of the propriedadeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the propriedadeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/propriedades/{id}")
    public ResponseEntity<PropriedadeDTO> getPropriedade(@PathVariable Long id) {
        log.debug("REST request to get Propriedade : {}", id);
        Optional<PropriedadeDTO> propriedadeDTO = propriedadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(propriedadeDTO);
    }

    /**
     * {@code DELETE  /propriedades/:id} : delete the "id" propriedade.
     *
     * @param id the id of the propriedadeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/propriedades/{id}")
    public ResponseEntity<Void> deletePropriedade(@PathVariable Long id) {
        log.debug("REST request to delete Propriedade : {}", id);
        propriedadeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
