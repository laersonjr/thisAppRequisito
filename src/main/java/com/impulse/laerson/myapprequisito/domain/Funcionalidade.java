package com.impulse.laerson.myapprequisito.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Funcionalidade.
 */
@Entity
@Table(name = "funcionalidade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Funcionalidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "funcionalidade_projeto", nullable = false)
    private String funcionalidadeProjeto;

    @JsonIgnoreProperties(value = { "requisitos", "departamentos" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Projeto projeto;

    @OneToMany(mappedBy = "funcionalidade")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "departamentos", "requisitos", "funcionalidade" }, allowSetters = true)
    private Set<RecursoFuncionalidade> recursoFuncionalidades = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Funcionalidade id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFuncionalidadeProjeto() {
        return this.funcionalidadeProjeto;
    }

    public Funcionalidade funcionalidadeProjeto(String funcionalidadeProjeto) {
        this.setFuncionalidadeProjeto(funcionalidadeProjeto);
        return this;
    }

    public void setFuncionalidadeProjeto(String funcionalidadeProjeto) {
        this.funcionalidadeProjeto = funcionalidadeProjeto;
    }

    public Projeto getProjeto() {
        return this.projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public Funcionalidade projeto(Projeto projeto) {
        this.setProjeto(projeto);
        return this;
    }

    public Set<RecursoFuncionalidade> getRecursoFuncionalidades() {
        return this.recursoFuncionalidades;
    }

    public void setRecursoFuncionalidades(Set<RecursoFuncionalidade> recursoFuncionalidades) {
        if (this.recursoFuncionalidades != null) {
            this.recursoFuncionalidades.forEach(i -> i.setFuncionalidade(null));
        }
        if (recursoFuncionalidades != null) {
            recursoFuncionalidades.forEach(i -> i.setFuncionalidade(this));
        }
        this.recursoFuncionalidades = recursoFuncionalidades;
    }

    public Funcionalidade recursoFuncionalidades(Set<RecursoFuncionalidade> recursoFuncionalidades) {
        this.setRecursoFuncionalidades(recursoFuncionalidades);
        return this;
    }

    public Funcionalidade addRecursoFuncionalidade(RecursoFuncionalidade recursoFuncionalidade) {
        this.recursoFuncionalidades.add(recursoFuncionalidade);
        recursoFuncionalidade.setFuncionalidade(this);
        return this;
    }

    public Funcionalidade removeRecursoFuncionalidade(RecursoFuncionalidade recursoFuncionalidade) {
        this.recursoFuncionalidades.remove(recursoFuncionalidade);
        recursoFuncionalidade.setFuncionalidade(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Funcionalidade)) {
            return false;
        }
        return id != null && id.equals(((Funcionalidade) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Funcionalidade{" +
            "id=" + getId() +
            ", funcionalidadeProjeto='" + getFuncionalidadeProjeto() + "'" +
            "}";
    }
}
