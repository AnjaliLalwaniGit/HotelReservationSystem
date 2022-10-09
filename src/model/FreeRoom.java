package model;

public class FreeRoom extends Room{

    public FreeRoom(String roomNumber, Double roomPrice, RoomType roomType, boolean isFree) {

        super(roomNumber,roomPrice,roomType,isFree);
        this.roomPrice=0.0;
        this.isFree=true;
    }


    @Override
    public String toString() {
        return ( "ROOM:"+ "Room Number "+ roomNumber+ " "+
                "Price "+ "FREE "+ " " +
                "Room Type:  " + roomType + " BED");
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
