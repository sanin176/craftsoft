package com.craftsoft.main.dto.mapper;

import com.craftsoft.main.db.model.Wallet;
import com.craftsoft.main.dto.WalletDto;
import com.craftsoft.main.dto.request.WalletRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WalletMapper {
    Wallet toWallet(WalletRequest walletRequest, Long userId);

    @Mapping(source = "id", target = "walletId")
    WalletDto toWalletDto(Wallet wallet);
}
