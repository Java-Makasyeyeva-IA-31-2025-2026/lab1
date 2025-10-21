package parking;

import java.util.ArrayDeque;
import java.util.Queue;

public class SyncParking implements Parking {

    private final int capacity;
    private final Queue<Car> spots = new ArrayDeque<>();

    public SyncParking(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be > 0");
        this.capacity = capacity;
    }

    @Override
    public synchronized void park(Car car) throws InterruptedException {
        while (spots.size() >= capacity) {
            wait();
        }
        spots.add(car);
        notifyAll();
    }

    @Override
    public synchronized Car leave() throws InterruptedException {
        while (spots.isEmpty()) {
            wait();
        }
        Car c = spots.poll();
        notifyAll();
        return c;
    }

    @Override
    public synchronized int currentOccupancy() {
        return spots.size();
    }

    @Override
    public int getCapacity() {
        return capacity;
    }
}
