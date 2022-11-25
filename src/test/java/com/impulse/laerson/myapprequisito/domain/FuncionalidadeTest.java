package com.impulse.laerson.myapprequisito.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.impulse.laerson.myapprequisito.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FuncionalidadeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Funcionalidade.class);
        Funcionalidade funcionalidade1 = new Funcionalidade();
        funcionalidade1.setId(1L);
        Funcionalidade funcionalidade2 = new Funcionalidade();
        funcionalidade2.setId(funcionalidade1.getId());
        assertThat(funcionalidade1).isEqualTo(funcionalidade2);
        funcionalidade2.setId(2L);
        assertThat(funcionalidade1).isNotEqualTo(funcionalidade2);
        funcionalidade1.setId(null);
        assertThat(funcionalidade1).isNotEqualTo(funcionalidade2);
    }
}
