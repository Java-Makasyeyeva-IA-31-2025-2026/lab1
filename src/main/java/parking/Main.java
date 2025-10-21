package parking;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int capacity = 3;

//        parking(new SyncParking(capacity));
//        parking(new DesyncParking(capacity));
    }

    private static void parking(Parking parking) throws InterruptedException {
        int totalParkers = 2;
        int totalRemovers = 2;
        int carsPerParker = 5;
        int carsPerRemover = 5;
        long parkerDelayMillis = 100;
        long removerDelayMillis = 150;

        Thread[] parkers = new Thread[totalParkers];
        Thread[] removers = new Thread[totalRemovers];

        for (int i = 0; i < totalParkers; i++) {
            parkers[i] = new Thread(
                    new CarParker(parking, i * carsPerParker + 1, carsPerParker, parkerDelayMillis),
                    "Parker-" + (i + 1)
            );
        }

        for (int i = 0; i < totalRemovers; i++) {
            removers[i] = new Thread(
                    new CarRemover(parking, carsPerRemover, removerDelayMillis),
                    "Remover-" + (i + 1)
            );
        }

        for (Thread t : parkers) t.start();
        for (Thread t : removers) t.start();

        for (Thread t : parkers) t.join();
        for (Thread t : removers) t.join();

        System.out.println("Simulation finished. Final occupancy = " + parking.currentOccupancy());
    }
}
