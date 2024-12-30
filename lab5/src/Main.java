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
 * Represents coffee beans.
 */
class CoffeeBeans extends Coffee {
    private double volumePerKg;

    /**
     * Constructs a CoffeeBeans object.
     *
     * @param name the name of the coffee beans
     * @param pricePerKg the price per kilogram of the coffee beans
     * @param quality the quality of the coffee beans
     * @param volumePerKg the volume per kilogram of the coffee beans
     */
    public CoffeeBeans(String name, double pricePerKg, double quality, double volumePerKg) {
        super(name, pricePerKg, quality);
        if (volumePerKg <= 0) {
            throw new IllegalArgumentException("Volume per kg must be positive.");
        }
        this.volumePerKg = volumePerKg;
    }

    @Override
    public double getVolume() {
        return volumePerKg;
    }
}

/**
 * Represents ground coffee.
 */
class GroundCoffee extends Coffee {
    private double volumePerKg;

    /**
     * Constructs a GroundCoffee object.
     *
     * @param name the name of the ground coffee
     * @param pricePerKg the price per kilogram of the ground coffee
     * @param quality the quality of the ground coffee
     * @param volumePerKg the volume per kilogram of the ground coffee
     */
    public GroundCoffee(String name, double pricePerKg, double quality, double volumePerKg) {
        super(name, pricePerKg, quality);
        if (volumePerKg <= 0) {
            throw new IllegalArgumentException("Volume per kg must be positive.");
        }
        this.volumePerKg = volumePerKg;
    }

    @Override
    public double getVolume() {
        return volumePerKg;
    }
}

/**
 * Represents instant coffee in jars or sachets.
 */
class InstantCoffee extends Coffee {
    private double volumePerKg;

    /**
     * Constructs an InstantCoffee object.
     *
     * @param name the name of the instant coffee
     * @param pricePerKg the price per kilogram of the instant coffee
     * @param quality the quality of the instant coffee
     * @param volumePerKg the volume per kilogram of the instant coffee
     */
    public InstantCoffee(String name, double pricePerKg, double quality, double volumePerKg) {
        super(name, pricePerKg, quality);
        if (volumePerKg <= 0) {
            throw new IllegalArgumentException("Volume per kg must be positive.");
        }
        this.volumePerKg = volumePerKg;
    }

    @Override
    public double getVolume() {
        return volumePerKg;
    }
}

/**
 * Represents a van that carries coffee.
 */
class CoffeeVan {
    private double maxVolume;
    private List<Coffee> cargo;

    /**
     * Constructs a CoffeeVan object.
     *
     * @param maxVolume the maximum volume the van can hold
     */
    public CoffeeVan(double maxVolume) {
        if (maxVolume <= 0) {
            throw new IllegalArgumentException("Maximum volume must be positive.");
        }
        this.maxVolume = maxVolume;
        this.cargo = new ArrayList<>();
    }

    /**
     * Adds coffee cargo to the van.
     *
     * @param coffee the coffee to be added
     * @param weight the weight of the coffee in kilograms
     * @throws IllegalStateException if there is not enough space in the van
     */
    public void addCargo(Coffee coffee, double weight) {
        double requiredVolume = weight * coffee.getVolume();
        if (requiredVolume > maxVolume) {
            throw new IllegalStateException("Not enough space in the van for this cargo.");
        }
        cargo.add(coffee);
        maxVolume -= requiredVolume;
    }

    /**
     * Sorts the cargo in the van by price-to-weight ratio.
     */
    public void sortCargoByPriceToWeight() {
        cargo.sort(Comparator.comparingDouble(c -> c.getPricePerKg() / c.getVolume()));
    }

    /**
     * Finds coffee in the van within a specified quality range.
     *
     * @param minQuality the minimum quality of the coffee
     * @param maxQuality the maximum quality of the coffee
     * @return a list of coffee that falls within the quality range
     */
    public List<Coffee> findCargoByQualityRange(double minQuality, double maxQuality) {
        List<Coffee> result = new ArrayList<>();
        for (Coffee coffee : cargo) {
            if (coffee.getQuality() >= minQuality && coffee.getQuality() <= maxQuality) {
                result.add(coffee);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "CoffeeVan contents: " + cargo.toString();
    }
}

/**
 * Main class to demonstrate the functionality.
 */
public class Main {
    public static void main(String[] args) {
        try {
            CoffeeVan van = new CoffeeVan(100);

            Coffee beans = new CoffeeBeans("Arabica Beans", 20.0, 8.5, 0.5);
            Coffee ground = new GroundCoffee("Ground Arabica", 18.0, 7.0, 0.4);
            Coffee instant = new InstantCoffee("Instant Coffee", 25.0, 9.0, 0.3);

            van.addCargo(beans, 10);
            van.addCargo(ground, 15);
            van.addCargo(instant, 20);

            System.out.println(van);

            van.sortCargoByPriceToWeight();
            System.out.println("\nAfter sorting by price to weight ratio:");
            System.out.println(van);

            System.out.println("\nFinding cargo in quality range 7.5 to 9.0:");
            List<Coffee> filtered = van.findCargoByQualityRange(7.5, 9.0);
            for (Coffee coffee : filtered) {
                System.out.println(coffee);
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
