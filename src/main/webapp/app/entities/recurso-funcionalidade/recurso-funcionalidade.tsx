import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRecursoFuncionalidade } from 'app/shared/model/recurso-funcionalidade.model';
import { getEntities } from './recurso-funcionalidade.reducer';

export const RecursoFuncionalidade = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const recursoFuncionalidadeList = useAppSelector(state => state.recursoFuncionalidade.entities);
  const loading = useAppSelector(state => state.recursoFuncionalidade.loading);
  const totalItems = useAppSelector(state => state.recursoFuncionalidade.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  return (
    <div>
      <h2 id="recurso-funcionalidade-heading" data-cy="RecursoFuncionalidadeHeading">
        <Translate contentKey="thisAppRequisitoApp.recursoFuncionalidade.home.title">Recurso Funcionalidades</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="thisAppRequisitoApp.recursoFuncionalidade.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/recurso-funcionalidade/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="thisAppRequisitoApp.recursoFuncionalidade.home.createLabel">Create new Recurso Funcionalidade</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {recursoFuncionalidadeList && recursoFuncionalidadeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="thisAppRequisitoApp.recursoFuncionalidade.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('idrf')}>
                  <Translate contentKey="thisAppRequisitoApp.recursoFuncionalidade.idrf">Idrf</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('descricaoRequisito')}>
                  <Translate contentKey="thisAppRequisitoApp.recursoFuncionalidade.descricaoRequisito">Descricao Requisito</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('prioridade')}>
                  <Translate contentKey="thisAppRequisitoApp.recursoFuncionalidade.prioridade">Prioridade</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('complexibilidade')}>
                  <Translate contentKey="thisAppRequisitoApp.recursoFuncionalidade.complexibilidade">Complexibilidade</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('esforco')}>
                  <Translate contentKey="thisAppRequisitoApp.recursoFuncionalidade.esforco">Esforco</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="thisAppRequisitoApp.recursoFuncionalidade.status">Status</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thisAppRequisitoApp.recursoFuncionalidade.funcionalidade">Funcionalidade</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {recursoFuncionalidadeList.map((recursoFuncionalidade, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/recurso-funcionalidade/${recursoFuncionalidade.id}`} color="link" size="sm">
                      {recursoFuncionalidade.id}
                    </Button>
                  </td>
                  <td>{recursoFuncionalidade.idrf}</td>
                  <td>{recursoFuncionalidade.descricaoRequisito}</td>
                  <td>
                    <Translate contentKey={`thisAppRequisitoApp.Prioridade.${recursoFuncionalidade.prioridade}`} />
                  </td>
                  <td>
                    <Translate contentKey={`thisAppRequisitoApp.Complexibilidade.${recursoFuncionalidade.complexibilidade}`} />
                  </td>
                  <td>{recursoFuncionalidade.esforco}</td>
                  <td>
                    <Translate contentKey={`thisAppRequisitoApp.Status.${recursoFuncionalidade.status}`} />
                  </td>
                  <td>
                    {recursoFuncionalidade.funcionalidade ? (
                      <Link to={`/funcionalidade/${recursoFuncionalidade.funcionalidade.id}`}>
                        {recursoFuncionalidade.funcionalidade.funcionalidadeProjeto}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/recurso-funcionalidade/${recursoFuncionalidade.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/recurso-funcionalidade/${recursoFuncionalidade.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/recurso-funcionalidade/${recursoFuncionalidade.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="thisAppRequisitoApp.recursoFuncionalidade.home.notFound">No Recurso Funcionalidades found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={recursoFuncionalidadeList && recursoFuncionalidadeList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default RecursoFuncionalidade;
