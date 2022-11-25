package com.impulse.laerson.myapprequisito.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.impulse.laerson.myapprequisito.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RecursoFuncionalidadeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecursoFuncionalidade.class);
        RecursoFuncionalidade recursoFuncionalidade1 = new RecursoFuncionalidade();
        recursoFuncionalidade1.setId(1L);
        RecursoFuncionalidade recursoFuncionalidade2 = new RecursoFuncionalidade();
        recursoFuncionalidade2.setId(recursoFuncionalidade1.getId());
        assertThat(recursoFuncionalidade1).isEqualTo(recursoFuncionalidade2);
        recursoFuncionalidade2.setId(2L);
        assertThat(recursoFuncionalidade1).isNotEqualTo(recursoFuncionalidade2);
        recursoFuncionalidade1.setId(null);
        assertThat(recursoFuncionalidade1).isNotEqualTo(recursoFuncionalidade2);
    }
}
