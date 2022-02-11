package com.publicis.sapient.creditcard.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.publicis.sapient.creditcard.management.mapper.CreditCardMapper;
import com.publicis.sapient.creditcard.management.model.CreditCardRequest;
import com.publicis.sapient.creditcard.management.model.CreditCardResponse;
import com.publicis.sapient.creditcard.management.service.impl.CreditCardServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static com.publicis.sapient.creditcard.management.util.TestUtil.createCreditCardRequest;
import static com.publicis.sapient.creditcard.management.util.TestUtil.createCreditCardResponse;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CreditCardController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class CreditCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private CreditCardController controller;

    @MockBean
    private CreditCardServiceImpl creditCardService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreditCardMapper creditCardMapper;

    @BeforeAll
    public void setup() {
        controller = new CreditCardController(creditCardService, creditCardMapper);
    }

    private static final String CREATE_URL = "/v1/cards/credit-card";
    private static final String GET_URL = "/v1/cards/credit-cards";

    @Test
    @DisplayName("Checks card number fails for minimum length")
    public void invalidCardNumberAndMinLengthCheck() throws Exception {
        CreditCardRequest creditCardRequest = createCreditCardRequest();
        creditCardRequest.setCardNumber("5465");
        mockMvc.perform(MockMvcRequestBuilders.post(CREATE_URL)
                        .content(objectMapper.writeValueAsString(creditCardRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].message", containsString("Invalid request")))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @DisplayName("Checks card number exceeds max length failure")
    public void invalidCardNumberAndMaxLengthCheck() throws Exception {
        CreditCardRequest creditCardRequest = createCreditCardRequest();
        creditCardRequest.setCardNumber("12345678912345678912");
        mockMvc.perform(MockMvcRequestBuilders.post(CREATE_URL)
                        .content(objectMapper.writeValueAsString(creditCardRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].message", containsString("Invalid request")))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @DisplayName("Checks card number has only digits failure")
    public void invalidCardNumberOnlyDigitCheck() throws Exception {
        CreditCardRequest creditCardRequest = createCreditCardRequest();
        creditCardRequest.setCardNumber("12345678912ad^5");
        mockMvc.perform(MockMvcRequestBuilders.post(CREATE_URL)
                        .content(objectMapper.writeValueAsString(creditCardRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].message", containsString("Invalid request")))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("Luhn check failure")
    public void invalidLuhnCheck() throws Exception {
        CreditCardRequest creditCardRequest = createCreditCardRequest();
        creditCardRequest.setCardNumber("1234567891234567");
        mockMvc.perform(MockMvcRequestBuilders.post(CREATE_URL)
                        .content(objectMapper.writeValueAsString(creditCardRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].message", containsString("Invalid request")))
                .andExpect(jsonPath("$[0].details", is("Invalid request - cardNumber Not a valid credit card number")));
    }

    @Test
    @DisplayName("Empty name check failure")
    public void emptyNameCheck() throws Exception {
        CreditCardRequest creditCardRequest = createCreditCardRequest();
        creditCardRequest.setName("");
        mockMvc.perform(MockMvcRequestBuilders.post(CREATE_URL)
                        .content(objectMapper.writeValueAsString(creditCardRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].message", containsString("Invalid request")))
                .andExpect(jsonPath("$[0].details", is("Invalid request - name size must be between 1 and 35")));
    }

    @Test
    @DisplayName("Missing limit parameter failure")
    public void missingLimitParameterCheck() throws Exception {
        CreditCardRequest creditCardRequest = CreditCardRequest.builder().cardNumber("4242424242426742").name("test").build();
        mockMvc.perform(MockMvcRequestBuilders.post(CREATE_URL)
                        .content(objectMapper.writeValueAsString(creditCardRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].message", containsString("Invalid request")))
                .andExpect(jsonPath("$[0].details", is("Invalid request - limit must not be null")));
    }

    @Test
    @DisplayName("Create Credit card success")
    public void createValidCreditCard() throws Exception {
        CreditCardRequest creditCardRequest = createCreditCardRequest();
        CreditCardResponse creditCardResponse = createCreditCardResponse();
        when(creditCardService.createCreditCard(any())).thenReturn(creditCardResponse);
        mockMvc.perform(MockMvcRequestBuilders.post(CREATE_URL)
                        .content(objectMapper.writeValueAsString(creditCardRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("test")));
    }

    @Test
    @DisplayName("Get all credit card success")
    public void getAllCreditCards() throws Exception {
        CreditCardResponse creditCardResponse = createCreditCardResponse();
        List<CreditCardResponse> creditCardResponseList = Arrays.asList(creditCardResponse);
        when(creditCardService.getAllCreditCards()).thenReturn(creditCardResponseList);
        mockMvc.perform(MockMvcRequestBuilders.get(GET_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("Technical error check")
    public void getAllInternalServerError() throws Exception {
        when(creditCardService.getAllCreditCards()).thenThrow(new NullPointerException());
        mockMvc.perform(MockMvcRequestBuilders.get(GET_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Technical error occurred")));
    }

    @Test
    @DisplayName("Data integration violation for reusing same name")
    public void createDataIntegrityViolation() throws Exception {
        CreditCardRequest creditCardRequest = createCreditCardRequest();
        when(creditCardService.createCreditCard(any())).thenThrow(new DataIntegrityViolationException("Test Exception"));
        mockMvc.perform(MockMvcRequestBuilders.post(CREATE_URL)
                        .content(objectMapper.writeValueAsString(creditCardRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details", is("Invalid request - Card number already in use.")));
    }
}
