package Pinecone.Framework.Util.RDB.Prototype;

import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class GeneralSimpleSQLSpawner implements SimpleSQLSpawner {
    public String spawnSimpleSingleKeyValueSequence( Object szKey, Object szValue, String szGlue ){
        return String.format(" `%s` = '%s' %s ",szKey,szValue, szGlue );
    }

    public String spawnInsertWithArray ( String szFullNameTable, Map dataMap, boolean bReplace ) {
        if ( dataMap != null ) {
            StringBuilder sqlStream = new StringBuilder();
            sqlStream.append( String.format( bReplace ? "REPLACE INTO `%s` " : "INSERT INTO `%s` ", szFullNameTable ) )  ;
            int i = 0, mapSize = dataMap.size();
            StringBuilder sql_key = new StringBuilder();
            StringBuilder sql_value = new StringBuilder();

            for ( Object each : dataMap.entrySet() ) {
                Map.Entry item = (Map.Entry) each;
                sql_key .append( "`" ) .append( item.getKey() ).append( "`" ).append((i != mapSize - 1) ? "," : "");
                sql_value .append("'" ) .append( item.getValue() ).append( "'" ).append ((i++ != mapSize - 1) ? "," : "");
            }
            sqlStream .append( " ( " ).append( sql_key.toString() ).append( " ) VALUES ( " ).append( sql_value.toString() ).append( " )" );
            return sqlStream.toString();
        }
        return "";
    }




    public String spawnNoConditionUpdateSQL( String szFullNameTable, Map dataMap ) {
        if ( dataMap != null ) {
            return String.format( "UPDATE `%s` SET %s ", szFullNameTable, this.spawnSimpleKeyValueSequence(dataMap, ",") );
        }
        return "";
    }

    public String spawnUpdateWithArray ( String szFullNameTable, Map dataMap, Vector<Map.Entry> conditionKeyValues, String szConditionGlue ) {
        if ( dataMap != null ) {
            String szConditionSQL = this.spawnSimpleKeyValueSequence( conditionKeyValues, szConditionGlue );
            if( !szConditionSQL.isEmpty() ){
                return this.spawnNoConditionUpdateSQL( szFullNameTable, dataMap ) + " WHERE " + szConditionSQL;
            }
        }
        return "";
    }

    public String spawnUpdateWithArray ( String szFullNameTable, Map dataMap, Map conditionKeyValues, String szConditionGlue ) {
        if ( dataMap != null ) {
            String szConditionSQL = this.spawnSimpleKeyValueSequence( conditionKeyValues, szConditionGlue );
            if( !szConditionSQL.isEmpty() ){
                return this.spawnNoConditionUpdateSQL( szFullNameTable, dataMap ) + " WHERE  " + szConditionSQL;
            }
        }
        return "";
    }



    public String spawnDeleteWithArray ( String szFullNameTable, Vector<Map.Entry> conditionKeyValues, String szConditionGlue ) {
        StringBuilder sqlStream = new StringBuilder();
        sqlStream .append( String.format( "DELETE FROM `%s` ", szFullNameTable ) );
        if ( conditionKeyValues != null ) {
            sqlStream.append( " WHERE " );
            sqlStream.append( this.spawnSimpleKeyValueSequence( conditionKeyValues, szConditionGlue ) );
        }
        return sqlStream.toString();
    }

    public String spawnDeleteWithArray ( String szFullNameTable, Map conditionKeyValues, String szConditionGlue ) {
        StringBuilder sqlStream = new StringBuilder();
        sqlStream .append( String.format( "DELETE FROM `%s` ", szFullNameTable ) );
        if ( conditionKeyValues != null ) {
            sqlStream.append( " WHERE  " );
            sqlStream.append( this.spawnSimpleKeyValueSequence( conditionKeyValues, szConditionGlue ) );
        }
        return sqlStream.toString();
    }




    public String spawnSimpleKeyValueSequence( Vector<Map.Entry> keyValues, String szGlue ) {
        if ( keyValues!= null ) {
            StringBuilder sqlStream = new StringBuilder();
            int i = 0, mapSize = keyValues.size();
            for ( Map.Entry item : keyValues ) {
                sqlStream.append( this.spawnSimpleSingleKeyValueSequence( item.getKey(),item.getValue(), (i++ != mapSize - 1) ? szGlue : "" ) );
            }
            return sqlStream.toString();
        }
        return "";
    }

    public String spawnSimpleKeyValueSequence( Map keyValues, String szGlue ) {
        if ( keyValues!= null ) {
            StringBuilder sqlStream = new StringBuilder();
            int i = 0, mapSize = keyValues.size();

            for ( Object each : keyValues.entrySet() ) {
                Map.Entry item = (Map.Entry) each;
                sqlStream.append(this.spawnSimpleSingleKeyValueSequence(item.getKey(), item.getValue(), (i++ != mapSize - 1) ? szGlue : ""));
            }
            return sqlStream.toString();
        }
        return "";
    }

}
