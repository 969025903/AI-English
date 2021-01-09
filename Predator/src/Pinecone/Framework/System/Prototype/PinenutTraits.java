package Pinecone.Framework.System.Prototype;

import Pinecone.Framework.System.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class PinenutTraits {
    public static String  FUN_TOJSONSTRING_NAME  =  "toJSONString";

    public static String invokeToJSONString ( Object that ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method fnToJSONString = that.getClass().getMethod( PinenutTraits.FUN_TOJSONSTRING_NAME );
        ReflectionUtils.makeAccessible( fnToJSONString );
        return (String) fnToJSONString.invoke( that );
    }

    public static String invokeToJSONString ( Object that, int nIndentFactor, int nIndentBlankNum ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method fnToJSONString = that.getClass().getMethod( PinenutTraits.FUN_TOJSONSTRING_NAME, int.class, int.class );
        ReflectionUtils.makeAccessible( fnToJSONString );
        return (String) fnToJSONString.invoke( that, nIndentFactor, nIndentBlankNum );
    }

    public static String invokeToJSONString ( Object that, String szDefaultResult )  {
        try{
            return PinenutTraits.invokeToJSONString( that );
        }
        catch ( NoSuchMethodException | IllegalAccessException | InvocationTargetException e ){
            if( szDefaultResult == null ){
                return that.toString();
            }
            return szDefaultResult;
        }
    }
}
