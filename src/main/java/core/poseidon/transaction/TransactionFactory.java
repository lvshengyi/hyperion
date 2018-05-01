package core.poseidon.transaction;

import java.sql.Connection;

/**
 * @author LvShengyI
 */
public class TransactionFactory {

    public static Transaction newTransaction(Connection conn){
        return new Transaction(conn);
    }
}
