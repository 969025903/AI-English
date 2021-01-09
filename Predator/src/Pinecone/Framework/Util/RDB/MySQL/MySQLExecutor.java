package Pinecone.Framework.Util.RDB.MySQL;
import Pinecone.Framework.Util.JSON.JSONArray;
import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.RDB.Prototype.GeneralJSONBasedRDBExecutor;
import Pinecone.Framework.Util.RDB.Prototype.GeneralSimpleSQLSpawner;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

public class MySQLExecutor implements GeneralJSONBasedRDBExecutor {
    private MySQLHost   mySQLHost;

    private String      mszTablePrefix;

    private GeneralSimpleSQLSpawner mSimpleSQLSpawner = null;

    private String      mszLastSQLSentence = "";


    private void init() {
        this.mSimpleSQLSpawner = new GeneralSimpleSQLSpawner();
    }


    public MySQLHost getMySQLHost(){
        this.init();
        return this.mySQLHost;
    }

    public MySQLExecutor( MySQLHost mySQLHost){
        this.init();
        this.mySQLHost = mySQLHost;
    }

    public MySQLExecutor( String szDBLocation, String szDBUsername, String szDBPassword ) throws SQLException {
        this.init();
        this.mySQLHost = new MySQLHost( szDBLocation, szDBUsername, szDBPassword );
    }

    public MySQLExecutor( String szDBLocation, String szDBUsername, String szDBPassword, String szCharset ) throws SQLException {
        this.init();
        this.mySQLHost = new MySQLHost( szDBLocation, szDBUsername, szDBPassword, szCharset );
    }




    public void setTablePrefix( String szTablePrefix ){
        this.mszTablePrefix = szTablePrefix;
    }

    public String getTablePrefix() {
        return this.mszTablePrefix;
    }

    public ResultSet query(String szSQL) throws SQLException {
        this.mszLastSQLSentence = szSQL;
        return this.mySQLHost.querySQL(szSQL);
    }

    public long execute(String szSQL, boolean bIgnoreNoAffected) throws SQLException {
        this.mszLastSQLSentence = szSQL;
        return this.mySQLHost.execute(szSQL,bIgnoreNoAffected);
    }

    public long execute(String szSQL) throws SQLException {
        this.mszLastSQLSentence = szSQL;
        return this.mySQLHost.execute(szSQL);
    }

    public String getLastSQLSentence() {
        return this.mszLastSQLSentence;
    }


    public String tableName( String szSimpleTable ){
        return this.mszTablePrefix + szSimpleTable;
    }

    public int getSumFromTable(String szSimpleTable){
        try{
            ResultSet resultSet = this.mySQLHost.querySQL("SELECT COUNT(*) FROM " + this.tableName(szSimpleTable));
            resultSet.next();
            return resultSet.getInt("COUNT(*)");
        }
        catch ( Exception E ){
            return 0;
        }
    }

    public int countFromTable( String szSQL ){
        try{
            ResultSet resultSet = this.mySQLHost.querySQL(szSQL);
            resultSet.next();
            return resultSet.getInt("COUNT(*)");
        }
        catch ( Exception E ){
            return 0;
        }
    }


    /** Insert Function **/
    public static ArrayList<String > column2Array( ResultSet rResult )throws SQLException {
        ArrayList<String> arrayList = new ArrayList<>();
        ResultSetMetaData metaData = rResult.getMetaData();
        int nColumnCount = metaData.getColumnCount();
        for ( int i = 1; i <= nColumnCount; i++ ) {
            arrayList.add(metaData.getColumnName(i));
        }
        return arrayList;
    }

    public ArrayList<Map<String,String > > fetchAssocAll( String szSimpleTable ) throws SQLException {
        return this.fetchAssoc("SELECT * FROM " + this.tableName(szSimpleTable) );
    }

    public ArrayList<Map<String,String > > fetchAssoc( String szSQL ) throws SQLException {
        ResultSet resultSet = this.query(szSQL);
        ArrayList<String > columnIndexMap = MySQLExecutor.column2Array(resultSet);
        ArrayList<Map<String,String > > queryResult = new ArrayList<>();
        int sizeofRowSet = columnIndexMap.size();

        int jc = 0;
        while ( resultSet.next() ){
            queryResult.add( new LinkedHashMap<>() );
            int nAt = 0;
            for (int i = 1; i <= sizeofRowSet; i++) {
                queryResult.get(jc).put(
                        columnIndexMap.get(nAt), resultSet.getString(i)
                );
                nAt++;
            }
            jc++;
        }

        return queryResult;
    }

    public JSONArray select    ( String szSQL ) throws SQLException {
        ResultSet resultSet = this.query( szSQL );
        ArrayList<String> columnIndexMap = MySQLExecutor.column2Array(resultSet);
        JSONArray queryResult = new JSONArray();

        int sizeofRowSet = columnIndexMap.size();
        int jc = 0;
        while (resultSet.next()){
            queryResult.put(new JSONObject());
            int nAt = 0;
            for (int i = 1; i <= sizeofRowSet; i++) {
                queryResult.getJSONObject(jc).put(
                        columnIndexMap.get(nAt), resultSet.getString(i)
                );
                nAt++;
            }
            jc++;
        }

        return queryResult;
    }

