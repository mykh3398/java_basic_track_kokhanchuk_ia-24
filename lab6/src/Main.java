import java.util.*;

/**
 * Abstract class representing a Coffee.
 */
abstract class Coffee {
    private String name;
    private double pricePerKg;
    private double quality;

    /**
     * Constructs a Coffee object.
     *
     * @param name the name of the coffee
     * @param pricePerKg the price per kilogram of the coffee
     * @param quality the quality of the coffee
     */
    public Coffee(String name, double pricePerKg, double quality) {
        if (pricePerKg <= 0 || quality <= 0) {
            throw new IllegalArgumentException("Price and quality must be positive.");
        }
        this.name = name;
        this.pricePerKg = pricePerKg;
        this.quality = quality;
    }

    /**
     * Gets the name of the coffee.
     *
     * @return the name of the coffee
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the price per kilogram of the coffee.
     *
     * @return the price per kilogram
     */
    public double getPricePerKg() {
        return pricePerKg;
    }

    /**
     * Gets the quality of the coffee.
     *
     * @return the quality of the coffee
     */
    public double getQuality() {
        return quality;
    }

    /**
     * Gets the volume per kilogram of the coffee.
     *
     * @return the volume per kilogram
     */
    public abstract double getVolume();

    @Override
    public String toString() {
        return String.format("%s: price/kg = %.2f, quality = %.2f", name, pricePerKg, quality);
    }
}

/**
 * Represents a doubly linked list-based set for Coffee objects.
 */
class CoffeeSet implements Set<Coffee> {
    private static class Node {
        Coffee data;
        Node next;
        Node prev;

        Node(Coffee data) {
            this.data = data;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    /**
     * Constructs an empty CoffeeSet.
     */
    public CoffeeSet() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Constructs a CoffeeSet containing a single Coffee object.
     *
     * @param coffee the Coffee object to initialize the set with
     */
    public CoffeeSet(Coffee coffee) {
        this();
        add(coffee);
    }

    /**
     * Constructs a CoffeeSet containing all elements from a given collection.
     *
     * @param collection the collection of Coffee objects to initialize the set with
     */
    public CoffeeSet(Collection<? extends Coffee> collection) {
        this();
        addAll(collection);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (!(o instanceof Coffee)) {
            return false;
        }
        Node current = head;
        while (current != null) {
            if (current.data.equals(o)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Returns an iterator to traverse the CoffeeSet in order.
     *
     * @return an iterator for the CoffeeSet
     */
    @Override
    public Iterator<Coffee> iterator() {
        return new Iterator<Coffee>() {
            private Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Coffee next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Coffee data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int index = 0;
        for (Node current = head; current != null; current = current.next) {
            array[index++] = current.data;
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = Arrays.copyOf(a, size);
        }
        int index = 0;
        for (Node current = head; current != null; current = current.next) {
            a[index++] = (T) current.data;
        }
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public boolean add(Coffee coffee) {
        if (contains(coffee)) {
            return false;
        }
        Node newNode = new Node(coffee);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof Coffee)) {
            return false;
        }
        Node current = head;
        while (current != null) {
            if (current.data.equals(o)) {
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    head = current.next;
                }
                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    tail = current.prev;
                }
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Coffee> c) {
        boolean modified = false;
        for (Coffee coffee : c) {
            modified |= add(coffee);
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Node current = head;
        while (current != null) {
            Node next = current.next;
            if (!c.contains(current.data)) {
                remove(current.data);
                modified = true;
            }
            current = next;
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object o : c) {
            modified |= remove(o);
        }
        return modified;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }
}

/**
 * Main class to demonstrate the functionality of CoffeeSet.
 */
public class Main {
    public static void main(String[] args) {
        // Define some coffee objects
        Coffee arabica = new Coffee("Arabica", 30.0, 85.0) {
            @Override
            public double getVolume() {
                return 0.8;
            }
        };

        Coffee robusta = new Coffee("Robusta", 20.0, 75.0) {
            @Override
            public double getVolume() {
                return 0.9;
            }
        };

        Coffee liberica = new Coffee("Liberica", 25.0, 80.0) {
            @Override
            public double getVolume() {
                return 0.85;
            }
        };

        // Create an empty CoffeeSet
        CoffeeSet coffeeSet = new CoffeeSet();
        System.out.println("Initial CoffeeSet size: " + coffeeSet.size());

        // Add coffees to the set
        coffeeSet.add(arabica);
        coffeeSet.add(robusta);
        coffeeSet.add(liberica);

        // Display the size and contents of the CoffeeSet
        System.out.println("CoffeeSet size after additions: " + coffeeSet.size());
        for (Coffee coffee : coffeeSet) {
            System.out.println(coffee);
        }

        // Check if a coffee is contained in the set
        System.out.println("Contains Arabica: " + coffeeSet.contains(arabica));

        // Remove a coffee and display the updated set
        coffeeSet.remove(robusta);
        System.out.println("CoffeeSet size after removal: " + coffeeSet.size());
        for (Coffee coffee : coffeeSet) {
            System.out.println(coffee);
        }
    }
}
