package com.sunbase.customerApp.service.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sunbase.customerApp.dto.CustomerRequest;
import com.sunbase.customerApp.dto.CustomerResponse;
import com.sunbase.customerApp.dto.SyncResponse;
import com.sunbase.customerApp.entity.Customer;
import com.sunbase.customerApp.repository.CustomerRepository;
import com.sunbase.customerApp.service.ApiSyncService;
import com.sunbase.customerApp.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    private final ApiSyncService apiSyncService;
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
    private Customer getExistCustomer(String uuid){
        Optional<Customer> existingCustomer = customerRepository.findByUuid(uuid);
        return existingCustomer.orElse(null);
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
                    .uuid(customer.getUuid())
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
    //    synced data from the remote api, getting data from syncAPI service
    public List<CustomerResponse> syncedDataFromRemoteApi() throws JsonProcessingException {

        List<Map<String, Object>> data = apiSyncService.fetchCustomerData();
//        store synced data from remote api in list
        List<Object> syncResponses = new ArrayList<>();
        for (Map<String, Object> datum : data) {
            SyncResponse syncResponse = new SyncResponse();
            syncResponse.setFirstName((String) datum.get("first_name"));
            syncResponse.setLastName((String) datum.get("last_name"));
            syncResponse.setStreet((String) datum.get("street"));
            syncResponse.setAddress((String) datum.get("address"));
            syncResponse.setCity((String) datum.get("city"));
            syncResponse.setState((String) datum.get("street"));
            syncResponse.setEmail((String) datum.get("email"));
            syncResponse.setPhone((String) datum.get("phone"));
            syncResponse.setUuid((String) datum.get("uuid"));
            syncResponses.add(syncResponse);
        }
        List<CustomerResponse> customerResponses = new ArrayList<>();
        updateCustomerTableWithRemoteData(syncResponses);
        //          fetch all the data from database;
        List<Customer> customers = customerRepository.findAll();
        sortByName(customers);
        for (Customer c : customers){
            if (c.getUuid() != null && !c.getUuid().isEmpty()) {
                CustomerResponse customerResponse = findCustomerById(c.getUuid());
                customerResponses.add(customerResponse);
            }
        }
        return customerResponses;
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
//        sort customer
        sortByName(customers);
        return findCustomerResponse(customers);
    }

    private void updateCustomerTableWithRemoteData(List<Object> syncResponse){
//      check if customer exist in the database or not, if not exist insert a new record
//        if present update it into our database
        for (Object key: syncResponse.stream().toList()) {
            SyncResponse c = (SyncResponse) key;
//            customer Request
            CustomerRequest customerRequest = new CustomerRequest(
                    c.getFirstName(),
                    c.getLastName(),
                    c.getStreet(),
                    c.getAddress(),
                    c.getCity(),
                    c.getState(),
                    c.getEmail(),
                    c.getPhone()
            );
//            Customer
            Customer customer = Customer.builder()
                    .uuid(c.getUuid())
                    .firstName(c.getFirstName())
                    .lastName(c.getLastName())
                    .street(c.getStreet())
                    .address(c.getAddress())
                    .city(c.getCity())
                    .state(c.getState())
                    .email(c.getEmail())
                    .phone(c.getPhone())
                    .build();

            System.out.println(c.getUuid());
            if(!customerRepository.existsByUuid(c.getUuid())){
                System.out.println(customerRequest.getFirstName());
//                create new customer
                addCustomer(customer);
            }else{
//                update with existing customer
                editCustomer(c.getUuid(),customerRequest);
            }
        }
    }
    //    add customer for remote api data
    private void addCustomer(Customer customer){
        customer = Customer.builder()
                .uuid(customer.getUuid())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .street(customer.getStreet())
                .address(customer.getAddress())
                .state(customer.getState())
                .city(customer.getCity())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .build();
//        save customer in customer table
        customerRepository.save(customer);
    }
    //    getting customer response
    private List<CustomerResponse> findCustomerResponse(List<Customer> customers){
        List<CustomerResponse> customerResponses = new ArrayList<>();
        for(Customer c : customers){
            CustomerResponse customerResponse = CustomerResponse.builder()
                    .uuid(c.getUuid())
                    .firstName(c.getFirstName())
                    .lastName(c.getLastName())
                    .address(c.getAddress())
                    .city(c.getCity())
                    .state(c.getState())
                    .email(c.getEmail())
                    .phone(c.getPhone())
                    .build();
            customerResponses.add(customerResponse);
        }
        return customerResponses;
    }
    //    search by search option and search query
    public List<CustomerResponse> getCustomersBySearch(String searchOption, String query){
        List<Customer> customers = null;
        List<CustomerResponse> customerResponses = null;
        if(searchOption.equals("First Name")){
//            search by first name
            customers = customerRepository.findByFirstName(query).stream().toList();
            sortByName(customers);
            customerResponses = findCustomerResponse(customers);

        } else if (searchOption.equals("Email")) {
//            search by email
            customers = customerRepository.findByEmail(query).stream().toList();
            sortByName(customers);
            customerResponses = findCustomerResponse(customers);

        } else if (searchOption.equals("City")) {
//            search by city
            customers = customerRepository.findByCity(query).stream().toList();
            sortByName(customers);
            customerResponses = findCustomerResponse(customers);

        }else if(searchOption.equals("Phone")){
//            search by phone
            customers = customerRepository.findByPhone(query).stream().toList();
            sortByName(customers);
            customerResponses = findCustomerResponse(customers);
        }else {
            throw new RuntimeException("search term not found");
        }
//        all the customer who matches with search term in sorted by first and last name
        return customerResponses;
    }
    //    sort all customers by their first and last name
    private void sortByName(List<Customer> customers){

        try {
            customers.sort((c1, c2) -> c1.getFirstName().toLowerCase().compareTo(c2.getFirstName().toLowerCase()));
        }catch (Exception e){
            throw new RuntimeException("Not found");
        }
    }

}