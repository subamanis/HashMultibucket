# HashMultibucket
A HashMap-like implementation where you can map a key to many values of different types.

### Description
A Generic class `Bucket<E>` is used as a container for the values. Each container wraps an element
of type `E` (unknown) inside it. This element can be any `Object`, `Collection` or `Map`.   
 
Inside the class `HashMultibucket<K>`, there is a `HashMap<K,List<Bucket<E>>` with keys of type `K`
 and a `List<Bucket<E>>` as value.  

Every time a new value is given for a particular key of the `HashMultibucket`, a `Bucket<E>` is created 
and added in the list mapped to the given key.

### Usage
Firstly, we create a HashMultibucket by specifying only the type of the keys.  
`Multibucket<String> mb = new HashMultibucket<>();`

Now we can use this data structure like a HashMap to create a new Bucket containing a value of any type,
and associate it with the given key:
`mb.put("Pete", 10);`  
`mb.put("Pete", "aString");`  
`mb.put("Pete", new HashMap<Integer, Set<String>>());`  
`mb.put("Pete", new ArrayList<Integer>());`  

We can access any value by specifying the key and the index of the Bucket that contains the value.  
`String s = mb.get("Pete", 1);`  
`Map<Integer, Set<String>> m = mb.get("Pete", 2);`


