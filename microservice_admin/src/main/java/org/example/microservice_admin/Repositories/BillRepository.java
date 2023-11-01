package org.example.microservice_admin.Repositories;

import org.example.microservice_admin.Entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository("billRepository")
public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query(value = "SELECT SUM(amount) FROM bill WHERE bill_date BETWEEN :dateFrom AND :dateUntil", nativeQuery = true)
    Double getBillingByTime(LocalDateTime dateFrom, LocalDateTime dateUntil);


}

