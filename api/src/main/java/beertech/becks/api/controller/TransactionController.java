package beertech.becks.api.controller;

import beertech.becks.api.entities.Balance;
import beertech.becks.api.entities.Transaction;
import beertech.becks.api.service.TransactionService;
import beertech.becks.api.tos.TransactionRequestTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static beertech.becks.api.constants.Constants.STATUS_200_GET_OK;
import static beertech.becks.api.constants.Constants.STATUS_201_CREATED;
import static beertech.becks.api.constants.Constants.STATUS_400_BAD_REQUEST;
import static beertech.becks.api.constants.Constants.STATUS_404_NOTFOUND;
import static beertech.becks.api.constants.Constants.STATUS_500_INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/transactions")
@Api(value = "Bank Becks Service")
public class TransactionController {

  @Autowired private TransactionService transactionService;

  @ApiResponses(
      value = {
        @ApiResponse(code = 201, message = STATUS_201_CREATED),
        @ApiResponse(code = 400, message = STATUS_400_BAD_REQUEST),
        @ApiResponse(code = 500, message = STATUS_500_INTERNAL_SERVER_ERROR)
      })
  @PostMapping
  public ResponseEntity<Transaction> createTransaction(
      @RequestBody TransactionRequestTO transactionTO) {
    Transaction createdTransaction = transactionService.createTransaction(transactionTO);

    return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
  }

  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = STATUS_200_GET_OK),
        @ApiResponse(code = 404, message = STATUS_404_NOTFOUND),
        @ApiResponse(code = 500, message = STATUS_500_INTERNAL_SERVER_ERROR)
      })
  @GetMapping
  public ResponseEntity<Balance> getBalance() {
    Balance balance = transactionService.getBalance();
    return new ResponseEntity<>(balance, HttpStatus.OK);
  }
}
