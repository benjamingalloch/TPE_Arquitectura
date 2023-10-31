package com.example.microservice_account.services;

import com.example.microservice_account.DTOs.AccountDTO;
import com.example.microservice_account.entities.Account;
import com.example.microservice_account.entities.User;
import com.example.microservice_account.repositories.AccountRepository;
import com.example.microservice_account.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service("accountService")
public class AccountService{
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public List<AccountDTO> getAll() {
        return this.accountRepository.findAll().stream().map(AccountDTO::new ).toList();
    }

    @Transactional
    public AccountDTO findById(Long id) {
        return this.accountRepository.findById(id).map(AccountDTO::new).orElseThrow(
                () -> new IllegalArgumentException("ID de Cuenta invalido: " + id));
    }

    @Transactional
    public AccountDTO save(AccountDTO accountDTO) {
        return new AccountDTO(this.accountRepository.save(new Account(accountDTO)));
    }

    @Transactional
    public void delete(Long id) {
        accountRepository.delete(accountRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID de Cuenta invalido: " + id)));
    }

    @Transactional
    public void update(Long id, AccountDTO entity) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID de Cuenta invalido: " + id));
        account.setRegisterDate(entity.getRegisterDate());
        account.setAvailable(entity.isAvailable());
        account.setIdMPago(entity.getIdMPago());
        accountRepository.save(account);
    }

    @Transactional
    public void linkUser(long userId, long accountId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("ID de usuario invalido: " + userId));

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("ID de cuenta invalido: " + accountId));

        account.addUser(user);
        accountRepository.save(account);
    }

    @Transactional
    public void unlinkUser(long userId, long accountId) {


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("ID de usuario invalido: " + userId));

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("ID de cuenta invalido: " + accountId));

        account.deleteUser(user);
    }

    @Transactional
    public void updateBalance(Double balance, long id) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID de Cuenta invalido: " + id));
        account.setBalance(balance);
        accountRepository.save(account);
    }

    @Transactional
    public Double getBalance(long id) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID de Cuenta invalido: " + id));
        return accountRepository.findById(account.getId()).orElseThrow().getBalance();
    }

    @Transactional
    public void suspendAccount(long id) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID de Cuenta invalido: " + id));
        account.setAvailable(false);
        accountRepository.save(account);
    }

    @Transactional
    public void activateAccount(long id) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID de Cuenta invalido: " + id));
        account.setAvailable(true);
        accountRepository.save(account);
    }


}
