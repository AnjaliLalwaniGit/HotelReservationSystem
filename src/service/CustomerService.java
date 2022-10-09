package service;

import model.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    private static CustomerService customerService=null;
    private  Map<String, Customer> customerMap;

    private  CustomerService(){
        customerMap= new HashMap<String,Customer>();
    }

    public static CustomerService getInstance(){
        if (customerService == null)
            customerService= new CustomerService();

        return customerService;
    }

    public void addCustomer(String email, String firstName, String lastName){

        Customer newCustomer = new Customer(firstName,lastName,email);
        customerMap.put(email,newCustomer);
    }

    public Customer getCustomer(String email){
        return  customerMap.get(email);
    }

    public  Collection<Customer> getAllCustomers(){

            Collection<Customer>  customerList = customerMap.values();
            return  customerList;
    }

}
