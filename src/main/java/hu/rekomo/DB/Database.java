package hu.rekomo.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author futo
 */
public class Database {
    
    protected Connection connection;
    protected Query query;

    public Database(String userName, String password) throws SQLException
    {
        this.connection = 
                DriverManager.getConnection("jdbc:postgresql://localhost:5432/warehouse", 
                        userName, 
                        password);
    }
    
    public int query(String query, Object[] params) throws SQLException{
         PreparedStatement ps = connection.prepareStatement(query);
         if (params != null){
           int index = 1;
           for(Object param : params){
             ps.setObject(index, param);
            index++;
           }
         }
         return ps.executeUpdate();
    }

    /**
     * Returns data from a table
     * @param table
     * @param columns
     * @param requirement
     * @param params
     * @return
     * @throws SQLException
     */
    public ResultSet select(String table, Object[] columns, String requirement, Object[] params) throws SQLException{
        query = new Query();
        query.select(columns)
             .from(table)
             .where(requirement);

        PreparedStatement ps = connection.prepareStatement(query.getQuery());
        if(params != null){
            int index = 1;
            for(Object param : params){
            ps.setObject(index, param);
            index++;
         }
        }

        ResultSet rs = ps.executeQuery();
        return rs;
    }
}
