package com.impulse.laerson.myapprequisito.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.impulse.laerson.myapprequisito.domain.enumeration.Complexibilidade;
import com.impulse.laerson.myapprequisito.domain.enumeration.Prioridade;
import com.impulse.laerson.myapprequisito.domain.enumeration.Status;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RecursoFuncionalidade.
 */
@Entity
@Table(name = "recurso_funcionalidade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RecursoFuncionalidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "idrf", nullable = false)
    private String idrf;

    @NotNull
    @Column(name = "descricao_requisito", nullable = false)
    private String descricaoRequisito;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "prioridade", nullable = false)
    private Prioridade prioridade;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "complexibilidade", nullable = false)
    private Complexibilidade complexibilidade;

    @Column(name = "esforco")
    private String esforco;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @OneToMany(mappedBy = "departamento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "projeto", "departamento" }, allowSetters = true)
    private Set<Departamento> departamentos = new HashSet<>();

    @OneToMany(mappedBy = "departamento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "projeto", "departamento" }, allowSetters = true)
    private Set<Requisito> requisitos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "projeto", "recursoFuncionalidades" }, allowSetters = true)
    private Funcionalidade funcionalidade;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RecursoFuncionalidade id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdrf() {
        return this.idrf;
    }

    public RecursoFuncionalidade idrf(String idrf) {
        this.setIdrf(idrf);
        return this;
    }

    public void setIdrf(String idrf) {
        this.idrf = idrf;
    }

    public String getDescricaoRequisito() {
        return this.descricaoRequisito;
    }

    public RecursoFuncionalidade descricaoRequisito(String descricaoRequisito) {
        this.setDescricaoRequisito(descricaoRequisito);
        return this;
    }

    public void setDescricaoRequisito(String descricaoRequisito) {
        this.descricaoRequisito = descricaoRequisito;
    }

    public Prioridade getPrioridade() {
        return this.prioridade;
    }

    public RecursoFuncionalidade prioridade(Prioridade prioridade) {
        this.setPrioridade(prioridade);
        return this;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public Complexibilidade getComplexibilidade() {
        return this.complexibilidade;
    }

    public RecursoFuncionalidade complexibilidade(Complexibilidade complexibilidade) {
        this.setComplexibilidade(complexibilidade);
        return this;
    }

    public void setComplexibilidade(Complexibilidade complexibilidade) {
        this.complexibilidade = complexibilidade;
    }

    public String getEsforco() {
        return this.esforco;
    }

    public RecursoFuncionalidade esforco(String esforco) {
        this.setEsforco(esforco);
        return this;
    }

    public void setEsforco(String esforco) {
        this.esforco = esforco;
    }

    public Status getStatus() {
        return this.status;
    }

    public RecursoFuncionalidade status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<Departamento> getDepartamentos() {
        return this.departamentos;
    }

    public void setDepartamentos(Set<Departamento> departamentos) {
        if (this.departamentos != null) {
            this.departamentos.forEach(i -> i.setDepartamento(null));
        }
        if (departamentos != null) {
            departamentos.forEach(i -> i.setDepartamento(this));
        }
        this.departamentos = departamentos;
    }

    public RecursoFuncionalidade departamentos(Set<Departamento> departamentos) {
        this.setDepartamentos(departamentos);
        return this;
    }

    public RecursoFuncionalidade addDepartamento(Departamento departamento) {
        this.departamentos.add(departamento);
        departamento.setDepartamento(this);
        return this;
    }

    public RecursoFuncionalidade removeDepartamento(Departamento departamento) {
        this.departamentos.remove(departamento);
        departamento.setDepartamento(null);
        return this;
    }

    public Set<Requisito> getRequisitos() {
        return this.requisitos;
    }

    public void setRequisitos(Set<Requisito> requisitos) {
        if (this.requisitos != null) {
            this.requisitos.forEach(i -> i.setDepartamento(null));
        }
        if (requisitos != null) {
            requisitos.forEach(i -> i.setDepartamento(this));
        }
        this.requisitos = requisitos;
    }

    public RecursoFuncionalidade requisitos(Set<Requisito> requisitos) {
        this.setRequisitos(requisitos);
        return this;
    }

    public RecursoFuncionalidade addRequisito(Requisito requisito) {
        this.requisitos.add(requisito);
        requisito.setDepartamento(this);
        return this;
    }

    public RecursoFuncionalidade removeRequisito(Requisito requisito) {
        this.requisitos.remove(requisito);
        requisito.setDepartamento(null);
        return this;
    }

    public Funcionalidade getFuncionalidade() {
        return this.funcionalidade;
    }

    public void setFuncionalidade(Funcionalidade funcionalidade) {
        this.funcionalidade = funcionalidade;
    }

    public RecursoFuncionalidade funcionalidade(Funcionalidade funcionalidade) {
        this.setFuncionalidade(funcionalidade);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecursoFuncionalidade)) {
            return false;
        }
        return id != null && id.equals(((RecursoFuncionalidade) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecursoFuncionalidade{" +
            "id=" + getId() +
            ", idrf='" + getIdrf() + "'" +
            ", descricaoRequisito='" + getDescricaoRequisito() + "'" +
            ", prioridade='" + getPrioridade() + "'" +
            ", complexibilidade='" + getComplexibilidade() + "'" +
            ", esforco='" + getEsforco() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
