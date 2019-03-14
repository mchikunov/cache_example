import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class LRUTest {

    LRU<String, Object> lru;
    @Rule
    public ExpectedException exception = ExpectedException.none();
    @Before
    public void setUp() throws Exception {
        String[] ss = "new direction generation".split(" ");

        lru = new LRU<>(4);

        for (int i = 0; i < 3; i++) {
            lru.put(ss[i], i+1);
        }
    }

    @After
    public void tearDown() throws Exception {
        lru = null;
    }

    @Test
    public void test() {
        //at the beginning of test we have 3 entry in cache (keys: new, direction, generation) and one spare capacity (4 as max size)
        lru.put("pants", 99); // let occupy the 4th place
        lru.put("window", 89); // let add one more and as the frequency is 1 in every entry, the last one is replaced ("new"),
        // as the oldest
        assertNull(lru.get("new"));
        lru.put("table", 100);
        assertNull(lru.get("direction"));


    }
}