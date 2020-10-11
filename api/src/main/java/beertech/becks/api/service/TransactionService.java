package beertech.becks.api.service;

import beertech.becks.api.entities.Balance;
import beertech.becks.api.entities.Transaction;
import beertech.becks.api.tos.CurrentAccountRequestTO;
import beertech.becks.api.tos.TransactionRequestTO;

public interface TransactionService {

    Transaction executeTransaction(TransactionRequestTO transactionTO);

}
