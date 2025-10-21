package parking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class SyncParkingTest {

    @Test
    public void testParkingSyncDoesNotExceedCapacity() throws InterruptedException {
        int capacity = 4;
        int delayMillis = 1;
        int totalParkers = 3;
        int totalRemovers = 3;
        int carsPerParker = 5;
        int carsPerRemover = 5;

        SyncParking parking = new SyncParking(capacity);

        Thread[] parkers = new Thread[totalParkers];
        Thread[] removers = new Thread[totalRemovers];

        for (int i = 0; i < totalParkers; i++) {
            parkers[i] = new Thread(new CarParker(parking, i * carsPerParker + 1, carsPerParker, delayMillis));
        }
        for (int i = 0; i < totalRemovers; i++) {
            removers[i] = new Thread(new CarRemover(parking, carsPerRemover, delayMillis));
        }

        for (Thread t : parkers) t.start();
        for (Thread t : removers) t.start();
        for (Thread t : parkers) t.join();
        for (Thread t : removers) t.join();

        assertTrue(parking.currentOccupancy() >= 0, "occupancy must be >= 0");
        assertTrue(parking.currentOccupancy() <= parking.getCapacity(), "occupancy must not exceed capacity");
    }
}