package lab2;

/**
 * Entry class represents an entry in a transaction
 * consisting of a user and the amount to be transferred
 * which will be used as one input or an output of a transaction.
 */
public class Entry {

    /** The amount to be transferred */
    private int amount;

    /** The user */
    private String user;

    /**
     * Create a new Entry object.
     *
     * @param user   the user who is involved in the transaction
     * @param amount the amount to be transferred
     */
    public Entry(String user, int amount) {
        this.amount = amount;
        this.user = user;
    }

    /**
     * Get the user of this entry.
     *
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Get the amount of this entry.
     *
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Print the entry in the form "text1 <user> text2 <amount>".
     *
     * @param text1 the first text to be printed before the user
     * @param text2 the second text to be printed before the amount
     */
    public void print(String text1, String text2) {
        System.out.println(text1 + getUser() + text2 + getAmount());
    }

    /**
     * Default way of printing out the user and amount.
     */
    public void print() {
        print("User: ", "value: ");
    }

    /**
     * Test cases for the Entry class.
     */
    public static void test() {
        System.out.println();
        System.out.println("Test Alice 10");
        (new Entry("Alice", 10)).print();
        System.out.println();
        System.out.println("Test Bob 20");
        (new Entry("Bob", 20)).print();
    }

    /**
     * The main function that runs the test cases.
     *
     * @param args command line arguments (unused)
     */
    public static void main(String[] args) {
        Entry.test();
    }
}
