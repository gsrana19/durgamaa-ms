package com.durgamandir.donation.repository;

import com.durgamandir.donation.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByOrderByCreatedAtDesc();
    
    Page<Expense> findAllByOrderByCreatedAtDesc(Pageable pageable);
    
    @Query("SELECT e FROM Expense e " +
           "WHERE (:description IS NULL OR :description = '' OR LOWER(e.description) LIKE LOWER(CONCAT('%', :description, '%'))) " +
           "ORDER BY e.createdAt DESC")
    Page<Expense> searchExpenses(@Param("description") String description, Pageable pageable);
    
    @Query("SELECT SUM(e.amount) FROM Expense e")
    BigDecimal getTotalExpenses();
}

