package com.engagetech.expenses;

import com.engagetech.expenses.dto.*;
import com.engagetech.expenses.entities.Expense;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
class ExpensesApplicationTests {

	private final ObjectMapper objectMapper = new ObjectMapper();

	private static String USERNAME = "haytham";
	private static String PASSWORD = "haytham123";
	private static final HttpHeaders HTTP_HEADERS = new HttpHeaders();
	private static final RestTemplate REST_TEMPLATE = new RestTemplate();

	private static final String AUTH_URL = "/api/v1/auth";
	private static final String VAT_AMOUNT_CALCULATOR_ENDPOINT = "/api/v1/expenses/vat";
	private static final String EXPENSES_ENDPOINT = "/api/v1/expenses/users/current";
	private static final String ADD_EXPENSES_ENDPOINT = "/api/v1/expenses/";

	@Autowired
	private MockMvc mockMvc;

	@Test
	void authenticateUser_ThenExpectAValidToken() throws Exception {
		String response = this.mockMvc.perform(post(AUTH_URL + "/")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
		.content(this.objectMapper.writeValueAsString(new AuthRequestDTO(USERNAME, PASSWORD)))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		AuthResponseDTO authResponse = this.objectMapper.readValue(response, AuthResponseDTO.class);
		Assertions.assertNotNull(authResponse);
		Assertions.assertNotNull(authResponse.getToken());
		HTTP_HEADERS.set("Authorization", "Bearer " + authResponse.getToken());
	}

	@Test
	void calculateVatIncludedAmount_ThenExpectSameValue() throws Exception {
		final BigDecimal amount = BigDecimal.valueOf(2000L);
		final BigDecimal vatAmount = BigDecimal.valueOf(400.00);
		String response = this.mockMvc.perform(get(VAT_AMOUNT_CALCULATOR_ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON_VALUE).param("amount", amount.toString()).headers(HTTP_HEADERS))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		VatResponseDTO vatResponse = this.objectMapper.readValue(response, VatResponseDTO.class);
		Assertions.assertNotNull(vatResponse);
		Assertions.assertNotNull(vatResponse.getAmount());
		Assertions.assertEquals(vatAmount, vatResponse.getAmount());
	}

	@Test
	void addExpense_ThenExpectSameCounter() throws Exception {
		ResponseEntity<CurrencyResponseDTO> currencyResponse = REST_TEMPLATE.getForEntity("https://api.exchangeratesapi.io/latest?base=EUR", CurrencyResponseDTO.class);
		BigDecimal euroRate = currencyResponse.getBody().getRates().get("GBP");
		final ExpenseRequestDTO expenseRequest = new ExpenseRequestDTO(new Date(), BigDecimal.valueOf(2000L), "EUR", "My Reason");
		// Get Expenses
		String initialExpensesResponse = this.mockMvc.perform(get(EXPENSES_ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON_VALUE).headers(HTTP_HEADERS)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		List<ExpenseDTO> initialExpenses = this.objectMapper.readValue(initialExpensesResponse, new TypeReference<List<ExpenseDTO>>() {});
		// Add New Expense
		String response = this.mockMvc.perform(post(ADD_EXPENSES_ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON_VALUE).headers(HTTP_HEADERS)
				.content(this.objectMapper.writeValueAsString(expenseRequest))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		Expense expense = this.objectMapper.readValue(response, Expense.class);
		// Get Expenses
		String finalExpensesResponse = this.mockMvc.perform(get(EXPENSES_ENDPOINT).headers(HTTP_HEADERS)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		List<ExpenseDTO> finalExpenses = this.objectMapper.readValue(finalExpensesResponse, new TypeReference<>() {});
		// Assertions
		// (EUR * 1000) =>
		final BigDecimal euroAmount = euroRate.multiply(expenseRequest.getAmount());
		final BigDecimal vat = BigDecimal.valueOf(20L).multiply(euroAmount).divide(BigDecimal.valueOf(100L), RoundingMode.DOWN);
		Assertions.assertEquals(getPlainText(euroRate.multiply(expenseRequest.getAmount()).add(vat)), getPlainText(expense.getAmount()));
		Assertions.assertEquals(getPlainText(vat) , getPlainText(expense.getVat()));
		Assertions.assertTrue(initialExpenses.size() < finalExpenses.size());
	}

	/**
	 * Utility to convert BigDecimal to String
	 * @param value amount
	 * @return String
	 */
	public static String getPlainText( BigDecimal value ) {
		if ( value == null )
			return null;
		// Strip only values with decimal digits
		BigDecimal striped = ( value.scale() > 0 ) ? value.stripTrailingZeros() : value;
		return striped.toPlainString();
	}

}
