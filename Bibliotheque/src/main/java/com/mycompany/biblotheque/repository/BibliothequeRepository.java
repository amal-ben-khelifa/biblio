package com.mycompany.biblotheque.repository;

import com.mycompany.biblotheque.domain.Bibliotheque;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Bibliotheque entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BibliothequeRepository extends JpaRepository<Bibliotheque, Long> {
}
