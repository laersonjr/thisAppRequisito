import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Projeto from './projeto';
import Requisito from './requisito';
import Departamento from './departamento';
import Funcionalidade from './funcionalidade';
import RecursoFuncionalidade from './recurso-funcionalidade';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="projeto/*" element={<Projeto />} />
        <Route path="requisito/*" element={<Requisito />} />
        <Route path="departamento/*" element={<Departamento />} />
        <Route path="funcionalidade/*" element={<Funcionalidade />} />
        <Route path="recurso-funcionalidade/*" element={<RecursoFuncionalidade />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
