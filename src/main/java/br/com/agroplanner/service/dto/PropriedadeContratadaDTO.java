package br.com.agroplanner.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import br.com.agroplanner.domain.enumeration.FormaDePagamento;

/**
 * A DTO for the {@link br.com.agroplanner.domain.PropriedadeContratada} entity.
 */
public class PropriedadeContratadaDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant dataInicial;

    @NotNull
    private Instant dataFinal;

    @NotNull
    private Integer quantidadeCabecas;

    @NotNull
    private BigDecimal valorContratado;

    @NotNull
    private FormaDePagamento formaPagamento;

    @NotNull
    private Integer parcelas;

    @NotNull
    private BigDecimal valorParcela;


    private Long propriedadeId;

    private String propriedadeNome;

    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Instant dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Instant getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Instant dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Integer getQuantidadeCabecas() {
        return quantidadeCabecas;
    }

    public void setQuantidadeCabecas(Integer quantidadeCabecas) {
        this.quantidadeCabecas = quantidadeCabecas;
    }

    public BigDecimal getValorContratado() {
        return valorContratado;
    }

    public void setValorContratado(BigDecimal valorContratado) {
        this.valorContratado = valorContratado;
    }

    public FormaDePagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaDePagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }

    public BigDecimal getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(BigDecimal valorParcela) {
        this.valorParcela = valorParcela;
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

        PropriedadeContratadaDTO propriedadeContratadaDTO = (PropriedadeContratadaDTO) o;
        if (propriedadeContratadaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), propriedadeContratadaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PropriedadeContratadaDTO{" +
            "id=" + getId() +
            ", dataInicial='" + getDataInicial() + "'" +
            ", dataFinal='" + getDataFinal() + "'" +
            ", quantidadeCabecas=" + getQuantidadeCabecas() +
            ", valorContratado=" + getValorContratado() +
            ", formaPagamento='" + getFormaPagamento() + "'" +
            ", parcelas=" + getParcelas() +
            ", valorParcela=" + getValorParcela() +
            ", propriedade=" + getPropriedadeId() +
            ", propriedade='" + getPropriedadeNome() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            "}";
    }
}
