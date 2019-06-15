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
 * Criteria class for the {@link br.com.agroplanner.domain.PropriedadeCaracteristica} entity. This class is used
 * in {@link br.com.agroplanner.web.rest.PropriedadeCaracteristicaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /propriedade-caracteristicas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PropriedadeCaracteristicaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter value;

    private LongFilter propriedadeId;

    private LongFilter caracteristicaId;

    public PropriedadeCaracteristicaCriteria(){
    }

    public PropriedadeCaracteristicaCriteria(PropriedadeCaracteristicaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.propriedadeId = other.propriedadeId == null ? null : other.propriedadeId.copy();
        this.caracteristicaId = other.caracteristicaId == null ? null : other.caracteristicaId.copy();
    }

    @Override
    public PropriedadeCaracteristicaCriteria copy() {
        return new PropriedadeCaracteristicaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getValue() {
        return value;
    }

    public void setValue(StringFilter value) {
        this.value = value;
    }

    public LongFilter getPropriedadeId() {
        return propriedadeId;
    }

    public void setPropriedadeId(LongFilter propriedadeId) {
        this.propriedadeId = propriedadeId;
    }

    public LongFilter getCaracteristicaId() {
        return caracteristicaId;
    }

    public void setCaracteristicaId(LongFilter caracteristicaId) {
        this.caracteristicaId = caracteristicaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PropriedadeCaracteristicaCriteria that = (PropriedadeCaracteristicaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(value, that.value) &&
            Objects.equals(propriedadeId, that.propriedadeId) &&
            Objects.equals(caracteristicaId, that.caracteristicaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        value,
        propriedadeId,
        caracteristicaId
        );
    }

    @Override
    public String toString() {
        return "Propriedade_caracteristicaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (value != null ? "value=" + value + ", " : "") +
                (propriedadeId != null ? "propriedadeId=" + propriedadeId + ", " : "") +
                (caracteristicaId != null ? "caracteristicaId=" + caracteristicaId + ", " : "") +
            "}";
    }

}
