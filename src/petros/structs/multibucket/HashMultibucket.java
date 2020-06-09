package petros.structs.multibucket;

import java.util.*;

/**
 * @author Petros Papatheodorou
 * @author GitHub: subamanis
 * @author petrospapa21@gmail.com
 *
 *
 * @param <K> Type of the keys.
 */
public class HashMultibucket<K> implements Multibucket<K>
{
    private final Map<K,List<Bucket<?>>> bucketMap;

    public HashMultibucket()
    {
        bucketMap = new HashMap<>();
    }

    public HashMultibucket(int initialCapacity)
    {
        bucketMap = new HashMap<>(initialCapacity);
    }

    public HashMultibucket(int initialCapacity, int loadFactor)
    {
        bucketMap = new HashMap<>(initialCapacity, loadFactor);
    }


    @Override
    public int size()
    {
        return bucketMap.size();
    }


    @Override
    public <V> void put(final K key, final V e)
    {
        List<Bucket<?>> list = bucketMap.computeIfAbsent(key, k -> new ArrayList<>());
        list.add(Bucket.of(e));
    }


    @Override
    public <V> boolean putIfAbsent(K key, V e)
    {
        List<Bucket<?>> list = bucketMap.get(key);
        if(list == null){
            list = new ArrayList<>();
            list.add(Bucket.of(e));
            bucketMap.put(key, list);
            return true;
        }

        for(Bucket<?> b : list)
        {
            if(b.get().equals(e)){
                return false;
            }
        }

        list.add(Bucket.of(e));
        return true;
    }


    @Override
    public <V> boolean putIfAbsentType(final K key, final V e)
    {
        List<Bucket<?>> list = bucketMap.get(key);
        if(list == null){
            list = new ArrayList<>();
            list.add(Bucket.of(e));
            bucketMap.put(key, list);
            return true;
        }

        Class<?> eClass = e.getClass();
        for(Bucket<?> b : list)
        {
            Class<?> bClass = b.get().getClass();
            if(bClass.equals(eClass)){
                return false;
            }
        }

        list.add(Bucket.of(e));
        return true;
    }


    @Override
    public <V> boolean putInCollection(K key, V e, int index)
    {
        List<Bucket<?>> existing = bucketMap.get(key);
        if(existing == null) return false;

        try{
            Bucket<?> b = existing.get(index);
            if(!b.containsCollection()) return false;
            b.addToCollection(e);
        }catch (Exception ex) {
            return false;
        }

        return true;
    }


    @Override
    public <T, V> boolean putInCollectionOfType(final K key, final V e, final T t, final int index)
    {
        List<Bucket<?>> existing = bucketMap.get(key);
        if(existing == null) return false;

        try{
            Bucket<?> b = existing.get(index);
            if(!b.containsCollection()) return false;
            b.addToCollection(e);
        }catch (Exception ex) {
            return false;
        }

        return true;
    }


    @Override
    public <M, V> boolean putInMap(K key, M mapKey, V e, int index)
    {
        List<Bucket<?>> existing = bucketMap.get(key);
        if(existing == null) return false;

        try{
            Bucket<?> b = existing.get(index);
            if(!b.containsMap()) return false;
            b.addToMap(mapKey, e);
        }catch (Exception ex) {
            return false;
        }

        return true;
    }


    @Override
    public <V> Object replace(final K key, final V e, final int index)
    {
        List<Bucket<?>> existing = bucketMap.get(key);
        if(existing == null) return null;

        try{
            Object prev = existing.get(index).get();
            Bucket<V> newBucket = Bucket.of(e);
            existing.set(index, newBucket);
            return prev;
        }catch (Exception ex) {
            return null;
        }
    }


    @Override @SuppressWarnings("unchecked")
    public <V> V replaceSameType(final K key, final V e, final int index)
    {
        List<Bucket<?>> existing = bucketMap.get(key);
        if(existing == null) return null;

        try{
            int typeCounter = 0;
            int generalCounter = -1;
            Bucket<V> wantedBucket = null;
            for(Bucket<?> b : existing){
                generalCounter++;
                if(b.getType() == e.getClass()){
                    if(typeCounter++ == index){
                        wantedBucket = (Bucket<V>)b;
                        break;
                    }
                }
            }
            if(wantedBucket == null) return null;

            V prevElement = (V)existing.get(generalCounter).get();
            existing.set(generalCounter, Bucket.of(e));
            return prevElement;
        }catch (Exception ex) {
            return null;
        }
    }


    @Override
    public <V> boolean contains(final K key, final V e)
    {
        List<Bucket<?>> existing = bucketMap.get(key);
        if(existing == null) return false;

        for(Bucket<?> b : existing)
        {
            if(b.get().equals(e)) return true;
        }

        return false;
    }


    @Override
    public <T> boolean containsType(final K key, final Class<T> t)
    {
        List<Bucket<?>> existing = bucketMap.get(key);
        if(existing == null) return false;

        for(Bucket<?> b : existing)
        {
            if(b.getType() == t) return true;
        }

        return false;
    }


    @Override
    public boolean containsKey(K key)
    {
        return bucketMap.containsKey(key);
    }


    @Override
    public Set<K> keySet()
    {
        return bucketMap.keySet();
    }


    @Override
    public <V> int getFirstIndex(final K key, final V e)
    {
        List<Bucket<?>> existing = bucketMap.get(key);
        if(existing == null) return -1;

        for (int i = 0; i < existing.size(); i++) {
            if(existing.get(i).get().equals(e)){
                return i;
            }
        }

        return -1;
    }


    @Override
    public <V> List<Integer> getAllIndexes(final K key, final V e)
    {
        List<Bucket<?>> existing = bucketMap.get(key);
        if(existing == null) return new ArrayList<>();

        List<Integer> out = new ArrayList<>();
        for (int i = 0; i < existing.size(); i++) {
            if(existing.get(i).get().equals(e)){
                out.add(i);
            }
        }

        return out;
    }


    @Override @SuppressWarnings("unchecked")
    public <E> E get(final K key, final int index)
    {
        List<Bucket<?>> existing = bucketMap.get(key);
        if(existing == null) return null;

        try{
            return (E)existing.get(index).get();
        }catch (Exception e){
            return null;
        }
    }


    @Override @SuppressWarnings("unchecked")
    public <M, V> V getFromMap(final K key, final M mapKey, final int index)
    {
        List<Bucket<?>> existing = bucketMap.get(key);
        if(existing == null) return null;

        try{
            return ((Map<M,V>)existing.get(index).get()).get(mapKey);
        }catch (Exception e){
            return null;
        }
    }


    @Override
    public <T> List<Bucket<?>> getAllOfType(final K key, final Class<T> t)
    {
        List<Bucket<?>> existing = bucketMap.get(key);
        if(existing == null) return new ArrayList<>();

        List<Bucket<?>> out = new ArrayList<>();
        for(Bucket<?> b : existing)
        {
            if(b.getType() == t) out.add(b);
        }

        return out;
    }


    @Override
    public List<Bucket<?>> getAll(final K key)
    {
        List<Bucket<?>> existing = bucketMap.get(key);
        if(existing == null) return new ArrayList<>();

        return Collections.unmodifiableList(existing);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashMultibucket<?> that = (HashMultibucket<?>) o;
        return bucketMap.equals(that.bucketMap);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(bucketMap);
    }

    @Override
    public String toString()
    {
        return  "HashMultibucket of size: "+size();
    }

}
