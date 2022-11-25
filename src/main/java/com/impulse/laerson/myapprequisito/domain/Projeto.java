package com.impulse.laerson.myapprequisito.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Projeto.
 */
@Entity
@Table(name = "projeto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Projeto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "data_de_criacao")
    private LocalDate dataDeCriacao;

    @Column(name = "data_de_inicio")
    private LocalDate dataDeInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @OneToMany(mappedBy = "projeto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "projeto", "departamento" }, allowSetters = true)
    private Set<Requisito> requisitos = new HashSet<>();

    @OneToMany(mappedBy = "projeto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "projeto", "departamento" }, allowSetters = true)
    private Set<Departamento> departamentos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Projeto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Projeto nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataDeCriacao() {
        return this.dataDeCriacao;
    }

    public Projeto dataDeCriacao(LocalDate dataDeCriacao) {
        this.setDataDeCriacao(dataDeCriacao);
        return this;
    }

    public void setDataDeCriacao(LocalDate dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }

    public LocalDate getDataDeInicio() {
        return this.dataDeInicio;
    }

    public Projeto dataDeInicio(LocalDate dataDeInicio) {
        this.setDataDeInicio(dataDeInicio);
        return this;
    }

    public void setDataDeInicio(LocalDate dataDeInicio) {
        this.dataDeInicio = dataDeInicio;
    }

    public LocalDate getDataFim() {
        return this.dataFim;
    }

    public Projeto dataFim(LocalDate dataFim) {
        this.setDataFim(dataFim);
        return this;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public Set<Requisito> getRequisitos() {
        return this.requisitos;
    }

    public void setRequisitos(Set<Requisito> requisitos) {
        if (this.requisitos != null) {
            this.requisitos.forEach(i -> i.setProjeto(null));
        }
        if (requisitos != null) {
            requisitos.forEach(i -> i.setProjeto(this));
        }
        this.requisitos = requisitos;
    }

    public Projeto requisitos(Set<Requisito> requisitos) {
        this.setRequisitos(requisitos);
        return this;
    }

    public Projeto addRequisito(Requisito requisito) {
        this.requisitos.add(requisito);
        requisito.setProjeto(this);
        return this;
    }

    public Projeto removeRequisito(Requisito requisito) {
        this.requisitos.remove(requisito);
        requisito.setProjeto(null);
        return this;
    }

    public Set<Departamento> getDepartamentos() {
        return this.departamentos;
    }

    public void setDepartamentos(Set<Departamento> departamentos) {
        if (this.departamentos != null) {
            this.departamentos.forEach(i -> i.setProjeto(null));
        }
        if (departamentos != null) {
            departamentos.forEach(i -> i.setProjeto(this));
        }
        this.departamentos = departamentos;
    }

    public Projeto departamentos(Set<Departamento> departamentos) {
        this.setDepartamentos(departamentos);
        return this;
    }

    public Projeto addDepartamento(Departamento departamento) {
        this.departamentos.add(departamento);
        departamento.setProjeto(this);
        return this;
    }

    public Projeto removeDepartamento(Departamento departamento) {
        this.departamentos.remove(departamento);
        departamento.setProjeto(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Projeto)) {
            return false;
        }
        return id != null && id.equals(((Projeto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Projeto{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", dataDeCriacao='" + getDataDeCriacao() + "'" +
            ", dataDeInicio='" + getDataDeInicio() + "'" +
            ", dataFim='" + getDataFim() + "'" +
            "}";
    }
}
