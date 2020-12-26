package com.mycompany.biblotheque.service;

import com.mycompany.biblotheque.domain.Auteurs;
import com.mycompany.biblotheque.repository.AuteursRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Auteurs}.
 */
@Service
@Transactional
public class AuteursService {

    private final Logger log = LoggerFactory.getLogger(AuteursService.class);

    private final AuteursRepository auteursRepository;

    public AuteursService(AuteursRepository auteursRepository) {
        this.auteursRepository = auteursRepository;
    }

    /**
     * Save a auteurs.
     *
     * @param auteurs the entity to save.
     * @return the persisted entity.
     */
    public Auteurs save(Auteurs auteurs) {
        log.debug("Request to save Auteurs : {}", auteurs);
        return auteursRepository.save(auteurs);
    }

    /**
     * Get all the auteurs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Auteurs> findAll() {
        log.debug("Request to get all Auteurs");
        return auteursRepository.findAll();
    }



    /**
     *  Get all the auteurs where Livre is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Auteurs> findAllWhereLivreIsNull() {
        log.debug("Request to get all auteurs where Livre is null");
        return StreamSupport
            .stream(auteursRepository.findAll().spliterator(), false)
            .filter(auteurs -> auteurs.getLivre() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one auteurs by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Auteurs> findOne(Long id) {
        log.debug("Request to get Auteurs : {}", id);
        return auteursRepository.findById(id);
    }

    /**
     * Delete the auteurs by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Auteurs : {}", id);
        auteursRepository.deleteById(id);
    }
}
