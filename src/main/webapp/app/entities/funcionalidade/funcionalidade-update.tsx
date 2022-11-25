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
import { IFuncionalidade } from 'app/shared/model/funcionalidade.model';
import { getEntity, updateEntity, createEntity, reset } from './funcionalidade.reducer';

export const FuncionalidadeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const projetos = useAppSelector(state => state.projeto.entities);
  const funcionalidadeEntity = useAppSelector(state => state.funcionalidade.entity);
  const loading = useAppSelector(state => state.funcionalidade.loading);
  const updating = useAppSelector(state => state.funcionalidade.updating);
  const updateSuccess = useAppSelector(state => state.funcionalidade.updateSuccess);

  const handleClose = () => {
    navigate('/funcionalidade');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProjetos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...funcionalidadeEntity,
      ...values,
      projeto: projetos.find(it => it.id.toString() === values.projeto.toString()),
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
          ...funcionalidadeEntity,
          projeto: funcionalidadeEntity?.projeto?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="thisAppRequisitoApp.funcionalidade.home.createOrEditLabel" data-cy="FuncionalidadeCreateUpdateHeading">
            <Translate contentKey="thisAppRequisitoApp.funcionalidade.home.createOrEditLabel">Create or edit a Funcionalidade</Translate>
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
                  id="funcionalidade-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('thisAppRequisitoApp.funcionalidade.funcionalidadeProjeto')}
                id="funcionalidade-funcionalidadeProjeto"
                name="funcionalidadeProjeto"
                data-cy="funcionalidadeProjeto"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="funcionalidade-projeto"
                name="projeto"
                data-cy="projeto"
                label={translate('thisAppRequisitoApp.funcionalidade.projeto')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/funcionalidade" replace color="info">
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

export default FuncionalidadeUpdate;
