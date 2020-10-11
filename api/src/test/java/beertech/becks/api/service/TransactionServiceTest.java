package beertech.becks.api.service;

import beertech.becks.api.entities.Transaction;
import beertech.becks.api.model.TypeOperation;
import beertech.becks.api.repositories.TransactionRepository;
import beertech.becks.api.service.impl.TransactionServiceImpl;
import beertech.becks.api.tos.TransactionRequestTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    private TransactionRepository transactionRepository;

    private TransactionService service;

    private TransactionRequestTO transactionTO;

    private Transaction transaction;

    private BigDecimal transactionValue;

    @Test
    public void given_withdraw_when_createTransaction_then_should_return_success() {

        transactionRepository = mock(TransactionRepository.class);

        //GIVEN
        service = new TransactionServiceImpl(transactionRepository);
        transactionValue = new BigDecimal(100);

        transactionTO = new TransactionRequestTO();
        transactionTO.setOperation("S");
        transactionTO.setValue(transactionValue);

        transaction = new Transaction();
        transaction.setValueTransaction(transactionTO.getValue().negate());
        transaction.setTypeOperation(TypeOperation.SAQUE);
        transaction.setDateTime(ZonedDateTime.now());

        //WHEN
        service.createTransaction(transactionTO);

        //THEN
        Assertions.assertNotNull(transaction.getDateTime());
        Assertions.assertEquals(transactionTO.getOperation(), transaction.getTypeOperation().getDescription());
        Assertions.assertEquals(transactionTO.getValue().negate(), transaction.getValueTransaction());
    }

    @Test
    public void given_deposit_when_createTransaction_then_should_return_success() {

        //GIVEN
        transactionRepository = mock(TransactionRepository.class);

        service = new TransactionServiceImpl(transactionRepository);
        transactionValue = new BigDecimal(500);

        transactionTO = new TransactionRequestTO();
        transactionTO.setOperation("D");
        transactionTO.setValue(transactionValue);

        transaction = new Transaction();
        transaction.setValueTransaction(transactionTO.getValue());
        transaction.setTypeOperation(TypeOperation.DEPOSITO);
        transaction.setDateTime(ZonedDateTime.now());

        //WHEN
        service.createTransaction(transactionTO);

        //THEN
        Assertions.assertNotNull(transaction.getDateTime());
        Assertions.assertEquals(transactionTO.getOperation(), transaction.getTypeOperation().getDescription());
        Assertions.assertEquals(transactionTO.getValue(), transaction.getValueTransaction());
    }
}
