public interface Cache<K, V> {
    public V get(K key);
    public V put(K key, V value);

}
