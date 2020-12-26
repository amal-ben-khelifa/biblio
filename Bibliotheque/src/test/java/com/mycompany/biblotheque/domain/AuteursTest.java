package com.mycompany.biblotheque.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.biblotheque.web.rest.TestUtil;

public class AuteursTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Auteurs.class);
        Auteurs auteurs1 = new Auteurs();
        auteurs1.setId(1L);
        Auteurs auteurs2 = new Auteurs();
        auteurs2.setId(auteurs1.getId());
        assertThat(auteurs1).isEqualTo(auteurs2);
        auteurs2.setId(2L);
        assertThat(auteurs1).isNotEqualTo(auteurs2);
        auteurs1.setId(null);
        assertThat(auteurs1).isNotEqualTo(auteurs2);
    }
}
