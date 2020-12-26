package com.mycompany.biblotheque.web.rest;

import com.mycompany.biblotheque.BibliothequeApp;
import com.mycompany.biblotheque.domain.Livres;
import com.mycompany.biblotheque.repository.LivresRepository;
import com.mycompany.biblotheque.service.LivresService;

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
 * Integration tests for the {@link LivresResource} REST controller.
 */
@SpringBootTest(classes = BibliothequeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LivresResourceIT {

    private static final String DEFAULT_NOM_LIVRE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_LIVRE = "BBBBBBBBBB";

    private static final String DEFAULT_GENRE = "AAAAAAAAAA";
    private static final String UPDATED_GENRE = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBREDEPAGES = "AAAAAAAAAA";
    private static final String UPDATED_NOMBREDEPAGES = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUES = "AAAAAAAAAA";
    private static final String UPDATED_LANGUES = "BBBBBBBBBB";

    private static final String DEFAULT_PRIX = "AAAAAAAAAA";
    private static final String UPDATED_PRIX = "BBBBBBBBBB";

    @Autowired
    private LivresRepository livresRepository;

    @Autowired
    private LivresService livresService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLivresMockMvc;

    private Livres livres;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Livres createEntity(EntityManager em) {
        Livres livres = new Livres()
            .nomLivre(DEFAULT_NOM_LIVRE)
            .genre(DEFAULT_GENRE)
            .nombredepages(DEFAULT_NOMBREDEPAGES)
            .langues(DEFAULT_LANGUES)
            .prix(DEFAULT_PRIX);
        return livres;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Livres createUpdatedEntity(EntityManager em) {
        Livres livres = new Livres()
            .nomLivre(UPDATED_NOM_LIVRE)
            .genre(UPDATED_GENRE)
            .nombredepages(UPDATED_NOMBREDEPAGES)
            .langues(UPDATED_LANGUES)
            .prix(UPDATED_PRIX);
        return livres;
    }

    @BeforeEach
    public void initTest() {
        livres = createEntity(em);
    }

    @Test
    @Transactional
    public void createLivres() throws Exception {
        int databaseSizeBeforeCreate = livresRepository.findAll().size();
        // Create the Livres
        restLivresMockMvc.perform(post("/api/livres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(livres)))
            .andExpect(status().isCreated());

        // Validate the Livres in the database
        List<Livres> livresList = livresRepository.findAll();
        assertThat(livresList).hasSize(databaseSizeBeforeCreate + 1);
        Livres testLivres = livresList.get(livresList.size() - 1);
        assertThat(testLivres.getNomLivre()).isEqualTo(DEFAULT_NOM_LIVRE);
        assertThat(testLivres.getGenre()).isEqualTo(DEFAULT_GENRE);
        assertThat(testLivres.getNombredepages()).isEqualTo(DEFAULT_NOMBREDEPAGES);
        assertThat(testLivres.getLangues()).isEqualTo(DEFAULT_LANGUES);
        assertThat(testLivres.getPrix()).isEqualTo(DEFAULT_PRIX);
    }

    @Test
    @Transactional
    public void createLivresWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = livresRepository.findAll().size();

        // Create the Livres with an existing ID
        livres.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLivresMockMvc.perform(post("/api/livres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(livres)))
            .andExpect(status().isBadRequest());

        // Validate the Livres in the database
        List<Livres> livresList = livresRepository.findAll();
        assertThat(livresList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLivres() throws Exception {
        // Initialize the database
        livresRepository.saveAndFlush(livres);

        // Get all the livresList
        restLivresMockMvc.perform(get("/api/livres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(livres.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomLivre").value(hasItem(DEFAULT_NOM_LIVRE)))
            .andExpect(jsonPath("$.[*].genre").value(hasItem(DEFAULT_GENRE)))
            .andExpect(jsonPath("$.[*].nombredepages").value(hasItem(DEFAULT_NOMBREDEPAGES)))
            .andExpect(jsonPath("$.[*].langues").value(hasItem(DEFAULT_LANGUES)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX)));
    }
    
    @Test
    @Transactional
    public void getLivres() throws Exception {
        // Initialize the database
        livresRepository.saveAndFlush(livres);

        // Get the livres
        restLivresMockMvc.perform(get("/api/livres/{id}", livres.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(livres.getId().intValue()))
            .andExpect(jsonPath("$.nomLivre").value(DEFAULT_NOM_LIVRE))
            .andExpect(jsonPath("$.genre").value(DEFAULT_GENRE))
            .andExpect(jsonPath("$.nombredepages").value(DEFAULT_NOMBREDEPAGES))
            .andExpect(jsonPath("$.langues").value(DEFAULT_LANGUES))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX));
    }
    @Test
    @Transactional
    public void getNonExistingLivres() throws Exception {
        // Get the livres
        restLivresMockMvc.perform(get("/api/livres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLivres() throws Exception {
        // Initialize the database
        livresService.save(livres);

        int databaseSizeBeforeUpdate = livresRepository.findAll().size();

        // Update the livres
        Livres updatedLivres = livresRepository.findById(livres.getId()).get();
        // Disconnect from session so that the updates on updatedLivres are not directly saved in db
        em.detach(updatedLivres);
        updatedLivres
            .nomLivre(UPDATED_NOM_LIVRE)
            .genre(UPDATED_GENRE)
            .nombredepages(UPDATED_NOMBREDEPAGES)
            .langues(UPDATED_LANGUES)
            .prix(UPDATED_PRIX);

        restLivresMockMvc.perform(put("/api/livres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLivres)))
            .andExpect(status().isOk());

        // Validate the Livres in the database
        List<Livres> livresList = livresRepository.findAll();
        assertThat(livresList).hasSize(databaseSizeBeforeUpdate);
        Livres testLivres = livresList.get(livresList.size() - 1);
        assertThat(testLivres.getNomLivre()).isEqualTo(UPDATED_NOM_LIVRE);
        assertThat(testLivres.getGenre()).isEqualTo(UPDATED_GENRE);
        assertThat(testLivres.getNombredepages()).isEqualTo(UPDATED_NOMBREDEPAGES);
        assertThat(testLivres.getLangues()).isEqualTo(UPDATED_LANGUES);
        assertThat(testLivres.getPrix()).isEqualTo(UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void updateNonExistingLivres() throws Exception {
        int databaseSizeBeforeUpdate = livresRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLivresMockMvc.perform(put("/api/livres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(livres)))
            .andExpect(status().isBadRequest());

        // Validate the Livres in the database
        List<Livres> livresList = livresRepository.findAll();
        assertThat(livresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLivres() throws Exception {
        // Initialize the database
        livresService.save(livres);

        int databaseSizeBeforeDelete = livresRepository.findAll().size();

        // Delete the livres
        restLivresMockMvc.perform(delete("/api/livres/{id}", livres.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Livres> livresList = livresRepository.findAll();
        assertThat(livresList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
