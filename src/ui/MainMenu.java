package ui;

import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import service.ReservationService;

import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

public class MainMenu {

    public static void main(String[] args) {

        Scanner scanner= new Scanner(System.in);
        boolean validInput = false;
        String userInput;
        HotelResource hotelResource=HotelResource.getInstance();
        AdminResource adminResource=AdminResource.getInstance();
        String custEmailRegEx = "^(.+)@(.+).com$";
        Pattern pattern= Pattern.compile(custEmailRegEx);
        String email;

        try
        {
            do{
                System.out.println("****************HOTEL RESERVATION SYSTEM****************\n"+
                        "========================================================\n"+
                        "1.Find and Reserve a room\n" +
                        "2.See my reservations\n" +
                        "3.Create an account\n" +
                        "4.Admin\n" +
                        "5.Exit\n" +
                        "========================================================\n"+
                        "Please enter a number to make the selection:");
                userInput= scanner.nextLine();
                switch(userInput){
                    case "1":
                        findAndReserveARoom();
                        break;
                    case "2":
                        do{
                            System.out.println("Enter email :  (format: name@domain.com)");
                            email= scanner.nextLine();
                        }while(!pattern.matcher(email).matches());
                        Collection<Reservation> custReservations=hotelResource.getCustomersReservation(email);
                        if(custReservations.isEmpty()){
                            System.out.println("No Reservatoins have been made");
                        }
                        for(Reservation reservation:custReservations){
                            System.out.println(reservation);
                        }
                        break;
                    case "3":
                        //Create a user account
                        createCustAccount();
                        break;
                    case "4":
                        AdminMenu adminMenu= new AdminMenu();
                        String selection=adminMenu.
                                selectAdminMenu();
                       if(selection.equals("5")){
                            continue;
                        }
                        validInput = true;
                        break;
                    case "5":
                        validInput = true;
                        break;
                    default:
                        validInput = false;
                        System.out.println("Invalid Input! Please enter a valid input");

                        break;
                }

            }while(!validInput  );
        }catch (Exception exception){
            exception.getLocalizedMessage();
        }
        finally {
            scanner.close();
        }
    }

