package br.com.agroplanner.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link br.com.agroplanner.domain.PropriedadeFoto} entity.
 */
public class PropriedadeFotoDTO implements Serializable {

    private Long id;

    
    @Lob
    private byte[] foto;

    private String fotoContentType;

    private Long propriedadeId;

    private String propriedadeNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public Long getPropriedadeId() {
        return propriedadeId;
    }

    public void setPropriedadeId(Long propriedadeId) {
        this.propriedadeId = propriedadeId;
    }

    public String getPropriedadeNome() {
        return propriedadeNome;
    }

    public void setPropriedadeNome(String propriedadeNome) {
        this.propriedadeNome = propriedadeNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PropriedadeFotoDTO propriedadeFotoDTO = (PropriedadeFotoDTO) o;
        if (propriedadeFotoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), propriedadeFotoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PropriedadeFotoDTO{" +
            "id=" + getId() +
            ", foto='" + getFoto() + "'" +
            ", propriedade=" + getPropriedadeId() +
            ", propriedade='" + getPropriedadeNome() + "'" +
            "}";
    }
}
