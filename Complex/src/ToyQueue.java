import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Random;

public class ToyQueue {
    private final PriorityQueue<Toy> toyQueue;
    private final Random random;

    public ToyQueue(String[] ids, String[] names, String[] frequencies) {
        toyQueue = new PriorityQueue<>();
        random = new Random();

        if (ids.length < 3 || names.length < 3 || frequencies.length < 3) {
            throw new IllegalArgumentException("Минимум 3 строки для каждого параметра должны быть предоставлены.");
        }

        for (int i = 0; i < 3; i++) {
            int id = Integer.parseInt(ids[i]);
            String name = names[i];
            int frequency = Integer.parseInt(frequencies[i]);
            toyQueue.add(new Toy(id, name, frequency));
        }
    }

    public void runGet(int times) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            for (int i = 0; i < times; i++) {
                int result = getRandomToyId();
                writer.write(Integer.toString(result));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getRandomToyId() {
        int randomNumber = random.nextInt(100);
        int threshold = 0;

        for (Toy toy : toyQueue) {
            threshold += toy.getFrequency();
            if (randomNumber < threshold) {
                return toy.getId();
            }
        }

        // Default case (shouldn't happen)
        return toyQueue.peek().getId();
    }

    public static void main(String[] args) {
        String[] ids = {"1", "2", "3"};
        String[] names = {"конструктор", "робот", "кукла"};
        String[] frequencies = {"2", "2", "6"};

        ToyQueue toyQueue = new ToyQueue(ids, names, frequencies);
        toyQueue.runGet(10);
    }
}

class Toy implements Comparable<Toy> {
    private final int id;
    private final String name;
    private final int frequency;

    public Toy(int id, String name, int frequency) {
        this.id = id;
        this.name = name;
        this.frequency = frequency;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getFrequency() {
        return frequency;
    }

    @Override
    public int compareTo(Toy other) {
        return Integer.compare(this.frequency, other.frequency);
    }
}
