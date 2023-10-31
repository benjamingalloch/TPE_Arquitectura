package com.example.microservice_account.services;

import com.example.microservice_account.DTOs.UserDTO;
import com.example.microservice_account.entities.Account;
import com.example.microservice_account.entities.User;
import com.example.microservice_account.entities.UserAccount;
import com.example.microservice_account.repositories.AccountRepository;
import com.example.microservice_account.repositories.UserAccountRepository;
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

    @Autowired
    private UserAccountRepository userAccountRepository;

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
        Objects.requireNonNull(userId);
        Objects.requireNonNull(accountId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("ID de usuario invalido: " + userId));

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("ID de cuenta invalido: " + accountId));

        if (userAccountRepository.findByUserAndAccount(user, account).isPresent()) {
            throw new IllegalArgumentException("La cuenta ya se encuentra asociada al usuario");
        }

        UserAccount nuevo = new UserAccount(user, account);
        userAccountRepository.save(nuevo);
    }

    @Transactional
    public void desvincularCuenta(long userId, long accountId) {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(accountId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("ID de usuario invalido: " + userId));

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("ID de cuenta invalido: " + accountId));


        userAccountRepository.deleteByUserAndAccount(user, account);
    }

	/*
	@Transactional(readOnly = true)
	public List<InformeUserDTO> informeUsers() {
		return this.inscriptos.informeUsers();
	}

	@Transactional(readOnly = true)
	public List<InformeUserCantEstudiantesDTO> usersOrdenadas() {
		return this.userRepository.usersOrdenadas();
	} */
}