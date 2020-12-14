package dahdproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import javafx.scene.text.Font;
import numberlist.IndexRangeException;
import numberlist.objectlist.Copiable;
import numberlist.objectlist.Date;
import numberlist.objectlist.Money;
import numberlist.objectlist.NumericObjectArrayList;

/**
 * Manages the orders received by the manager. Can contain the following
 * information about each order: list of foods, name, expected delivery date,
 * and total value.
 *
 * @author Dustin Sumarli
 * @version 3/17/2020
 */
public class Orders implements Serializable {

    private static Orders orders;
    private static String[] foodOptions
            = {"Pizza", "Salad", "Chicken Wings", "Spring Rolls"};
    private ArrayList<ArrayList<String>> listsOfFoods; // an order contains multiple foods
    private ArrayList<String> names;
    private NumericObjectArrayList dueDates;
    private NumericObjectArrayList totals;
    private NumericObjectArrayList datesRequested;

    /**
     * Private default constructor to prevent unauthorized instantiation.
     * Initializes fields.
     */
    private Orders() {
        listsOfFoods = new ArrayList<>();
        names = new ArrayList<>();
        dueDates = new NumericObjectArrayList();
        totals = new NumericObjectArrayList();
        datesRequested = new NumericObjectArrayList();
    }

    /**
     * To instantiate the singleton class and prevent multiple instances.
     *
     * @return the Orders class instance
     */
    public static Orders getInstace() {
        if (orders == null) {
            orders = new Orders();
        }
        return orders;
    }

    /**
     * Accesses the food options available to order.
     *
     * @return a list of food options.
     */
    public static String[] getFoodOptions() {
        return foodOptions;
    }

    /**
     * Accesses the list of lists of foods of each order.
     *
     * @return the list of foods of each order.
     */
    public ArrayList<ArrayList<String>> getListsOfFoods() {
        return listsOfFoods;
    }

    /**
     * Accesses the list of names of each order.
     *
     * @return the list of names.
     */
    public ArrayList<String> getNames() {
        return names;
    }

    /**
     * Accesses the list of delivery due date of each order.
     *
     * @return the list of delivery due date of each order.
     */
    public NumericObjectArrayList getDueDates() {
        return dueDates;
    }

    /**
     * Accesses the list of total values of each order.
     *
     * @return the list of total values of each order.
     */
    public NumericObjectArrayList getTotals() {
        return totals;
    }

    /**
     * Accesses the list of dates each order was requested.
     *
     * @return the list of dates each order was requested.
     */
    public NumericObjectArrayList getDatesRequested() {
        return datesRequested;
    }

    /**
     * Adds an order. Requires a list of foods for the order, the name, and the
     * delivery date. This method extracts the total from the list of foods and
     * adds it to the list.
     *
     * @param foods the list of foods for this order
     * @param name the name of the person ordering
     * @param date the expected delivery date of the order
     */
    public void addOrder(ArrayList<String> foods, String name, Copiable date) {
        listsOfFoods.add(foods);
        names.add(name);
        dueDates.insert(date);
        totals.insert(getTotal(foods));
        datesRequested.insert(Date.getCurrentDate());
    }

    /**
     * Gets the total value of a list of food items.
     *
     * @param foods the list of food items to examine
     * @return the total value of the list of food items.
     */
    public static Money getTotal(ArrayList<String> foods) {
        Money total = new Money();
        if (foods == null || foods.contains(null)) {
            return total;
        }
        for (String food : foods) {
            switch (food) { // adds the cost of each food
                case "Pizza":
                    total = total.add(new Money(10, (byte) 0));
                    break;
                case "Salad":
                    total = total.add(new Money(7, (byte) 50));
                    break;
                case "Chicken Wings":
                    total = total.add(new Money(5, (byte) 0));
                    break;
                case "Spring Rolls":
                    total = total.add(new Money(2, (byte) 50));
                    break;
            }
        }
        return total;
    }

