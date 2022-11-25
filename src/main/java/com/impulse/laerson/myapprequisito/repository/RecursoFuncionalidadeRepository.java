package com.impulse.laerson.myapprequisito.repository;

import com.impulse.laerson.myapprequisito.domain.RecursoFuncionalidade;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RecursoFuncionalidade entity.
 */
@Repository
public interface RecursoFuncionalidadeRepository extends JpaRepository<RecursoFuncionalidade, Long> {
    default Optional<RecursoFuncionalidade> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<RecursoFuncionalidade> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<RecursoFuncionalidade> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct recursoFuncionalidade from RecursoFuncionalidade recursoFuncionalidade left join fetch recursoFuncionalidade.funcionalidade",
        countQuery = "select count(distinct recursoFuncionalidade) from RecursoFuncionalidade recursoFuncionalidade"
    )
    Page<RecursoFuncionalidade> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct recursoFuncionalidade from RecursoFuncionalidade recursoFuncionalidade left join fetch recursoFuncionalidade.funcionalidade"
    )
    List<RecursoFuncionalidade> findAllWithToOneRelationships();

    @Query(
        "select recursoFuncionalidade from RecursoFuncionalidade recursoFuncionalidade left join fetch recursoFuncionalidade.funcionalidade where recursoFuncionalidade.id =:id"
    )
    Optional<RecursoFuncionalidade> findOneWithToOneRelationships(@Param("id") Long id);
}
