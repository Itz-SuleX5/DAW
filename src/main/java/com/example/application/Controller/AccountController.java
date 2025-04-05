package com.example.application.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.Entities.Account;
import com.example.application.services.AccountService;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    /**
     * Obtiene todas las cuentas activas
     */
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllActiveAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    /**
     * Obtiene una cuenta por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id)
                .map(account -> new ResponseEntity<>(account, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Obtiene cuentas por usuario
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Account>> getAccountsByUserId(@PathVariable Long userId) {
        List<Account> accounts = accountService.getAccountsByUserId(userId);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    /**
     * Crea una nueva cuenta
     */
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account, @RequestParam Long userId) {
        try {
            Account newAccount = accountService.createAccount(account, userId);
            return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Actualiza una cuenta existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account accountDetails) {
        try {
            Account updatedAccount = accountService.updateAccount(id, accountDetails);
            return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Desactiva una cuenta (eliminación lógica)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateAccount(@PathVariable Long id) {
        try {
            accountService.deactivateAccount(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Actualiza el saldo de una cuenta
     */
    @PatchMapping("/{id}/balance")
    public ResponseEntity<Account> updateBalance(@PathVariable Long id, @RequestBody Map<String, Double> balanceUpdate) {
        try {
            Double amount = balanceUpdate.get("amount");
            if (amount == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            
            Account updatedAccount = accountService.updateAccountBalance(id, amount);
            return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Obtiene cuentas por tipo
     */
    @GetMapping("/type/{accountType}")
    public ResponseEntity<List<Account>> getAccountsByType(@PathVariable String accountType) {
        List<Account> accounts = accountService.getAccountsByType(accountType);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }
    
    /**
     * Obtiene cuentas por moneda
     */
    @GetMapping("/currency/{currency}")
    public ResponseEntity<List<Account>> getAccountsByCurrency(@PathVariable String currency) {
        List<Account> accounts = accountService.getAccountsByCurrency(currency);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }
}
