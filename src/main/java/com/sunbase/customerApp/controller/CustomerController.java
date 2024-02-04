package com.sunbase.customerApp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sunbase.customerApp.dto.CustomerRequest;
import com.sunbase.customerApp.dto.CustomerResponse;
import com.sunbase.customerApp.dto.SyncResponse;
import com.sunbase.customerApp.service.ApiSyncService;
import com.sunbase.customerApp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * customer end-points
 */
@RestController
@RequestMapping("/api/v1/sunbase/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final ApiSyncService apiSyncService;

    //    create customer using CustomerRequest dto
    @PostMapping("/add-customer")
    public ResponseEntity<String> createCustomer(
            @RequestBody CustomerRequest customerRequest
    ){
        return new ResponseEntity<>(customerService.addCustomer(customerRequest), HttpStatus.OK);
    }
    //     get uuid from user mail
    @GetMapping("/get-uuid")
    public ResponseEntity<String> getCustomerUuidByEmail(
            @RequestParam("email") String email
    ){
        return new ResponseEntity<>(customerService.findCustomerUUID(email),HttpStatus.OK);
    }
    //    update customer
    @PutMapping("/edit-customer")
    public ResponseEntity<String> updateCustomer(
            @RequestParam("id") String uuid,
            @RequestBody CustomerRequest customerRequest
    ){
        return new ResponseEntity<>(customerService.editCustomer(uuid,customerRequest),HttpStatus.OK);
    }
    //      get customer by uuid
    @GetMapping("/get-customerById")
    public ResponseEntity<CustomerResponse> getCustomerById(
            @RequestParam("id") String uuid
    ){
        return new ResponseEntity<>(customerService.findCustomerById(uuid),HttpStatus.OK);
    }

    //    delete customer by their id
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteById(
            @RequestParam("id") String uuid
    ){
        return new ResponseEntity<>(customerService.deleteCustomerById(uuid),HttpStatus.OK);
    }

    @GetMapping("/sync-customer")
    public ResponseEntity<List<Map<String,Object>> > syncCustomerData() throws JsonProcessingException {
        return new ResponseEntity<>(apiSyncService.fetchCustomerData(),HttpStatus.OK);
    }
}