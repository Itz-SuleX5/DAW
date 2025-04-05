package com.example.application.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.Entities.Account;
import com.example.application.Entities.AccountRepository;
import com.example.application.Entities.User;
import com.example.application.Entities.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private UserRepository userRepository;

    /**
     * Obtiene todas las cuentas activas
     */
    public List<Account> getAllActiveAccounts() {
        return accountRepository.findByIsActiveTrue();
    }

    /**
     * Obtiene una cuenta por su ID
     */
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    /**
     * Obtiene una cuenta por su nombre
     */
    public Optional<Account> getAccountByName(String name) {
        return accountRepository.findByAccountName(name);
    }

    /**
     * Obtiene todas las cuentas de un usuario
     */
    public List<Account> getAccountsByUserId(Long idUser) {
        return accountRepository.findByUser_IdUserAndIsActiveTrue(idUser);
    }

    /**
     * Crea una nueva cuenta
     */
    @Transactional
    public Account createAccount(Account account, Long userId) {
        // Verificar si existe el usuario
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));
        
        // Verificar si ya existe una cuenta con el mismo nombre
        if (accountRepository.findByAccountName(account.getAccountName()).isPresent()) {
            throw new RuntimeException("Ya existe una cuenta con el nombre: " + account.getAccountName());
        }
        
        // Establecer el usuario de la cuenta
        account.setUser(user);
        
        // Establecer el saldo inicial igual al total al crear la cuenta
        account.setTotal(account.getInitialBalance());
        
        // Establecer la fecha de creación
        account.setCreatedAt(new Date());
        
        // Activar la cuenta
        account.setActive(true);
        
        return accountRepository.save(account);
    }

    /**
     * Actualiza una cuenta existente
     */
    @Transactional
    public Account updateAccount(Long id, Account accountDetails) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + id));
        
        // Actualizar solo los campos permitidos
        account.setAccountName(accountDetails.getAccountName());
        account.setAccountType(accountDetails.getAccountType());
        account.setCurrency(accountDetails.getCurrency());
        
        // No actualizamos el saldo total ni el saldo inicial directamente
        // Estos deben ser actualizados a través de transacciones
        
        return accountRepository.save(account);
    }

    /**
     * Desactiva una cuenta (eliminación lógica)
     */
    @Transactional
    public void deactivateAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + id));
        
        account.setActive(false);
        accountRepository.save(account);
    }

    /**
     * Actualiza el saldo total de una cuenta
     */
    @Transactional
    public Account updateAccountBalance(Long id, double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + id));
        
        account.setTotal(account.getTotal() + amount);
        return accountRepository.save(account);
    }
    
    /**
     * Obtiene cuentas por tipo
     */
    public List<Account> getAccountsByType(String accountType) {
        return accountRepository.findByAccountTypeAndIsActiveTrue(accountType);
    }
    
    /**
     * Obtiene cuentas por moneda
     */
    public List<Account> getAccountsByCurrency(String currency) {
        return accountRepository.findByCurrencyAndIsActiveTrue(currency);
    }
}
