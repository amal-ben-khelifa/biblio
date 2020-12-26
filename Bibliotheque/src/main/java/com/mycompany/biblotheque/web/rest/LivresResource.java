package com.mycompany.biblotheque.web.rest;

import com.mycompany.biblotheque.domain.Livres;
import com.mycompany.biblotheque.service.LivresService;
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
 * REST controller for managing {@link com.mycompany.biblotheque.domain.Livres}.
 */
@RestController
@RequestMapping("/api")
public class LivresResource {

    private final Logger log = LoggerFactory.getLogger(LivresResource.class);

    private static final String ENTITY_NAME = "livres";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LivresService livresService;

    public LivresResource(LivresService livresService) {
        this.livresService = livresService;
    }

    /**
     * {@code POST  /livres} : Create a new livres.
     *
     * @param livres the livres to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new livres, or with status {@code 400 (Bad Request)} if the livres has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/livres")
    public ResponseEntity<Livres> createLivres(@RequestBody Livres livres) throws URISyntaxException {
        log.debug("REST request to save Livres : {}", livres);
        if (livres.getId() != null) {
            throw new BadRequestAlertException("A new livres cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Livres result = livresService.save(livres);
        return ResponseEntity.created(new URI("/api/livres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /livres} : Updates an existing livres.
     *
     * @param livres the livres to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated livres,
     * or with status {@code 400 (Bad Request)} if the livres is not valid,
     * or with status {@code 500 (Internal Server Error)} if the livres couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/livres")
    public ResponseEntity<Livres> updateLivres(@RequestBody Livres livres) throws URISyntaxException {
        log.debug("REST request to update Livres : {}", livres);
        if (livres.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Livres result = livresService.save(livres);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, livres.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /livres} : get all the livres.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of livres in body.
     */
    @GetMapping("/livres")
    public List<Livres> getAllLivres() {
        log.debug("REST request to get all Livres");
        return livresService.findAll();
    }

    /**
     * {@code GET  /livres/:id} : get the "id" livres.
     *
     * @param id the id of the livres to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the livres, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/livres/{id}")
    public ResponseEntity<Livres> getLivres(@PathVariable Long id) {
        log.debug("REST request to get Livres : {}", id);
        Optional<Livres> livres = livresService.findOne(id);
        return ResponseUtil.wrapOrNotFound(livres);
    }

    /**
     * {@code DELETE  /livres/:id} : delete the "id" livres.
     *
     * @param id the id of the livres to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/livres/{id}")
    public ResponseEntity<Void> deleteLivres(@PathVariable Long id) {
        log.debug("REST request to delete Livres : {}", id);
        livresService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
