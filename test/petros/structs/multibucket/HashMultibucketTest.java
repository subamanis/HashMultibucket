package petros.structs.multibucket;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class HashMultibucketTest
{

    @Test
    void put_size_getAll()
    {
        Multibucket<String> smb = new HashMultibucket<>();

        assertEquals(0, smb.size());
        smb.put("ni",1);
        assertEquals(1, smb.size());
        smb.put("ni",2);
        assertEquals(1, smb.size());
        assertEquals(2, smb.getAll("ni").size());
    }


    @Test
    void putIfAbsent()
    {
        Multibucket<Integer> imb = new HashMultibucket<>();

        assertTrue(imb.putIfAbsent(1,11));
        assertFalse(imb.putIfAbsent(1,11));
        assertTrue(imb.putIfAbsent(1, "ee"));
        assertTrue(imb.putIfAbsent(2, 11));
    }


    @Test
    void putIfAbsentType()
    {
        Multibucket<Integer> imb = new HashMultibucket<>();

        assertTrue(imb.putIfAbsentType(1,11));
        assertTrue(imb.putIfAbsentType(1, "ee"));
        assertFalse(imb.putIfAbsentType(1, 11));
        assertFalse(imb.putIfAbsentType(1, "11"));
        assertTrue(imb.putIfAbsentType(1, new ArrayList<>()));
        assertTrue(imb.putIfAbsentType(2, 11));
        assertTrue(imb.putIfAbsentType(2, "11"));
        assertFalse(imb.putIfAbsentType(2, 111));
    }


    @Test
    void put_putInCollection()
    {
        Multibucket<String> smb = new HashMultibucket<>();

        assertFalse(smb.putInCollection("ni",2,0));
        smb.put("ni",1);
        assertFalse(smb.putInCollection("ni",2,0));
        List<Integer> li = new ArrayList<>();
        li.add(1);
        smb.put("ni",li);
        assertTrue(smb.putInCollection("ni",2,1));
    }


    @Test
    void replace()
    {
        Multibucket<String> smb = new HashMultibucket<>();

        assertNull(smb.replace("ni", 1,1));
        smb.put("ni",3);
        assertEquals(3, smb.replace("ni", 1,0));
        assertNull(smb.replace("ni", 2,1));
        Map<String,Integer> msi = new HashMap<>();
        assertEquals(1, smb.replace("ni", msi,0));
        assertEquals(msi, smb.replace("ni", 44,0));
    }


    @Test
    void replaceSameType()
    {
        Multibucket<String> smb = new HashMultibucket<>();

        assertNull(smb.replaceSameType("ni", 1,1));
        smb.put("ni",3);
        assertEquals(3, smb.replaceSameType("ni", 1,0));
        assertNull(smb.replaceSameType("ni", 2,1));
        smb.put("ni",4);
        smb.put("ni","eee");
        smb.put("ni","errr");
        List<Integer> l1 = new ArrayList<>();
        l1.add(11);
        List<Integer> l2 = new ArrayList<>();
        l2.add(22);

        smb.put("ni",l1);
        smb.put("ni",l2);
        assertEquals(l2, smb.replaceSameType("ni", new ArrayList<>(),1));
        assertEquals("errr", smb.replaceSameType("ni", "44",1));
    }


    @Test
    void contains()
    {
        Multibucket<String> smb = new HashMultibucket<>();
        assertFalse(smb.contains("ee",2));
        assertFalse(smb.contains("ee",null));
        smb.put("ni",3);
        assertTrue(smb.contains("ni", 3));
    }


    @Test
    void containsType()
    {
        Multibucket<String> smb = new HashMultibucket<>();
        assertFalse(smb.containsType("ni",Integer.class));
        smb.put("ni",3);
        assertTrue(smb.containsType("ni",Integer.class));
        smb.put("ni", new HashMap<String, List<Integer>>());
        assertTrue(smb.containsType("ni", HashMap.class));
        assertFalse(smb.containsType("ni", Float.class));
        assertFalse(smb.containsType("ni", String.class));
        assertFalse(smb.containsType("ni", List.class));
    }


    @Test
    void containsKey()
    {
        Multibucket<String> smb = new HashMultibucket<>();
        assertFalse(smb.containsKey("ni"));
        smb.put("ni",3);
        assertTrue(smb.containsKey("ni"));
    }


    @Test
    void keySet()
    {
        Multibucket<String> smb = new HashMultibucket<>();

        assertEquals(0, smb.keySet().size());
        smb.put("ee",1);
        smb.put("ee",2);
        assertEquals(1, smb.keySet().size());
        smb.put("ee1",2);
        assertEquals(2, smb.keySet().size());
    }

    @Test
    void get()
    {
        Multibucket<String> smb = new HashMultibucket<>();

        assertNull(smb.get("ee",0));
        smb.put("ee",1);
        smb.put("ee",2);
        assertEquals((Integer)1, smb.get("ee",0));
        assertNull(smb.get("ee",2));
        smb.put("ee", new HashMap<String,Integer>());
        assertEquals(new HashMap<String,Integer>(), smb.get("ee",2));
    }


    @Test
    void getFirstIndex_allIndexes()
    {
        Multibucket<String> smb = new HashMultibucket<>();

        assertEquals(-1, smb.getFirstIndex("ee",1));
        smb.put("ee", 1);
        smb.put("ee", 1);
        smb.put("ee", 3);
        smb.put("ee", 1);
        assertEquals(2, smb.getFirstIndex("ee",3));
        smb.put("ee", new HashMap<>());
        smb.put("ee", 33);
        smb.put("ee", new HashMap<>());
        assertEquals(4, smb.getFirstIndex("ee",new HashMap<>()));

        assertEquals(3, smb.getAllIndexes("ee",1).size());
        assertEquals(2, smb.getAllIndexes("ee",new HashMap<>()).size());
    }


    @Test
    void getAllOfType()
    {
        Multibucket<String> smb = new HashMultibucket<>();

        assertEquals(0, smb.getAllOfType("ee",Integer.class).size());
        smb.put("ee",1);
        smb.put("ee",2);
        smb.put("ee","s1");
        smb.put("ee","s2");
        smb.put("ee","s3");
        assertEquals(3, smb.getAllOfType("ee",String.class).size());
        assertEquals(0, smb.getAllOfType("ee",HashMap.class).size());
    }


    @Test
    void getAll()
    {
        Multibucket<String> smb = new HashMultibucket<>();

        assertEquals(0, smb.getAll("ee").size());
        smb.put("ee",1);
        smb.put("ee",2);
        smb.put("ee","s1");
        smb.put("ee","s2");
        assertEquals(4, smb.getAll("ee").size());
    }

}