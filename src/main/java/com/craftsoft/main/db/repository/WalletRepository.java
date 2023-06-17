package com.craftsoft.main.db.repository;

import com.craftsoft.main.db.model.Wallet;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends CrudRepository<Wallet, Long> {

    Optional<Wallet> findByIdAndUserId(Long id, Long userId);

    @Modifying
    @Query("UPDATE Wallet SET isBlock = false WHERE isBlock = true")
    void unblockWallets();

}
