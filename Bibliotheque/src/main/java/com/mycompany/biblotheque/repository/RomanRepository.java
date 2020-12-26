package com.mycompany.biblotheque.repository;

import com.mycompany.biblotheque.domain.Roman;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Roman entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RomanRepository extends JpaRepository<Roman, Long> {
}
