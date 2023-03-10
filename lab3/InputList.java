package lab3;

import java.security.PublicKey;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.InvalidKeyException;
import java.util.ArrayList;

/** InputList
 *  defines a list of Inputs  of a  transaction
 *
 */

public class InputList{


    /** 
      * underlying list of inputs
      */
    
    private ArrayList<Input> inputList;

    /** 
      * add an Input to the list
      */
    

    public void addEntry(Input input){
	inputList.add(input);
    }

    /** 
      * add a input given by sender amount and signature
      */
    
    public void addEntry(PublicKey sender,int amount,byte[] signature){
	inputList.add(new Input(sender,amount,signature));
    }



    /** 
      * add an entry given by sender as a string, amount, with signature created 
      *   using outputList, wallet
      */
    

    public void addEntry(String sender,int amount, OutputList outputList,Wallet wallet)
	throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {	
	inputList.add(new Input(sender, amount,outputList,wallet));
    }


    /** 
      * add an entry given by sender as a publicKey, amount, with signature created 
      *   using outputList, wallet
      */
    

    public void addEntry(PublicKey sender,int amount, OutputList outputList,Wallet wallet)
	throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {	
	inputList.add(new Input(sender, amount,outputList,wallet));
    }    
    
    

    /** 
      * constructor constructing the empty InputList
      */
    
    public InputList(){
	inputList =  new ArrayList<Input>();
    }

    /** 
      * constructor constructing a list containing one entry
          consisting of a sender, an amount, and a signature
      */    
    
    public InputList(PublicKey sender,int amount,byte[] signature){
	inputList = new ArrayList<Input>();
	addEntry(sender,amount,signature);
    }




    /** 
     * If we have a Wallet covering the sender
     * and an outputList
     * then we can compute the signature by signing the transaction to be signed consisting
     *    of the public key and input amount and the outputList
     *    using the private key of the sender
     */    



