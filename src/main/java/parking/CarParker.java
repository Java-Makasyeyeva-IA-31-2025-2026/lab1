package parking;

public class CarParker implements Runnable {

    private final int count;
    private final int startId;
    private final long delayMillis;
    private final Parking parking;

    public CarParker(Parking parking, int startId, int count, long delayMillis) {
        this.count = count;
        this.parking = parking;
        this.startId = startId;
        this.delayMillis = delayMillis;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            try {
                Car car = new Car(startId + i);
                parking.park(car);
                System.out.println(Thread.currentThread().getName() + " parked " + car + ". Occupancy: " + parking.currentOccupancy());
                Thread.sleep(delayMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Parker interrupted");
                return;
            } catch (IllegalStateException ex) {
                System.err.println(Thread.currentThread().getName() + " failed to park: " + ex.getMessage());
            }
        }
    }
}
