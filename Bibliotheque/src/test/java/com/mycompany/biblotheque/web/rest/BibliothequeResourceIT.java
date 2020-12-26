package com.mycompany.biblotheque.web.rest;

import com.mycompany.biblotheque.BibliothequeApp;
import com.mycompany.biblotheque.domain.Bibliotheque;
import com.mycompany.biblotheque.repository.BibliothequeRepository;
import com.mycompany.biblotheque.service.BibliothequeService;

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
 * Integration tests for the {@link BibliothequeResource} REST controller.
 */
@SpringBootTest(classes = BibliothequeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BibliothequeResourceIT {

    private static final String DEFAULT_NOM_BIBLIO = "AAAAAAAAAA";
    private static final String UPDATED_NOM_BIBLIO = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    @Autowired
    private BibliothequeRepository bibliothequeRepository;

    @Autowired
    private BibliothequeService bibliothequeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBibliothequeMockMvc;

    private Bibliotheque bibliotheque;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bibliotheque createEntity(EntityManager em) {
        Bibliotheque bibliotheque = new Bibliotheque()
            .nomBiblio(DEFAULT_NOM_BIBLIO)
            .adresse(DEFAULT_ADRESSE);
        return bibliotheque;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bibliotheque createUpdatedEntity(EntityManager em) {
        Bibliotheque bibliotheque = new Bibliotheque()
            .nomBiblio(UPDATED_NOM_BIBLIO)
            .adresse(UPDATED_ADRESSE);
        return bibliotheque;
    }

    @BeforeEach
    public void initTest() {
        bibliotheque = createEntity(em);
    }

    @Test
    @Transactional
    public void createBibliotheque() throws Exception {
        int databaseSizeBeforeCreate = bibliothequeRepository.findAll().size();
        // Create the Bibliotheque
        restBibliothequeMockMvc.perform(post("/api/bibliotheques")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bibliotheque)))
            .andExpect(status().isCreated());

        // Validate the Bibliotheque in the database
        List<Bibliotheque> bibliothequeList = bibliothequeRepository.findAll();
        assertThat(bibliothequeList).hasSize(databaseSizeBeforeCreate + 1);
        Bibliotheque testBibliotheque = bibliothequeList.get(bibliothequeList.size() - 1);
        assertThat(testBibliotheque.getNomBiblio()).isEqualTo(DEFAULT_NOM_BIBLIO);
        assertThat(testBibliotheque.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
    }

    @Test
    @Transactional
    public void createBibliothequeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bibliothequeRepository.findAll().size();

        // Create the Bibliotheque with an existing ID
        bibliotheque.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBibliothequeMockMvc.perform(post("/api/bibliotheques")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bibliotheque)))
            .andExpect(status().isBadRequest());

        // Validate the Bibliotheque in the database
        List<Bibliotheque> bibliothequeList = bibliothequeRepository.findAll();
        assertThat(bibliothequeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBibliotheques() throws Exception {
        // Initialize the database
        bibliothequeRepository.saveAndFlush(bibliotheque);

        // Get all the bibliothequeList
        restBibliothequeMockMvc.perform(get("/api/bibliotheques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bibliotheque.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomBiblio").value(hasItem(DEFAULT_NOM_BIBLIO)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)));
    }
    
    @Test
    @Transactional
    public void getBibliotheque() throws Exception {
        // Initialize the database
        bibliothequeRepository.saveAndFlush(bibliotheque);

        // Get the bibliotheque
        restBibliothequeMockMvc.perform(get("/api/bibliotheques/{id}", bibliotheque.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bibliotheque.getId().intValue()))
            .andExpect(jsonPath("$.nomBiblio").value(DEFAULT_NOM_BIBLIO))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE));
    }
    @Test
    @Transactional
    public void getNonExistingBibliotheque() throws Exception {
        // Get the bibliotheque
        restBibliothequeMockMvc.perform(get("/api/bibliotheques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBibliotheque() throws Exception {
        // Initialize the database
        bibliothequeService.save(bibliotheque);

        int databaseSizeBeforeUpdate = bibliothequeRepository.findAll().size();

        // Update the bibliotheque
        Bibliotheque updatedBibliotheque = bibliothequeRepository.findById(bibliotheque.getId()).get();
        // Disconnect from session so that the updates on updatedBibliotheque are not directly saved in db
        em.detach(updatedBibliotheque);
        updatedBibliotheque
            .nomBiblio(UPDATED_NOM_BIBLIO)
            .adresse(UPDATED_ADRESSE);

        restBibliothequeMockMvc.perform(put("/api/bibliotheques")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBibliotheque)))
            .andExpect(status().isOk());

        // Validate the Bibliotheque in the database
        List<Bibliotheque> bibliothequeList = bibliothequeRepository.findAll();
        assertThat(bibliothequeList).hasSize(databaseSizeBeforeUpdate);
        Bibliotheque testBibliotheque = bibliothequeList.get(bibliothequeList.size() - 1);
        assertThat(testBibliotheque.getNomBiblio()).isEqualTo(UPDATED_NOM_BIBLIO);
        assertThat(testBibliotheque.getAdresse()).isEqualTo(UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void updateNonExistingBibliotheque() throws Exception {
        int databaseSizeBeforeUpdate = bibliothequeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBibliothequeMockMvc.perform(put("/api/bibliotheques")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bibliotheque)))
            .andExpect(status().isBadRequest());

        // Validate the Bibliotheque in the database
        List<Bibliotheque> bibliothequeList = bibliothequeRepository.findAll();
        assertThat(bibliothequeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBibliotheque() throws Exception {
        // Initialize the database
        bibliothequeService.save(bibliotheque);

        int databaseSizeBeforeDelete = bibliothequeRepository.findAll().size();

        // Delete the bibliotheque
        restBibliothequeMockMvc.perform(delete("/api/bibliotheques/{id}", bibliotheque.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bibliotheque> bibliothequeList = bibliothequeRepository.findAll();
        assertThat(bibliothequeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
