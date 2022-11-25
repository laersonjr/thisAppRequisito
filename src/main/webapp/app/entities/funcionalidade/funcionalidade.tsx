import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFuncionalidade } from 'app/shared/model/funcionalidade.model';
import { getEntities } from './funcionalidade.reducer';

export const Funcionalidade = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const funcionalidadeList = useAppSelector(state => state.funcionalidade.entities);
  const loading = useAppSelector(state => state.funcionalidade.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="funcionalidade-heading" data-cy="FuncionalidadeHeading">
        <Translate contentKey="thisAppRequisitoApp.funcionalidade.home.title">Funcionalidades</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="thisAppRequisitoApp.funcionalidade.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/funcionalidade/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="thisAppRequisitoApp.funcionalidade.home.createLabel">Create new Funcionalidade</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {funcionalidadeList && funcionalidadeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="thisAppRequisitoApp.funcionalidade.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="thisAppRequisitoApp.funcionalidade.funcionalidadeProjeto">Funcionalidade Projeto</Translate>
                </th>
                <th>
                  <Translate contentKey="thisAppRequisitoApp.funcionalidade.projeto">Projeto</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {funcionalidadeList.map((funcionalidade, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/funcionalidade/${funcionalidade.id}`} color="link" size="sm">
                      {funcionalidade.id}
                    </Button>
                  </td>
                  <td>{funcionalidade.funcionalidadeProjeto}</td>
                  <td>
                    {funcionalidade.projeto ? <Link to={`/projeto/${funcionalidade.projeto.id}`}>{funcionalidade.projeto.nome}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/funcionalidade/${funcionalidade.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/funcionalidade/${funcionalidade.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/funcionalidade/${funcionalidade.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="thisAppRequisitoApp.funcionalidade.home.notFound">No Funcionalidades found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Funcionalidade;
