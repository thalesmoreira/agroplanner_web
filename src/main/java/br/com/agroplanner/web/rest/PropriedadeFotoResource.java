package br.com.agroplanner.web.rest;

import br.com.agroplanner.service.PropriedadeFotoService;
import br.com.agroplanner.web.rest.errors.BadRequestAlertException;
import br.com.agroplanner.service.dto.PropriedadeFotoDTO;
import br.com.agroplanner.service.dto.PropriedadeFotoCriteria;
import br.com.agroplanner.service.PropriedadeFotoQueryService;

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
 * REST controller for managing {@link br.com.agroplanner.domain.PropriedadeFoto}.
 */
@RestController
@RequestMapping("/api")
public class PropriedadeFotoResource {

    private final Logger log = LoggerFactory.getLogger(PropriedadeFotoResource.class);

    private static final String ENTITY_NAME = "propriedadeFoto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PropriedadeFotoService propriedadeFotoService;

    private final PropriedadeFotoQueryService propriedadeFotoQueryService;

    public PropriedadeFotoResource(PropriedadeFotoService propriedadeFotoService, PropriedadeFotoQueryService propriedadeFotoQueryService) {
        this.propriedadeFotoService = propriedadeFotoService;
        this.propriedadeFotoQueryService = propriedadeFotoQueryService;
    }

    /**
     * {@code POST  /propriedade-fotos} : Create a new propriedadeFoto.
     *
     * @param propriedadeFotoDTO the propriedadeFotoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new propriedadeFotoDTO, or with status {@code 400 (Bad Request)} if the propriedadeFoto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/propriedade-fotos")
    public ResponseEntity<PropriedadeFotoDTO> createPropriedadeFoto(@Valid @RequestBody PropriedadeFotoDTO propriedadeFotoDTO) throws URISyntaxException {
        log.debug("REST request to save PropriedadeFoto : {}", propriedadeFotoDTO);
        if (propriedadeFotoDTO.getId() != null) {
            throw new BadRequestAlertException("A new propriedadeFoto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PropriedadeFotoDTO result = propriedadeFotoService.save(propriedadeFotoDTO);
        return ResponseEntity.created(new URI("/api/propriedade-fotos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /propriedade-fotos} : Updates an existing propriedadeFoto.
     *
     * @param propriedadeFotoDTO the propriedadeFotoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated propriedadeFotoDTO,
     * or with status {@code 400 (Bad Request)} if the propriedadeFotoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the propriedadeFotoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/propriedade-fotos")
    public ResponseEntity<PropriedadeFotoDTO> updatePropriedadeFoto(@Valid @RequestBody PropriedadeFotoDTO propriedadeFotoDTO) throws URISyntaxException {
        log.debug("REST request to update PropriedadeFoto : {}", propriedadeFotoDTO);
        if (propriedadeFotoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PropriedadeFotoDTO result = propriedadeFotoService.save(propriedadeFotoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, propriedadeFotoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /propriedade-fotos} : get all the propriedadeFotos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of propriedadeFotos in body.
     */
    @GetMapping("/propriedade-fotos")
    public ResponseEntity<List<PropriedadeFotoDTO>> getAllPropriedadeFotos(PropriedadeFotoCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get PropriedadeFotos by criteria: {}", criteria);
        Page<PropriedadeFotoDTO> page = propriedadeFotoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /propriedade-fotos/count} : count all the propriedadeFotos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/propriedade-fotos/count")
    public ResponseEntity<Long> countPropriedadeFotos(PropriedadeFotoCriteria criteria) {
        log.debug("REST request to count PropriedadeFotos by criteria: {}", criteria);
        return ResponseEntity.ok().body(propriedadeFotoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /propriedade-fotos/:id} : get the "id" propriedadeFoto.
     *
     * @param id the id of the propriedadeFotoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the propriedadeFotoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/propriedade-fotos/{id}")
    public ResponseEntity<PropriedadeFotoDTO> getPropriedadeFoto(@PathVariable Long id) {
        log.debug("REST request to get PropriedadeFoto : {}", id);
        Optional<PropriedadeFotoDTO> propriedadeFotoDTO = propriedadeFotoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(propriedadeFotoDTO);
    }

    /**
     * {@code DELETE  /propriedade-fotos/:id} : delete the "id" propriedadeFoto.
     *
     * @param id the id of the propriedadeFotoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/propriedade-fotos/{id}")
    public ResponseEntity<Void> deletePropriedadeFoto(@PathVariable Long id) {
        log.debug("REST request to delete PropriedadeFoto : {}", id);
        propriedadeFotoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
