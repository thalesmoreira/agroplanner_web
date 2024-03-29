package br.com.agroplanner.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A PropriedadeCaracteristica.
 */
@Entity
@Table(name = "propriedade_caracteristica")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PropriedadeCaracteristica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 250)
    @Column(name = "value", length = 250)
    private String value;

    @ManyToOne(optional = false)
    @NotNull
    private Propriedade propriedade;

    @ManyToOne(optional = false)
    @NotNull
    private Caracteristica caracteristica;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public PropriedadeCaracteristica value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Propriedade getPropriedade() {
        return propriedade;
    }

    public PropriedadeCaracteristica propriedade(Propriedade propriedade) {
        this.propriedade = propriedade;
        return this;
    }

    public void setPropriedade(Propriedade propriedade) {
        this.propriedade = propriedade;
    }

    public Caracteristica getCaracteristica() {
        return caracteristica;
    }

    public PropriedadeCaracteristica caracteristica(Caracteristica caracteristica) {
        this.caracteristica = caracteristica;
        return this;
    }

    public void setCaracteristica(Caracteristica caracteristica) {
        this.caracteristica = caracteristica;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PropriedadeCaracteristica)) {
            return false;
        }
        return id != null && id.equals(((PropriedadeCaracteristica) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PropriedadeCaracteristica{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
