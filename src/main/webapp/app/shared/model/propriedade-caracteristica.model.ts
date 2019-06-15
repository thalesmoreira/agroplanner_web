export interface IPropriedade_caracteristica {
  id?: number;
  value?: string;
  propriedadeNome?: string;
  propriedadeId?: number;
  caracteristicaNome?: string;
  caracteristicaId?: number;
}

export class Propriedade_caracteristica implements IPropriedade_caracteristica {
  constructor(
    public id?: number,
    public value?: string,
    public propriedadeNome?: string,
    public propriedadeId?: number,
    public caracteristicaNome?: string,
    public caracteristicaId?: number
  ) {}
}
