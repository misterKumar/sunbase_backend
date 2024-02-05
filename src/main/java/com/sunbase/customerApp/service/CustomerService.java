package com.sunbase.customerApp.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.sunbase.customerApp.dto.CustomerRequest;
import com.sunbase.customerApp.dto.CustomerResponse;
import com.sunbase.customerApp.entity.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    String addCustomer(CustomerRequest customerRequest);
    String editCustomer(String uuid,CustomerRequest customerRequest);
    String findCustomerUUID(String email);
    CustomerResponse findCustomerById(String uuid);

    List<CustomerResponse> getCustomersBySearch(String searchTerm);

    String deleteCustomerById(String uuid);
    List<CustomerResponse> syncedDataFromRemoteApi() throws JsonProcessingException;
}