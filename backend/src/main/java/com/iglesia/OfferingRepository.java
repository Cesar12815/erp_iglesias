package com.iglesia;

// feat: apply Repository Pattern — add semantic query methods to OfferingRepository
// ADR-03: Repository Pattern — encapsula lógica de consultas, DashboardController no construye fechas manualmente

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OfferingRepository extends JpaRepository<Offering, Long> {

    List<Offering> findAllByPersonChurchId(Long churchId);

    long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    /**
     * ADR-03: Suma total de ofrendas en un rango de fechas.
     * El repositorio encapsula el CÓMO — el controller solo pide el QUÉ.
     */
    @Query("SELECT COALESCE(SUM(o.amount), 0) FROM Offering o WHERE o.createdAt BETWEEN :start AND :end")
    BigDecimal sumAmountByCreatedAtBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
