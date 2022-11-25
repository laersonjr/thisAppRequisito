import { IProjeto } from 'app/shared/model/projeto.model';
import { IRecursoFuncionalidade } from 'app/shared/model/recurso-funcionalidade.model';

export interface IFuncionalidade {
  id?: number;
  funcionalidadeProjeto?: string;
  projeto?: IProjeto | null;
  recursoFuncionalidades?: IRecursoFuncionalidade[] | null;
}

export const defaultValue: Readonly<IFuncionalidade> = {};
