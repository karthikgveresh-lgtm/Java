
import java.util.*;

enum VehicleType {
    BIKE, CAR, TRUCK
}

class Vehicle {
     String number;
     VehicleType type;

    public Vehicle(String number, VehicleType type) {
        this.number = number;
        this.type = type;
    }

    public VehicleType getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }
}

class ParkingSlot {
     int slotId;
     VehicleType type;
     boolean isOccupied;
     Vehicle vehicle;

    public ParkingSlot(int slotId, VehicleType type) {
        this.slotId = slotId;
        this.type = type;
        this.isOccupied = false;
    }

    public boolean canPark(Vehicle v) {
        return !isOccupied && v.getType() == type;
    }

    public void park(Vehicle v) {
        this.vehicle = v;
        this.isOccupied = true;
    }

    public void removeVehicle() {
        this.vehicle = null;
        this.isOccupied = false;
    }

    public int getSlotId() {
        return slotId;
    }

    public boolean isFree() {
        return !isOccupied;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}

class Floor {
     int floorNumber;
     List<ParkingSlot> slots;

    public Floor(int floorNumber, List<ParkingSlot> slots) {
        this.floorNumber = floorNumber;
        this.slots = slots;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public ParkingSlot findAvailableSlot(Vehicle v) {
        for (ParkingSlot slot : slots) {
            if (slot.canPark(v)) {
                return slot;
            }
        }
        return null;
    }
}

class Ticket {
    private static int counter = 0;
     int ticketId;
     Vehicle vehicle;
     ParkingSlot slot;

    public Ticket(Vehicle vehicle, ParkingSlot slot) {
        this.ticketId = ++counter;
        this.vehicle = vehicle;
        this.slot = slot;
    }

    public ParkingSlot getSlot() {
        return slot;
    }

    public int getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}


class ParkingLott {
     List<Floor> floors;

    public ParkingLott(List<Floor> floors) {
        this.floors = floors;
    }

    public Ticket parkVehicle(Vehicle v) {
        for (Floor floor : floors) {
            ParkingSlot slot = floor.findAvailableSlot(v);
            if (slot != null) {
                slot.park(v);
                System.out.println("Vehicle parked");
                return new Ticket(v, slot);
            }
        }
        System.out.println("No space available");
        return null;
    }

    public void unparkVehicle(Ticket ticket) {
        ticket.getSlot().removeVehicle();
        System.out.println("Vehicle removed");
    }
}

public class ParkingLot {
    public static void main(String[] args) {

        List<ParkingSlot> slots = new ArrayList<>();
        slots.add(new ParkingSlot(1, VehicleType.CAR));
        slots.add(new ParkingSlot(2, VehicleType.BIKE));

        Floor floor = new Floor(1, slots);

        ParkingLott lot = new ParkingLott(Arrays.asList(floor));

        Vehicle car = new Vehicle("KA01AB1234", VehicleType.CAR);

        Ticket ticket = lot.parkVehicle(car);

        lot.unparkVehicle(ticket);
    }
}