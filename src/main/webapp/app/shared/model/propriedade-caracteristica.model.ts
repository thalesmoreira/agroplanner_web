export interface IPropriedadeCaracteristica {
  id?: number;
  value?: string;
  propriedadeNome?: string;
  propriedadeId?: number;
  caracteristicaNome?: string;
  caracteristicaId?: number;
}

export class PropriedadeCaracteristica implements IPropriedadeCaracteristica {
  constructor(
    public id?: number,
    public value?: string,
    public propriedadeNome?: string,
    public propriedadeId?: number,
    public caracteristicaNome?: string,
    public caracteristicaId?: number
  ) {}
}
