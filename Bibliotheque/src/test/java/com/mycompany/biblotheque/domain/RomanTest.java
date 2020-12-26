package com.mycompany.biblotheque.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.biblotheque.web.rest.TestUtil;

public class RomanTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Roman.class);
        Roman roman1 = new Roman();
        roman1.setId(1L);
        Roman roman2 = new Roman();
        roman2.setId(roman1.getId());
        assertThat(roman1).isEqualTo(roman2);
        roman2.setId(2L);
        assertThat(roman1).isNotEqualTo(roman2);
        roman1.setId(null);
        assertThat(roman1).isNotEqualTo(roman2);
    }
}
