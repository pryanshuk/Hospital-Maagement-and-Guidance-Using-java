package common;

import java.sql.Connection;
import java.sql.Statement;


public abstract class AbstractDatabase implements DatabaseOperations {
    

    protected Connection con;
    protected Statement stmt;

    public AbstractDatabase(Connection con, Statement stmt) {
        this.con = con;
        this.stmt = stmt;
    }
}
