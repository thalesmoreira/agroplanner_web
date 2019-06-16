package br.com.agroplanner.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import br.com.agroplanner.domain.enumeration.FormaDePagamento;

/**
 * A PropriedadeContratada.
 */
@Entity
@Table(name = "propriedade_contratada")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PropriedadeContratada implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "data_inicial", nullable = false)
    private Instant dataInicial;

    @NotNull
    @Column(name = "data_final", nullable = false)
    private Instant dataFinal;

    @NotNull
    @Column(name = "quantidade_cabecas", nullable = false)
    private Integer quantidadeCabecas;

    @NotNull
    @Column(name = "valor_contratado", precision = 21, scale = 2, nullable = false)
    private BigDecimal valorContratado;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento", nullable = false)
    private FormaDePagamento formaPagamento;

    @NotNull
    @Column(name = "parcelas", nullable = false)
    private Integer parcelas;

    @NotNull
    @Column(name = "valor_parcela", precision = 21, scale = 2, nullable = false)
    private BigDecimal valorParcela;

    @ManyToOne(optional = false)
    @NotNull
    private Propriedade propriedade;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("propriedadeContratadas")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataInicial() {
        return dataInicial;
    }

    public PropriedadeContratada dataInicial(Instant dataInicial) {
        this.dataInicial = dataInicial;
        return this;
    }

    public void setDataInicial(Instant dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Instant getDataFinal() {
        return dataFinal;
    }

    public PropriedadeContratada dataFinal(Instant dataFinal) {
        this.dataFinal = dataFinal;
        return this;
    }

    public void setDataFinal(Instant dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Integer getQuantidadeCabecas() {
        return quantidadeCabecas;
    }

    public PropriedadeContratada quantidadeCabecas(Integer quantidadeCabecas) {
        this.quantidadeCabecas = quantidadeCabecas;
        return this;
    }

    public void setQuantidadeCabecas(Integer quantidadeCabecas) {
        this.quantidadeCabecas = quantidadeCabecas;
    }

    public BigDecimal getValorContratado() {
        return valorContratado;
    }

    public PropriedadeContratada valorContratado(BigDecimal valorContratado) {
        this.valorContratado = valorContratado;
        return this;
    }

    public void setValorContratado(BigDecimal valorContratado) {
        this.valorContratado = valorContratado;
    }

    public FormaDePagamento getFormaPagamento() {
        return formaPagamento;
    }

    public PropriedadeContratada formaPagamento(FormaDePagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
        return this;
    }

    public void setFormaPagamento(FormaDePagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public PropriedadeContratada parcelas(Integer parcelas) {
        this.parcelas = parcelas;
        return this;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }

    public BigDecimal getValorParcela() {
        return valorParcela;
    }

    public PropriedadeContratada valorParcela(BigDecimal valorParcela) {
        this.valorParcela = valorParcela;
        return this;
    }

    public void setValorParcela(BigDecimal valorParcela) {
        this.valorParcela = valorParcela;
    }

    public Propriedade getPropriedade() {
        return propriedade;
    }

    public PropriedadeContratada propriedade(Propriedade propriedade) {
        this.propriedade = propriedade;
        return this;
    }

    public void setPropriedade(Propriedade propriedade) {
        this.propriedade = propriedade;
    }

    public User getUser() {
        return user;
    }

    public PropriedadeContratada user(User user) {
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
        if (!(o instanceof PropriedadeContratada)) {
            return false;
        }
        return id != null && id.equals(((PropriedadeContratada) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PropriedadeContratada{" +
            "id=" + getId() +
            ", dataInicial='" + getDataInicial() + "'" +
            ", dataFinal='" + getDataFinal() + "'" +
            ", quantidadeCabecas=" + getQuantidadeCabecas() +
            ", valorContratado=" + getValorContratado() +
            ", formaPagamento='" + getFormaPagamento() + "'" +
            ", parcelas=" + getParcelas() +
            ", valorParcela=" + getValorParcela() +
            "}";
    }
}
