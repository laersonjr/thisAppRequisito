package com.impulse.laerson.myapprequisito.repository;

import com.impulse.laerson.myapprequisito.domain.Requisito;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Requisito entity.
 */
@Repository
public interface RequisitoRepository extends JpaRepository<Requisito, Long> {
    default Optional<Requisito> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Requisito> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Requisito> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct requisito from Requisito requisito left join fetch requisito.projeto left join fetch requisito.departamento",
        countQuery = "select count(distinct requisito) from Requisito requisito"
    )
    Page<Requisito> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct requisito from Requisito requisito left join fetch requisito.projeto left join fetch requisito.departamento")
    List<Requisito> findAllWithToOneRelationships();

    @Query(
        "select requisito from Requisito requisito left join fetch requisito.projeto left join fetch requisito.departamento where requisito.id =:id"
    )
    Optional<Requisito> findOneWithToOneRelationships(@Param("id") Long id);
}
