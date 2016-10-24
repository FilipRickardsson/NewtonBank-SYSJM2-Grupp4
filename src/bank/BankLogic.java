package bank;

public class BankLogic {
    
    /**
         * letar igenom customer listan skapar ett credit account returnerar -1
         * om inget hittas
         */
    public int addCreditAccount(long ssn){
        // kanske ska ändra kontonummer till tex 2001
        int accountNumber = 1001;
       
        for (int i = 0; i<customer.size(); i++)
        {
            if (ssn == customer.get[i].get(ssn)){
        CreditAccount ca = new CreditAccount(ssn);
        accountNumber++;
        break;
            }
            else
                accountNumber = -1;
                    }
                return accountNumber;
    }
    /**
     * letar igenom customer listan  på ssn och account id och returnerar alla transactions
     * på det kontot returnerar -1 om inget hittas
     * @return 
     */
    public arrayList<transactions>getTransactions(long ssn, int accountID)
    {
        for (int i = 0; i < customer.size(); i++) {
            // möjligen ändring till att bara söka på ssn
            if (ssn == customer.get[i].get(ssn) && accountID == customer.get[i].get(accountID)){
                
                for (int j = 0; j < transactions.size(); j++) {
                    
                    return transactions.get[i].toString();
            }
            }
        }
        return -1;
    }
}
