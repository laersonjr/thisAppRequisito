import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './recurso-funcionalidade.reducer';

export const RecursoFuncionalidadeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const recursoFuncionalidadeEntity = useAppSelector(state => state.recursoFuncionalidade.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="recursoFuncionalidadeDetailsHeading">
          <Translate contentKey="thisAppRequisitoApp.recursoFuncionalidade.detail.title">RecursoFuncionalidade</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{recursoFuncionalidadeEntity.id}</dd>
          <dt>
            <span id="idrf">
              <Translate contentKey="thisAppRequisitoApp.recursoFuncionalidade.idrf">Idrf</Translate>
            </span>
          </dt>
          <dd>{recursoFuncionalidadeEntity.idrf}</dd>
          <dt>
            <span id="descricaoRequisito">
              <Translate contentKey="thisAppRequisitoApp.recursoFuncionalidade.descricaoRequisito">Descricao Requisito</Translate>
            </span>
          </dt>
          <dd>{recursoFuncionalidadeEntity.descricaoRequisito}</dd>
          <dt>
            <span id="prioridade">
              <Translate contentKey="thisAppRequisitoApp.recursoFuncionalidade.prioridade">Prioridade</Translate>
            </span>
          </dt>
          <dd>{recursoFuncionalidadeEntity.prioridade}</dd>
          <dt>
            <span id="complexibilidade">
              <Translate contentKey="thisAppRequisitoApp.recursoFuncionalidade.complexibilidade">Complexibilidade</Translate>
            </span>
          </dt>
          <dd>{recursoFuncionalidadeEntity.complexibilidade}</dd>
          <dt>
            <span id="esforco">
              <Translate contentKey="thisAppRequisitoApp.recursoFuncionalidade.esforco">Esforco</Translate>
            </span>
          </dt>
          <dd>{recursoFuncionalidadeEntity.esforco}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="thisAppRequisitoApp.recursoFuncionalidade.status">Status</Translate>
            </span>
          </dt>
          <dd>{recursoFuncionalidadeEntity.status}</dd>
          <dt>
            <Translate contentKey="thisAppRequisitoApp.recursoFuncionalidade.funcionalidade">Funcionalidade</Translate>
          </dt>
          <dd>{recursoFuncionalidadeEntity.funcionalidade ? recursoFuncionalidadeEntity.funcionalidade.funcionalidadeProjeto : ''}</dd>
        </dl>
        <Button tag={Link} to="/recurso-funcionalidade" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/recurso-funcionalidade/${recursoFuncionalidadeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RecursoFuncionalidadeDetail;