    public static void findAndReserveARoom(){

        HotelResource hotelResource=HotelResource.getInstance();
        Scanner scanner= new Scanner(System.in);
        String rNumber;
        String custCheckIn;
        String custCheckOut;
        String dateRegEx = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
        Pattern datePattern= Pattern.compile(dateRegEx);
        Date chInDate=null;
        Date chOutDate=null;
        String custBookRoom;
        IRoom roomToBeBooked=null;
        String isCustAccHolder;
        String custEmail=null;
        String custEmailRegEx = "^(.+)@(.+).com$";
        Pattern pattern= Pattern.compile(custEmailRegEx);
        boolean isValidRoomNum=false;

        //Get the check in date
        do {
            System.out.println("Enter CheckIn Date: (format:mm/dd/yyyy) ");
            custCheckIn = scanner.nextLine();
        }while(!datePattern.matcher(custCheckIn).matches()) ;

        //Get the check out date
        do {
            System.out.println("Enter CheckOut Date: (format:mm/dd/yyyy) ");
            custCheckOut = scanner.nextLine();
        }while(!datePattern.matcher(custCheckOut).matches());

        //Find the available rooms
        try {
             chInDate = new SimpleDateFormat("MM/dd/yyyy").parse(custCheckIn);
             chOutDate= new SimpleDateFormat("MM/dd/yyyy").parse(custCheckOut);
        }catch(ParseException parseException){
           int exceptionOffset= parseException.getErrorOffset();
        }
        Collection<IRoom> roomList=hotelResource.findARoom(chInDate,chOutDate);
        String roomNum;
        boolean flag=false;
        if(roomList.isEmpty()) {
            //Find recommended rooms
            Calendar start = Calendar.getInstance();
            start.setTime(chInDate);
            Calendar end = Calendar.getInstance();
            end.setTime(chOutDate);
            //Advance the check in and check out dates by 5 days
            start.add(Calendar.DATE,7);
            chInDate=start.getTime();
            end.add(Calendar.DATE,7);
            chOutDate=end.getTime();
            roomList=hotelResource.findARoom(chInDate,chOutDate);

            if(roomList.isEmpty()) {
                System.out.println("No Rooms Available");
                return;
            }
            else {
                //Display recommeded  rooms
                System.out.println("No rooms available in the selected date range\n"+
                        "Recommended rooms available from  " + chInDate +"  to  "+ chOutDate);
                for (IRoom room : roomList)
                    System.out.println(room);
            }

        }
        else {
            //Display available rooms
            for (IRoom room : roomList)
                System.out.println(room);
        }

        do{
            System.out.println("Do you want to book a room(y/n):");
            custBookRoom= scanner.nextLine();
            if(custBookRoom.equals("n")||custBookRoom.equals("N")){
                break;
            } else if (custBookRoom.equals("y")||custBookRoom.equals("Y")) {
                //Customer wants to book the room
                do {
                    System.out.println("Do you have an account with us? (Y/N):");
                    isCustAccHolder = scanner.nextLine();
                    if (isCustAccHolder.equals("n") || isCustAccHolder.equals("N")) {
                        //Get customer details
                        custEmail=createCustAccount();

                    } else if (isCustAccHolder.equals("y") || isCustAccHolder.equals("Y")) {
                        //Get customer email and look for the account
                        do{
                            System.out.println("Enter email :  (format: name@domain.com)");
                            custEmail= scanner.nextLine();
                        }while(!pattern.matcher(custEmail).matches());
                        Customer rCustomer=hotelResource.getCustomer(custEmail);
                        if(rCustomer == null){
                            //Customer account not found
                            System.out.println("Custmoer Account not found. Please enter your details to create an account");
                            custEmail=createCustAccount();
                        }
                        else{
                            //Customer account found
                            System.out.println(rCustomer);
                        }

                    }
                }while(!isCustAccHolder.equals("N")&&!isCustAccHolder.equals("n")&&
                        !isCustAccHolder.equals("Y")&&!isCustAccHolder.equals("y"));

                //Select the room to book
                do {
                    System.out.println("Enter An Available Room Number:");
                    if (scanner.hasNextInt()) {
                        rNumber = scanner.nextLine();
                        for (IRoom room : roomList)
                            if (room.getRoomNumber().equals(String.valueOf(rNumber))) {
                                //make a reservation for this room number
                                roomToBeBooked = room;
                                isValidRoomNum = true;
                                break;
                            }
                    } else {
                        scanner.nextLine();
                        isValidRoomNum = false;
                    }
                } while (!isValidRoomNum);
                //Now make the reservation
                hotelResource.bookARoom(custEmail,roomToBeBooked,chInDate,chOutDate);
                Collection<Reservation> custReservations=hotelResource.getCustomersReservation(custEmail);
                if(custReservations.isEmpty()){
                    System.out.println("No Reservatoins have been made");
                }
                for(Reservation reservation:custReservations){
                    System.out.println(reservation);
                }

            }
        }while((!custBookRoom.equals("N")&&!custBookRoom.equals("n")) &&
                (!custBookRoom.equals("Y")&&!custBookRoom.equals("y")));

    }

    public static String createCustAccount(){
        HotelResource hotelResource=HotelResource.getInstance();
        Scanner sc= new Scanner(System.in);
        String custEmail;
        String custFirstName;
        String custLastName;
        String custEmailRegEx = "^(.+)@(.+).com$";
        Pattern pattern= Pattern.compile(custEmailRegEx);

        do{
            System.out.println("Enter email :  (format: name@domain.com)");
            custEmail= sc.nextLine();
        }while(!pattern.matcher(custEmail).matches());
        System.out.println("Enter FirstName:");
        custFirstName=sc.nextLine();
        System.out.println("Enter LastName:");
        custLastName=sc.nextLine();
        hotelResource.createACustomer(custEmail,custFirstName,custLastName);
        return custEmail;

    }


}
