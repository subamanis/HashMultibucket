package petros.structs.multibucket;

import org.junit.jupiter.api.Test;
import petros.structs.multibucket.Bucket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BucketTest
{

    @Test
    void isEmpty()
    {
        Bucket<Integer> b = Bucket.emptyBucket();
        assertTrue(b.isEmpty());

        b.put(3);
        assertFalse(b.isEmpty());
    }

    @Test
    void containsCollection()
    {
        Bucket<Integer> b = Bucket.emptyBucket();
        assertFalse(b.containsCollection());
        b.put(2);
        assertFalse(b.containsCollection());

        Bucket<HashMap<String, Integer>> b2 = Bucket.of(new HashMap<>());
        assertFalse(b2.containsCollection());

        Bucket<ArrayList<Integer>> b1 = Bucket.emptyBucket();
        b1.put(new ArrayList<>(0));
        assertTrue(b1.containsCollection());
    }

    @Test
    void containedInCollection()
    {
        Bucket<Integer> b = Bucket.emptyBucket();
        assertFalse(b.containedInCollection(2));
        b.put(2);
        assertFalse(b.containedInCollection(2));

        List<Integer> ls = new ArrayList<>();
        ls.add(3);
        Bucket<List<Integer>> b1 = Bucket.emptyBucket();
        b1.put(ls);
        assertTrue(b1.containedInCollection(3));
        assertFalse(b1.containedInCollection(2));
        assertFalse(b1.containedInCollection("3"));
    }

    @Test
    void containsMap()
    {
        Bucket<Integer> b = Bucket.emptyBucket();
        assertFalse(b.containsMap());
        b.put(2);
        assertFalse(b.containsMap());

        Bucket<HashMap<String, Integer>> b2 = Bucket.emptyBucket();
        b2.put(new HashMap<>());
        assertTrue(b2.containsMap());

        Bucket<ArrayList<Integer>> b1 = Bucket.emptyBucket();
        b1.put(new ArrayList<>(0));
        assertFalse(b1.containsMap());
    }

    @Test
    void put()
    {
        Bucket<Integer> b = Bucket.emptyBucket();
        assertNull(b.put(1));
        assertEquals(1, b.put(3));
    }

    @Test
    void putIfEmpty()
    {
        Bucket<Integer> b = Bucket.emptyBucket();
        assertNull(b.putIfEmpty(1));
        assertEquals(1, b.putIfEmpty(3));
    }

    @Test
    void addToCollection()
    {
        List<Integer> al = new ArrayList<>();
        Bucket<List<Integer>> b = Bucket.emptyBucket();
        b.put(al);
        assertTrue(b.addToCollection(2));
        assertTrue(al.contains(2));

        Map<String, Integer> hm = new HashMap<>();
        Bucket<Map<String, Integer>> b2 = Bucket.emptyBucket();
        b2.put(hm);
        assertFalse(b2.addToCollection(2));
    }

    @Test
    void addToMap()
    {
        Map<String, Integer> hm = new HashMap<>();
        Bucket<Map<String, Integer>> b2 = Bucket.emptyBucket();
        b2.put(hm);
        assertNull(b2.addToMap("e", 2));
        assertEquals(hm.get("e"), b2.addToMap("e",3));

        List<Integer> al = new ArrayList<>();
        Bucket<List<Integer>> b = Bucket.emptyBucket();
        b.put(al);
        assertTrue(b.addToCollection(2));
        assertTrue(al.contains(2));
    }

    @Test
    void get()
    {
        Bucket<Integer> b = Bucket.emptyBucket();
        assertNull(b.get());
        b.put(2);
        assertEquals(2, b.get());

        List<Integer> ls = new ArrayList<>();
        ls.add(2);
        Bucket<List<Integer>> b1 = Bucket.emptyBucket();
        b1.put(ls);
        assertEquals(ls, b1.get());
    }

    @Test
    void getFromMap()
    {
        Bucket<Integer> b = Bucket.emptyBucket();
        assertNull(b.getFromMap(2));

        Map<Integer,Integer> mp = new HashMap<>();
        mp.put(1,2);
        Bucket<Map<Integer, Integer>> b1 = Bucket.emptyBucket();
        b1.put(mp);
        assertEquals(Integer.valueOf(2), b1.getFromMap(1));
        assertNotEquals(Integer.valueOf(3), b1.getFromMap(1));
        assertNotEquals(Integer.valueOf(2), b1.getFromMap(3));
    }
}