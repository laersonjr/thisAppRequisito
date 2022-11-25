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
import { IRequisito } from 'app/shared/model/requisito.model';
import { getEntity, updateEntity, createEntity, reset } from './requisito.reducer';

export const RequisitoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const projetos = useAppSelector(state => state.projeto.entities);
  const recursoFuncionalidades = useAppSelector(state => state.recursoFuncionalidade.entities);
  const requisitoEntity = useAppSelector(state => state.requisito.entity);
  const loading = useAppSelector(state => state.requisito.loading);
  const updating = useAppSelector(state => state.requisito.updating);
  const updateSuccess = useAppSelector(state => state.requisito.updateSuccess);

  const handleClose = () => {
    navigate('/requisito');
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
      ...requisitoEntity,
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
          ...requisitoEntity,
          projeto: requisitoEntity?.projeto?.id,
          departamento: requisitoEntity?.departamento?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="thisAppRequisitoApp.requisito.home.createOrEditLabel" data-cy="RequisitoCreateUpdateHeading">
            <Translate contentKey="thisAppRequisitoApp.requisito.home.createOrEditLabel">Create or edit a Requisito</Translate>
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
                  id="requisito-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('thisAppRequisitoApp.requisito.nome')}
                id="requisito-nome"
                name="nome"
                data-cy="nome"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="requisito-projeto"
                name="projeto"
                data-cy="projeto"
                label={translate('thisAppRequisitoApp.requisito.projeto')}
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
                id="requisito-departamento"
                name="departamento"
                data-cy="departamento"
                label={translate('thisAppRequisitoApp.requisito.departamento')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/requisito" replace color="info">
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

export default RequisitoUpdate;
