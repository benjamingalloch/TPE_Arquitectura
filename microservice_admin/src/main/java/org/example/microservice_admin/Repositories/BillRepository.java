package org.example.microservice_admin.Repositories;

import org.example.microservice_admin.Entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("billRepository")
public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query(value = "SELECT SUM(charge) FROM bill WHERE bill_date BETWEEN ?1 AND ?2", nativeQuery = true)
    Double getBilling(String dateFrom, String dateUntil);
}

