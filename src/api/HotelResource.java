package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {

    private static HotelResource hotelResource=null;

    private CustomerService customerService=null;
    private ReservationService reservationService=null;

    private HotelResource(){
        customerService=CustomerService.getInstance();
        reservationService=ReservationService.getInstance();
    }

    public static HotelResource getInstance(){
        if(hotelResource == null){
            hotelResource= new HotelResource();
        }
        return hotelResource;
    }

    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email,String firstName,String lastName){
        customerService.addCustomer(email,firstName,lastName);
    }

    public IRoom getRoom(String roomNumber){
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate,Date checkOutDate){
        return reservationService.reserveARoom(customerService.getCustomer(customerEmail),room,checkInDate,checkOutDate);
    }

    public Collection<Reservation> getCustomersReservation(String customerEmail){
       return reservationService.getCustomersReservation(customerService.getCustomer(customerEmail));
    }

    public Collection<IRoom> findARoom(Date checkIn,Date checkOut){
       return reservationService.findRooms(checkIn,checkOut);
    }
}
