package beertech.becks.api.service.impl;

import beertech.becks.api.entities.CurrentAccount;
import beertech.becks.api.repositories.CurrentAccountRepository;
import beertech.becks.api.service.CurrentAccountService;
import beertech.becks.api.tos.CurrentAccountRequestTO;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CurrentAccountServiceImpl implements CurrentAccountService {

    /**
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger(CurrentAccountServiceImpl.class);

    private CurrentAccountRepository currentAccountRepository;

    @Autowired
    public CurrentAccountServiceImpl(CurrentAccountRepository currentAccountRepository) {
        this.currentAccountRepository = currentAccountRepository;
    }

    @Override
    public CurrentAccount createAccount() {

        LOGGER.info("Creating Current Account...");

        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setBalance(BigDecimal.valueOf(0)); // conta inicia zerada
        currentAccount.setHashValue(generateHashValue());

        return currentAccountRepository.save(currentAccount);
    }

    @Override
    public BigDecimal getBalance(String hashAccountValue) {

        LOGGER.info("Checking Current Account Balance...");

        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setHashValue(hashAccountValue);

        return currentAccountRepository.findByHashValue(currentAccount.getHashValue()).getBalance();
    }

    @Override
    public CurrentAccount updateCurrentAccountBalance(CurrentAccountRequestTO currentAccountRequestTO) {

        AtomicReference<CurrentAccount> newCurrentAccount = new AtomicReference<>(new CurrentAccount());

        CurrentAccount currentAccount = currentAccountRepository.findByHashValue(currentAccountRequestTO.getCurrentAccountIndentify());

        BigDecimal currBalance = currentAccount.getBalance();

        List<BigDecimal> list = new LinkedList<>();
        list.add(currBalance);
        list.add(currentAccountRequestTO.getBalance());
        BigDecimal newBalance = list.stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        LOGGER.info("Updating Current Account Balance to: {"+newBalance+"}");
        currentAccountRepository.findById(currentAccount.getId()).ifPresent((CurrentAccount result) -> {
            result.setBalance(newBalance);
            newCurrentAccount.set(currentAccountRepository.save(result));
        });

        return newCurrentAccount.get();
    }

    @Override
    public CurrentAccount doCurrentAccountTransfer(CurrentAccountRequestTO currentAccountRequestTO) {

        AtomicReference<CurrentAccount> newCurrentAccount = new AtomicReference<>(new CurrentAccount());

        CurrentAccount originAccount = currentAccountRepository.findByHashValue(currentAccountRequestTO.getCurrentAccountIndentify());
        CurrentAccount destinationAccount = currentAccountRepository.findByHashValue(currentAccountRequestTO.getDestinationCurrentAccount());

        LOGGER.info("Executing transfer between accounts...");

        BigDecimal originBalance = originAccount.getBalance();

        List<BigDecimal> listOriginAccount = new LinkedList<>();
        listOriginAccount.add(originBalance);
        listOriginAccount.add(currentAccountRequestTO.getBalance().negate());
        BigDecimal newOriginBalance = listOriginAccount.stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        LOGGER.info("Updating Origin Current Account Balance to: {"+newOriginBalance+"}");
        currentAccountRepository.findById(originAccount.getId()).ifPresent((CurrentAccount result) -> {
            result.setBalance(newOriginBalance);
            newCurrentAccount.set(currentAccountRepository.save(result));
        });

        BigDecimal destinationBalance = destinationAccount.getBalance();

        List<BigDecimal> listDestinationAccount = new LinkedList<>();
        listDestinationAccount.add(destinationBalance);
        listDestinationAccount.add(currentAccountRequestTO.getBalance());
        BigDecimal newDestinationBalance = listDestinationAccount.stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        LOGGER.info("Updating Origin Current Account Balance to: {"+newDestinationBalance+"}");
        currentAccountRepository.findById(destinationAccount.getId()).ifPresent((CurrentAccount result) -> {
            result.setBalance(newDestinationBalance);
            currentAccountRepository.save(result);
        });

        return newCurrentAccount.get();
    }

    private String generateHashValue() {

        int lLimit = 48;
        int rLimit = 122;
        int stringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(lLimit, rLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(stringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return DigestUtils.sha256Hex(generatedString);
    }
}
