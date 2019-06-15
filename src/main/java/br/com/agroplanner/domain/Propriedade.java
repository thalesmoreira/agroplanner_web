package br.com.agroplanner.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Propriedade.
 */
@Entity
@Table(name = "propriedade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Propriedade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 250)
    @Column(name = "nome", length = 250, nullable = false)
    private String nome;

    @NotNull
    @Size(max = 1024)
    @Column(name = "descricao", length = 1024, nullable = false)
    private String descricao;

    @NotNull
    @Size(max = 250)
    @Column(name = "localidade", length = 250, nullable = false)
    private String localidade;

    @Lob
    @Column(name = "georeferenciamento")
    private String georeferenciamento;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("propriedades")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Propriedade nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Propriedade descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocalidade() {
        return localidade;
    }

    public Propriedade localidade(String localidade) {
        this.localidade = localidade;
        return this;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getGeoreferenciamento() {
        return georeferenciamento;
    }

    public Propriedade georeferenciamento(String georeferenciamento) {
        this.georeferenciamento = georeferenciamento;
        return this;
    }

    public void setGeoreferenciamento(String georeferenciamento) {
        this.georeferenciamento = georeferenciamento;
    }

    public User getUser() {
        return user;
    }

    public Propriedade user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Propriedade)) {
            return false;
        }
        return id != null && id.equals(((Propriedade) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Propriedade{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", localidade='" + getLocalidade() + "'" +
            ", georeferenciamento='" + getGeoreferenciamento() + "'" +
            "}";
    }
}
