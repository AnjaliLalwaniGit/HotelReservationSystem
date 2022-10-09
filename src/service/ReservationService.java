package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.time.LocalDate;
import java.util.*;

public class ReservationService {

    private static ReservationService reservationService=null;

    //Map holding the room number(String) and room details(IRoom)
    private Map<String, IRoom> roomMap;
    //Map to keep track of booking for a room(roomID) for a date.
    //Holds list of dates a room is booked for.
    private Map<String, List<Date>> bookingsMap;//List has more overhead??HashSEt??

    private Collection<Reservation> reservations;

    private ReservationService(){
        roomMap= new HashMap<String,IRoom>();
        bookingsMap= new HashMap<String,List<Date>>();
        reservations= new ArrayList<Reservation>();
    }

    public static ReservationService getInstance(){
        if(reservationService == null)
        {
            reservationService= new ReservationService();
        }
        return reservationService;
    }

    Collection<Reservation> getReservations() {
        return reservations;
    }

    void setReservations(Collection<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void addRoom(IRoom room){
        String roomNumber=room.getRoomNumber();
        roomMap.put(roomNumber,room);
        List<Date> dateList= new ArrayList<Date>();
        bookingsMap.put(roomNumber,dateList);

    }

    public IRoom getARoom(String roomId){
        return roomMap.get(roomId);
    }

    public Collection<IRoom> getAllRooms(){
        Collection<IRoom>  roomList= roomMap.values();
        return roomList;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate,Date checkOutDate){

        Reservation reservation=null;
        List<Date> dateList=null;


        //Iterate through the days to book the room
        Calendar start = Calendar.getInstance();
        start.setTime(checkInDate);

        Calendar end = Calendar.getInstance();
        end.setTime(checkOutDate);

        while( !start.after(end)){
            boolean isAvailable= false;
            Date targetDay = start.getTime();
            dateList=bookingsMap.get(room.getRoomNumber());
            dateList.add(targetDay);
            start.add(Calendar.DATE, 1);
        }
        //update the bookings for the room
        bookingsMap.replace(room.getRoomNumber(),dateList);

        reservation= new Reservation(customer,room,checkInDate,checkOutDate);
        reservations.add(reservation);
        return reservation;
    }



    public Collection<IRoom>  findRooms(Date checkInDate,Date checkOutDate) {
        Collection<IRoom> rooms = new ArrayList<IRoom>();
        boolean roomAvailable=false;
        List<Date> dates = new ArrayList<Date>();

        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();

        for (Map.Entry<String, List<Date>> entry : bookingsMap.entrySet()) {

            start.setTime(checkInDate);
            end.setTime(checkOutDate);
            dates=entry.getValue();
            if(dates.isEmpty()){
                roomAvailable= true;
            }
            else{

                while (!start.after(end)) {
                    Date targetDay = start.getTime();
                    roomAvailable = !(dates.contains(targetDay));
                    if(roomAvailable == false)
                        break;
                    start.add(Calendar.DATE, 1);
                }
            }
            if(roomAvailable == true)
                rooms.add(roomMap.get(entry.getKey()));
        }

    return rooms;

    }


    public Collection<Reservation> getCustomersReservation(Customer customer){

        Collection<Reservation> custReservation=new ArrayList<Reservation>();

        Iterator<Reservation>  iterator=reservations.iterator();

        while (iterator.hasNext()){
            Reservation reservation=iterator.next();
             if(reservation.getCustomer().equals(customer)){
                 custReservation.add(reservation);
             }
        }
        return custReservation;
    }

    public void printAllReservation(){

        if(reservations.size() == 0){
            System.out.println("No Reservations found");
        }
        else{
            for(Reservation reservation: reservations){

                System.out.println(reservation);
            }
        }

    }

}
