import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Projeto from './projeto';
import ProjetoDetail from './projeto-detail';
import ProjetoUpdate from './projeto-update';
import ProjetoDeleteDialog from './projeto-delete-dialog';

const ProjetoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Projeto />} />
    <Route path="new" element={<ProjetoUpdate />} />
    <Route path=":id">
      <Route index element={<ProjetoDetail />} />
      <Route path="edit" element={<ProjetoUpdate />} />
      <Route path="delete" element={<ProjetoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProjetoRoutes;
