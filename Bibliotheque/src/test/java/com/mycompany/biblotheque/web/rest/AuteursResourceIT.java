package com.mycompany.biblotheque.web.rest;

import com.mycompany.biblotheque.BibliothequeApp;
import com.mycompany.biblotheque.domain.Auteurs;
import com.mycompany.biblotheque.repository.AuteursRepository;
import com.mycompany.biblotheque.service.AuteursService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AuteursResource} REST controller.
 */
@SpringBootTest(classes = BibliothequeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AuteursResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATEDENAISSANCE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATEDENAISSANCE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AuteursRepository auteursRepository;

    @Autowired
    private AuteursService auteursService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAuteursMockMvc;

    private Auteurs auteurs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Auteurs createEntity(EntityManager em) {
        Auteurs auteurs = new Auteurs()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .datedenaissance(DEFAULT_DATEDENAISSANCE);
        return auteurs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Auteurs createUpdatedEntity(EntityManager em) {
        Auteurs auteurs = new Auteurs()
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .datedenaissance(UPDATED_DATEDENAISSANCE);
        return auteurs;
    }

    @BeforeEach
    public void initTest() {
        auteurs = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuteurs() throws Exception {
        int databaseSizeBeforeCreate = auteursRepository.findAll().size();
        // Create the Auteurs
        restAuteursMockMvc.perform(post("/api/auteurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(auteurs)))
            .andExpect(status().isCreated());

        // Validate the Auteurs in the database
        List<Auteurs> auteursList = auteursRepository.findAll();
        assertThat(auteursList).hasSize(databaseSizeBeforeCreate + 1);
        Auteurs testAuteurs = auteursList.get(auteursList.size() - 1);
        assertThat(testAuteurs.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testAuteurs.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testAuteurs.getDatedenaissance()).isEqualTo(DEFAULT_DATEDENAISSANCE);
    }

    @Test
    @Transactional
    public void createAuteursWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auteursRepository.findAll().size();

        // Create the Auteurs with an existing ID
        auteurs.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuteursMockMvc.perform(post("/api/auteurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(auteurs)))
            .andExpect(status().isBadRequest());

        // Validate the Auteurs in the database
        List<Auteurs> auteursList = auteursRepository.findAll();
        assertThat(auteursList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAuteurs() throws Exception {
        // Initialize the database
        auteursRepository.saveAndFlush(auteurs);

        // Get all the auteursList
        restAuteursMockMvc.perform(get("/api/auteurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auteurs.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].datedenaissance").value(hasItem(DEFAULT_DATEDENAISSANCE.toString())));
    }
    
    @Test
    @Transactional
    public void getAuteurs() throws Exception {
        // Initialize the database
        auteursRepository.saveAndFlush(auteurs);

        // Get the auteurs
        restAuteursMockMvc.perform(get("/api/auteurs/{id}", auteurs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(auteurs.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.datedenaissance").value(DEFAULT_DATEDENAISSANCE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAuteurs() throws Exception {
        // Get the auteurs
        restAuteursMockMvc.perform(get("/api/auteurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuteurs() throws Exception {
        // Initialize the database
        auteursService.save(auteurs);

        int databaseSizeBeforeUpdate = auteursRepository.findAll().size();

        // Update the auteurs
        Auteurs updatedAuteurs = auteursRepository.findById(auteurs.getId()).get();
        // Disconnect from session so that the updates on updatedAuteurs are not directly saved in db
        em.detach(updatedAuteurs);
        updatedAuteurs
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .datedenaissance(UPDATED_DATEDENAISSANCE);

        restAuteursMockMvc.perform(put("/api/auteurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuteurs)))
            .andExpect(status().isOk());

        // Validate the Auteurs in the database
        List<Auteurs> auteursList = auteursRepository.findAll();
        assertThat(auteursList).hasSize(databaseSizeBeforeUpdate);
        Auteurs testAuteurs = auteursList.get(auteursList.size() - 1);
        assertThat(testAuteurs.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAuteurs.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testAuteurs.getDatedenaissance()).isEqualTo(UPDATED_DATEDENAISSANCE);
    }

    @Test
    @Transactional
    public void updateNonExistingAuteurs() throws Exception {
        int databaseSizeBeforeUpdate = auteursRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuteursMockMvc.perform(put("/api/auteurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(auteurs)))
            .andExpect(status().isBadRequest());

        // Validate the Auteurs in the database
        List<Auteurs> auteursList = auteursRepository.findAll();
        assertThat(auteursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAuteurs() throws Exception {
        // Initialize the database
        auteursService.save(auteurs);

        int databaseSizeBeforeDelete = auteursRepository.findAll().size();

        // Delete the auteurs
        restAuteursMockMvc.perform(delete("/api/auteurs/{id}", auteurs.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Auteurs> auteursList = auteursRepository.findAll();
        assertThat(auteursList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
