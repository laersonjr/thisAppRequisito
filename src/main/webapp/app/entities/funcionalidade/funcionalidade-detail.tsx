import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './funcionalidade.reducer';

export const FuncionalidadeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const funcionalidadeEntity = useAppSelector(state => state.funcionalidade.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="funcionalidadeDetailsHeading">
          <Translate contentKey="thisAppRequisitoApp.funcionalidade.detail.title">Funcionalidade</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{funcionalidadeEntity.id}</dd>
          <dt>
            <span id="funcionalidadeProjeto">
              <Translate contentKey="thisAppRequisitoApp.funcionalidade.funcionalidadeProjeto">Funcionalidade Projeto</Translate>
            </span>
          </dt>
          <dd>{funcionalidadeEntity.funcionalidadeProjeto}</dd>
          <dt>
            <Translate contentKey="thisAppRequisitoApp.funcionalidade.projeto">Projeto</Translate>
          </dt>
          <dd>{funcionalidadeEntity.projeto ? funcionalidadeEntity.projeto.nome : ''}</dd>
        </dl>
        <Button tag={Link} to="/funcionalidade" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/funcionalidade/${funcionalidadeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FuncionalidadeDetail;
