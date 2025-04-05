package com.example.application.Entities;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
      // Buscar cuenta por nombre
    Optional<Account> findByAccountName(String accountName);
    
    // Buscar cuentas por usuario
    List<Account> findByUser_IdUserAndIsActiveTrue(Long idUser);
    
    // Buscar cuentas por tipo
    List<Account> findByAccountTypeAndIsActiveTrue(String accountType);
    
    // Buscar cuentas por moneda
    List<Account> findByCurrencyAndIsActiveTrue(String currency);
    
    // Buscar cuentas activas
    List<Account> findByIsActiveTrue();
}
