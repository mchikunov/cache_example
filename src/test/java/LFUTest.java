

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;






public class LFUTest {
    LFU<String, Object> lfu;
    @Rule
    public ExpectedException exception = ExpectedException.none();
    @Before
    public void setUp() throws Exception {
        String[] ss = "new direction generation".split(" ");

        lfu = new LFU<>(4);

        for (int i = 0; i < 3; i++) {
            lfu.put(ss[i], i+1);
        }
    }

    @After
    public void tearDown() throws Exception {
        lfu = null;
    }



    @Test
    public void test() {
        //at the beginning of test we have 3 entry in cache (keys: new, direction, generation) and one spare capacity (4 of max size)
        lfu.put("pants", 99); // let occupy the 4th place
        lfu.put("window", 89); // let add one more and as the frequency is 1 in every entry, the last one is replaced ("sugar")
       lfu.get("window"); // increase frequency to 2
       lfu.get("new"); //increase frequency to 2
       lfu.get("generation"); //increase frequency to 2
        // the only 1 frequency left in "direction", lets check if it will be replaced with new key on eviction.
        assertEquals(1, lfu.getFrequency(2)); //get frequency of "direction" by its value 2
       lfu.put("checkDeletionDirectionFrequencyOne", 100); //put for eviction test one more entry
       assertEquals(1, lfu.getFrequency(100));// check that entry is in cache
        assertNull(lfu.get("direction")); //check that "direction" entry with least frequency is deleted from cache
    }



}

