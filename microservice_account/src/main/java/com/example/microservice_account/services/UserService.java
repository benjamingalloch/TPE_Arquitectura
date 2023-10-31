package com.example.microservice_account.services;

import com.example.microservice_account.DTOs.UserDTO;
import com.example.microservice_account.entities.Account;
import com.example.microservice_account.entities.User;
import com.example.microservice_account.repositories.AccountRepository;
import com.example.microservice_account.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service("userService")
public class UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public List<UserDTO> findAll() {
        return this.userRepository.findAll().stream().map(UserDTO::new ).toList();
    }

    @Transactional
    public UserDTO findById(Long id) {
        return this.userRepository.findById(id).map(UserDTO::new).orElseThrow(
                () -> new IllegalArgumentException("ID de Usuario invalido: " + id));
    }

    @Transactional
    public UserDTO save(UserDTO entity) {
        return new UserDTO(this.userRepository.save(new User(entity)));
    }

    @Transactional
    public void delete(Long id) {
        userRepository.delete(userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID de Usuario invalido: " + id)));
    }

    @Transactional
    public void update(Long id, UserDTO entity) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID de Usuario invalido: " + id));
        user.setName(entity.getName());
        user.setSurname(entity.getSurname());
        user.setPhoneNumber(entity.getPhoneNumber());
        user.setEmail(entity.getEmail());
        userRepository.save(user);
    }

    @Transactional
    public void asociarCuenta(long userId, long accountId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("ID de usuario invalido: " + userId));

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("ID de cuenta invalido: " + accountId));

        user.addAccount(account);
        userRepository.save(user);
    }

    @Transactional
    public void desvincularCuenta(long userId, long accountId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("ID de usuario invalido: " + userId));

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("ID de cuenta invalido: " + accountId));

        user.deleteAccount(account);
    }

}