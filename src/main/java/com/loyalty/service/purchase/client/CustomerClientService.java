package com.loyalty.service.purchase.client;

import com.loyalty.service.purchase.model.ResponseDTO;
import com.loyalty.service.purchase.webclient.WebClientConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class CustomerClientService {

    @Value(value = "${customer.client.uri}")
    private String baseUrl;


    @Autowired
    private WebClientConfig webClientConfig;

    public ResponseDTO getCustomerById(Long customerId) {
        log.info("Calling customer client service to get customer detail having customer id {} ", customerId);
        String endPointUrl = "/getPoints/";
        WebClient webClient = webClientConfig.getWebClient();
        return webClient.get().uri(baseUrl + endPointUrl + customerId).retrieve().bodyToMono(ResponseDTO.class).block();
    }
}
