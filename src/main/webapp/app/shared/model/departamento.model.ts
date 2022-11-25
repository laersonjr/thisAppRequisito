import { IProjeto } from 'app/shared/model/projeto.model';
import { IRecursoFuncionalidade } from 'app/shared/model/recurso-funcionalidade.model';

export interface IDepartamento {
  id?: number;
  nome?: string;
  projeto?: IProjeto | null;
  departamento?: IRecursoFuncionalidade | null;
}

export const defaultValue: Readonly<IDepartamento> = {};
