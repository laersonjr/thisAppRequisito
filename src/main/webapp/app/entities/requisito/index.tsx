import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Requisito from './requisito';
import RequisitoDetail from './requisito-detail';
import RequisitoUpdate from './requisito-update';
import RequisitoDeleteDialog from './requisito-delete-dialog';

const RequisitoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Requisito />} />
    <Route path="new" element={<RequisitoUpdate />} />
    <Route path=":id">
      <Route index element={<RequisitoDetail />} />
      <Route path="edit" element={<RequisitoUpdate />} />
      <Route path="delete" element={<RequisitoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RequisitoRoutes;
