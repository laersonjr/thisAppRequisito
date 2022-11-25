import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './projeto.reducer';

export const ProjetoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const projetoEntity = useAppSelector(state => state.projeto.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="projetoDetailsHeading">
          <Translate contentKey="thisAppRequisitoApp.projeto.detail.title">Projeto</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{projetoEntity.id}</dd>
          <dt>
            <span id="nome">
              <Translate contentKey="thisAppRequisitoApp.projeto.nome">Nome</Translate>
            </span>
          </dt>
          <dd>{projetoEntity.nome}</dd>
          <dt>
            <span id="dataDeCriacao">
              <Translate contentKey="thisAppRequisitoApp.projeto.dataDeCriacao">Data De Criacao</Translate>
            </span>
          </dt>
          <dd>
            {projetoEntity.dataDeCriacao ? (
              <TextFormat value={projetoEntity.dataDeCriacao} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="dataDeInicio">
              <Translate contentKey="thisAppRequisitoApp.projeto.dataDeInicio">Data De Inicio</Translate>
            </span>
          </dt>
          <dd>
            {projetoEntity.dataDeInicio ? (
              <TextFormat value={projetoEntity.dataDeInicio} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="dataFim">
              <Translate contentKey="thisAppRequisitoApp.projeto.dataFim">Data Fim</Translate>
            </span>
          </dt>
          <dd>{projetoEntity.dataFim ? <TextFormat value={projetoEntity.dataFim} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/projeto" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/projeto/${projetoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProjetoDetail;
