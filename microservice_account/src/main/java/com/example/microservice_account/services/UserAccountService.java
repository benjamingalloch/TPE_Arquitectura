package com.example.microservice_account.services;

import com.example.microservice_account.entities.Account;
import com.example.microservice_account.repositories.UserAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userAccountService")
public class UserAccountService{
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Transactional
    public List<Account> getCuentasByUserId(long userId) {
        return this.userAccountRepository.findByUserId(userId);
    }

}