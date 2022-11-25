import { IProjeto } from 'app/shared/model/projeto.model';
import { IRecursoFuncionalidade } from 'app/shared/model/recurso-funcionalidade.model';

export interface IRequisito {
  id?: number;
  nome?: string;
  projeto?: IProjeto | null;
  departamento?: IRecursoFuncionalidade | null;
}

export const defaultValue: Readonly<IRequisito> = {};
