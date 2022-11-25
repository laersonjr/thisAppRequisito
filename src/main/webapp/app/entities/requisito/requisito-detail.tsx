import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './requisito.reducer';

export const RequisitoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const requisitoEntity = useAppSelector(state => state.requisito.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="requisitoDetailsHeading">
          <Translate contentKey="thisAppRequisitoApp.requisito.detail.title">Requisito</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{requisitoEntity.id}</dd>
          <dt>
            <span id="nome">
              <Translate contentKey="thisAppRequisitoApp.requisito.nome">Nome</Translate>
            </span>
          </dt>
          <dd>{requisitoEntity.nome}</dd>
          <dt>
            <Translate contentKey="thisAppRequisitoApp.requisito.projeto">Projeto</Translate>
          </dt>
          <dd>{requisitoEntity.projeto ? requisitoEntity.projeto.nome : ''}</dd>
          <dt>
            <Translate contentKey="thisAppRequisitoApp.requisito.departamento">Departamento</Translate>
          </dt>
          <dd>{requisitoEntity.departamento ? requisitoEntity.departamento.nome : ''}</dd>
        </dl>
        <Button tag={Link} to="/requisito" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/requisito/${requisitoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RequisitoDetail;
