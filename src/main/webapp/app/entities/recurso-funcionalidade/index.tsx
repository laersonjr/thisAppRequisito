import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RecursoFuncionalidade from './recurso-funcionalidade';
import RecursoFuncionalidadeDetail from './recurso-funcionalidade-detail';
import RecursoFuncionalidadeUpdate from './recurso-funcionalidade-update';
import RecursoFuncionalidadeDeleteDialog from './recurso-funcionalidade-delete-dialog';

const RecursoFuncionalidadeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RecursoFuncionalidade />} />
    <Route path="new" element={<RecursoFuncionalidadeUpdate />} />
    <Route path=":id">
      <Route index element={<RecursoFuncionalidadeDetail />} />
      <Route path="edit" element={<RecursoFuncionalidadeUpdate />} />
      <Route path="delete" element={<RecursoFuncionalidadeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RecursoFuncionalidadeRoutes;
