package com.mycompany.biblotheque.repository;

import com.mycompany.biblotheque.domain.JeuxEducatif;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the JeuxEducatif entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JeuxEducatifRepository extends JpaRepository<JeuxEducatif, Long> {
}
