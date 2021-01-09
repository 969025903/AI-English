package Pinecone.Framework.System.Prototype;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;

public class Prototype {
    private String mszPrototypeName = "Object";

    private String mszTypeName = "";

    private Class<?> mParent = null;

    private Object   mThis   = null;

    private Object   mNext   = null;

    public Prototype() {
    }

    public Prototype( Object that ){
        this.mParent = that.getClass().getSuperclass();
        this.mThis   = that;

        this.mszPrototypeName = that.getClass().getSimpleName();
        this.mszTypeName      = that.getClass().getName();
    }


    public void setNext( Object that ){
        this.mNext = that;
    }

    public Object getNext(){
        return this.mNext;
    }

    public Object prototype(){
        return this;
    }

    public Object proto(){
        return this.mThis;
    }

    public Class<?> parent(){
        return this.mParent;
    }

    public String name(){
        return this.mszPrototypeName;
    }

    public String typeName(){
        return this.mszTypeName;
    }

    public String toString(){
        return this.typeName();
    }

    public static String prototypeName( Object that ){
        try {
            return that.getClass().getSimpleName();
        }
        catch ( Exception E ){
            return "Object";
        }
    }

    public static Prototype typeid( Object that ) {
        return new Prototype( that );
    }





    public static HashSet<String > getDeclaredMethodsNameSet( Object hThatClass ){
        HashSet<String > hashSet = new HashSet<>();
        Prototype.getDeclaredMethodsNameSet( hashSet, hThatClass );
        return hashSet;
    }

    public static void getDeclaredMethodsNameSet( HashSet<String > hSet, Object hThatClass ){
        Prototype.getDeclaredMethodsNameSet( hSet, hThatClass.getClass() );
    }

    public static HashSet<String > getDeclaredMethodsNameSet( Class<?> hThatClass ){
        HashSet<String > hashSet = new HashSet<>();
        Prototype.getDeclaredMethodsNameSet( hashSet, hThatClass );
        return hashSet;
    }

    public static void getDeclaredMethodsNameSet( HashSet<String > hSet,  Class<?> hThatClass ){
        Method[] methods = hThatClass.getDeclaredMethods();
        for ( Method row : methods ) {
            hSet.add( row.getName() );
        }
    }


    public static Object invokeNoParameterMethod ( Object hThatClass , String szFunctionName ) throws NoSuchMethodException, InvocationTargetException ,IllegalAccessException {
        Method method = hThatClass.getClass().getMethod( szFunctionName );
        return method.invoke( hThatClass );
    }

}
