package com.example.microservice_account.repositories;

import com.example.microservice_account.DTOs.AccountDTO;
import com.example.microservice_account.entities.Account;
import com.example.microservice_account.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT a " +
            "FROM User u " +
            "JOIN u.accounts a " +
            "WHERE u.id = :userId")
    List<Account> getAccountsByUserId(long userId);
}
