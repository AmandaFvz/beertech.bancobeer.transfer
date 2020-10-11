package beertech.becks.api.service;

import beertech.becks.api.entities.CurrentAccount;
import beertech.becks.api.tos.CurrentAccountRequestTO;

import java.math.BigDecimal;

public interface CurrentAccountService {

    CurrentAccount createAccount();

    BigDecimal getBalance(String hashAccountValue);

    CurrentAccount updateCurrentAccountBalance(CurrentAccountRequestTO currentAccountRequestTO);

    CurrentAccount doCurrentAccountTransfer(CurrentAccountRequestTO currentAccountRequestTO);
}
