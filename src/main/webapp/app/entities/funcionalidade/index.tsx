import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Funcionalidade from './funcionalidade';
import FuncionalidadeDetail from './funcionalidade-detail';
import FuncionalidadeUpdate from './funcionalidade-update';
import FuncionalidadeDeleteDialog from './funcionalidade-delete-dialog';

const FuncionalidadeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Funcionalidade />} />
    <Route path="new" element={<FuncionalidadeUpdate />} />
    <Route path=":id">
      <Route index element={<FuncionalidadeDetail />} />
      <Route path="edit" element={<FuncionalidadeUpdate />} />
      <Route path="delete" element={<FuncionalidadeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FuncionalidadeRoutes;
