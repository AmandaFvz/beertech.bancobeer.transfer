package beertech.becks.api.tos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentAccountRequestTO {

    private String currentAccountIndentify;

    private BigDecimal balance;

    private String destinationCurrentAccount;
}
