package beertech.becks.api.entities;

import beertech.becks.api.model.TypeOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "operation")
	private TypeOperation typeOperation;

	@Column(name = "value_transaction")
	private BigDecimal valueTransaction;

	@Column(name = "data_transaction")
	private ZonedDateTime dateTime;

	@ManyToOne
	@JoinColumn(name = "account_hash_value", referencedColumnName = "hash_value")
	private CurrentAccount currentAccount;

	@Column(name = "destination_hash_value")
	private String destinationHashValue;
}
