# HashMultibucket
A HashMap-like implementation where you can map a key to many values of different types.

### Usage
Firstly, we create a HashMultibucket by specifying only the type of the keys.  
`Multibucket<String> mb = new HashMultibucket<>();`

Now we can use this data structure like a HashMap to put a key-value pair, 
with value being of any type.   
`mb.put("Pete", 10);`  
`mb.put("Pete", "aString");`  
`mb.put("Pete", new HashMap<Integer, Set<String>>());`  
`mb.put("Pete", new ArrayList<Integer>());`  

We can access any value by specifying the key and the index of the Bucket that contains the value.  
`String s = mb.get("Pete", 1);`  
`Map<Integer, Set<String>> m = mb.get("Pete", 2);`
