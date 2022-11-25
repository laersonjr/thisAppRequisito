package com.impulse.laerson.myapprequisito.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.impulse.laerson.myapprequisito.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RequisitoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Requisito.class);
        Requisito requisito1 = new Requisito();
        requisito1.setId(1L);
        Requisito requisito2 = new Requisito();
        requisito2.setId(requisito1.getId());
        assertThat(requisito1).isEqualTo(requisito2);
        requisito2.setId(2L);
        assertThat(requisito1).isNotEqualTo(requisito2);
        requisito1.setId(null);
        assertThat(requisito1).isNotEqualTo(requisito2);
    }
}
