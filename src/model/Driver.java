package model;

import service.CustomerService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Driver {
    public static void main(String[] args) {
        Customer customer=new Customer("Anjali","Lalwani","anjli@ttt.com");

        //System.out.println(customer);

        //Test code to check customer service
        /*
        CustomerService customerService= new CustomerService();
        customerService.addCustomer("aaa@dd.com","aaaaaa","aewe");
        customerService.addCustomer("bbb@dd.com","bbbbbbb","faskdflk");
        customerService.addCustomer("ccc@dd.com","ccc","dsjfka");
        customerService.addCustomer("ddd@dd.com","dsfasdf","dddasdgasdg");

        Collection<Customer>  list= customerService.getAllCustomers();

       Iterator<Customer> iterator=list.iterator();

       while (iterator.hasNext()) {
           System.out.println(iterator.next());




       }
       */



    }
}
