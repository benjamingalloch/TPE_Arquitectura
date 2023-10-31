package com.example.microservice_account.repositories;

import com.example.microservice_account.entities.Account;
import com.example.microservice_account.entities.User;
import com.example.microservice_account.entities.UserAccount;
import com.example.microservice_account.entities.UserAccountPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("userAccountRepository")
public interface UserAccountRepository extends JpaRepository<UserAccount, UserAccountPK> {
    Optional<UserAccount> findByUserAndAccount(User user, Account account);
    void deleteByUserAndAccount(User user, Account account);
    UserAccount save(UserAccount userAccount);
    Optional<UserAccount> findByUser(User user);

    @Query("SELECT a FROM Account a WHERE a.id IN (SELECT ua.account.id FROM UserAccount ua WHERE ua.user.id = ?1)")
    List<Account> findByUserId(long userId);
}
