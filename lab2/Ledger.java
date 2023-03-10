package lab2;



/** 
 *   Ledger defines for each user the balance at a given time
     in the ledger model of bitcoins
     and contains methods for checking and updating the ledger
     including processing a transaction
 */

public class Ledger extends UserAmount{


    /** 
     *
     *  Task 4: Fill in the method checkTransactionValid
     *          You need to replace the dummy value true by the correct calculation
     *
     * Check a transaction is valid:
     *    the sum of outputs is less than or equal the sum of inputs
     *    and the inputs can be deducted from the ledger.
     *
     */    
    
 public boolean checkTxValid(Transaction tx) {
    double inputsSum = 0;
    double outputsSum = 0;

    // calculate the sum of inputs
    for (TxInput input : tx.inputs) {
        UserAmount inputAmount = findUserAmount(input.user);
        if (inputAmount == null || inputAmount.amount < input.amount) {
            return false;
        }
        inputsSum += input.amount;
    }

    // calculate the sum of outputs
    for (TxOutput output : tx.outputs) {
        outputsSum += output.amount;
    }

    // check if inputsSum >= outputsSum
    return inputsSum >= outputsSum;
}

   /** 
 * Process a transaction
 *    by first deducting all the inputs
 *    and then adding all the outputs.
 *
 */    

public void processTransaction(Transaction tx){
    // Check if the transaction is valid
    if (!checkTxValid(tx)) {
        System.out.println("Transaction is not valid");
        return;
    }

    // Deduct all the inputs from the ledger
    for (TxInput input : tx.inputs) {
        String user = input.getUser();
        double amount = input.getAmount();
        deduct(user, amount);
    }

    // Add all the outputs to the ledger
    for (TxOutput output : tx.outputs) {
        String user = output.getUser();
        double amount = output.getAmount();
        add(user, amount);
    }
}



/** 
     *  Task 6: Fill in the testcases as described in the labsheet
     *    
     * Testcase
     */
    
    public static void test() {
        Ledger ledger = new Ledger();
        
        // Add initial funds for Alice and Bob
        ledger.addToLedger(new EntryList("Alice", 100));
        ledger.addToLedger(new EntryList("Bob", 200));
        
        // Create and process a valid transaction from Alice to Bob
        Transaction tx1 = new Transaction();
        tx1.addInput(new TxInput("Alice", 50));
        tx1.addOutput(new TxOutput("Bob", 30));
        tx1.addOutput(new TxOutput("Alice", 20));
        
        if (ledger.checkTxValid(tx1)) {
            ledger.processTransaction(tx1);
            System.out.println("Transaction 1 processed successfully!");
        } else {
            System.out.println("Transaction 1 is not valid!");
        }
        
        // Create and process an invalid transaction from Bob to Alice
        Transaction tx2 = new Transaction();
        tx2.addInput(new TxInput("Bob", 100));
        tx2.addOutput(new TxOutput("Alice", 150));
        
        if (ledger.checkTxValid(tx2)) {
            ledger.processTransaction(tx2);
            System.out.println("Transaction 2 processed successfully!");
        } else {
            System.out.println("Transaction 2 is not valid!");
        }
        
        // Print the updated ledger
        System.out.println("\nUpdated ledger:");
        ledger.print();
    }
