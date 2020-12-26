package com.mycompany.biblotheque.web.rest;

import com.mycompany.biblotheque.domain.JeuxEducatif;
import com.mycompany.biblotheque.service.JeuxEducatifService;
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

/**
 * REST controller for managing {@link com.mycompany.biblotheque.domain.JeuxEducatif}.
 */
@RestController
@RequestMapping("/api")
public class JeuxEducatifResource {

    private final Logger log = LoggerFactory.getLogger(JeuxEducatifResource.class);

    private static final String ENTITY_NAME = "jeuxEducatif";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JeuxEducatifService jeuxEducatifService;

    public JeuxEducatifResource(JeuxEducatifService jeuxEducatifService) {
        this.jeuxEducatifService = jeuxEducatifService;
    }

    /**
     * {@code POST  /jeux-educatifs} : Create a new jeuxEducatif.
     *
     * @param jeuxEducatif the jeuxEducatif to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jeuxEducatif, or with status {@code 400 (Bad Request)} if the jeuxEducatif has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/jeux-educatifs")
    public ResponseEntity<JeuxEducatif> createJeuxEducatif(@RequestBody JeuxEducatif jeuxEducatif) throws URISyntaxException {
        log.debug("REST request to save JeuxEducatif : {}", jeuxEducatif);
        if (jeuxEducatif.getId() != null) {
            throw new BadRequestAlertException("A new jeuxEducatif cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JeuxEducatif result = jeuxEducatifService.save(jeuxEducatif);
        return ResponseEntity.created(new URI("/api/jeux-educatifs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /jeux-educatifs} : Updates an existing jeuxEducatif.
     *
     * @param jeuxEducatif the jeuxEducatif to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jeuxEducatif,
     * or with status {@code 400 (Bad Request)} if the jeuxEducatif is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jeuxEducatif couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/jeux-educatifs")
    public ResponseEntity<JeuxEducatif> updateJeuxEducatif(@RequestBody JeuxEducatif jeuxEducatif) throws URISyntaxException {
        log.debug("REST request to update JeuxEducatif : {}", jeuxEducatif);
        if (jeuxEducatif.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JeuxEducatif result = jeuxEducatifService.save(jeuxEducatif);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jeuxEducatif.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /jeux-educatifs} : get all the jeuxEducatifs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jeuxEducatifs in body.
     */
    @GetMapping("/jeux-educatifs")
    public List<JeuxEducatif> getAllJeuxEducatifs() {
        log.debug("REST request to get all JeuxEducatifs");
        return jeuxEducatifService.findAll();
    }

    /**
     * {@code GET  /jeux-educatifs/:id} : get the "id" jeuxEducatif.
     *
     * @param id the id of the jeuxEducatif to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jeuxEducatif, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/jeux-educatifs/{id}")
    public ResponseEntity<JeuxEducatif> getJeuxEducatif(@PathVariable Long id) {
        log.debug("REST request to get JeuxEducatif : {}", id);
        Optional<JeuxEducatif> jeuxEducatif = jeuxEducatifService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jeuxEducatif);
    }

    /**
     * {@code DELETE  /jeux-educatifs/:id} : delete the "id" jeuxEducatif.
     *
     * @param id the id of the jeuxEducatif to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/jeux-educatifs/{id}")
    public ResponseEntity<Void> deleteJeuxEducatif(@PathVariable Long id) {
        log.debug("REST request to delete JeuxEducatif : {}", id);
        jeuxEducatifService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
