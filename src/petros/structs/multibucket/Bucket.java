package petros.structs.multibucket;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author Petros Papatheodorou
 * @author GitHub: subamanis
 * @author petrospapa21@gmail.com
 *
 *
 * @param <E> The type of the element of the Bucket.
 */
public class Bucket<E> implements GenericBucket<E>
{
    private E element;

    public Bucket() { }

    public Bucket(final E element)
    {
        this.element = element;
    }

    public static <T> Bucket<T> of(final T element)
    {
        return new Bucket<>(element);
    }

    public static <T> Bucket<T> emptyBucket()
    {
        return new Bucket<>();
    }


    @Override
    public boolean isEmpty()
    {
        return element == null;
    }


    @Override
    public void empty()
    {
        this.element = null;
    }


    @Override
    public boolean containsCollection()
    {
        if(isEmpty()) return false;

        return element instanceof Collection;
    }


    @Override @SuppressWarnings("unchecked")
    public <V> boolean containedInCollection(final V e)
    {
        if(!containsCollection()) return false;

        return ((Collection<V>) element).contains(e);
    }


    @Override
    public boolean containsMap()
    {
        if(isEmpty()) return false;

        return element instanceof Map;
    }


    @Override
    public E put(final E e)
    {
        E previous = this.element;
        this.element = e;
        return previous;
    }


    @Override
    public E putIfEmpty(final E e)
    {
        if(this.element != null) return element;

        this.element = e;
        return null;
    }


    @Override @SuppressWarnings("unchecked")
    public <V> boolean addToCollection(final V e)
    {
        if(!containsCollection()) return false;

        return ((Collection<V>) element).add(e);
    }


    @Override @SuppressWarnings("unchecked")
    public <K,V> V addToMap(final K key, final V value)
    {
        if(!containsMap()) return null;

        return ((Map<K, V>) element).put(key, value);
    }


    @Override
    public E get()
    {
        return element;
    }


    @Override @SuppressWarnings("unchecked")
    public <K, V> V getFromMap(final K key)
    {
        if(!containsMap()) return null;

        return ((Map<K, V>) element).get(key);
    }

    @Override
    public Class<?> getType()
    {
        return element.getClass();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bucket<?> bucket = (Bucket<?>) o;
        return Objects.equals(element, bucket.element);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(element);
    }

    @Override
    public String toString()
    {
        return element.toString();
    }

}
