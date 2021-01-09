package Pinecone;
import Pinecone.Framework.Debug.Console;
import Pinecone.Framework.Debug.Debug;
import Pinecone.Framework.System.PineconeSystem;
import Pinecone.Framework.System.Prototype.Prototype;
import Pinecone.Framework.System.util.ReflectionUtils;
import Pinecone.Framework.Util.File.FileUtil;
import Pinecone.Framework.Util.JSON.JSONArray;
import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.JSON.JSONSerializer;
import Pinecone.Framework.Util.JSON.prototype.JSONType;
import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.RDB.Prototype.GeneralSimpleSQLSpawner;

import javax.servlet.http.Cookie;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.sql.Array;
import java.util.*;
import java.util.concurrent.Callable;

public class Pinecone {
    public static final String VERSION         = "3.0.1";

    public static final String RELEASE_DATE    = "2021/06/06";

    public static final String ROOT_SERVER     = "http://www.rednest.cn/";

    public static final String MY_PROGRAM_NAME = "Pinecone";              // Define your custom program name.

    public static final String CONTACT_INFO    = "E-Mail:arb@rednest.cn"; // Giving your contact information, if this program interrupt abnormally.

    public static final int FLOAT_ACCURACY     = 32;

    public static final int COMMON_ACCURACY_LIMIT = 10000;



    private static String szGlobalLocation = "/src/Pinecone";

    private static String szUserDir = "E:/MyFiles/CodeScript/Project/Hazelnut/Predator/";

    public static String getUserDir(){
        return szUserDir;
    }

    public static void setUserDir(String sszUserDir){
        Pinecone.szUserDir = sszUserDir;
    }

    public static String getRuntimePath(){
        return Pinecone.getUserDir() + szGlobalLocation;
    }

    public static String getGlobalLocation(){
        return szGlobalLocation;
    }

    public static void setGlobalLocation(String sGlobalLocation){
        szGlobalLocation = sGlobalLocation;
    }

    public static void initialize() throws Exception{

    }



    public static HashSet<String > pfnTuple2HashSet ( JSONArray pTuple ) {
        HashSet<String > hashSet = new HashSet<>();
        for ( Object row : pTuple.getArray() ) {
            hashSet.add( (String) row );
        }
        return hashSet;
    }


    public static void main(String[] args) throws Exception{
        PineconeSystem.inti(new Callable() {
            @Override
            public Object call() throws Exception {
                long startTime = System.currentTimeMillis();//Nop

                Debug.trace( System.getProperty("user.dir") );

                HostMatrix illuminationSystem = new HostMatrix("E:/MyFiles/CodeScript/Project/Hazelnut/Predator/Predator/src/Resources/","config.json5");

                //Debug.trace( illuminationSystem.getSystemConfig() );


                Shit shit = new Shit();

                JSONObject jsonShit = new JSONObject("{'name':'shit',length:1998, '78':7888, 'array':[4,false,null,{name:'shit',length:1998}]}");




//                Field[] fields = shit.getClass().getDeclaredFields();
//
//                JSONObject jsonShit = new JSONObject("{name:'shit',length:1998, '78':7888}");
//
//                for ( Field field : fields ) {
//                    ReflectionUtils.makeAccessible( field );
//                    field.set( shit, jsonShit.opt( field.getName() )  );
//                }
//
//                Debug.trace( jsonShit.toJSONString() );
//
//
//                Debug.trace(  shit.toString() );



                System.out.println("\nRuntime : " + (System.currentTimeMillis() - startTime) + "ms");
                return 0;
            }
        });
    }
}

class Shit{
    public String    name = "ff";
    private int       length;

    public String toString(){
        return String.format( "{\"name\":\"%s\",\"length\":%d}", this.name, this.length );
    }
}