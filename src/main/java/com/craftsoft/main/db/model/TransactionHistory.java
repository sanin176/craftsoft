package com.craftsoft.main.db.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_history_id_generator")
    @SequenceGenerator(name = "transaction_history_id_generator", sequenceName = "transaction_history_id_seq", allocationSize = 1)
    Long id;
    Long walletId;
    Long userId;
    String operation; // in future it might be ENUM
    Double balanceBefore;
    Double balanceAfter;
    Double amount;
    Boolean isSuspicious = false;
    LocalDateTime createdDate = LocalDateTime.now();

}
