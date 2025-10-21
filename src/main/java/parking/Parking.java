package parking;

public interface Parking {

    void park(Car car) throws InterruptedException;

    Car leave() throws InterruptedException;

    int currentOccupancy();

    int getCapacity();
}
