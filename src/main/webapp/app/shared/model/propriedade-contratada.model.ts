import { Moment } from 'moment';

export const enum FormaDePagamento {
  DEBITO = 'DEBITO',
  CREDITO = 'CREDITO',
  BOLETO = 'BOLETO'
}

export interface IPropriedadeContratada {
  id?: number;
  dataInicial?: Moment;
  dataFinal?: Moment;
  quantidadeCabecas?: number;
  valorContratado?: number;
  formaPagamento?: FormaDePagamento;
  parcelas?: number;
  valorParcela?: number;
  propriedadeNome?: string;
  propriedadeId?: number;
  userLogin?: string;
  userId?: number;
}

export class PropriedadeContratada implements IPropriedadeContratada {
  constructor(
    public id?: number,
    public dataInicial?: Moment,
    public dataFinal?: Moment,
    public quantidadeCabecas?: number,
    public valorContratado?: number,
    public formaPagamento?: FormaDePagamento,
    public parcelas?: number,
    public valorParcela?: number,
    public propriedadeNome?: string,
    public propriedadeId?: number,
    public userLogin?: string,
    public userId?: number
  ) {}
}
