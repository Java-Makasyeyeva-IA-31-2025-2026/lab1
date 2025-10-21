package parking;

import java.util.ArrayDeque;
import java.util.Queue;

public class DesyncParking implements Parking {

    private final int capacity;
    private final Queue<Car> spots = new ArrayDeque<>();

    public DesyncParking(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be > 0");
        this.capacity = capacity;
    }

    @Override
    public void park(Car car) {
        if (spots.size() >= capacity) {
            throw new IllegalStateException("No free spots (attempt to park while full)");
        }
        spots.add(car);
    }

    @Override
    public Car leave() {
        if (spots.isEmpty()) {
            throw new IllegalStateException("No cars to leave (attempt to remove from empty)");
        }
        return spots.poll();
    }

    @Override
    public int currentOccupancy() {
        return spots.size();
    }

    @Override
    public int getCapacity() {
        return capacity;
    }
}
