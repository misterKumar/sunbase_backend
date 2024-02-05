package com.sunbase.customerApp.service.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sunbase.customerApp.dto.SyncResponse;
import com.sunbase.customerApp.service.ApiSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ApiSyncServiceImpl implements ApiSyncService {
    private final RestTemplate restTemplate;

    //     authenticate the user by providing the credentials
    @Override
    public String authenticateAndGetToken() {
        // Authentication request
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type","application/json");
//        hard coded login credential for external api call
        String loginId = "test@sunbasedata.com";
        String password = "Test@123";
        HttpEntity<String> request = new HttpEntity<>(
                "{\"login_id\":\"" + loginId + "\", " +
                        "\"password\":\"" + password + "\"}",
                headers);
        String AUTH_URL = "https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
        ResponseEntity<String> response = restTemplate.postForEntity(
                AUTH_URL,
                request,
                String.class);
//        check if the authentication is successful
        if(response.getStatusCode().is2xxSuccessful()){
//            the response body is a JSON object with an "access_token"
            return response.getBody();
        }else {
            throw new RuntimeException("Authentication failed");
        }
//        the response contains the token
    }

    //    fetch customer details after authentication by using the Bearer token
    @Override
    public List<Map<String,Object>> fetchCustomerData() throws JsonProcessingException {
        String token = convertStringToJson(authenticateAndGetToken());
        String CUSTOMER_URL = "https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp";
        // Fetch data request with the token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        // Add the cmd parameter as a query parameter in the URL
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(CUSTOMER_URL)
                .queryParam("cmd", "get_customer_list");

        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<List<Map<String,Object>>> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<Map<String,Object>>>() {});

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
            // Process the responseData as needed
        } else {
            throw new RuntimeException("authentication failed.");
        }
    }
    //    convert string to
    public String convertStringToJson(String token){
        JsonObject jsonObject = JsonParser.parseString(token).getAsJsonObject();
        // Access the value of access_token
        return jsonObject.get("access_token").getAsString();
    }
}