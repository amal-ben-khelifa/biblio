package com.mycompany.biblotheque.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.biblotheque.web.rest.TestUtil;

public class BibliothequeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bibliotheque.class);
        Bibliotheque bibliotheque1 = new Bibliotheque();
        bibliotheque1.setId(1L);
        Bibliotheque bibliotheque2 = new Bibliotheque();
        bibliotheque2.setId(bibliotheque1.getId());
        assertThat(bibliotheque1).isEqualTo(bibliotheque2);
        bibliotheque2.setId(2L);
        assertThat(bibliotheque1).isNotEqualTo(bibliotheque2);
        bibliotheque1.setId(null);
        assertThat(bibliotheque1).isNotEqualTo(bibliotheque2);
    }
}
