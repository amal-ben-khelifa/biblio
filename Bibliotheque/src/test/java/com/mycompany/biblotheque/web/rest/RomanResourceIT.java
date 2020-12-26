package com.mycompany.biblotheque.web.rest;

import com.mycompany.biblotheque.BibliothequeApp;
import com.mycompany.biblotheque.domain.Roman;
import com.mycompany.biblotheque.repository.RomanRepository;

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
 * Integration tests for the {@link RomanResource} REST controller.
 */
@SpringBootTest(classes = BibliothequeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RomanResourceIT {

    private static final String DEFAULT_STREET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_STREET_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_PROVINCE = "BBBBBBBBBB";

    @Autowired
    private RomanRepository romanRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRomanMockMvc;

    private Roman roman;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Roman createEntity(EntityManager em) {
        Roman roman = new Roman()
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .postalCode(DEFAULT_POSTAL_CODE)
            .city(DEFAULT_CITY)
            .stateProvince(DEFAULT_STATE_PROVINCE);
        return roman;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Roman createUpdatedEntity(EntityManager em) {
        Roman roman = new Roman()
            .streetAddress(UPDATED_STREET_ADDRESS)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE);
        return roman;
    }

    @BeforeEach
    public void initTest() {
        roman = createEntity(em);
    }

    @Test
    @Transactional
    public void createRoman() throws Exception {
        int databaseSizeBeforeCreate = romanRepository.findAll().size();
        // Create the Roman
        restRomanMockMvc.perform(post("/api/romen")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(roman)))
            .andExpect(status().isCreated());

        // Validate the Roman in the database
        List<Roman> romanList = romanRepository.findAll();
        assertThat(romanList).hasSize(databaseSizeBeforeCreate + 1);
        Roman testRoman = romanList.get(romanList.size() - 1);
        assertThat(testRoman.getStreetAddress()).isEqualTo(DEFAULT_STREET_ADDRESS);
        assertThat(testRoman.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testRoman.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testRoman.getStateProvince()).isEqualTo(DEFAULT_STATE_PROVINCE);
    }

    @Test
    @Transactional
    public void createRomanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = romanRepository.findAll().size();

        // Create the Roman with an existing ID
        roman.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRomanMockMvc.perform(post("/api/romen")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(roman)))
            .andExpect(status().isBadRequest());

        // Validate the Roman in the database
        List<Roman> romanList = romanRepository.findAll();
        assertThat(romanList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRomen() throws Exception {
        // Initialize the database
        romanRepository.saveAndFlush(roman);

        // Get all the romanList
        restRomanMockMvc.perform(get("/api/romen?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roman.getId().intValue())))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE)));
    }
    
    @Test
    @Transactional
    public void getRoman() throws Exception {
        // Initialize the database
        romanRepository.saveAndFlush(roman);

        // Get the roman
        restRomanMockMvc.perform(get("/api/romen/{id}", roman.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(roman.getId().intValue()))
            .andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.stateProvince").value(DEFAULT_STATE_PROVINCE));
    }
    @Test
    @Transactional
    public void getNonExistingRoman() throws Exception {
        // Get the roman
        restRomanMockMvc.perform(get("/api/romen/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRoman() throws Exception {
        // Initialize the database
        romanRepository.saveAndFlush(roman);

        int databaseSizeBeforeUpdate = romanRepository.findAll().size();

        // Update the roman
        Roman updatedRoman = romanRepository.findById(roman.getId()).get();
        // Disconnect from session so that the updates on updatedRoman are not directly saved in db
        em.detach(updatedRoman);
        updatedRoman
            .streetAddress(UPDATED_STREET_ADDRESS)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE);

        restRomanMockMvc.perform(put("/api/romen")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRoman)))
            .andExpect(status().isOk());

        // Validate the Roman in the database
        List<Roman> romanList = romanRepository.findAll();
        assertThat(romanList).hasSize(databaseSizeBeforeUpdate);
        Roman testRoman = romanList.get(romanList.size() - 1);
        assertThat(testRoman.getStreetAddress()).isEqualTo(UPDATED_STREET_ADDRESS);
        assertThat(testRoman.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testRoman.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testRoman.getStateProvince()).isEqualTo(UPDATED_STATE_PROVINCE);
    }

    @Test
    @Transactional
    public void updateNonExistingRoman() throws Exception {
        int databaseSizeBeforeUpdate = romanRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRomanMockMvc.perform(put("/api/romen")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(roman)))
            .andExpect(status().isBadRequest());

        // Validate the Roman in the database
        List<Roman> romanList = romanRepository.findAll();
        assertThat(romanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRoman() throws Exception {
        // Initialize the database
        romanRepository.saveAndFlush(roman);

        int databaseSizeBeforeDelete = romanRepository.findAll().size();

        // Delete the roman
        restRomanMockMvc.perform(delete("/api/romen/{id}", roman.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Roman> romanList = romanRepository.findAll();
        assertThat(romanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
