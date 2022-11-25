import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProjeto } from 'app/shared/model/projeto.model';
import { getEntity, updateEntity, createEntity, reset } from './projeto.reducer';

export const ProjetoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const projetoEntity = useAppSelector(state => state.projeto.entity);
  const loading = useAppSelector(state => state.projeto.loading);
  const updating = useAppSelector(state => state.projeto.updating);
  const updateSuccess = useAppSelector(state => state.projeto.updateSuccess);

  const handleClose = () => {
    navigate('/projeto' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...projetoEntity,
      ...values,
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
          ...projetoEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="thisAppRequisitoApp.projeto.home.createOrEditLabel" data-cy="ProjetoCreateUpdateHeading">
            <Translate contentKey="thisAppRequisitoApp.projeto.home.createOrEditLabel">Create or edit a Projeto</Translate>
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
                  id="projeto-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('thisAppRequisitoApp.projeto.nome')}
                id="projeto-nome"
                name="nome"
                data-cy="nome"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thisAppRequisitoApp.projeto.dataDeCriacao')}
                id="projeto-dataDeCriacao"
                name="dataDeCriacao"
                data-cy="dataDeCriacao"
                type="date"
              />
              <ValidatedField
                label={translate('thisAppRequisitoApp.projeto.dataDeInicio')}
                id="projeto-dataDeInicio"
                name="dataDeInicio"
                data-cy="dataDeInicio"
                type="date"
              />
              <ValidatedField
                label={translate('thisAppRequisitoApp.projeto.dataFim')}
                id="projeto-dataFim"
                name="dataFim"
                data-cy="dataFim"
                type="date"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/projeto" replace color="info">
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

export default ProjetoUpdate;
