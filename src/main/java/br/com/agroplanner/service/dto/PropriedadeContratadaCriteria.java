package br.com.agroplanner.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import br.com.agroplanner.domain.enumeration.FormaDePagamento;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link br.com.agroplanner.domain.PropriedadeContratada} entity. This class is used
 * in {@link br.com.agroplanner.web.rest.PropriedadeContratadaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /propriedade-contratadas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PropriedadeContratadaCriteria implements Serializable, Criteria {
    /**
     * Class for filtering FormaDePagamento
     */
    public static class FormaDePagamentoFilter extends Filter<FormaDePagamento> {

        public FormaDePagamentoFilter() {
        }

        public FormaDePagamentoFilter(FormaDePagamentoFilter filter) {
            super(filter);
        }

        @Override
        public FormaDePagamentoFilter copy() {
            return new FormaDePagamentoFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter dataInicial;

    private InstantFilter dataFinal;

    private IntegerFilter quantidadeCabecas;

    private BigDecimalFilter valorContratado;

    private FormaDePagamentoFilter formaPagamento;

    private IntegerFilter parcelas;

    private BigDecimalFilter valorParcela;

    private LongFilter propriedadeId;

    private LongFilter userId;

    public PropriedadeContratadaCriteria(){
    }

    public PropriedadeContratadaCriteria(PropriedadeContratadaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.dataInicial = other.dataInicial == null ? null : other.dataInicial.copy();
        this.dataFinal = other.dataFinal == null ? null : other.dataFinal.copy();
        this.quantidadeCabecas = other.quantidadeCabecas == null ? null : other.quantidadeCabecas.copy();
        this.valorContratado = other.valorContratado == null ? null : other.valorContratado.copy();
        this.formaPagamento = other.formaPagamento == null ? null : other.formaPagamento.copy();
        this.parcelas = other.parcelas == null ? null : other.parcelas.copy();
        this.valorParcela = other.valorParcela == null ? null : other.valorParcela.copy();
        this.propriedadeId = other.propriedadeId == null ? null : other.propriedadeId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public PropriedadeContratadaCriteria copy() {
        return new PropriedadeContratadaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(InstantFilter dataInicial) {
        this.dataInicial = dataInicial;
    }

    public InstantFilter getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(InstantFilter dataFinal) {
        this.dataFinal = dataFinal;
    }

    public IntegerFilter getQuantidadeCabecas() {
        return quantidadeCabecas;
    }

    public void setQuantidadeCabecas(IntegerFilter quantidadeCabecas) {
        this.quantidadeCabecas = quantidadeCabecas;
    }

    public BigDecimalFilter getValorContratado() {
        return valorContratado;
    }

    public void setValorContratado(BigDecimalFilter valorContratado) {
        this.valorContratado = valorContratado;
    }

    public FormaDePagamentoFilter getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaDePagamentoFilter formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public IntegerFilter getParcelas() {
        return parcelas;
    }

    public void setParcelas(IntegerFilter parcelas) {
        this.parcelas = parcelas;
    }

    public BigDecimalFilter getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(BigDecimalFilter valorParcela) {
        this.valorParcela = valorParcela;
    }

    public LongFilter getPropriedadeId() {
        return propriedadeId;
    }

    public void setPropriedadeId(LongFilter propriedadeId) {
        this.propriedadeId = propriedadeId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PropriedadeContratadaCriteria that = (PropriedadeContratadaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dataInicial, that.dataInicial) &&
            Objects.equals(dataFinal, that.dataFinal) &&
            Objects.equals(quantidadeCabecas, that.quantidadeCabecas) &&
            Objects.equals(valorContratado, that.valorContratado) &&
            Objects.equals(formaPagamento, that.formaPagamento) &&
            Objects.equals(parcelas, that.parcelas) &&
            Objects.equals(valorParcela, that.valorParcela) &&
            Objects.equals(propriedadeId, that.propriedadeId) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dataInicial,
        dataFinal,
        quantidadeCabecas,
        valorContratado,
        formaPagamento,
        parcelas,
        valorParcela,
        propriedadeId,
        userId
        );
    }

    @Override
    public String toString() {
        return "PropriedadeContratadaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dataInicial != null ? "dataInicial=" + dataInicial + ", " : "") +
                (dataFinal != null ? "dataFinal=" + dataFinal + ", " : "") +
                (quantidadeCabecas != null ? "quantidadeCabecas=" + quantidadeCabecas + ", " : "") +
                (valorContratado != null ? "valorContratado=" + valorContratado + ", " : "") +
                (formaPagamento != null ? "formaPagamento=" + formaPagamento + ", " : "") +
                (parcelas != null ? "parcelas=" + parcelas + ", " : "") +
                (valorParcela != null ? "valorParcela=" + valorParcela + ", " : "") +
                (propriedadeId != null ? "propriedadeId=" + propriedadeId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
