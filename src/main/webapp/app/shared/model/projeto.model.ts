import dayjs from 'dayjs';
import { IRequisito } from 'app/shared/model/requisito.model';
import { IDepartamento } from 'app/shared/model/departamento.model';

export interface IProjeto {
  id?: number;
  nome?: string;
  dataDeCriacao?: string | null;
  dataDeInicio?: string | null;
  dataFim?: string | null;
  requisitos?: IRequisito[] | null;
  departamentos?: IDepartamento[] | null;
}

export const defaultValue: Readonly<IProjeto> = {};
