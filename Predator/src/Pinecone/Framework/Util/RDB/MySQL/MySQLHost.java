package Pinecone.Framework.Util.RDB.MySQL;

import Pinecone.Framework.Util.RDB.Prototype.GeneralRDBHost;

import java.sql.*;

public class MySQLHost implements GeneralRDBHost {
    private String      mszLocation;

    private String      mszUsername;

    private String      mszPassword;

    private String      mszCharset;

    private Connection  mGlobalConnection;

    private Statement   mGlobalStatement;

    private PreparedStatement mPreparedStatement;



    public MySQLHost(String dbLocation, String dbUsername, String dbPassword) throws SQLException {
        this.mszLocation = dbLocation;
        this.mszUsername = dbUsername;
        this.mszPassword = dbPassword;
        this.mszCharset = "UTF-8";
        this.getConnection();
    }

    public MySQLHost(String dbLocation, String dbUsername, String dbPassword, String dbCharset) throws SQLException {
        this.mszLocation = dbLocation;
        this.mszUsername = dbUsername;
        this.mszPassword = dbPassword;
        this.mszCharset = dbCharset;
        this.getConnection();
    }

    private void getConnection() throws SQLException {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e){
            throw new SQLException( "JDBC Driver is not found.", "CLASS_NOT_FOUND", e );
        }

        this.mGlobalConnection = DriverManager.getConnection("jdbc:mysql://" + this.mszLocation + "?characterEncoding="+ this.mszCharset +"&useSSL=false",this.mszUsername,this.mszPassword);
    }

    public Connection getGlobalConnection(){
        return this.mGlobalConnection;
    }

    public Statement getGlobalStatement() throws SQLException{
        this.createGlobalStatement();
        return this.mGlobalStatement;
    }

    public PreparedStatement getGlobalPreparedStatement() throws SQLException {
        return null;
    }

    private void createGlobalStatement() throws SQLException {
        if( this.mGlobalConnection != null ){
            if( this.mGlobalStatement == null ){
                this.mGlobalStatement = this.mGlobalConnection.createStatement();
            }
            if( this.mGlobalConnection.isClosed() ){
                this.getConnection();
            }
            if( this.mGlobalStatement.isClosed() ){
                this.mGlobalStatement = this.mGlobalConnection.createStatement();
            }
        }
    }


    /** Executor **/
    public ResultSet querySQL(String sql) throws SQLException {
        this.createGlobalStatement();
        return this.mGlobalStatement.executeQuery(sql);
    }

    public long execute(String sql, boolean ignoreNoAffected) throws SQLException {
        this.createGlobalStatement();
        this.mGlobalStatement.execute(sql);
        if( ignoreNoAffected ){
            return 1;
        }
        return this.mGlobalStatement.getUpdateCount();
    }

    public long execute(String sql) throws SQLException {
        return execute(sql , false);
    }

}
