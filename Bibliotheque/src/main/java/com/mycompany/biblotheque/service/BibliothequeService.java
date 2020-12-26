package com.mycompany.biblotheque.service;

import com.mycompany.biblotheque.domain.Bibliotheque;
import com.mycompany.biblotheque.repository.BibliothequeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Bibliotheque}.
 */
@Service
@Transactional
public class BibliothequeService {

    private final Logger log = LoggerFactory.getLogger(BibliothequeService.class);

    private final BibliothequeRepository bibliothequeRepository;

    public BibliothequeService(BibliothequeRepository bibliothequeRepository) {
        this.bibliothequeRepository = bibliothequeRepository;
    }

    /**
     * Save a bibliotheque.
     *
     * @param bibliotheque the entity to save.
     * @return the persisted entity.
     */
    public Bibliotheque save(Bibliotheque bibliotheque) {
        log.debug("Request to save Bibliotheque : {}", bibliotheque);
        return bibliothequeRepository.save(bibliotheque);
    }

    /**
     * Get all the bibliotheques.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Bibliotheque> findAll() {
        log.debug("Request to get all Bibliotheques");
        return bibliothequeRepository.findAll();
    }


    /**
     * Get one bibliotheque by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Bibliotheque> findOne(Long id) {
        log.debug("Request to get Bibliotheque : {}", id);
        return bibliothequeRepository.findById(id);
    }

    /**
     * Delete the bibliotheque by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Bibliotheque : {}", id);
        bibliothequeRepository.deleteById(id);
    }
}
