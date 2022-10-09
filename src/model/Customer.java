package model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {

    private String firstName;
    private String lastName;
    private String email;


    public Customer(String firstName, String lastName, String email) {
        final String emailRegEx = "^(.+)@(.+).com$";
        Pattern pattern= Pattern.compile(emailRegEx);
         if(!pattern.matcher(email).matches()){
             throw new IllegalArgumentException("ERROR! - Invalid Email");
         }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {

        final String emailRegEx = "^(.+)@(.+).com$";
        Pattern pattern= Pattern.compile(emailRegEx);
        if(!pattern.matcher(email).matches()){
            throw new IllegalArgumentException("ERROR! - Invalid Email");
        }
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer: "+firstName + " " + lastName + " " + email;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Customer)) {
            return false;
        }
        Customer customer = (Customer) obj;
        return firstName.equals(customer.firstName)&& lastName.equals(customer.lastName)&& email.equals(customer.email);

    }

    @Override

    public int hashCode() {
        int hash = 5;
        hash= hash + 31* (this.firstName != null? this.firstName.hashCode(): 0);
        hash= hash + 31* (this.lastName != null? this.lastName.hashCode(): 0);
        hash= hash + 31* (this.email != null? this.email.hashCode(): 0);
        return hash;
    }
}
