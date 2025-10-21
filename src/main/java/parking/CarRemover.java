package parking;

public class CarRemover implements Runnable {

    private final int count;
    private final Parking parking;
    private final long delayMillis;

    public CarRemover(Parking parking, int count, long delayMillis) {
        this.count = count;
        this.parking = parking;
        this.delayMillis = delayMillis;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            try {
                Car c = parking.leave();
                System.out.println(Thread.currentThread().getName() + " removed " + c + ". Occupancy: " + parking.currentOccupancy());
                Thread.sleep(delayMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Remover interrupted");
                return;
            } catch (IllegalStateException ex) {
                System.err.println(Thread.currentThread().getName() + " failed to remove: " + ex.getMessage());
            }
        }
    }
}
