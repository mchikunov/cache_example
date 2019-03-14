
import java.util.LinkedHashMap;
import java.util.Map;



public class LRU<K, V> implements Cache<K, V>{
    private LRUStorage storage;


    public LRU(int capacity) {
        this.storage = new LRUStorage(capacity);
    }

    @Override
    public V get(K key) {

        return storage.get(key);
    }
    @Override
    public V put(K key, V value) {

        return storage.put(key,value);
    }




    private class LRUStorage extends LinkedHashMap<K, V>{
        private final int capacity;

        private LRUStorage (int capacity){
            this.capacity = capacity;
        }
        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest){
            return size()>capacity ;
        }
    }

    @Override
    public String toString() {
        return  "storage= " + storage ;
    }


}

