package com.craftsoft.main.integrationtest;

import com.craftsoft.main.CraftsoftApplicationTests;
import com.craftsoft.main.dto.WalletDto;
import com.craftsoft.main.dto.request.DepositRequest;
import com.craftsoft.main.dto.request.WalletRequest;
import com.craftsoft.main.service.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WalletControllerTest extends CraftsoftApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WalletService walletService;

    @Test
    @Order(1)
    void createWallet() throws Exception {
        WalletRequest request = getWalletRequest();
        WalletDto dto = new WalletDto();
        dto.setWalletId(1L);

        mockMvc.perform(post("/wallet/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message",
                        is("Wallet " + dto.getWalletId() + " created successfully")));
    }

    @Test
    @Order(2)
    void depositFunds() throws Exception {
        DepositRequest request = new DepositRequest(1345.0);

        mockMvc.perform(post("/wallet/1/deposit/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Funds deposit successfully")));
    }

    @Test
    @Order(3)
    void withdrawFunds() throws Exception {
        DepositRequest request = new DepositRequest(234.0);

        mockMvc.perform(post("/wallet/1/withdraw/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Funds withdrawn successfully")));
    }

    @Test
    void getWalletById() {
    }

    private WalletRequest getWalletRequest() {
        return new WalletRequest("TestName",
                "TestLastName",
                "test@gmail.com",
                "123456789",
                new Timestamp(1000),
                "Test city");
    }
}