package model;

public class Room implements IRoom{

    String roomNumber;
    Double roomPrice;
    RoomType roomType ;
    boolean isFree;

    public Room(String roomNumber, Double roomPrice, RoomType roomType, boolean isFree) {
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.roomType = roomType;
        this.isFree = isFree;
    }


    public Double getRoomPrice(){
        return roomPrice;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public RoomType getRoomType(){

        return roomType;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setRoomPrice(Double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    @Override
    public boolean isFree() {
        return isFree;
    }

    @Override
    public String toString() {
        return ("ROOM:"+ "Room Number "+ roomNumber+ " "+
                         "Price "+ roomPrice+ " " +
                          "Room Type:  " + roomType+ " BED");
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
