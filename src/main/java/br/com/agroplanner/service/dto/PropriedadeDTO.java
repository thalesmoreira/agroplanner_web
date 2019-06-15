package br.com.agroplanner.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link br.com.agroplanner.domain.Propriedade} entity.
 */
public class PropriedadeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 250)
    private String nome;

    @NotNull
    @Size(max = 1024)
    private String descricao;

    @NotNull
    @Size(max = 250)
    private String localidade;

    @Lob
    private String georeferenciamento;


    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getGeoreferenciamento() {
        return georeferenciamento;
    }

    public void setGeoreferenciamento(String georeferenciamento) {
        this.georeferenciamento = georeferenciamento;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PropriedadeDTO propriedadeDTO = (PropriedadeDTO) o;
        if (propriedadeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), propriedadeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PropriedadeDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", localidade='" + getLocalidade() + "'" +
            ", georeferenciamento='" + getGeoreferenciamento() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            "}";
    }
}
