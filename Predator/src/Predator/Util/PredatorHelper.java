package Predator.Util;

import Pinecone.Framework.Util.JSON.JSONArray;
import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedControl;
import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedModel;
import Pinecone.Framework.Util.Net.Illumination.prototype.Wizard;
import Pinecone.Framework.Util.RDB.MySQL.MySQLExecutor;
import Predator.System.Predator;
import Predator.System.PredatorWizardSoul;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;


public class PredatorHelper {
    protected Predator              mMatrix           = null ;

    private   PredatorModelHelper   mModelHelper      = null ;

    private   PredatorControlHelper mControlHelper    = null ;

    private   PredatorCipherHelper  mCipherHelper     = null ;

    private   PredatorWordSortHelper mWordSortHelper  = null;


    protected void init( Predator matrix ){
        this.mMatrix = matrix;
    }

    protected PredatorHelper(){}

    public PredatorHelper( Predator matrix ) {
        this.init( matrix );
        this.mModelHelper    = new PredatorModelHelper   ( matrix );
        this.mControlHelper  = new PredatorControlHelper ( matrix );
        this.mCipherHelper   = new PredatorCipherHelper  ( matrix );
        this.mWordSortHelper = new PredatorWordSortHelper( matrix );
    }

    public PredatorModelHelper model(){
        return this.mModelHelper;
    }

    public PredatorControlHelper control(){
        return this.mControlHelper;
    }

    public PredatorCipherHelper cipher(){
        return this.mCipherHelper;
    }

    public PredatorWordSortHelper sort() {return this.mWordSortHelper;}


    protected MySQLExecutor mysql(){
        return this.mMatrix.mysql();
    }



    public static String getUserRole(){
        return "public";
    }

    public static JSONArray spawnMenuFairyWithRole( JSONObject hMenuStream ){
        String role = PredatorHelper.getUserRole();
        JSONArray currentMenu = hMenuStream.getJSONArray("union") ;

        if( role.equals("superAdmin") ){

        }
        else {
            JSONArray client = hMenuStream.getJSONArray("client");
            for( int i=0; i < client.length();i++ ){
                currentMenu.put(client.getJSONObject(i));
            }

            JSONArray roleMenu = hMenuStream.optJSONArray( role );
            if( roleMenu != null ){
                for( int i=0; i < roleMenu.length();i++ ){
                    currentMenu.put(roleMenu.getJSONObject(i));
                }
            }
        }

        JSONObject jsonObject = new JSONObject( "{'title':'管理员专区', 'href':'javascript:void(0)', 'icon':'fa fa-magic'}" );
        jsonObject.put("submenu",hMenuStream.getJSONArray("admin"));

        currentMenu.put(jsonObject);

        return currentMenu;
    }

    public static int getPageBegin( int pageID, int sumOfPage , int pageLimit ){
        double limitNum = Math.ceil( (double) sumOfPage / pageLimit );
        if (pageID == -1) {
            pageID = (int)limitNum;
        }
        return (pageID - 1) * pageLimit;
    }

    public static int getPageBeginDefault( Object pageID, int nSumOfPage ,int pageLimit ){
        int cPageID = 1;
        if( pageID != null ){
            cPageID = pageID instanceof Number ? ((Number)pageID).intValue() : Integer.parseInt((String)pageID);
        }

        return getPageBegin(cPageID,nSumOfPage,pageLimit);
    }


    public static String formatLimitSentence( long nBegin, long nLimit ){
        return String.format( " LIMIT %d , %d ", nBegin, nLimit );
    }




    /** Soul **/
    public static PredatorWizardSoul mySoul( HttpServletRequest request ){
        return (PredatorWizardSoul) request.getAttribute("Prototype");
    }

    public static Wizard wizard( HttpServletRequest request ){
        return (Wizard) request.getAttribute("Prototype");
    }

    public static JSONBasedModel model(HttpServletRequest request ){
        return (JSONBasedModel) request.getAttribute("Prototype");
    }

    public static JSONBasedControl controls(HttpServletRequest request ){
        return (JSONBasedControl) request.getAttribute("Prototype");
    }

    public static String jspMyName( Object that ){
        try {
            return that.getClass().getSimpleName().split("_")[0];
        }
        catch ( Exception E ){
            return "";
        }
    }

    public static boolean assertString( String s ){
        return s != null && !s.isEmpty();
    }



    /** Cipher **/
    public static byte[] base64Decode( String b ) {
        return Base64.getDecoder().decode( b.getBytes() );
    }

    public static String base64Encode( byte[] bytes ){
        return new String( Base64.getEncoder().encode( bytes ) );
    }



}
