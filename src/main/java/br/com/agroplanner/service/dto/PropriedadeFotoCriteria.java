package br.com.agroplanner.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link br.com.agroplanner.domain.PropriedadeFoto} entity. This class is used
 * in {@link br.com.agroplanner.web.rest.PropriedadeFotoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /propriedade-fotos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PropriedadeFotoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter propriedadeId;

    public PropriedadeFotoCriteria(){
    }

    public PropriedadeFotoCriteria(PropriedadeFotoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.propriedadeId = other.propriedadeId == null ? null : other.propriedadeId.copy();
    }

    @Override
    public PropriedadeFotoCriteria copy() {
        return new PropriedadeFotoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getPropriedadeId() {
        return propriedadeId;
    }

    public void setPropriedadeId(LongFilter propriedadeId) {
        this.propriedadeId = propriedadeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PropriedadeFotoCriteria that = (PropriedadeFotoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(propriedadeId, that.propriedadeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        propriedadeId
        );
    }

    @Override
    public String toString() {
        return "PropriedadeFotoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (propriedadeId != null ? "propriedadeId=" + propriedadeId + ", " : "") +
            "}";
    }

}
