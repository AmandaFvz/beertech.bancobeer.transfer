package beertech.becks.consumer.tos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Represents the json sent by the rabbit queue
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
	/**
	 * The operation type (S || D)
	 */
	private String operation;

	/**
	 * The operation value
	 */
	private BigDecimal value;

	private String originAccount;

	private String destinationAccount;
}