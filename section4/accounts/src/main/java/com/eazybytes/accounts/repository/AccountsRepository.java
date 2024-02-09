package com.eazybytes.accounts.repository;

import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Accounts,Long> {
Optional<Accounts> findByCustomerId(Long customerId);

@Transactional
    void deleteByCustomerId(Long customerId);
}