    public JSONArray selectAll ( String szSimpleTable ) throws SQLException {
        return this.select("SELECT * FROM " + this.tableName(szSimpleTable) );
    }


    /** Insert Function **/
    public long insertWithArray ( String szSimpleTable, Map dataMap, boolean bReplace ) throws SQLException {
        if ( dataMap != null ) {
            return this.execute( this.mSimpleSQLSpawner.spawnInsertWithArray( this.tableName( szSimpleTable ), dataMap, bReplace ) );
        }
        return -1;
    }

    public long insertWithArray ( String szSimpleTable, Map dataMap ) throws SQLException {
        return insertWithArray(szSimpleTable,dataMap,false);
    }



    /** Update Function **/
    public long updateWithArray ( String szSimpleTable, Map dataMap, Vector<Map.Entry> conditionMap, String szConditionGlue ) throws SQLException {
        if ( dataMap != null ) {
            return this.execute(
                    this.mSimpleSQLSpawner.spawnUpdateWithArray (
                            this.tableName( szSimpleTable ),
                            dataMap,
                            conditionMap,
                            szConditionGlue
                    ),
                    true
            );
        }
        return -1;
    }

    public long updateWithArray ( String szSimpleTable, Map dataMap, Vector<Map.Entry> conditionMap ) throws SQLException {
        return this.updateWithArray( szSimpleTable, dataMap, conditionMap, "AND" );
    }

    public long updateWithArray ( String szSimpleTable, Map dataMap, Map conditionMap, String szConditionGlue ) throws SQLException {
        if ( dataMap != null ) {
            return this.execute(
                    this.mSimpleSQLSpawner.spawnUpdateWithArray (
                            this.tableName( szSimpleTable ),
                            dataMap,
                            conditionMap,
                            szConditionGlue
                    ),
                    true
            );
        }
        return -1;
    }

    public long updateWithArray ( String szSimpleTable, Map dataMap, Map conditionMap ) throws SQLException {
        return this.updateWithArray( szSimpleTable, dataMap, conditionMap, "AND" );
    }

    public long updateWithArray ( String szSimpleTable, Map dataMap, String szConditionSQL ) throws SQLException {
        if ( dataMap != null ) {
            StringBuilder sqlStream = new StringBuilder();
            sqlStream.append( this.mSimpleSQLSpawner.spawnNoConditionUpdateSQL( this.tableName( szSimpleTable ),dataMap ) );

            if ( szConditionSQL!= null ) {
                if( !szConditionSQL.toLowerCase().contains("where")){
                    sqlStream.append(" WHERE ");
                }
                sqlStream.append( szConditionSQL );
            }
            return this.execute( sqlStream.toString(), true );
        }
        return -1;
    }

    public long updateWithArray ( String szSimpleTable, Map dataMap ) throws SQLException {
        return updateWithArray( szSimpleTable, dataMap, (Map) null, "AND" );
    }



    /** Delete Function **/
    public long deleteWithArray ( String szSimpleTable, Vector<Map.Entry> conditionMap,  String szConditionGlue ) throws SQLException {
        if ( conditionMap != null ) {
            return this.execute( this.mSimpleSQLSpawner.spawnDeleteWithArray( this.tableName(szSimpleTable), conditionMap, szConditionGlue ) );
        }
        return this.execute("TRUNCATE `" + tableName(szSimpleTable) + '`');
    }

    public long deleteWithArray ( String szSimpleTable, Map conditionMap,  String szConditionGlue ) throws SQLException {
        if ( conditionMap != null ) {
            return this.execute( this.mSimpleSQLSpawner.spawnDeleteWithArray( this.tableName(szSimpleTable), conditionMap, szConditionGlue ) );
        }
        return this.execute("TRUNCATE  `" + tableName(szSimpleTable) + '`');
    }

    public long deleteWithArray ( String szSimpleTable, Vector<Map.Entry> conditionMap ) throws SQLException {
        return this.deleteWithArray( szSimpleTable,conditionMap,"AND" );
    }

    public long deleteWithArray ( String szSimpleTable, Map conditionMap ) throws SQLException {
        return this.deleteWithArray( szSimpleTable,conditionMap,"AND" );
    }

    public long deleteWithSQL   ( String szSimpleTable, String szConditionSQL ) throws SQLException {
        StringBuilder sqlStream = new StringBuilder();
        sqlStream .append( "DELETE FROM `" ).append( tableName(szSimpleTable) ).append( "`" );
        if ( szConditionSQL!= null ) {
            if( !szConditionSQL.toLowerCase().contains("where")){
                sqlStream.append(" WHERE ");
            }
            sqlStream.append( szConditionSQL );
        }
        System.out.println(sqlStream.toString());
        return this.execute( sqlStream.toString() );
    }


    /** Link Function **/
}
