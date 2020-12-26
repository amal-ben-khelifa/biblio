package com.mycompany.biblotheque.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.biblotheque.web.rest.TestUtil;

public class LivresTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Livres.class);
        Livres livres1 = new Livres();
        livres1.setId(1L);
        Livres livres2 = new Livres();
        livres2.setId(livres1.getId());
        assertThat(livres1).isEqualTo(livres2);
        livres2.setId(2L);
        assertThat(livres1).isNotEqualTo(livres2);
        livres1.setId(null);
        assertThat(livres1).isNotEqualTo(livres2);
    }
}
