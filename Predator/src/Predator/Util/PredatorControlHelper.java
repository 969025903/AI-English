package Predator.Util;

import Pinecone.Framework.Debug.Debug;
import Pinecone.Framework.Util.JSON.JSONArray;
import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedControl;
import Predator.System.Predator;
import Predator.System.PredatorWizardSoul;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

public class PredatorControlHelper extends PredatorHelper {
    public PredatorControlHelper( Predator matrix ){
        this.init( matrix );
    }

    /** Genie **/
    public static Method getControlGenie(JSONBasedControl that, String szGenie, Class<?>... parameterTypes ) {
        try {
            return that.getClass().getMethod( szGenie, parameterTypes );
        }
        catch ( NoSuchMethodException e ){
            return null;
        }
    }

    public static Object invokeControlGenie(JSONBasedControl that, Method hfnGenie, Object...more ) {
        if( hfnGenie != null ){
            try {
                return hfnGenie.invoke( that, more );
            }
            catch ( IllegalAccessException | InvocationTargetException e ){
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void commonMassDelete(JSONBasedControl that, String szKey, String szFunDeleteOne ) {
        JSONObject $_SPOST = ( (PredatorWizardSoul) that).$_POST(true);
        Object ids = $_SPOST.opt( szKey );

        boolean bRes = true;
        if( ids instanceof String ){
            bRes = (boolean) PredatorControlHelper.invokeControlGenie( that, PredatorControlHelper.getControlGenie( that, szFunDeleteOne, String.class ), (String) ids );
        }
        else if( ids instanceof JSONArray){
            JSONArray idsArray = (JSONArray) ids;
            for( int i=0; i < idsArray.length(); ++i ) {
                Debug.trace( idsArray.optString( i ) );
                bRes &= (boolean) PredatorControlHelper.invokeControlGenie( that, PredatorControlHelper.getControlGenie( that, szFunDeleteOne, String.class ), idsArray.optString( i ) );
            }
        }
        else {
            bRes = false;
        }

        ( (PredatorWizardSoul) that).out().print( bRes ? "true" : "false" );
        ( (PredatorWizardSoul) that).stop();
    }


    public static Map simpleDeleteSingleObject( JSONObject $_GET, String szKey, String szDefaultValue ){
        szDefaultValue = PredatorHelper.assertString( szDefaultValue ) ? szDefaultValue : $_GET.optString( szKey );
        if( !PredatorHelper.assertString( szDefaultValue ) ){
            return null;
        }

        TreeMap<String, String > map = new TreeMap<>();
        map.put( szKey, szDefaultValue );
        return map;
    }

    public boolean simpleDeleteSingleObject ( String szKey, String szDefaultValue, String szTable ) throws SQLException {
        JSONObject $_SGET = this.mMatrix.getSystemDispatcher().$_GET(true);
        Map map = PredatorControlHelper.simpleDeleteSingleObject( $_SGET, szKey, szDefaultValue );
        if( map == null ){
            return false;
        }
        return this.mysql().deleteWithArray( szTable, map ) > 0;
    }
}
