package hu.rekomo.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * Source: https://www.ictdemy.com/java/jdbc/programming-a-database-wrapper-in-java-the-database-class
 */
public class Database {
    
    protected Connection connection;
    protected Query query;
    private final String DB = "warehouse";

    /**
     * The Database class constructor
     * @param userName
     * @param password
     * @throws SQLException
     */
    public Database(String userName, String password) throws SQLException
    {
        this.connection = 
                DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + DB, 
                        userName, 
                        password);
    }

     /**
     *
     * @param query
     * @param params
     * @return
     * @throws SQLException
     */
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
    
    /**
     * Removes data from a database table
     * @param table
     * @param requirement
     * @param param
     * @return
     * @throws SQLException
     */
    public int delete(String table, String requirement, Object[] param) throws SQLException{
        query = new Query();
        query.delete(table)
             .where(requirement);
        return query(query.getQuery(), param);
    }
    
    /**
     * Inserts one row to a database table
     * @param table
     * @param params
     * @return
     * @throws java.sql.SQLException
     */
    public int insert(String table, Object[] params) throws SQLException{
        query = new Query();
        query.insert(table)
             .values(params);
        return query(query.getQuery(), params);
    }
    
   /**
     * Updates data stored in a database table
     * @param table
     * @param columns
     * @param requirement
     * @param params
     * @return
     * @throws SQLException
     */
    public int update(String table, String[] columns, String requirement, Object[] params) throws SQLException{
        query = new Query();

        query.update(table)
             .set(columns)
             .where(requirement);

        return query(query.getQuery(), params);
    }

}
