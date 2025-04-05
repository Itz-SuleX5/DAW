package com.example.application.services;

import com.example.application.Entities.User;
import com.example.application.Entities.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private  UserRepository userRepository;
    

    
    /**
     * Obtiene todos los usuarios
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    /**
     * Obtiene un usuario por su ID
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    /**
     * Obtiene un usuario por su nombre de usuario
     */
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    /**
     * Obtiene un usuario por su correo electrónico
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    /**
     * Crea un nuevo usuario
     */
    @Transactional
    public User createUser(User user) {
        // Verificar si ya existe un usuario con el mismo nombre de usuario
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Ya existe un usuario con el nombre: " + user.getUsername());
        }
        
        // Verificar si ya existe un usuario con el mismo correo electrónico
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con el correo: " + user.getEmail());
        }
        
        
        return userRepository.save(user);
    }
    
    /**
     * Actualiza un usuario existente
     */
    @Transactional
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        
        // Verificar si el nuevo nombre de usuario ya está en uso por otro usuario
        if (!user.getUsername().equals(userDetails.getUsername()) && 
                userRepository.existsByUsername(userDetails.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está en uso: " + userDetails.getUsername());
        }
        
        // Verificar si el nuevo correo electrónico ya está en uso por otro usuario
        if (!user.getEmail().equals(userDetails.getEmail()) && 
                userRepository.existsByEmail(userDetails.getEmail())) {
            throw new RuntimeException("El correo electrónico ya está en uso: " + userDetails.getEmail());
        }
        
        // Actualizar los datos del usuario
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        
        
        return userRepository.save(user);
    }
    
    /**
     * Cambia la contraseña de un usuario
     */
    @Transactional
    public User changePassword(Long id, String oldPassword, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        
        
        return userRepository.save(user);
    }
    
    /**
     * Elimina un usuario por su ID
     */
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        userRepository.deleteById(id);
    }

}
