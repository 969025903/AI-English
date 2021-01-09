package Pinecone.Framework.Util.Net.Illumination;

import Pinecone.Framework.System.Prototype.Spawner;
import Pinecone.Framework.Util.Net.Illumination.prototype.QueryStringBasedMVCMatrix;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class IlluminationSystemSpawner implements Spawner {
    public static HostMatrix spawnMatrix ( String szMatrixPrototypeName, IlluminationServlet servlet ){
        HostMatrix hMatrix = null;
        try {
            Class<?> pVoid = Class.forName( szMatrixPrototypeName );
            try{
                Constructor<?> constructor = pVoid.getConstructor( IlluminationServlet.class );
                hMatrix = (HostMatrix) constructor.newInstance( servlet );
            }
            catch (NoSuchMethodException | InvocationTargetException e1){
                System.err.println( "Critical Error: Servlet Class is error defined." );
                e1.printStackTrace();
            }
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException e){
            System.err.println( "Critical Error: Checking 'web.xml' [ServletSystem] to find what was happened. " );
            e.printStackTrace();
        }

        return hMatrix;
    }

    public static SystemDispatcher spawnDispatcher ( String szDispatcherPrototypeName, HostMatrix matrix ){
        SystemDispatcher systemDispatcher = null;
        try {
            Class<?> pVoid = Class.forName( szDispatcherPrototypeName );
            try{
                Constructor<?> constructor = pVoid.getConstructor(HostMatrix.class);
                systemDispatcher = (SystemDispatcher) constructor.newInstance( matrix );
            }
            catch (NoSuchMethodException | InvocationTargetException e1){
                System.err.println( "Critical Error: system Dispatcher is error defined." );
                e1.printStackTrace();
            }
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException e){
            System.err.println( "Critical Error: Checking 'web.xml' [SystemDispatcher] to find what was happened. " );
            e.printStackTrace();
        }

        return systemDispatcher;
    }

    public static WizardSummoner spawnWizardSummoner ( String szSummonerPrototypeName, QueryStringBasedMVCMatrix matrix ) {
        WizardSummoner summoner = null;
        try {
            Class<?> pVoid = Class.forName( szSummonerPrototypeName );
            try{
                Constructor<?> constructor = pVoid.getConstructor( QueryStringBasedMVCMatrix.class );
                summoner = (WizardSummoner) constructor.newInstance( matrix );
            }
            catch (NoSuchMethodException | InvocationTargetException e1){
                e1.printStackTrace();
            }
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException e){
            e.printStackTrace();
        }

        return summoner;
    }

}
