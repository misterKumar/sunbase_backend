package com.sunbase.customerApp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sunbase.customerApp.dto.CustomerRequest;
import com.sunbase.customerApp.dto.CustomerResponse;
import com.sunbase.customerApp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * customer end-points
 */

@RestController
@RequestMapping("/api/v1/sunbase/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
//    create customer using CustomerRequest dto


    @PostMapping("/add-customer")
    public ResponseEntity<String> createCustomer(
            @RequestBody CustomerRequest customerRequest
    ){
        return new ResponseEntity<>(customerService.addCustomer(customerRequest), HttpStatus.OK);
    }
    @GetMapping("/get-all-customers")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers(){
        return new ResponseEntity<>(customerService.getAllCustomers(),HttpStatus.OK);
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
    //    sync customer from remote api
    @GetMapping("/sync-customer")
    public ResponseEntity<List<CustomerResponse>> syncedDataFromRemoteApi() throws JsonProcessingException{
        return new ResponseEntity<>(customerService.syncedDataFromRemoteApi(),HttpStatus.OK);
    }
    //    search by search term in sorted order
    @GetMapping("/search")
    public ResponseEntity<List<CustomerResponse>> searchResult(
            @RequestParam("searchOption") String searchOption, @RequestParam("q") String query
    ){
        return new ResponseEntity<>(customerService.getCustomersBySearch(searchOption, query),HttpStatus.OK);
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        SecurityContextHolder.clearContext(); // Clear authentication context
        return ResponseEntity.ok().build();
    }
}