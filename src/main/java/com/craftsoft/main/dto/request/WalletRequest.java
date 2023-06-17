package com.craftsoft.main.dto.request;

import java.sql.Timestamp;

public record WalletRequest(String firstName,
                            String lastName,
                            String email,
                            String phoneNumber,
                            Timestamp dateOfBirth,
                            String address) {
}