    public InputList(String sender,int amount, OutputList outputList,Wallet wallet)
	throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
	inputList = new ArrayList<Input>();
	addEntry(sender, amount,outputList,wallet);
    }    

    /*  as before but referring to the sender by the public key rather than the string */
    
    public InputList(PublicKey sender,int amount, OutputList outputList,Wallet wallet)
	throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
	inputList = new ArrayList<Input>();
	addEntry(sender, amount,outputList,wallet);
    }    
    

    

    /** 
      * constructor constructing a list containing two entries
          each consisting of a sender, an amount, and a signature
          note that it is not verifiedthat the signatures are correct
      */        

    public InputList(PublicKey sender1,int amount1,byte[] signature1,
		       PublicKey sender2,int amount2,byte[] signature2){
	inputList = new ArrayList<Input>();
	addEntry(sender1,amount1,signature1);
	addEntry(sender2,amount2,signature2);
    }

    /**  as before but for 2 users, using outputList and wallet **/
    
    public InputList(String sender1,int amount1,
		       String sender2,int amount2,
		       OutputList outputList,Wallet wallet)
	throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
	inputList = new ArrayList<Input>();
	addEntry(new Input(sender1, amount1,outputList,wallet));
	addEntry(new Input(sender2, amount2,outputList,wallet));	
    }    


    /**  as before but for 2 users using outputList and wallet,
         this time senders are given by public keys  **/
    
    public InputList(PublicKey sender1,int amount1,
		       PublicKey sender2,int amount2,
		       OutputList outputList,Wallet wallet)
	throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
	inputList = new ArrayList<Input>();
	addEntry(new Input(sender1, amount1,outputList,wallet));
	addEntry(new Input(sender2, amount2,outputList,wallet));	
    }    
    
	

    /** 
      * obtain the underlying list
      */
    
    public ArrayList<Input> toList(){
	return(inputList);
    };


    /* obtain the number of entries */
    
    public int size(){
	return(toList().size());
    }


    /* get one entry by its index */
    
    public Input get(int index){
	return (toList()).get(index);
    }

    /** 
      * compute the sum of entries in the list
      */

    public int toSum(){
	int result = 0;
	for (Input  entry : toList()){
	    result += entry.getAmount();
		};
	return result;
    };	


    /** 
      * when checking that InputList can be deducted
      *   from an ledger
      *   it is not enough to check that each single item can be deducted
      *   since for the same sender several items might occur
      *   
      *   in order to check that the InputList can be deducted
      *    we first create an ledger containing for each user the
      *    sum of amounts to be deducted
      *
      *   then we can check whether each entry in the original ledger is
      *     greater the sum of items for each user to be deducted
      */

    public Ledger toLedger(){
	Ledger result = new Ledger();
	for (Input  entry : toList()){
	    result.addBalance(entry.getSender(),entry.getAmount());
	};
	return result;
    }    



    /**   function  to print all items in the InputList
     *    in the form 
     *      word1  <sender> word2 <amount>  

     *
     *   we use the pubKeyMap in order to look up the names for each public key
     */
    
    public void print(String word1, String word2,PublicKeyMap pubKeyMap) {
	for (Input entry : inputList) {
	    entry.print(word1,word2,pubKeyMap);
	}
    }

    /**  Task 2
         Check the signatures of each element of the InputList is correct
         w.r.t. the OutputList given
         You can make use of the checkSignature method of individual Input
         (elements of Input).

         In order for the code to compile it has been defined as True
         but that should be adapted.
    **/

    public boolean checkSignature(OutputList outList){
	// to be replaced by the correct code	
	return true;
    }


    /* 
     *  Task 5: Fill in the method checkDeductableFromLedger
     *
     *  Check that a list of publicKey amounts can be deducted from the 
     *     current ledger
     *
     *   done by first converting the list of publicKey amounts into an ledger
     *     and then checking that the resulting ledger can be deducted.
     *   
     *  This is very similar to the task in lab2 for EntryList
     */

    public boolean checkDeductableFromLedger(Ledger ledger){
public class Ledger {
    private Map<String, Integer> balances;
    
    public Ledger() {
        this.balances = new HashMap<String, Integer>();
    }
    
    public void addBalance(String publicKey, int amount) {
        if (balances.containsKey(publicKey)) {
            balances.put(publicKey, balances.get(publicKey) + amount);
        } else {
            balances.put(publicKey, amount);
        }
    }
    
    public boolean deduct(String publicKey, int amount) {
        if (!balances.containsKey(publicKey)) {
            return false;
        }
        
        int balance = balances.get(publicKey);
        if (balance < amount) {
            return false;
        }
        
        balances.put(publicKey, balance - amount);
        return true;
    }
    
    public boolean checkDeductableFromLedger(List<String> publicKeys, List<Integer> amounts) {
        Ledger tempLedger = new Ledger();
        for (int i = 0; i < publicKeys.size(); i++) {
            tempLedger.addBalance(publicKeys.get(i), amounts.get(i));
        }
        return this.canDeduct(tempLedger);
    }
    
    private boolean canDeduct(Ledger ledger) {
        for (String publicKey : ledger.balances.keySet()) {
            if (!this.balances.containsKey(publicKey)) {
                return false;
            }
            int balance = this.balances.get(publicKey);
            if (balance < ledger.balances.get(publicKey)) {
                return false;
            }
        }
        return true;
    }
}

	return true;	
    };

    /** 
     * Task 6
     * Subtract a list of Inputs from the ledger 
     *   (which will be later the list of inputs of a transaction)
     *   requires that the list to be deducted is deductable.
     *   Similar to what was done in EntryList in lab2
     */

    public void subtractFromLedger(Ledger ledger){
    public class Ledger {
    private Map<String, Double> balances;

    public Ledger(Map<String, Double> balances) {
        this.balances = new HashMap<>(balances);
    }

    public Map<String, Double> getBalances() {
        return new HashMap<>(balances);
    }

    public boolean deductLedger(Ledger otherLedger) {
        for (String publicKey : otherLedger.getBalances().keySet()) {
            if (!balances.containsKey(publicKey)) {
                return false; // publicKey not in this ledger, deduction fails
            }
            double balance = balances.get(publicKey);
            double otherBalance = otherLedger.getBalances().get(publicKey);
            if (otherBalance > balance) {
                return false; // insufficient funds, deduction fails
            }
            balances.put(publicKey, balance - otherBalance); // update balance
        }
        return true; // deduction successful
    }

    public boolean checkDeductableFromLedger(List<String> publicKeys, List<Double> amounts) {
        // Create a new ledger from the given publicKeys and amounts
        Map<String, Double> otherBalances = new HashMap<>();
        for (int i = 0; i < publicKeys.size(); i++) {
            String publicKey = publicKeys.get(i);
            double amount = amounts.get(i);
            otherBalances.put(publicKey, amount);
        }
        Ledger otherLedger = new Ledger(otherBalances);

        // Check if the other ledger can be deducted from this ledger
        Ledger newLedger = new Ledger(balances);
        return newLedger.deductLedger(otherLedger);
    }
}

    }

    
    

    /** 
     * Default way of printing out the InputList
     */

    public void print(PublicKeyMap pubKeyMap) {
	print("Sender: "," value:  ",pubKeyMap);
    }

    /** 
     * Generic Test cases, providing a headline
     *    printing out the InputList
     *    and printing out the sum of amounts in the InputList
     */            
    

    public void testCase(String header,PublicKeyMap pubKeyMap){
	System.out.println(header);
	print(pubKeyMap);
	System.out.println("Sum of Amounts = " + toSum());	
	System.out.println();	
    };

    /** 
     * Test cases
     */            
    
    public static void test()
		throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
	Wallet wallet = SampleWallet.generate(new String[]{ "Alice", "Bob", "Carol", "David"});
	PublicKeyMap pubKeyMap = wallet.toPublicKeyMap();	
	byte[] sampleMessage1 = KeyUtils.integer2ByteArray(1);
	byte[] signedMessage1 = wallet.signMessage(sampleMessage1,"Alice");
	PublicKey pubKeyA =	pubKeyMap.getPublicKey("Alice");
	PublicKey pubKeyB =	pubKeyMap.getPublicKey("Bob"); 			
	(new InputList(pubKeyA,10,signedMessage1)).testCase("Test Alice 10",pubKeyMap);

	(new InputList(pubKeyB,20,signedMessage1)).testCase("Test Bob 20",pubKeyMap);
	
	(new InputList(pubKeyA,10,signedMessage1,
			 pubKeyA,10,signedMessage1)).testCase("Alice twice 10",pubKeyMap);

	InputList l = new InputList(pubKeyA,10,signedMessage1,
					pubKeyB,20,signedMessage1);
	l.testCase("Test Alice 10 and Bob  20",pubKeyMap);
	
	System.out.println("Same List but with words Sender and sends");	
	l.print("Sender "," sends ",pubKeyMap);		
	
    }
    

    /** 
     * main function running test cases
     */            
    
    public static void main(String[] args)
    		throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
	InputList.test();
    }    

};    