    /**
     * Sorts the orders by name. Insertion sort is implemented to get O(1) space
     * complexity (prevents mass duplication of parallel lists) while having a
     * better average time complexity than bubble sort.
     */
    public void sortByName() {
        for (int i = 1; i < names.size(); i++) {
            try {
                // grab copies of elements before insertions
                String currentName = names.get(i);
                ArrayList<String> currentListOfFoods = (ArrayList<String>) listsOfFoods.get(i).clone();
                Date currentDueDate = (Date) dueDates.getValueAt(i);
                Money currentTotal = (Money) totals.getValueAt(i);
                Date currentDateRequested = (Date) datesRequested.getValueAt(i);
                int k;
                // compare names
                for (k = i - 1; k >= 0 && names.get(k).compareToIgnoreCase(currentName) > 0; k--) {
                    names.set(k + 1, names.get(k));
                    listsOfFoods.set(k + 1, listsOfFoods.get(k));
                    dueDates.replace(k + 1, dueDates.getValueAt(k));
                    totals.replace(k + 1, totals.getValueAt(k));
                    datesRequested.replace(k + 1, datesRequested.getValueAt(k));
                }
                // keep all other data sorted with their respective names
                names.set(k + 1, currentName);
                listsOfFoods.set(k + 1, currentListOfFoods);
                dueDates.replace(k + 1, currentDueDate);
                totals.replace(k + 1, currentTotal);
                datesRequested.replace(k + 1, currentDateRequested);
            } catch (IndexRangeException ex) {
                System.out.println("This should never happen");
            }
        }
    }

    /**
     * Sorts the orders by total value. Insertion sort is implemented to get
     * O(1) space complexity (prevents mass duplication of parallel lists) while
     * having a better average time complexity than bubble sort.
     */
    public void sortByTotal() {
        for (int i = 1; i < names.size(); i++) {
            try {
                // grab copies of elements before insertions
                Money currentTotal = (Money) totals.getValueAt(i);
                ArrayList<String> currentListOfFoods = (ArrayList<String>) listsOfFoods.get(i).clone();
                String currentName = names.get(i);
                Date currentDueDate = (Date) dueDates.getValueAt(i);
                Date currentDateRequested = (Date) datesRequested.getValueAt(i);
                int k;
                // compare totals
                for (k = i - 1; k >= 0 && ((Money) totals.getValueAt(k)).compareTo(currentTotal) > 0; k--) {
                    totals.replace(k + 1, totals.getValueAt(k));
                    listsOfFoods.set(k + 1, listsOfFoods.get(k));
                    names.set(k + 1, names.get(k));
                    dueDates.replace(k + 1, dueDates.getValueAt(k));
                    datesRequested.replace(k + 1, datesRequested.getValueAt(k));
                }
                // keep all other data sorted with their respective totals
                totals.replace(k + 1, currentTotal);
                listsOfFoods.set(k + 1, currentListOfFoods);
                names.set(k + 1, currentName);
                dueDates.replace(k + 1, currentDueDate);
                datesRequested.replace(k + 1, currentDateRequested);
            } catch (IndexRangeException ex) {
                System.out.println("This should never happen");
            }
        }
    }

    /**
     * Sorts the orders by expected delivery date. Insertion sort is implemented
     * to get O(1) space complexity (prevents mass duplication of parallel
     * lists) while having a better average time complexity than bubble sort.
     */
    public void sortByDate() {
        for (int i = 1; i < names.size(); i++) {
            try {
                // grab copies of elements before insertions
                Date currentDueDate = (Date) dueDates.getValueAt(i);
                ArrayList<String> currentListOfFoods = (ArrayList<String>) listsOfFoods.get(i).clone();
                String currentName = names.get(i);
                Money currentTotal = (Money) totals.getValueAt(i);
                Date currentDateRequested = (Date) datesRequested.getValueAt(i);
                int k;
                // compare dueDates
                for (k = i - 1; k >= 0 && ((Date) dueDates.getValueAt(k)).compareTo(currentDueDate) > 0; k--) {
                    dueDates.replace(k + 1, dueDates.getValueAt(k));
                    totals.replace(k + 1, totals.getValueAt(k));
                    listsOfFoods.set(k + 1, listsOfFoods.get(k));
                    names.set(k + 1, names.get(k));
                    datesRequested.replace(k + 1, datesRequested.getValueAt(k));
                }
                // keep all other data sorted with their respective totals
                dueDates.replace(k + 1, currentDueDate);
                listsOfFoods.set(k + 1, currentListOfFoods);
                names.set(k + 1, currentName);
                totals.replace(k + 1, currentTotal);
                datesRequested.replace(k + 1, currentDateRequested);
            } catch (IndexRangeException ex) {
                System.out.println("This should never happen");
            }
        }
    }

