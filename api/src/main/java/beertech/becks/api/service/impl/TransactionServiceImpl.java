package beertech.becks.api.service.impl;

import beertech.becks.api.entities.CurrentAccount;
import beertech.becks.api.entities.Transaction;
import beertech.becks.api.repositories.TransactionRepository;
import beertech.becks.api.service.CurrentAccountService;
import beertech.becks.api.service.TransactionService;
import beertech.becks.api.tos.CurrentAccountRequestTO;
import beertech.becks.api.tos.TransactionRequestTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static beertech.becks.api.model.TypeOperation.*;
import static java.time.ZonedDateTime.now;

@Service
public class TransactionServiceImpl implements TransactionService {

  /**
   * Logger
   */
  private static final Logger LOGGER = Logger.getLogger(TransactionServiceImpl.class);

  private TransactionRepository transactionRepository;

  private CurrentAccountService currentAccountService;

  @Autowired
  public TransactionServiceImpl(TransactionRepository transactionRepository, CurrentAccountService currentAccountService) {
    this.transactionRepository = transactionRepository;
    this.currentAccountService = currentAccountService;
  }

  @Override
  public Transaction executeTransaction(TransactionRequestTO transactionTO) {

    LOGGER.info("Executing Transaction. " +
            "OperationType:{"+transactionTO.getOperation()+"}, " +
            "TransactionValue:{"+transactionTO.getValue()+"}");

    Transaction transaction = new Transaction();

    if (SAQUE.getDescription().equals(transactionTO.getOperation())) {
      transaction.setValueTransaction(transactionTO.getValue().negate());
      transaction.setTypeOperation(SAQUE);
      transaction.setCurrentAccount(updateCurrentAccountBalance(transactionTO.getOriginAccount(), transactionTO.getValue().negate()));
    }
    else if(DEPOSITO.getDescription().equals(transactionTO.getOperation())) {
      transaction.setValueTransaction(transactionTO.getValue());
      transaction.setTypeOperation(DEPOSITO);
      transaction.setCurrentAccount(updateCurrentAccountBalance(transactionTO.getOriginAccount(), transactionTO.getValue()));
    }
    else {
      transaction.setValueTransaction(transactionTO.getValue());
      transaction.setTypeOperation(TRANSFERENCIA);
      transaction.setDestinationHashValue(transactionTO.getDestinationAccount());
      transaction.setCurrentAccount(doCurrentAccountTransfer(transactionTO.getOriginAccount(), transactionTO.getDestinationAccount(), transactionTO.getValue()));
    }

    transaction.setDateTime(now());

    return transactionRepository.save(transaction);
  }

  private CurrentAccount updateCurrentAccountBalance(String currAccount, BigDecimal value) {

    CurrentAccountRequestTO currentAccountRequestTO = new CurrentAccountRequestTO();
    currentAccountRequestTO.setCurrentAccountIndentify(currAccount);
    currentAccountRequestTO.setBalance(value);

    return currentAccountService.updateCurrentAccountBalance(currentAccountRequestTO);
  }

  private CurrentAccount doCurrentAccountTransfer(String originCurrAccount, String destinationCurrAccount, BigDecimal value) {

    CurrentAccountRequestTO currentAccountRequestTO = new CurrentAccountRequestTO();
    currentAccountRequestTO.setCurrentAccountIndentify(originCurrAccount);
    currentAccountRequestTO.setDestinationCurrentAccount(destinationCurrAccount);
    currentAccountRequestTO.setBalance(value);

    return currentAccountService.doCurrentAccountTransfer(currentAccountRequestTO);
  }
}
