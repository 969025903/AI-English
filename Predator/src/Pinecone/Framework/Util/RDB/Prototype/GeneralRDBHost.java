package Pinecone.Framework.Util.RDB.Prototype;

import java.sql.*;

public interface GeneralRDBHost {
    Connection getGlobalConnection();

    ResultSet querySQL(String sql) throws SQLException;

    long execute(String sql, boolean ignoreNoAffected) throws SQLException;

    long execute(String sql) throws SQLException;
}
