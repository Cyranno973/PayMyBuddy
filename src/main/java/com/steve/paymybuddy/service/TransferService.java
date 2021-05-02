package com.steve.paymybuddy.service;

import com.steve.paymybuddy.dto.ExternalTransferDto;
import com.steve.paymybuddy.dto.InternalTransferDto;

import java.util.List;

public interface TransferService {

    InternalTransferDto doInternalTransfer(InternalTransferDto internalTransferDto);

    ExternalTransferDto doExternalTransfer(ExternalTransferDto externalTransferDto);

    List<InternalTransferDto> findInternalTransferByUser(String emailOwner);

    List<ExternalTransferDto> findExternalTransferByUser(String username);
}
