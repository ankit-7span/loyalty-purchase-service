package com.loyalty.service.purchase.controller;


import com.loyalty.service.purchase.model.PurchaseRequest;
import com.loyalty.service.purchase.model.PurchaseResponse;
import com.loyalty.service.purchase.model.PurchaseResponseDTO;
import com.loyalty.service.purchase.service.PurchaseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PurchaseController.class)
class PurchaseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PurchaseService purchaseService;

    @Test
    void purchase() throws Exception {
        final var purchaseResponse = new PurchaseResponse(1L, 2L, 4000L, null);
        when(purchaseService.purchase(any(PurchaseRequest.class))).thenReturn(new PurchaseResponseDTO(200, "Purchase Successfully", purchaseResponse));
        final var request = post("/api/purchase/").
                contentType(MediaType.APPLICATION_JSON).content("{\"customerID\":1,\"partnerStoreID\":2,\"purchaseAmount\":4000,\"redemptionPoints\":0}");
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                {
                                          "resultCode": 200,
                                          "resultMessage": "Purchase Successfully",
                                          "response": {
                                              "customerID": 1,
                                              "partnerStoreID": 2,
                                              "purchaseAmount": 4000,
                                              "purchaseDate": null
                                          }
                                      }
                                 """
                )).andReturn();
    }
}