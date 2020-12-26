package com.mycompany.biblotheque.web.rest;

import com.mycompany.biblotheque.domain.Auteurs;
import com.mycompany.biblotheque.service.AuteursService;
import com.mycompany.biblotheque.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.mycompany.biblotheque.domain.Auteurs}.
 */
@RestController
@RequestMapping("/api")
public class AuteursResource {

    private final Logger log = LoggerFactory.getLogger(AuteursResource.class);

    private static final String ENTITY_NAME = "auteurs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuteursService auteursService;

    public AuteursResource(AuteursService auteursService) {
        this.auteursService = auteursService;
    }

    /**
     * {@code POST  /auteurs} : Create a new auteurs.
     *
     * @param auteurs the auteurs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new auteurs, or with status {@code 400 (Bad Request)} if the auteurs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/auteurs")
    public ResponseEntity<Auteurs> createAuteurs(@RequestBody Auteurs auteurs) throws URISyntaxException {
        log.debug("REST request to save Auteurs : {}", auteurs);
        if (auteurs.getId() != null) {
            throw new BadRequestAlertException("A new auteurs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Auteurs result = auteursService.save(auteurs);
        return ResponseEntity.created(new URI("/api/auteurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /auteurs} : Updates an existing auteurs.
     *
     * @param auteurs the auteurs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated auteurs,
     * or with status {@code 400 (Bad Request)} if the auteurs is not valid,
     * or with status {@code 500 (Internal Server Error)} if the auteurs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/auteurs")
    public ResponseEntity<Auteurs> updateAuteurs(@RequestBody Auteurs auteurs) throws URISyntaxException {
        log.debug("REST request to update Auteurs : {}", auteurs);
        if (auteurs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Auteurs result = auteursService.save(auteurs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, auteurs.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /auteurs} : get all the auteurs.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of auteurs in body.
     */
    @GetMapping("/auteurs")
    public List<Auteurs> getAllAuteurs(@RequestParam(required = false) String filter) {
        if ("livre-is-null".equals(filter)) {
            log.debug("REST request to get all Auteurss where livre is null");
            return auteursService.findAllWhereLivreIsNull();
        }
        log.debug("REST request to get all Auteurs");
        return auteursService.findAll();
    }

    /**
     * {@code GET  /auteurs/:id} : get the "id" auteurs.
     *
     * @param id the id of the auteurs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the auteurs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/auteurs/{id}")
    public ResponseEntity<Auteurs> getAuteurs(@PathVariable Long id) {
        log.debug("REST request to get Auteurs : {}", id);
        Optional<Auteurs> auteurs = auteursService.findOne(id);
        return ResponseUtil.wrapOrNotFound(auteurs);
    }

    /**
     * {@code DELETE  /auteurs/:id} : delete the "id" auteurs.
     *
     * @param id the id of the auteurs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/auteurs/{id}")
    public ResponseEntity<Void> deleteAuteurs(@PathVariable Long id) {
        log.debug("REST request to delete Auteurs : {}", id);
        auteursService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
