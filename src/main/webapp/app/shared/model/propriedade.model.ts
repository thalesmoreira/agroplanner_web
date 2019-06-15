export interface IPropriedade {
  id?: number;
  nome?: string;
  descricao?: string;
  localidade?: string;
  georeferenciamento?: any;
  userLogin?: string;
  userId?: number;
}

export class Propriedade implements IPropriedade {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string,
    public localidade?: string,
    public georeferenciamento?: any,
    public userLogin?: string,
    public userId?: number
  ) {}
}
