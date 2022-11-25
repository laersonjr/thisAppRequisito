import { IDepartamento } from 'app/shared/model/departamento.model';
import { IRequisito } from 'app/shared/model/requisito.model';
import { IFuncionalidade } from 'app/shared/model/funcionalidade.model';
import { Prioridade } from 'app/shared/model/enumerations/prioridade.model';
import { Complexibilidade } from 'app/shared/model/enumerations/complexibilidade.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IRecursoFuncionalidade {
  id?: number;
  idrf?: string;
  descricaoRequisito?: string;
  prioridade?: Prioridade;
  complexibilidade?: Complexibilidade;
  esforco?: string | null;
  status?: Status;
  departamentos?: IDepartamento[] | null;
  requisitos?: IRequisito[] | null;
  funcionalidade?: IFuncionalidade | null;
}

export const defaultValue: Readonly<IRecursoFuncionalidade> = {};
