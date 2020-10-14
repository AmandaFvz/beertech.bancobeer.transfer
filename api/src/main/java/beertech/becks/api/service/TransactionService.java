package beertech.becks.api.service;

import beertech.becks.api.entities.Transaction;
import beertech.becks.api.tos.TransactionRequestTO;

public interface TransactionService {

    Transaction executeTransaction(TransactionRequestTO transactionTO);

}
