package core.poseidon.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author LvShengyI
 */
public class Transaction {

    private Connection conn;

    public Transaction(Connection conn){
        this.conn = conn;
    }

    public void commit(){
        try {
            if(!conn.getAutoCommit()){
                conn.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rollback(){
        try {
            if(!conn.getAutoCommit()){
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
