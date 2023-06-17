package com.craftsoft.main.controller;

import com.craftsoft.main.dto.ApiResponse;
import com.craftsoft.main.dto.WalletDto;
import com.craftsoft.main.dto.request.DepositRequest;
import com.craftsoft.main.dto.request.WalletRequest;
import com.craftsoft.main.dto.request.WithdrawRequest;
import com.craftsoft.main.service.WalletService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wallet")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletController {

    WalletService walletService;

    /*
     * TODO
     * we need to take 'userId' from session/token and so on...this is for example now
     * */
    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse<WalletDto>> createWallet(@RequestBody WalletRequest walletRequest,
                                                               @PathVariable final Long userId) {
        WalletDto walletDto = walletService.createWallet(walletRequest, userId);
        ApiResponse<WalletDto> response = new ApiResponse<>("success",
                String.format("Wallet %s created successfully", walletDto.getWalletId()), walletDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * TODO
     * we need to take 'userId' from session/token and so on...this is for example now
     * */
    @PostMapping("/{walletId}/deposit/{userId}")
    public ResponseEntity<ApiResponse<Void>> depositFunds(@RequestBody DepositRequest depositRequest,
                                                          @PathVariable final Long walletId,
                                                          @PathVariable final Long userId) {
        walletService.depositFunds(depositRequest, walletId, userId);
        ApiResponse<Void> response = new ApiResponse<>("success",
                "Funds deposit successfully", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * TODO
     * we need to take 'userId' from session/token and so on...this is for example now
     * */
    @PostMapping("/{walletId}/withdraw/{userId}")
    public ResponseEntity<ApiResponse<Void>> withdrawFunds(@RequestBody WithdrawRequest withdrawRequest,
                                                           @PathVariable final Long walletId,
                                                           @PathVariable final Long userId) {
        walletService.withdrawFunds(withdrawRequest, walletId, userId);
        ApiResponse<Void> response = new ApiResponse<>("success",
                "Funds withdrawn successfully", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * TODO
     * we need to take 'userId' from session/token and so on...this is for example now
     * */
    @GetMapping("/{walletId}/{userId}")
    public ResponseEntity<Void> getWalletById(@PathVariable final Long walletId,
                                              @PathVariable final Long userId) {
        walletService.getWalletById(walletId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
