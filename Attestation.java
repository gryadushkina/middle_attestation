import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Random;

class Toy {
    int id;
    String name;
    int frequency;

    public Toy(int id, String name, int frequency) {
        this.id = id;
        this.name = name;
        this.frequency = frequency;
    }
}

public class Attestation {
    private PriorityQueue<Toy> toyQueue;

    public Attestation() {
        toyQueue = new PriorityQueue<>((t1, t2) -> t2.frequency - t1.frequency);
    }
    public void put(int id, String name, int frequency) {
        Toy toy = new Toy(id, name, frequency);
        toyQueue.add(toy);
    }

    public int get() {
        Random random = new Random();
        int totalFrequency = toyQueue.stream().mapToInt(t -> t.frequency).sum();
        int randomValue = random.nextInt(totalFrequency);
        int currentFrequency = 0;
        for (Toy toy : toyQueue) {
            currentFrequency += toy.frequency;
            if (randomValue < currentFrequency) {
                return toy.id;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        Attestation toyStore = new Attestation();

        toyStore.put(1, "машинка", 2);
        toyStore.put(2, "кукла", 2);
        toyStore.put(3, "робот", 6);

        try (FileWriter writer = new FileWriter("output.txt")) {
            for (int i = 0; i < 10; i++) {
                int toyId = toyStore.get();
                writer.write(String.valueOf(toyId));
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}