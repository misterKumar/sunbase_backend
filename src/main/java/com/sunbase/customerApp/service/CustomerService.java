package com.sunbase.customerApp.service;



import com.sunbase.customerApp.dto.CustomerRequest;
import com.sunbase.customerApp.dto.CustomerResponse;

import java.util.UUID;

public interface CustomerService {
    String addCustomer(CustomerRequest customerRequest);
    String editCustomer(String uuid,CustomerRequest customerRequest);
    String findCustomerUUID(String email);
    CustomerResponse findCustomerById(String uuid);

    String deleteCustomerById(String uuid);
}
