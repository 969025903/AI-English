package Predator.Util;

import Pinecone.Framework.Util.JSON.JSONArray;
import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedModel;
import Pinecone.Framework.Util.Net.Illumination.prototype.Wizard;
import Predator.System.Predator;

import java.sql.SQLException;
import java.util.Map;

public class PredatorModelHelper extends PredatorHelper {
    public PredatorModelHelper( Predator matrix ){
        this.init( matrix );
    }


    public JSONArray fetchDefaultMainWordLevels() throws SQLException {
        return this.fetchDefaultMainWordLevels(null);
    }

    public JSONArray fetchDefaultMainWordLevels( String other ) throws SQLException {
        other = other != null ? other : "";
        return this.mysql().select( "SELECT id,m_level FROM " + this.mysql().tableName( Predator.TABLE_MAIN_WORD_LEVELS ) + " " + other );
    }

    public JSONObject fetchDefaultMainWordLevelsMap() throws SQLException {
        return this.fetchDefaultMainWordLevelsMap(null);
    }

    public JSONObject fetchDefaultMainWordLevelsMap( String other ) throws SQLException {
        JSONArray jsonArray = this.fetchDefaultMainWordLevels(other);
        JSONObject map = new JSONObject();

        for ( int i = 0; i < jsonArray.length(); i++ ) {
            JSONObject row = jsonArray.optJSONObject(i);
            map.put( row.optString("id"), row.optString( "m_level" ) );
        }

        return map;
    }

    public JSONArray fetchDefaultGlossaryTypes() throws SQLException {
        return this.fetchDefaultGlossaryTypes(null);
    }

    public JSONArray fetchDefaultGlossaryTypes( String other ) throws SQLException {
        other = other != null ? other : "";
        return this.mysql().select( "SELECT id,g_type FROM " + this.mysql().tableName( Predator.TABLE_GLOSSARY_TYPE ) + " " + other );
    }

    public JSONObject fetchDefaultGlossaryTypesMap() throws SQLException {
        return this.fetchDefaultGlossaryTypesMap(null);
    }

    public JSONObject fetchDefaultGlossaryTypesMap( String other ) throws SQLException {
        JSONArray jsonArray = this.fetchDefaultGlossaryTypes(other);
        JSONObject map = new JSONObject();

        for ( int i = 0; i < jsonArray.length(); i++ ) {
            JSONObject row = jsonArray.optJSONObject(i);
            map.put( row.optString("id"), row.optString( "g_type" ) );
        }
        return map;
    }


    public void simplePaginationPreTreat(JSONBasedModel that, String szModularBasicSQL, String szConditionSQL ){
        JSONObject pageData = that.getPageData();

        pageData.put( "nPageLimit" , ((Wizard)that).getModularConfig().optInt("pageLimit") );
        pageData.put( "nSumOfPage" , this.mysql().countFromTable( String.format( szModularBasicSQL, "COUNT(*)", szConditionSQL ) ) );
        pageData.put( "nBeginNum" ,  PredatorHelper.getPageBeginDefault(
                this.mMatrix.getSystemDispatcher().$_GSC().opt("pageid"),
                pageData.optInt( "nSumOfPage" ),
                pageData.optInt("nPageLimit") )
        );
    }
}
