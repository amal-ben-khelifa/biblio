package com.mycompany.biblotheque.web.rest;

import com.mycompany.biblotheque.domain.Bibliotheque;
import com.mycompany.biblotheque.service.BibliothequeService;
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
 * REST controller for managing {@link com.mycompany.biblotheque.domain.Bibliotheque}.
 */
@RestController
@RequestMapping("/api")
public class BibliothequeResource {

    private final Logger log = LoggerFactory.getLogger(BibliothequeResource.class);

    private static final String ENTITY_NAME = "bibliotheque";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BibliothequeService bibliothequeService;

    public BibliothequeResource(BibliothequeService bibliothequeService) {
        this.bibliothequeService = bibliothequeService;
    }

    /**
     * {@code POST  /bibliotheques} : Create a new bibliotheque.
     *
     * @param bibliotheque the bibliotheque to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bibliotheque, or with status {@code 400 (Bad Request)} if the bibliotheque has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bibliotheques")
    public ResponseEntity<Bibliotheque> createBibliotheque(@RequestBody Bibliotheque bibliotheque) throws URISyntaxException {
        log.debug("REST request to save Bibliotheque : {}", bibliotheque);
        if (bibliotheque.getId() != null) {
            throw new BadRequestAlertException("A new bibliotheque cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bibliotheque result = bibliothequeService.save(bibliotheque);
        return ResponseEntity.created(new URI("/api/bibliotheques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bibliotheques} : Updates an existing bibliotheque.
     *
     * @param bibliotheque the bibliotheque to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bibliotheque,
     * or with status {@code 400 (Bad Request)} if the bibliotheque is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bibliotheque couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bibliotheques")
    public ResponseEntity<Bibliotheque> updateBibliotheque(@RequestBody Bibliotheque bibliotheque) throws URISyntaxException {
        log.debug("REST request to update Bibliotheque : {}", bibliotheque);
        if (bibliotheque.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Bibliotheque result = bibliothequeService.save(bibliotheque);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bibliotheque.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bibliotheques} : get all the bibliotheques.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bibliotheques in body.
     */
    @GetMapping("/bibliotheques")
    public List<Bibliotheque> getAllBibliotheques() {
        log.debug("REST request to get all Bibliotheques");
        return bibliothequeService.findAll();
    }

    /**
     * {@code GET  /bibliotheques/:id} : get the "id" bibliotheque.
     *
     * @param id the id of the bibliotheque to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bibliotheque, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bibliotheques/{id}")
    public ResponseEntity<Bibliotheque> getBibliotheque(@PathVariable Long id) {
        log.debug("REST request to get Bibliotheque : {}", id);
        Optional<Bibliotheque> bibliotheque = bibliothequeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bibliotheque);
    }

    /**
     * {@code DELETE  /bibliotheques/:id} : delete the "id" bibliotheque.
     *
     * @param id the id of the bibliotheque to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bibliotheques/{id}")
    public ResponseEntity<Void> deleteBibliotheque(@PathVariable Long id) {
        log.debug("REST request to delete Bibliotheque : {}", id);
        bibliothequeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
