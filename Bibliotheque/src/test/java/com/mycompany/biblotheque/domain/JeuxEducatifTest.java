package com.mycompany.biblotheque.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.biblotheque.web.rest.TestUtil;

public class JeuxEducatifTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JeuxEducatif.class);
        JeuxEducatif jeuxEducatif1 = new JeuxEducatif();
        jeuxEducatif1.setId(1L);
        JeuxEducatif jeuxEducatif2 = new JeuxEducatif();
        jeuxEducatif2.setId(jeuxEducatif1.getId());
        assertThat(jeuxEducatif1).isEqualTo(jeuxEducatif2);
        jeuxEducatif2.setId(2L);
        assertThat(jeuxEducatif1).isNotEqualTo(jeuxEducatif2);
        jeuxEducatif1.setId(null);
        assertThat(jeuxEducatif1).isNotEqualTo(jeuxEducatif2);
    }
}
