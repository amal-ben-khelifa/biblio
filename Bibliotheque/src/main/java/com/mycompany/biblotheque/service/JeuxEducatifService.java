package com.mycompany.biblotheque.service;

import com.mycompany.biblotheque.domain.JeuxEducatif;
import com.mycompany.biblotheque.repository.JeuxEducatifRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link JeuxEducatif}.
 */
@Service
@Transactional
public class JeuxEducatifService {

    private final Logger log = LoggerFactory.getLogger(JeuxEducatifService.class);

    private final JeuxEducatifRepository jeuxEducatifRepository;

    public JeuxEducatifService(JeuxEducatifRepository jeuxEducatifRepository) {
        this.jeuxEducatifRepository = jeuxEducatifRepository;
    }

    /**
     * Save a jeuxEducatif.
     *
     * @param jeuxEducatif the entity to save.
     * @return the persisted entity.
     */
    public JeuxEducatif save(JeuxEducatif jeuxEducatif) {
        log.debug("Request to save JeuxEducatif : {}", jeuxEducatif);
        return jeuxEducatifRepository.save(jeuxEducatif);
    }

    /**
     * Get all the jeuxEducatifs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<JeuxEducatif> findAll() {
        log.debug("Request to get all JeuxEducatifs");
        return jeuxEducatifRepository.findAll();
    }


    /**
     * Get one jeuxEducatif by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JeuxEducatif> findOne(Long id) {
        log.debug("Request to get JeuxEducatif : {}", id);
        return jeuxEducatifRepository.findById(id);
    }

    /**
     * Delete the jeuxEducatif by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete JeuxEducatif : {}", id);
        jeuxEducatifRepository.deleteById(id);
    }
}
