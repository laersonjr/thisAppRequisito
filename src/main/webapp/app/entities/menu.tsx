import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/projeto">
        <Translate contentKey="global.menu.entities.projeto" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/requisito">
        <Translate contentKey="global.menu.entities.requisito" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/departamento">
        <Translate contentKey="global.menu.entities.departamento" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/funcionalidade">
        <Translate contentKey="global.menu.entities.funcionalidade" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/recurso-funcionalidade">
        <Translate contentKey="global.menu.entities.recursoFuncionalidade" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
