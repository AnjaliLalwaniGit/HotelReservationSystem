package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {

    private static AdminResource adminResource=null;

    private CustomerService customerService=null;
    private ReservationService reservationService=null;

    private AdminResource(){
        customerService=CustomerService.getInstance();
        reservationService=ReservationService.getInstance();
    }

    public static AdminResource getInstance(){
        if(adminResource == null){
            adminResource= new AdminResource();
        }
        return adminResource;
    }

    public Customer getCustomer(String email){
       return customerService.getCustomer(email);
    }

    public void addRoom(Collection<IRoom> rooms){

        for(IRoom room:rooms){
            reservationService.addRoom(room);
        }
    }

    public Collection<IRoom> getAllRooms(){

        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers(){

        return customerService.getAllCustomers();
    }

    public void displayAllReservations(){
        reservationService.printAllReservation();
    }

}
