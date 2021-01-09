package Pinecone.Framework.Util.JSON.prototype;

import Pinecone.Framework.Debug.Debug;
import Pinecone.Framework.System.Prototype.YokedPair;
import Pinecone.Framework.System.util.ReflectionUtils;
import Pinecone.Framework.System.util.StringUtils;
import Pinecone.Framework.Util.JSON.JSONArray;
import Pinecone.Framework.Util.JSON.JSONException;
import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.JSON.JSONUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JSONType implements YokedPair {
    private Object   mJsonProto;

    private Object   mJavaObject;


    public Object getLeft(){
        return this.mJsonProto;
    }

    public Object getRight(){
        return this.mJavaObject;
    }

    protected void javaObjectJsonify(){

    }

    protected void jsonTypify(){

    }

    public void yokeLeft  ( Object left ){
        this.mJsonProto = left;
    }

    public void yokeRight ( Object right ){
        this.mJavaObject = right;
    }









    public static Number jsonify( Number data ){
        return data;
    }

    public static Boolean jsonify( Boolean data ){
        return data;
    }

    public static String jsonify( String data ){
        return data;
    }

    public static JSONObject jsonify( JSONObject data ){
        return data;
    }

    public static JSONArray jsonify( JSONArray data ){
        return data;
    }

    public static Object  jsonify( Object data ){
        return JSONType.jsonify( data, true );
    }

    public static Object  jsonify( Object data, boolean bRecursive ){
        if ( data != null ) {
            if ( data instanceof JSONObject ) {
                return data;
            }
            else if ( data instanceof JSONArray ) {
                return data;
            }
            else if ( data instanceof Number || data instanceof Boolean || data instanceof String || data == JSONObject.NULL ) {
                return data;
            }
            else if ( data instanceof Map ) {
                return new JSONObject((Map)data);
            }
            else if ( data instanceof List ) {
                return new JSONArray((List) data);
            }
            else if ( data instanceof Collection ) {
                return new JSONArray((Collection)data);
            }
            else if ( data.getClass().isArray() ) {
                return new JSONArray(data);
            }
            else if ( data instanceof JSONString ) {
                return ( (JSONString) data ).toJSONString();
            }
            else {
                return JSONType.javaObjectJsonify( data, bRecursive );
            }
        }

        return JSONObject.NULL;
    }

    public static Object  javaObjectJsonify( Object data, boolean bRecursive ){
        if( data != null ){
            if( data.getClass().isArray() ){
                return new JSONArray(data);
            }
            else if( data.getClass().isEnum() ) {
                return data.toString();
            }
            else {
                JSONObject jsonObject = new JSONObject();
                Field[] fields = data.getClass().getDeclaredFields();
                for ( Field field : fields ) {
                    ReflectionUtils.makeAccessible( field );
                    Object value;
                    try{
                        value = field.get( data );
                    }
                    catch ( IllegalAccessException e ){
                        value = null;
                    }
                    jsonObject.embed( field.getName(), JSONType.jsonify(value, bRecursive) );
                }
                return jsonObject;
            }

        }
        return JSONObject.NULL;
    }


}
