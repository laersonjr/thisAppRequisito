package com.impulse.laerson.myapprequisito.repository;

import com.impulse.laerson.myapprequisito.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
