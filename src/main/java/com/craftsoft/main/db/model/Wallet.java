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

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallet_id_generator")
    @SequenceGenerator(name = "wallet_id_generator", sequenceName = "wallet_id_seq", allocationSize = 1)
    Long id;
    Long userId;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    Timestamp dateOfBirth;
    String address;
    String currency = "EUR";
    Double balance = 0.0;
    Boolean isSuspicious = false;

    Double maxLimit = 0.0;
    Boolean isBlock = false;

    //    String createdBy; // in future we can make it
    LocalDateTime createdDate = LocalDateTime.now();
    //    String lastModifiedBy; // in future we can make it
    LocalDateTime lastModifiedDate = LocalDateTime.now();
}
