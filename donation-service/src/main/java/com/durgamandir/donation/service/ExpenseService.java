package com.durgamandir.donation.service;

import com.durgamandir.donation.dto.ExpenseRequest;
import com.durgamandir.donation.dto.ExpenseResponse;
import com.durgamandir.donation.dto.PaginatedExpenseResponse;
import com.durgamandir.donation.entity.Expense;
import com.durgamandir.donation.repository.ExpenseRepository;
import com.durgamandir.donation.repository.DonationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExpenseService {
    
    private final ExpenseRepository expenseRepository;
    private final DonationRepository donationRepository;
    
    public ExpenseService(ExpenseRepository expenseRepository, DonationRepository donationRepository) {
        this.expenseRepository = expenseRepository;
        this.donationRepository = donationRepository;
    }
    
    public List<ExpenseResponse> getAllExpenses() {
        return expenseRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public PaginatedExpenseResponse getExpensesPaginated(int page, int size, String description) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Expense> expensePage;
        
        // Use search method if description is provided
        if (description != null && !description.trim().isEmpty()) {
            expensePage = expenseRepository.searchExpenses(description.trim(), pageable);
        } else {
            expensePage = expenseRepository.findAllByOrderByCreatedAtDesc(pageable);
        }
        
        List<ExpenseResponse> expenses = expensePage.getContent()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        
        return new PaginatedExpenseResponse(
                expenses,
                expensePage.getNumber(),
                expensePage.getTotalPages(),
                expensePage.getTotalElements(),
                expensePage.getSize()
        );
    }
    
    public ExpenseResponse createExpense(ExpenseRequest request) {
        // Validate that the expense doesn't exceed total donations
        BigDecimal totalDonations = donationRepository.getTotalAmount();
        if (totalDonations == null) {
            totalDonations = BigDecimal.ZERO;
        }
        BigDecimal currentTotalExpenses = getTotalExpenses();
        BigDecimal newTotalExpenses = currentTotalExpenses.add(request.getAmount());
        
        if (newTotalExpenses.compareTo(totalDonations) > 0) {
            BigDecimal remaining = totalDonations.subtract(currentTotalExpenses);
            if (remaining.compareTo(BigDecimal.ZERO) < 0) {
                remaining = BigDecimal.ZERO;
            }
            throw new IllegalArgumentException(
                String.format("Expense amount (₹%s) exceeds remaining balance (₹%s). Maximum allowed: ₹%s",
                    request.getAmount().toPlainString(),
                    remaining.toPlainString(),
                    remaining.toPlainString())
            );
        }
        
        Expense expense = new Expense();
        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setNotes(request.getNotes());
        expense.setPurchaseDate(request.getPurchaseDate());
        expense.setSupportingDocument(request.getSupportingDocument());
        
        Expense saved = expenseRepository.save(expense);
        return mapToResponse(saved);
    }
    
    public ExpenseResponse updateExpense(Long id, ExpenseRequest request) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Expense not found with id: " + id));
        
        // Validate that the updated expense doesn't exceed total donations
        BigDecimal totalDonations = donationRepository.getTotalAmount();
        if (totalDonations == null) {
            totalDonations = BigDecimal.ZERO;
        }
        BigDecimal currentTotalExpenses = getTotalExpenses();
        // Subtract the current expense amount and add the new amount
        BigDecimal newTotalExpenses = currentTotalExpenses
                .subtract(expense.getAmount())
                .add(request.getAmount());
        
        if (newTotalExpenses.compareTo(totalDonations) > 0) {
            BigDecimal remaining = totalDonations.subtract(currentTotalExpenses.subtract(expense.getAmount()));
            if (remaining.compareTo(BigDecimal.ZERO) < 0) {
                remaining = BigDecimal.ZERO;
            }
            throw new IllegalArgumentException(
                String.format("Updated expense amount (₹%s) exceeds remaining balance (₹%s). Maximum allowed: ₹%s",
                    request.getAmount().toPlainString(),
                    remaining.toPlainString(),
                    remaining.toPlainString())
            );
        }
        
        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setNotes(request.getNotes());
        expense.setPurchaseDate(request.getPurchaseDate());
        expense.setSupportingDocument(request.getSupportingDocument());
        
        Expense saved = expenseRepository.save(expense);
        return mapToResponse(saved);
    }
    
    public void deleteExpense(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new IllegalArgumentException("Expense not found with id: " + id);
        }
        expenseRepository.deleteById(id);
    }
    
    public BigDecimal getTotalExpenses() {
        BigDecimal total = expenseRepository.getTotalExpenses();
        return total != null ? total : BigDecimal.ZERO;
    }
    
    private ExpenseResponse mapToResponse(Expense expense) {
        return new ExpenseResponse(
                expense.getId(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getCategory(),
                expense.getNotes(),
                expense.getPurchaseDate(),
                expense.getSupportingDocument(),
                expense.getCreatedAt(),
                expense.getUpdatedAt()
        );
    }
}

