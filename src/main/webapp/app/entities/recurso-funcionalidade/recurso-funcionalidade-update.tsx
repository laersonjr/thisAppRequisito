import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFuncionalidade } from 'app/shared/model/funcionalidade.model';
import { getEntities as getFuncionalidades } from 'app/entities/funcionalidade/funcionalidade.reducer';
import { IRecursoFuncionalidade } from 'app/shared/model/recurso-funcionalidade.model';
import { Prioridade } from 'app/shared/model/enumerations/prioridade.model';
import { Complexibilidade } from 'app/shared/model/enumerations/complexibilidade.model';
import { Status } from 'app/shared/model/enumerations/status.model';
import { getEntity, updateEntity, createEntity, reset } from './recurso-funcionalidade.reducer';

export const RecursoFuncionalidadeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const funcionalidades = useAppSelector(state => state.funcionalidade.entities);
  const recursoFuncionalidadeEntity = useAppSelector(state => state.recursoFuncionalidade.entity);
  const loading = useAppSelector(state => state.recursoFuncionalidade.loading);
  const updating = useAppSelector(state => state.recursoFuncionalidade.updating);
  const updateSuccess = useAppSelector(state => state.recursoFuncionalidade.updateSuccess);
  const prioridadeValues = Object.keys(Prioridade);
  const complexibilidadeValues = Object.keys(Complexibilidade);
  const statusValues = Object.keys(Status);

  const handleClose = () => {
    navigate('/recurso-funcionalidade' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getFuncionalidades({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...recursoFuncionalidadeEntity,
      ...values,
      funcionalidade: funcionalidades.find(it => it.id.toString() === values.funcionalidade.toString()),
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
          prioridade: 'BAIXA',
          complexibilidade: 'BAIXA',
          status: 'AGUARDANDO',
          ...recursoFuncionalidadeEntity,
          funcionalidade: recursoFuncionalidadeEntity?.funcionalidade?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="thisAppRequisitoApp.recursoFuncionalidade.home.createOrEditLabel" data-cy="RecursoFuncionalidadeCreateUpdateHeading">
            <Translate contentKey="thisAppRequisitoApp.recursoFuncionalidade.home.createOrEditLabel">
              Create or edit a RecursoFuncionalidade
            </Translate>
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
                  id="recurso-funcionalidade-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('thisAppRequisitoApp.recursoFuncionalidade.idrf')}
                id="recurso-funcionalidade-idrf"
                name="idrf"
                data-cy="idrf"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thisAppRequisitoApp.recursoFuncionalidade.descricaoRequisito')}
                id="recurso-funcionalidade-descricaoRequisito"
                name="descricaoRequisito"
                data-cy="descricaoRequisito"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thisAppRequisitoApp.recursoFuncionalidade.prioridade')}
                id="recurso-funcionalidade-prioridade"
                name="prioridade"
                data-cy="prioridade"
                type="select"
              >
                {prioridadeValues.map(prioridade => (
                  <option value={prioridade} key={prioridade}>
                    {translate('thisAppRequisitoApp.Prioridade.' + prioridade)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('thisAppRequisitoApp.recursoFuncionalidade.complexibilidade')}
                id="recurso-funcionalidade-complexibilidade"
                name="complexibilidade"
                data-cy="complexibilidade"
                type="select"
              >
                {complexibilidadeValues.map(complexibilidade => (
                  <option value={complexibilidade} key={complexibilidade}>
                    {translate('thisAppRequisitoApp.Complexibilidade.' + complexibilidade)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('thisAppRequisitoApp.recursoFuncionalidade.esforco')}
                id="recurso-funcionalidade-esforco"
                name="esforco"
                data-cy="esforco"
                type="text"
              />
              <ValidatedField
                label={translate('thisAppRequisitoApp.recursoFuncionalidade.status')}
                id="recurso-funcionalidade-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {statusValues.map(status => (
                  <option value={status} key={status}>
                    {translate('thisAppRequisitoApp.Status.' + status)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="recurso-funcionalidade-funcionalidade"
                name="funcionalidade"
                data-cy="funcionalidade"
                label={translate('thisAppRequisitoApp.recursoFuncionalidade.funcionalidade')}
                type="select"
              >
                <option value="" key="0" />
                {funcionalidades
                  ? funcionalidades.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.funcionalidadeProjeto}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/recurso-funcionalidade" replace color="info">
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

export default RecursoFuncionalidadeUpdate;
