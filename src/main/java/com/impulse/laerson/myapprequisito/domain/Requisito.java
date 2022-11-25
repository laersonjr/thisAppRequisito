package com.impulse.laerson.myapprequisito.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Requisito.
 */
@Entity
@Table(name = "requisito")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Requisito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @ManyToOne
    @JsonIgnoreProperties(value = { "requisitos", "departamentos" }, allowSetters = true)
    private Projeto projeto;

    @ManyToOne
    @JsonIgnoreProperties(value = { "departamentos", "requisitos", "funcionalidade" }, allowSetters = true)
    private RecursoFuncionalidade departamento;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Requisito id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Requisito nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Projeto getProjeto() {
        return this.projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public Requisito projeto(Projeto projeto) {
        this.setProjeto(projeto);
        return this;
    }

    public RecursoFuncionalidade getDepartamento() {
        return this.departamento;
    }

    public void setDepartamento(RecursoFuncionalidade recursoFuncionalidade) {
        this.departamento = recursoFuncionalidade;
    }

    public Requisito departamento(RecursoFuncionalidade recursoFuncionalidade) {
        this.setDepartamento(recursoFuncionalidade);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Requisito)) {
            return false;
        }
        return id != null && id.equals(((Requisito) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Requisito{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
