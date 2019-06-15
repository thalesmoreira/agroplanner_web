export interface IPropriedadeFoto {
  id?: number;
  fotoContentType?: string;
  foto?: any;
  propriedadeNome?: string;
  propriedadeId?: number;
}

export class PropriedadeFoto implements IPropriedadeFoto {
  constructor(
    public id?: number,
    public fotoContentType?: string,
    public foto?: any,
    public propriedadeNome?: string,
    public propriedadeId?: number
  ) {}
}
