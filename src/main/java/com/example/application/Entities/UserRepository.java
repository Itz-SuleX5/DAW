package com.example.application.Entities;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
     // Buscar usuario por nombre de usuario
    Optional<User> findByUsername(String username);
    
     // Buscar usuario por correo electrónico
    Optional<User> findByEmail(String email);
    
     // Verificar si existe un usuario con determinado nombre de usuario
    boolean existsByUsername(String username);
    
     // Verificar si existe un usuario con determinado correo electrónico
    boolean existsByEmail(String email);
}
