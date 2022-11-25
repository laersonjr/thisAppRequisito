import projeto from 'app/entities/projeto/projeto.reducer';
import requisito from 'app/entities/requisito/requisito.reducer';
import departamento from 'app/entities/departamento/departamento.reducer';
import funcionalidade from 'app/entities/funcionalidade/funcionalidade.reducer';
import recursoFuncionalidade from 'app/entities/recurso-funcionalidade/recurso-funcionalidade.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  projeto,
  requisito,
  departamento,
  funcionalidade,
  recursoFuncionalidade,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
