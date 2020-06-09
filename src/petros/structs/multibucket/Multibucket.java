package petros.structs.multibucket;

import java.util.List;
import java.util.Set;

/**
 * @author Petros Papatheodorou
 * @author GitHub: subamanis
 * @author petrospapa21@gmail.com
 *
 *
 * @param <K> The type of the key.
 */
public interface Multibucket<K>
{

    /**
     * @return Number of key-value pairs
     */
    int size();


    /**
     * @return A {@code Set} containing the keys.
     */
    Set<K> keySet();


    /**
     * Check if the given value is associated with the given key.
     * @param <V> Type of the value (can be any type, but Collections and Maps will not be checked in depth).
     * @return {@code false} if no such value exists for the given key.
     */
    <V> boolean contains(K key, V e);


    /**
     * Check if the given key has an association with a value of this type.
     * @param t The class obtained by {@code .getClass()} or {@code .class}.
     * @return {@code false} if no such class type element exists for the given key.
     */
    <T> boolean containsType(K key, Class<T> t);


    /**
     * Check if the given key exists in the structure
     * @return {@code true} if the key exists.
     */
    boolean containsKey(K key);


    /**
     * Create a new {@code Bucket} for the provided key and put an element inside. If no such key exists, create a pair.
     */
    <V> void put(K key, V e);


    /**
     * Associates the specified value with the given key, if no such an association exists.
     * @return {@code true} if the value has been added, or {@code false} if the value already existed.
     */
    <V> boolean putIfAbsent(K key, V e);


    /**
     * If a {@code Bucket} of such type doesn't exist, create one with the specified element. If no such key exists, create a pair.
     * @return {@code true} if the operation is successful, {@code false} if such a type is associated with this key.
     */
    <V> boolean putIfAbsentType(K key, V e);


    /**
     * If such a key exists, put the specified value inside the {@code Collection} of the {@code Bucket} of the specified index.
     * @param index The index of the {@code Bucket}, relative to all the buckets associated with the provided key.
     * @return {@code true} if the operation is successful, {@code false} if the key doesn't exist or if the index of
     * the {@code Bucket} is wrong or if the specified Bucket doesn't contain a modifiable {@code Collection}.
     */
    <V> boolean putInCollection(K key, V e, int index);


    /**
     * If such a key exists, put the specified value inside the {@code Collection} of the {@code Bucket} of the specified index,
     * relative to the Buckets of the specified type.
     * @param index The index of the {@code Bucket}, relative only to the Buckets of the specified type.
     * @param <T> The type of the {@code Bucket} that will host the element.
     * @return {@code true} if the operation was successful, {@code false} if the key doesn't exist or if the index of
     * the {@code Bucket} is wrong.
     */
    <T,V> boolean putInCollectionOfType(K key, V e, T t, int index);


    /**
     * If the {@code Bucket} of the specified index contains a {@code Map}, put a key-value pair inside.
     * @param key The key of the {@code Bucket}.
     * @param mapKey The key to be put in the {@code Map}.
     * @param index The index of the {@code Bucket}, relative to all the buckets associated with the provided key.
     * @return {@code true} if the operation was successful, {@code false} if the key doesn't exist or if the index of
     * the {@code Bucket} is wrong or if the specified {@code Bucket} doesn't contain a {@code Map}.
     */
    <M,V> boolean putInMap(K key, M mapKey, V e, int index);


    /**
     * If such a key exists, replaces the element of the {@code Bucket} at the specified index with the given value.
     * @param index The index of the {@code Bucket} relative to all the others.
     * @return The previous element of the {@code Bucket}, that may have any type.
     */
    <V> Object replace(K key, V e, int index);


    /**
     * If such a key exists, replaces the element of the {@code Bucket} at the specified index relative to the other Buckets
     * of the same type, with the given element.
     * @param index The index of the {@code Bucket} relative to the other Buckets of the same type.
     * @param <V> The type both of the new and the existing element.
     * @return The previous element.
     */
    <V> V replaceSameType(K key, V e, int index);


    /**
     * Get the index number of the first occurrence of the specified value from the Buckets of the given key.
     * @return The number of the index if such a value was found, or -1 if no such key exists or if the value wasn't found.
     */
    <V> int getFirstIndex(K key, V e);


    /**
     * Get all the numbers of the indexes of the Buckets that contain an element equal to the given.
     * @return An {@code ArrayList} with all the indexes, or an empty {@code ArrayList} if the key doesn't exist or
     * no such Buckets are mapped to this key.
     */
    <V> List<Integer> getAllIndexes(K key, V e);


    /**
     * Return the element of the {@code Bucket} of the specified index from to Buckets of the given key.
     * @param <E> The return type (can be anything).
     * @return true if the operation was successful, false if no such key exists, or an exception as thrown while
     * trying to access the {@code Bucket} of the index.
     */
    <E> E get(K key, int index);


    /**
     * If the {@code Bucket} at the specified index contains a {@code Map}, get the value associated with the given key.
     * @param key The key of the {@code Bucket}.
     * @param mapKey The key to be put in the {@code Map}.
     * @return The value of the key of the Map if the operation was successful, {@code null} if a key doesn't exist
     * or if the index of the {@code Bucket} is wrong or if the specified {@code Bucket} doesn't contain a {@code Map}.
     */
    <M,V> V getFromMap(K key, M mapKey, int index);


    /**
     * Get all the elements of the specified class type that are mapped to the given key.
     * @param t The class obtained by .getClass() or .class.
     * @return An {@code ArrayList} with the matching Buckets.
     */
    <T> List<Bucket<?>> getAllOfType(K key, Class<T> t);


    /**
     * Get all the Buckets that are mapped with the specified key.
     * @return An {@code ArrayList} of all the Buckets.
     */
    List<Bucket<?>> getAll(K key);

}
