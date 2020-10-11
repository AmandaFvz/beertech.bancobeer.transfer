package beertech.becks.api.controller;

import beertech.becks.api.entities.CurrentAccount;
import beertech.becks.api.entities.Transaction;
import beertech.becks.api.service.CurrentAccountService;
import beertech.becks.api.tos.CurrentAccountRequestTO;
import beertech.becks.api.tos.TransactionRequestTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static beertech.becks.api.constants.Constants.*;

@RestController
@RequestMapping("/currentAccount")
@Api(value = "Bank Becks Service")
public class CurrentAccountController {

    @Autowired
    private CurrentAccountService currentAccountService;

    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = STATUS_201_CREATED),
                    @ApiResponse(code = 400, message = STATUS_400_BAD_REQUEST),
                    @ApiResponse(code = 500, message = STATUS_500_INTERNAL_SERVER_ERROR)
            })
    @PostMapping
    public ResponseEntity<CurrentAccount> createCurrentAccount() {

        CurrentAccount createdCurrentAccount = currentAccountService.createAccount();

        return new ResponseEntity<>(createdCurrentAccount, HttpStatus.CREATED);
    }

    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = STATUS_200_GET_OK),
                    @ApiResponse(code = 404, message = STATUS_404_NOTFOUND),
                    @ApiResponse(code = 500, message = STATUS_500_INTERNAL_SERVER_ERROR)
            })
    @GetMapping
    public ResponseEntity<BigDecimal> getBalance(@RequestParam String hashAccountValue) {

        BigDecimal balance = currentAccountService.getBalance(hashAccountValue);
        return new ResponseEntity<>(balance, HttpStatus.OK);

    }
}
