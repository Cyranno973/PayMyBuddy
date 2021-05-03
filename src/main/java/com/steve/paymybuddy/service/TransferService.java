package com.steve.paymybuddy.service;

import com.steve.paymybuddy.dto.ExternalTransferDto;
import com.steve.paymybuddy.dto.InternalTransferDto;

import java.sql.SQLException;
import java.util.List;

public interface TransferService {

    InternalTransferDto doInternalTransfer(InternalTransferDto internalTransferDto) throws SQLException;

    ExternalTransferDto doExternalTransfer(ExternalTransferDto externalTransferDto) throws SQLException;

    List<InternalTransferDto> findInternalTransferByUser(String emailOwner);

    List<ExternalTransferDto> findExternalTransferByUser(String username);
}
