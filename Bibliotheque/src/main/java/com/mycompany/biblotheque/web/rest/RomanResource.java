package com.mycompany.biblotheque.web.rest;

import com.mycompany.biblotheque.domain.Roman;
import com.mycompany.biblotheque.repository.RomanRepository;
import com.mycompany.biblotheque.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.biblotheque.domain.Roman}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RomanResource {

    private final Logger log = LoggerFactory.getLogger(RomanResource.class);

    private static final String ENTITY_NAME = "roman";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RomanRepository romanRepository;

    public RomanResource(RomanRepository romanRepository) {
        this.romanRepository = romanRepository;
    }

    /**
     * {@code POST  /romen} : Create a new roman.
     *
     * @param roman the roman to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roman, or with status {@code 400 (Bad Request)} if the roman has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/romen")
    public ResponseEntity<Roman> createRoman(@RequestBody Roman roman) throws URISyntaxException {
        log.debug("REST request to save Roman : {}", roman);
        if (roman.getId() != null) {
            throw new BadRequestAlertException("A new roman cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Roman result = romanRepository.save(roman);
        return ResponseEntity.created(new URI("/api/romen/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /romen} : Updates an existing roman.
     *
     * @param roman the roman to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roman,
     * or with status {@code 400 (Bad Request)} if the roman is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roman couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/romen")
    public ResponseEntity<Roman> updateRoman(@RequestBody Roman roman) throws URISyntaxException {
        log.debug("REST request to update Roman : {}", roman);
        if (roman.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Roman result = romanRepository.save(roman);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roman.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /romen} : get all the romen.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of romen in body.
     */
    @GetMapping("/romen")
    public List<Roman> getAllRomen() {
        log.debug("REST request to get all Romen");
        return romanRepository.findAll();
    }

    /**
     * {@code GET  /romen/:id} : get the "id" roman.
     *
     * @param id the id of the roman to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roman, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/romen/{id}")
    public ResponseEntity<Roman> getRoman(@PathVariable Long id) {
        log.debug("REST request to get Roman : {}", id);
        Optional<Roman> roman = romanRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(roman);
    }

    /**
     * {@code DELETE  /romen/:id} : delete the "id" roman.
     *
     * @param id the id of the roman to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/romen/{id}")
    public ResponseEntity<Void> deleteRoman(@PathVariable Long id) {
        log.debug("REST request to delete Roman : {}", id);
        romanRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
