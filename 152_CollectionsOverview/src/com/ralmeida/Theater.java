package com.ralmeida;

import java.util.*;

public class Theater {
    private final String theaterName;
    private List<Seat> seats = new ArrayList<>();

    // Comparator implementation to sort by different fields
    static final Comparator<Seat> PRICE_ORDER = new Comparator<Seat>() {
        @Override
        public int compare(Seat seat1, Seat seat2) {
            if (seat1.getPrice() < seat2.getPrice() ){
                return -1;
            } else if(seat1.getPrice() > seat2.getPrice()){
                return 1;
            } else {
                return 0;
            }
        }
    };


    public Theater(String theaterName, int numberRows, int seatPerRow) {
        this.theaterName = theaterName;

        int lastRow = 'A' + (numberRows - 1);
        for (char row = 'A'; row <= lastRow; row++){
            for (int seatNum = 1; seatNum <= seatPerRow; seatNum++){
                double price = 12.00;
                if ((row < 'D') && (seatNum >= 4 && seatNum <=7)){
                    price = 14.00;
                } else if ((row > 'F') || (seatNum < 4 || seatNum > 7)){
                    price = 7.00;
                }
                Seat seat = new Seat(row + String.format("%02d", seatNum), price);
                seats.add(seat);
            }
        }
    }

    public String getTheaterName() {
        return theaterName;
    }

    public boolean reserveSeat(String seatNumber){
        Seat requestedSeat = new Seat(seatNumber, 0);
        int foundSeat = Collections.binarySearch(seats, requestedSeat, null);
        if(foundSeat >= 0){
            return seats.get(foundSeat).reserve();
        } else {
            System.out.println("There is no set " + seatNumber);
            return false;
        }

//        for (Seat seat : seats){
//            System.out.print(".");
//            if(seat.getSeatNumber().equals(seatNumber)){
//                requestedSeat = seat;
//                break;
//            }
//        }
//
//        if (requestedSeat == null){
//            System.out.println("There is no seat " + seatNumber);
//            return false;
//        }
//
//        return requestedSeat.reserve();
    }

    // for testing
//    public void getSeats(){
//        for (Seat seat : seats){
//            System.out.println(seat.getSeatNumber());
//        }
//    }

    public Collection<Seat> getSeats(){
        return seats;
    }

    public class Seat implements Comparable<Seat>{
        private final String seatNumber;
        private double price;
        private boolean reserved = false;

        public Seat(String seatNumber, double price) {
            this.seatNumber = seatNumber;
            this.price = price;
        }

        public boolean reserve(){
            if(!this.reserved){
                this.reserved = true;
                System.out.println("Seat " + seatNumber + " reserved.");
                return true;
            } else {
                System.out.println("Sorry, seat " + seatNumber + " already reserved.");
                return false;
            }
        }

        public boolean cancel(){
            if(this.reserved){
                this.reserved = false;
                System.out.println("Reservation of seat " + seatNumber + " cancelled.");
                return true;
            } else {
                System.out.println("No reservation found for seat " + seatNumber);
                return false;
            }
        }

        public String getSeatNumber() {
            return seatNumber;
        }

        public double getPrice() {
            return price;
        }

        @Override
        public int compareTo(Seat seat) {
            return this.seatNumber.compareToIgnoreCase(seat.getSeatNumber());
        }
    }
}
