package com.mycompany.biblotheque.web.rest;

import com.mycompany.biblotheque.BibliothequeApp;
import com.mycompany.biblotheque.domain.JeuxEducatif;
import com.mycompany.biblotheque.repository.JeuxEducatifRepository;
import com.mycompany.biblotheque.service.JeuxEducatifService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link JeuxEducatifResource} REST controller.
 */
@SpringBootTest(classes = BibliothequeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class JeuxEducatifResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRIX = "AAAAAAAAAA";
    private static final String UPDATED_PRIX = "BBBBBBBBBB";

    @Autowired
    private JeuxEducatifRepository jeuxEducatifRepository;

    @Autowired
    private JeuxEducatifService jeuxEducatifService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJeuxEducatifMockMvc;

    private JeuxEducatif jeuxEducatif;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JeuxEducatif createEntity(EntityManager em) {
        JeuxEducatif jeuxEducatif = new JeuxEducatif()
            .nom(DEFAULT_NOM)
            .prix(DEFAULT_PRIX);
        return jeuxEducatif;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JeuxEducatif createUpdatedEntity(EntityManager em) {
        JeuxEducatif jeuxEducatif = new JeuxEducatif()
            .nom(UPDATED_NOM)
            .prix(UPDATED_PRIX);
        return jeuxEducatif;
    }

    @BeforeEach
    public void initTest() {
        jeuxEducatif = createEntity(em);
    }

    @Test
    @Transactional
    public void createJeuxEducatif() throws Exception {
        int databaseSizeBeforeCreate = jeuxEducatifRepository.findAll().size();
        // Create the JeuxEducatif
        restJeuxEducatifMockMvc.perform(post("/api/jeux-educatifs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jeuxEducatif)))
            .andExpect(status().isCreated());

        // Validate the JeuxEducatif in the database
        List<JeuxEducatif> jeuxEducatifList = jeuxEducatifRepository.findAll();
        assertThat(jeuxEducatifList).hasSize(databaseSizeBeforeCreate + 1);
        JeuxEducatif testJeuxEducatif = jeuxEducatifList.get(jeuxEducatifList.size() - 1);
        assertThat(testJeuxEducatif.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testJeuxEducatif.getPrix()).isEqualTo(DEFAULT_PRIX);
    }

    @Test
    @Transactional
    public void createJeuxEducatifWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jeuxEducatifRepository.findAll().size();

        // Create the JeuxEducatif with an existing ID
        jeuxEducatif.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJeuxEducatifMockMvc.perform(post("/api/jeux-educatifs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jeuxEducatif)))
            .andExpect(status().isBadRequest());

        // Validate the JeuxEducatif in the database
        List<JeuxEducatif> jeuxEducatifList = jeuxEducatifRepository.findAll();
        assertThat(jeuxEducatifList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllJeuxEducatifs() throws Exception {
        // Initialize the database
        jeuxEducatifRepository.saveAndFlush(jeuxEducatif);

        // Get all the jeuxEducatifList
        restJeuxEducatifMockMvc.perform(get("/api/jeux-educatifs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jeuxEducatif.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX)));
    }
    
    @Test
    @Transactional
    public void getJeuxEducatif() throws Exception {
        // Initialize the database
        jeuxEducatifRepository.saveAndFlush(jeuxEducatif);

        // Get the jeuxEducatif
        restJeuxEducatifMockMvc.perform(get("/api/jeux-educatifs/{id}", jeuxEducatif.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jeuxEducatif.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX));
    }
    @Test
    @Transactional
    public void getNonExistingJeuxEducatif() throws Exception {
        // Get the jeuxEducatif
        restJeuxEducatifMockMvc.perform(get("/api/jeux-educatifs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJeuxEducatif() throws Exception {
        // Initialize the database
        jeuxEducatifService.save(jeuxEducatif);

        int databaseSizeBeforeUpdate = jeuxEducatifRepository.findAll().size();

        // Update the jeuxEducatif
        JeuxEducatif updatedJeuxEducatif = jeuxEducatifRepository.findById(jeuxEducatif.getId()).get();
        // Disconnect from session so that the updates on updatedJeuxEducatif are not directly saved in db
        em.detach(updatedJeuxEducatif);
        updatedJeuxEducatif
            .nom(UPDATED_NOM)
            .prix(UPDATED_PRIX);

        restJeuxEducatifMockMvc.perform(put("/api/jeux-educatifs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedJeuxEducatif)))
            .andExpect(status().isOk());

        // Validate the JeuxEducatif in the database
        List<JeuxEducatif> jeuxEducatifList = jeuxEducatifRepository.findAll();
        assertThat(jeuxEducatifList).hasSize(databaseSizeBeforeUpdate);
        JeuxEducatif testJeuxEducatif = jeuxEducatifList.get(jeuxEducatifList.size() - 1);
        assertThat(testJeuxEducatif.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testJeuxEducatif.getPrix()).isEqualTo(UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void updateNonExistingJeuxEducatif() throws Exception {
        int databaseSizeBeforeUpdate = jeuxEducatifRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJeuxEducatifMockMvc.perform(put("/api/jeux-educatifs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jeuxEducatif)))
            .andExpect(status().isBadRequest());

        // Validate the JeuxEducatif in the database
        List<JeuxEducatif> jeuxEducatifList = jeuxEducatifRepository.findAll();
        assertThat(jeuxEducatifList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJeuxEducatif() throws Exception {
        // Initialize the database
        jeuxEducatifService.save(jeuxEducatif);

        int databaseSizeBeforeDelete = jeuxEducatifRepository.findAll().size();

        // Delete the jeuxEducatif
        restJeuxEducatifMockMvc.perform(delete("/api/jeux-educatifs/{id}", jeuxEducatif.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JeuxEducatif> jeuxEducatifList = jeuxEducatifRepository.findAll();
        assertThat(jeuxEducatifList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
