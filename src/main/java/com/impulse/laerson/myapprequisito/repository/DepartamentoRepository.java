package com.impulse.laerson.myapprequisito.repository;

import com.impulse.laerson.myapprequisito.domain.Departamento;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Departamento entity.
 */
@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
    default Optional<Departamento> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Departamento> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Departamento> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct departamento from Departamento departamento left join fetch departamento.projeto left join fetch departamento.departamento",
        countQuery = "select count(distinct departamento) from Departamento departamento"
    )
    Page<Departamento> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct departamento from Departamento departamento left join fetch departamento.projeto left join fetch departamento.departamento"
    )
    List<Departamento> findAllWithToOneRelationships();

    @Query(
        "select departamento from Departamento departamento left join fetch departamento.projeto left join fetch departamento.departamento where departamento.id =:id"
    )
    Optional<Departamento> findOneWithToOneRelationships(@Param("id") Long id);
}
