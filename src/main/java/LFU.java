
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class LFU<K, V> implements Cache<K, V>{

    private final LinkedHashMap<K, Node> storage;
    private final int capacity;

    public LFU(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity should be more than 0");
        }
        this.capacity = capacity;
        this.storage = new LinkedHashMap<>(capacity, 1);


    }



   @Override
    public V get(K key) {
        Node node = storage.get(key);
        if (node == null) {
            return null;
        }
        return node.incrementFrequency().getValue();
    }

   @Override
    public V put(K key, V value) {

        doEvictionIfNeeded(key);

        Node oldNode = storage.put(key, new Node(value));
        if (oldNode == null) {
            return null;
        }
        return oldNode.getValue();
    }

    long getFrequency(V val){
        Node node = new Node(val);
        return node.getFrequency();
    }


    private void doEvictionIfNeeded(K putKey) {
        if (storage.size() < capacity) {
            return;
        }
        long minFrequency = Long.MAX_VALUE;
        K keyToRemove = null;
        for (Map.Entry<K, Node> entry : storage.entrySet()) {
            if (Objects.equals(entry.getKey(), putKey)) {

                return;
            }
            if (minFrequency >= entry.getValue().getFrequency()) {
                minFrequency = entry.getValue().getFrequency();
                keyToRemove = entry.getKey();
            }
        }
        storage.remove(keyToRemove);
    }

    private class Node {
        private final V value;
        private long frequency;


        public Node(V value) {
            this.value = value;
            this.frequency = 1;
        }

        public V getValue() {
            return value;
        }

        public long getFrequency() {
            return frequency;
        }

        public Node incrementFrequency() {
            ++frequency;
            return this;
        }

        @Override
        public String toString() {
            return "Node [value=" + value + ", frequency=" + frequency + "]";
        }

    }
    @Override
    public String toString() {
        return "storage = "+ storage + ", capacity=" + capacity ;
    }
}