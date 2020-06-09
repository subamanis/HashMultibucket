package petros.structs.multibucket;

/**
 * @author Petros Papatheodorou
 * @author GitHub: subamanis
 * @author petrospapa21@gmail.com
 *
 *
 * @param <E> The type of the element of the Bucket.
 */
public interface GenericBucket<E>
{

    /**
     * Returns whether the Bucket contains an element or not.
     * @return {@code true} if an element is already in the Bucket, {@code false} if not.
     */
    boolean isEmpty();


    /**
     * Remove the element from the Bucket.
     */
    void empty();


    /**
     * Checks whether the {@code Bucket} contains a {@code Collection}.
     * @return {@code true} if the element is {@code instanceof Collection}.
     */
    boolean containsCollection();


    /**
     * If the bucket contains a {@code Collection}, it checks whether the element is inside.
     * @param e Element to check.
     * @return {@code true} if the {@code Bucket} contains a {@code Collection} and element is inside.
     */
    <T> boolean containedInCollection(T e);


    /**
     * Checks whether the {@code Bucket} contains a {@code Map}.
     * @return {@code true} if the element is {@code instanceof Map}.
     */
    boolean containsMap();


    /**
     * Puts an element inside the {@code Bucket}, replaces existing one if any.
     * @param e Element to be put.
     * @return Previous element in the bucket, else {@code null}.
     */
    E put(E e);


    /**
     * Puts an element inside the {@code Bucket}, if the {@code Bucket} was empty
     * @param e Element to be put
     * @return Existing element in the bucket, else {@code null}
     */
    E putIfEmpty(E e);


    /**
     * If the bucket contains a {@code Collection}, add an element inside it.
     * @param e Element to be added.
     * @return {@code true} if element gets added or {@code false} if the {@code Bucket} doesn't contain a Collection.
     */
    <V> boolean addToCollection(V e);


    /**
     * @param key The key of the {@code map}.
     * @param value The value of the {@code map}.
     * @param <K> Type of the {@code key}.
     * @param <V> Type of the {@code value}.
     * @return The previous value associated with that {@code key}, else {@code null}.
     */
    <K,V> V addToMap(K key, V value);


    /**
     * @return The element of the {@code Bucket}.
     */
    E get();


    /**
     * Get the value of the index if the {@code Bucket} contains a {@code Collection}.
     * @param key Key of the element of the {@code Map} to be retrieved.
     * @param <K> Type of the {@code key}.
     * @param <V> Type of the {@code value}.
     * @return The element of the {@code Map}.
     */
    <K, V> V getFromMap(K key);


    /**
     * @return The class of the element of the Bucket, obtained by calling getClass().
     */
    Class<?> getType();
}
