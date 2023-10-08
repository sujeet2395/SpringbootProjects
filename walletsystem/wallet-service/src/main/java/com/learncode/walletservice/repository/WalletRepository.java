package com.learncode.walletservice.repository;

import com.learncode.walletservice.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUsernameAndActive(String fromUsername, Boolean active);

    Optional<Wallet> findByUsername(String username);
}
