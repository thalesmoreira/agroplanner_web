export interface ICaracteristica {
  id?: number;
  nome?: string;
  descricao?: string;
}

export class Caracteristica implements ICaracteristica {
  constructor(public id?: number, public nome?: string, public descricao?: string) {}
}
