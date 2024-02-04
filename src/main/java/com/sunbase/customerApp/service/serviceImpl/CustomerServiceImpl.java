package com.sunbase.customerApp.service.serviceImpl;


import com.sunbase.customerApp.dto.CustomerRequest;
import com.sunbase.customerApp.dto.CustomerResponse;
import com.sunbase.customerApp.entity.Customer;
import com.sunbase.customerApp.repository.CustomerRepository;
import com.sunbase.customerApp.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    @Override
    public String addCustomer(CustomerRequest customerRequest) {
        Customer customer;
        String email = customerRequest.getEmail();
        String phone = customerRequest.getPhone();
//        checking for existing email and phone number
        if(customerRepository.existsByEmail(email)){
            return "invalid email";
        }
        if(customerRepository.existsByPhone(phone)){
            return "number already registered";
        }
        customer = Customer.builder()
                .uuid(UUID.randomUUID().toString())
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .street(customerRequest.getStreet())
                .address(customerRequest.getAddress())
                .state(customerRequest.getState())
                .city(customerRequest.getCity())
                .phone(customerRequest.getPhone())
                .email(customerRequest.getEmail())
                .build();
//        save customer in customer table
        customerRepository.save(customer);
        return "customer added successfully";
    }
    //    get customer by uuid
    public Customer getExistCustomer(String uuid){
        Optional<Customer> existingCustomer = customerRepository.findByUuid(uuid);
        return existingCustomer.orElse(null);
    }
    //    get uuid by customer email
    @Override
    public String findCustomerUUID(String email){
        Optional<Customer> existingCustomer = customerRepository.findByEmail(email);
        Customer customer = null;
        if(existingCustomer.isPresent()){
            customer = existingCustomer.get();
        }
        return customer != null ? customer.getUuid() : null;
    }
    //    update customer details
    @Override
    public String editCustomer(String uuid,CustomerRequest customerRequest){
//        get existing customer
        Customer customer = getExistCustomer(uuid);
        if(customer != null){
            customer.setFirstName(customerRequest.getFirstName());
            customer.setLastName(customerRequest.getLastName());
            customer.setStreet(customerRequest.getStreet());
            customer.setAddress(customerRequest.getAddress());
            customer.setState(customerRequest.getState());
            customer.setCity(customerRequest.getCity());
            customer.setPhone(customerRequest.getPhone());
            customer.setEmail(customerRequest.getEmail());
            customerRepository.save(customer);
        }else {
            throw new RuntimeException("Customer with email " + customerRequest.getEmail() + " not found");
        }
        return "edit successfully";
    }
    @Override
    public CustomerResponse findCustomerById(String uuid){
        Customer customer = getExistCustomer(uuid);
        CustomerResponse customerResponse = null;
        if (customer != null){
            customerResponse = CustomerResponse.builder()
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .address(customer.getAddress())
                    .city(customer.getCity())
                    .state(customer.getState())
                    .email(customer.getEmail())
                    .phone(customer.getPhone())
                    .build();
        }
        return customerResponse;
    }
    //     delete customer by id
    @Override
    @Transactional
    public String deleteCustomerById(String uuid) {
        Customer customer = getExistCustomer(uuid);
        if(customer != null){
            customerRepository.deleteByUuid(uuid);
            return "Customer deleted successfully";
        }else {
            throw new RuntimeException("Customer not exist");
        }
    }
}