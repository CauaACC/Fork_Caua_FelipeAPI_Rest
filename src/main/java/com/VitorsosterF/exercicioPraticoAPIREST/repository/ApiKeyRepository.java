package com.VitorsosterF.exercicioPraticoAPIREST.repository;

import com.VitorsosterF.exercicioPraticoAPIREST.model.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

    Optional<ApiKey> findByKeyHashAndAtivaTrue(String keyHash);
}
