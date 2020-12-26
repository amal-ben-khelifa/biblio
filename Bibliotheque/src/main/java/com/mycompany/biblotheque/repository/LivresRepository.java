package com.mycompany.biblotheque.repository;

import com.mycompany.biblotheque.domain.Livres;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Livres entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LivresRepository extends JpaRepository<Livres, Long> {
}
