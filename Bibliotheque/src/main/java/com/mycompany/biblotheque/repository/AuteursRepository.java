package com.mycompany.biblotheque.repository;

import com.mycompany.biblotheque.domain.Auteurs;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Auteurs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuteursRepository extends JpaRepository<Auteurs, Long> {
}
