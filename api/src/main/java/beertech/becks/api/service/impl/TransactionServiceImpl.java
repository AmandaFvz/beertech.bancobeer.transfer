package beertech.becks.api.service.impl;

import beertech.becks.api.entities.Balance;
import beertech.becks.api.entities.Transaction;
import beertech.becks.api.repositories.TransactionRepository;
import beertech.becks.api.service.TransactionService;
import beertech.becks.api.tos.TransactionRequestTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static beertech.becks.api.model.TypeOperation.DEPOSITO;
import static beertech.becks.api.model.TypeOperation.SAQUE;
import static java.time.ZonedDateTime.now;

@Service
public class TransactionServiceImpl implements TransactionService {

  private TransactionRepository transactionRepository;

  @Autowired
  public TransactionServiceImpl(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  @Override
  public Transaction createTransaction(TransactionRequestTO transactionTO) {
    Transaction transaction = new Transaction();

    if (SAQUE.getDescription().equals(transactionTO.getOperation())) {
      transaction.setValueTransaction(transactionTO.getValue().negate());
      transaction.setTypeOperation(SAQUE);
    } else {
      transaction.setValueTransaction(transactionTO.getValue());
      transaction.setTypeOperation(DEPOSITO);
    }

    transaction.setDateTime(now());

    return transactionRepository.save(transaction);
  }

  @Override
  public Balance getBalance() {
    Balance balance = new Balance();
    BigDecimal sum =
        transactionRepository.findAll().stream()
            .map(Transaction::getValueTransaction)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    balance.setBalance(sum);
    return balance;
  }
}
