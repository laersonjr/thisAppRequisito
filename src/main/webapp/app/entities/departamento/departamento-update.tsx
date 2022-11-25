import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProjeto } from 'app/shared/model/projeto.model';
import { getEntities as getProjetos } from 'app/entities/projeto/projeto.reducer';
import { IRecursoFuncionalidade } from 'app/shared/model/recurso-funcionalidade.model';
import { getEntities as getRecursoFuncionalidades } from 'app/entities/recurso-funcionalidade/recurso-funcionalidade.reducer';
import { IDepartamento } from 'app/shared/model/departamento.model';
import { getEntity, updateEntity, createEntity, reset } from './departamento.reducer';

export const DepartamentoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const projetos = useAppSelector(state => state.projeto.entities);
  const recursoFuncionalidades = useAppSelector(state => state.recursoFuncionalidade.entities);
  const departamentoEntity = useAppSelector(state => state.departamento.entity);
  const loading = useAppSelector(state => state.departamento.loading);
  const updating = useAppSelector(state => state.departamento.updating);
  const updateSuccess = useAppSelector(state => state.departamento.updateSuccess);

  const handleClose = () => {
    navigate('/departamento');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getProjetos({}));
    dispatch(getRecursoFuncionalidades({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...departamentoEntity,
      ...values,
      projeto: projetos.find(it => it.id.toString() === values.projeto.toString()),
      departamento: recursoFuncionalidades.find(it => it.id.toString() === values.departamento.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...departamentoEntity,
          projeto: departamentoEntity?.projeto?.id,
          departamento: departamentoEntity?.departamento?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="thisAppRequisitoApp.departamento.home.createOrEditLabel" data-cy="DepartamentoCreateUpdateHeading">
            <Translate contentKey="thisAppRequisitoApp.departamento.home.createOrEditLabel">Create or edit a Departamento</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="departamento-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('thisAppRequisitoApp.departamento.nome')}
                id="departamento-nome"
                name="nome"
                data-cy="nome"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="departamento-projeto"
                name="projeto"
                data-cy="projeto"
                label={translate('thisAppRequisitoApp.departamento.projeto')}
                type="select"
              >
                <option value="" key="0" />
                {projetos
                  ? projetos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nome}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="departamento-departamento"
                name="departamento"
                data-cy="departamento"
                label={translate('thisAppRequisitoApp.departamento.departamento')}
                type="select"
              >
                <option value="" key="0" />
                {recursoFuncionalidades
                  ? recursoFuncionalidades.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nome}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/departamento" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default DepartamentoUpdate;
