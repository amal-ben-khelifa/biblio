package com.mycompany.biblotheque.service;

import com.mycompany.biblotheque.domain.Livres;
import com.mycompany.biblotheque.repository.LivresRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Livres}.
 */
@Service
@Transactional
public class LivresService {

    private final Logger log = LoggerFactory.getLogger(LivresService.class);

    private final LivresRepository livresRepository;

    public LivresService(LivresRepository livresRepository) {
        this.livresRepository = livresRepository;
    }

    /**
     * Save a livres.
     *
     * @param livres the entity to save.
     * @return the persisted entity.
     */
    public Livres save(Livres livres) {
        log.debug("Request to save Livres : {}", livres);
        return livresRepository.save(livres);
    }

    /**
     * Get all the livres.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Livres> findAll() {
        log.debug("Request to get all Livres");
        return livresRepository.findAll();
    }


    /**
     * Get one livres by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Livres> findOne(Long id) {
        log.debug("Request to get Livres : {}", id);
        return livresRepository.findById(id);
    }

    /**
     * Delete the livres by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Livres : {}", id);
        livresRepository.deleteById(id);
    }
}