    public void sortByDateCreated() {
        for (int i = 1; i < names.size(); i++) {
            try {
                // grab copies of elements before insertions
                Date currentDateRequested = (Date) datesRequested.getValueAt(i);
                Date currentDueDate = (Date) dueDates.getValueAt(i);
                ArrayList<String> currentListOfFoods = (ArrayList<String>) listsOfFoods.get(i).clone();
                String currentName = names.get(i);
                Money currentTotal = (Money) totals.getValueAt(i);
                int k;
                // compare datesRequested
                for (k = i - 1; k >= 0 && ((Date) datesRequested.getValueAt(k)).compareTo(currentDateRequested) > 0; k--) {
                    datesRequested.replace(k + 1, datesRequested.getValueAt(k));
                    dueDates.replace(k + 1, dueDates.getValueAt(k));
                    totals.replace(k + 1, totals.getValueAt(k));
                    listsOfFoods.set(k + 1, listsOfFoods.get(k));
                    names.set(k + 1, names.get(k));
                }
                // keep all other data sorted with their respective totals
                datesRequested.replace(k + 1, currentDateRequested);
                dueDates.replace(k + 1, currentDueDate);
                listsOfFoods.set(k + 1, currentListOfFoods);
                names.set(k + 1, currentName);
                totals.replace(k + 1, currentTotal);
            } catch (IndexRangeException ex) {
                System.out.println("This should never happen");
            }
        }
    }

    /**
     * Saves the current list of orders to a specified file.
     *
     * @param file the file to save the list of orders into.
     */
    public static void saveTo(File file) {
        try (ObjectOutputStream output
                = new ObjectOutputStream(new FileOutputStream(file))) {
            output.writeObject(orders);
        } catch (IOException ex) {
            System.out.println("Cannot write to file.");
        }
    }

    /**
     * Saves the current list of orders to a filed named "orders.ser".
     */
    public static void saveData() {
        try {
            File data = new File("orders.ser");
            if (!data.exists()) {
                data.createNewFile();
            }
            if (data.canWrite()) {
                saveTo(data);
            }
        } catch (IOException ex) {
            System.out.println("Cannot create file");
        }
    }

    /**
     * Retrieves a list of orders from a specified file.
     *
     * @param file the file to read the list of orders from.
     * @return the list of orders.
     */
    public static Orders readList(File file) {
        Orders order = null;
        try (ObjectInputStream input
                = new ObjectInputStream(new FileInputStream(file))) {
            orders = (Orders) input.readObject();
        } catch (IOException ex) {
            System.out.println("Cannot read from file.");
        } catch (ClassNotFoundException ex) {
            System.out.println("Wrong type of file.");
        }
        return orders;
    }

    /**
     * Retrieves a list of orders from the orders.ser file if it exists.
     *
     * @return the list of orders.
     */
    public static Orders readData() {
        File data = new File("orders.ser");
        if (data.exists()) {
            return readList(data);
        } else {
            return orders;
        }
    }

    /**
     * Provides String representation of class data.
     *
     * @return ex. Customer John Doe | due 01/01/2000 | Total: $10.10
     */
    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < names.size(); i++) {
            try {
                result += "Customer: " + names.get(i)
                        + " | pick-up date: " + dueDates.getValueAt(i).toString()
                        + " | Total: " + totals.getValueAt(i).toString() + "\n";
            } catch (IndexRangeException ex) {
                System.out.println("This should never happen.");
            }
        }
        return result;
    }

}
