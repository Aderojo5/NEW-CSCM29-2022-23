package lab3;

import java.security.PublicKey;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.InvalidKeyException;


/** 
 *   Ledger defines an ledger in the ledger model of bitcoins
 *     it extends UserAmount
 */

public class Ledger extends UserAmount {




   
    /** 
     *
     *  Task 8 Check a transaction is valid.
     *
     *  this means that 
     *    the sum of outputs is less than or equal the sum of inputs
     *    all signatures are valid
     *    and the inputs can be deducted from the ledger.

     *    This method has been set to true so that the code compiles - that should
     *    be changed
     */    

    public boolean checkTransactionValid(Transaction tx){
	int inputSum = 0;
    for (Transaction.Input input : tx.inputs) {
        if (!contains(input.publicKey)) {
            // The ledger does not contain the input public key, so the transaction is invalid
            return false;
        }
        int value = getBalance(input.publicKey);
        if (!verifySignature(input.signature, input.publicKey, tx.getData())) {
            // The signature is invalid, so the transaction is invalid
            return false;
        }
        inputSum += value;
    }
    
    // Calculate the sum of outputs
    int outputSum = 0;
    for (Transaction.Output output : tx.outputs) {
        outputSum += output.value;
    }
    
    // Check that the input sum is greater than or equal to the output sum
    return inputSum >= outputSum;
	return true;		
    };


    /** 
     * Task 9 Fill in the method processTransaction
     *
     * Process a transaction
     *    by first deducting all the inputs
     *    and then adding all the outputs.
     * Requires that the transaction is valid
     */    
    
    public void processTransaction(Transaction tx){
    // Deduct all the inputs from the ledger
    for (Transaction.Input input : tx.inputs) {
        int value = getBalance(input.publicKey);
        setBalance(input.publicKey, value - getOutputValue(tx, input.publicKey));
    }

    // Add all the outputs to the ledger
    for (Transaction.Output output : tx.outputs) {
        int value = getBalance(output.publicKey);
        setBalance(output.publicKey, value + output.value);
    }
}

    };

    
    /** 
     * Prints the current state of the ledger. 
     */

    public void print(PublicKeyMap pubKeyMap) {
	for (PublicKey publicKey : publicKeyList ) {
	    Integer value = getBalance(publicKey);
	    System.out.println("The balance for " +
			       pubKeyMap.getUser(publicKey) + " is " + value); 
	}

    }



    /** 
     * Testcase
     */

    public static void test()
    throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

    Wallet aliceWallet = SampleWallet.generate(new String[]{ "Alice" });
    Wallet bobWallet = SampleWallet.generate(new String[]{ "Bob" });
    PublicKey alicePublicKey = aliceWallet.getPublicKeys().get(0);
    PublicKey bobPublicKey = bobWallet.getPublicKeys().get(0);
    byte[] message = KeyUtils.integer2ByteArray(1);

    // Test case 1: Transaction is valid
    Transaction tx1 = new Transaction(new Transaction.Output[]{ new Transaction.Output(alicePublicKey, 50) });
    tx1.addInput(new Transaction.Input(alicePublicKey, aliceWallet.signMessage(message, "Alice")));
    assert(checkTransactionValid(tx1));
    processTransaction(tx1);
    assert(getBalance(alicePublicKey) == 50);

    // Test case 2: Transaction input signature is invalid
    Transaction tx2 = new Transaction(new Transaction.Output[]{ new Transaction.Output(alicePublicKey, 50) });
    tx2.addInput(new Transaction.Input(alicePublicKey, bobWallet.signMessage(message, "Bob")));
    assert(!checkTransactionValid(tx2));
    processTransaction(tx2);
    assert(getBalance(alicePublicKey) == 50);

    // Test case 3: Transaction input public key is not in the ledger
    Transaction tx3 = new Transaction(new Transaction.Output[]{ new Transaction.Output



	/***   Task 10
               add  to the test case the test as described in the lab sheet
                
               you can use the above exampleSignature, when a sample signature is needed
	       which cannot be computed from the data.

	**/

               
	
    }

    /** 
     * main function running test cases
     */            

    public static void main(String[] args)
	throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
	Ledger.test();
    }
}
