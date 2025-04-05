package com.example.application.Entities;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionsRepository
    extends
        JpaRepository<Transactions, Long>,
        JpaSpecificationExecutor<Transactions>{

        List<Transactions> findByTransactionDateBetween(LocalDate starDate, LocalDate endDate);

        @Query("SELECT t FROM Transactions t WHERE t.category.name = :name")
        List<Transactions> findByCategory(@Param("name") String name);
        

        @Query("SELECT t.category, SUM(t.transactionAmount) FROM Transactions t GROUP BY t.category")
        List<Object[]> sumAmountGroupByCategory();
    
}
