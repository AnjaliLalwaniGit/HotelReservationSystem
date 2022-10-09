package ui;

import api.AdminResource;
import api.HotelResource;
import model.*;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    AdminResource adminResource=null;
    Collection<IRoom> rList=new ArrayList<IRoom>();


    public AdminMenu(){
        adminResource= AdminResource.getInstance();

    }
    public String selectAdminMenu(){

        Scanner scanner= new Scanner(System.in);
        boolean validInput = false;
        String adminMenuInput;

        do{
            System.out.println( "========================================================\n"+
                    "1.See all customers\n" +
                    "2.See all rooms\n" +
                    "3.See all reservations\n" +
                    "4.Add a room\n" +
                    "5.Back to Main Menu\n"+
                    "========================================================");
            adminMenuInput= scanner.nextLine();
            switch(adminMenuInput){
                case "1":
                    getAllCustomers();
                    break;
                case "2":
                    getAllRooms();
                    break;
                case "3":
                    displayAllReserv();
                    break;
                case "4":
                    adminAddRooms();
                    break;
                case "5":
                    validInput = true;
                    return adminMenuInput;
                default:
                    System.out.println("Invalid Input! Please enter a valid input");
                    break;
            }

        }while(!validInput);
        return adminMenuInput;
    }

    private Collection<IRoom> getRoomList(Collection<IRoom> roomList) {
        return roomList;
    }

    public void addRoom(){
        Scanner sc= new Scanner(System.in);
        int roomNum;
        String roomNumber;
        IRoom room;
        boolean roomExists=false;

        try
        {
            //Get the room details
            //Room Number
           do{
               System.out.println("Enter Room Number:");
               while(!sc.hasNextInt()){
                   sc.next();
                   System.out.println("Invalid Input !!\nEnter Room Number:");
               }
               roomNum=sc.nextInt();
               roomExists=false;
               //Collection<IRoom> allRooms= adminResource.getAllRooms();
               if(!rList.isEmpty()){
                   for(IRoom roomIt : rList){
                       if(roomIt.getRoomNumber().equals(String.valueOf(roomNum))){
                           roomExists=true;//duplicate room number
                           System.out.println("Duplicate room entry");
                           break;
                       }
                   }
               }
           }while(roomExists);

            //Room price
            System.out.println("Enter price per night:");
            while(!sc.hasNextDouble()){
                sc.next();
                System.out.println("Invalid Input !!\nEnter price per night:");
            }
            double price=sc.nextDouble();
            //Room Type
            System.out.println("Enter Room Type: 1 for SINGLE BED , 2 for DOUBLE BED:");
            while( !sc.hasNextInt() ){
                sc.next();
                System.out.println("Invalid Input !!\n" +
                        "Enter Room Type: 1 for SINGLE BED , 2 for DOUBLE BED:");
            }
            int roomType= sc.nextInt();
            while((roomType != 1) &&(roomType != 2) ){
                System.out.println("Invalid Input !!\n" +
                       "Enter Room Type: 1 for SINGLE BED , 2 for DOUBLE BED:");
                roomType= sc.nextInt();

            }
            roomNumber=String.valueOf(roomNum);
            //Add Room
            if(price == 0.0){//room is free
                if(roomType ==1){//single bed
                    room=new FreeRoom(roomNumber,price,RoomType.SINGLE,true);
                }
                else{
                    room=new FreeRoom(roomNumber,price,RoomType.DOUBLE,true);
                }
            }
            else{
                if(roomType ==1){//single bed
                    room=new Room(roomNumber,price,RoomType.SINGLE,false);
                }
                else{
                    room=new Room(roomNumber,price,RoomType.DOUBLE,false);
                }
            }
            rList.add(room);

        }catch (Exception exception){
            exception.getLocalizedMessage();
        }

    }

    public void getAllCustomers(){

        Collection<Customer>  custList=adminResource.getAllCustomers();
        if(custList.size()== 0){
            //No customer found
            System.out.println("No customer found!!\n" +
                    "Make another selection");
        }
        else {
            for (Customer customer : custList) {
                System.out.println(customer);
            }
        }

    }

    public void getAllRooms(){
        Collection<IRoom> roomList=adminResource.getAllRooms();
        if(roomList.size()== 0){
            //No customer found
            System.out.println("No Rooms found!!\n" +
                    "Make another selection");
        }
        else {
            for (IRoom room : roomList) {
                System.out.println(room);
            }
        }
    }

    public void displayAllReserv(){
        adminResource.displayAllReservations();
    }
    public void adminAddRooms(){

        Scanner sc= new Scanner(System.in);
        String input;
        addRoom();

        do{
            System.out.println("Do you want to add another room(y/n):");
            input= sc.nextLine();
            if(input.equals("n")||input.equals("N")){
                break;
            } else if (input.equals("y")||input.equals("Y")) {
                addRoom();
            }

        }while (!input.equals("N")||!input.equals("n"));
        adminResource.addRoom(rList);
    }

}
