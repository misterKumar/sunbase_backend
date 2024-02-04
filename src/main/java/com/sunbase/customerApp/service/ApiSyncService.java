package com.sunbase.customerApp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sunbase.customerApp.dto.CustomerResponse;
import com.sunbase.customerApp.dto.SyncResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ApiSyncService {
    String authenticateAndGetToken();
    List<Map<String,Object>> fetchCustomerData() throws JsonProcessingException;
}