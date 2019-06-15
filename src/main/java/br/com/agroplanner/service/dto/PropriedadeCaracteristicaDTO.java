package br.com.agroplanner.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.agroplanner.domain.PropriedadeCaracteristica} entity.
 */
public class PropriedadeCaracteristicaDTO implements Serializable {

    private Long id;

    @Size(max = 250)
    private String value;


    private Long propriedadeId;

    private String propriedadeNome;

    private Long caracteristicaId;

    private String caracteristicaNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public Long getCaracteristicaId() {
        return caracteristicaId;
    }

    public void setCaracteristicaId(Long caracteristicaId) {
        this.caracteristicaId = caracteristicaId;
    }

    public String getCaracteristicaNome() {
        return caracteristicaNome;
    }

    public void setCaracteristicaNome(String caracteristicaNome) {
        this.caracteristicaNome = caracteristicaNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PropriedadeCaracteristicaDTO propriedadeCaracteristicaDTO = (PropriedadeCaracteristicaDTO) o;
        if (propriedadeCaracteristicaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), propriedadeCaracteristicaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Propriedade_caracteristicaDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", propriedade=" + getPropriedadeId() +
            ", propriedade='" + getPropriedadeNome() + "'" +
            ", caracteristica=" + getCaracteristicaId() +
            ", caracteristica='" + getCaracteristicaNome() + "'" +
            "}";
    }
}
