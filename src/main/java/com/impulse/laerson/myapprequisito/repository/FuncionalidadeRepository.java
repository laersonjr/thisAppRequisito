package com.impulse.laerson.myapprequisito.repository;

import com.impulse.laerson.myapprequisito.domain.Funcionalidade;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Funcionalidade entity.
 */
@Repository
public interface FuncionalidadeRepository extends JpaRepository<Funcionalidade, Long> {
    default Optional<Funcionalidade> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Funcionalidade> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Funcionalidade> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct funcionalidade from Funcionalidade funcionalidade left join fetch funcionalidade.projeto",
        countQuery = "select count(distinct funcionalidade) from Funcionalidade funcionalidade"
    )
    Page<Funcionalidade> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct funcionalidade from Funcionalidade funcionalidade left join fetch funcionalidade.projeto")
    List<Funcionalidade> findAllWithToOneRelationships();

    @Query("select funcionalidade from Funcionalidade funcionalidade left join fetch funcionalidade.projeto where funcionalidade.id =:id")
    Optional<Funcionalidade> findOneWithToOneRelationships(@Param("id") Long id);
}
