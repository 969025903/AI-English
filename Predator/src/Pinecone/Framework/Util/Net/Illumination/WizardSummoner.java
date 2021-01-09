package Pinecone.Framework.Util.Net.Illumination;

import Pinecone.Framework.System.Prototype.Overrideable;
import Pinecone.Framework.Util.Net.Illumination.prototype.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class WizardSummoner implements JSONBasedSummoner {
    protected QueryStringBasedMVCMatrix  mParentMatrix   = null ;

    protected Wizard                     mLastWizard     = null ;

    public WizardSummoner( QueryStringBasedMVCMatrix hMatrix ) {
        this.mParentMatrix = hMatrix;
    }

    public QueryStringBasedMVCMatrix getParentMatrix() {
        return this.mParentMatrix;
    }

   public Wizard getLastSummoned(){
        return this.mLastWizard;
   }



    @Overrideable
    public String spawnNamespace( String szNickName ){
        return this.mParentMatrix.getWizardPackageName() + "." + szNickName;
    }

    @Overrideable
    protected String spawnFullModelPrototypeName( String szNickName ){
        return this.spawnNamespace(szNickName) + "." + szNickName + this.mParentMatrix.getModelClassSuffix();
    }

    @Overrideable
    protected String spawnFullControlPrototypeName( String szNickName ){
        return this.spawnNamespace(szNickName) + "." + szNickName + this.mParentMatrix.getControlClassSuffix();
    }

    protected JSONBasedModel   spawnWizardModelByCallHisName(String szClassName ){
        JSONBasedModel obj = null;
        try {
            Class<?> pVoid = Class.forName( szClassName );
            try{
                Constructor<?> constructor = pVoid.getConstructor( HostMatrix.class );
                obj = (JSONBasedModel) constructor.newInstance( (HostMatrix)this.mParentMatrix );
            }
            catch (NoSuchMethodException | InvocationTargetException e1){
                e1.printStackTrace();
            }
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException e){
            System.err.println( "Summon Error: [" + e.toString() + "]" );
            //e.printStackTrace();
        }

        return obj;
    }

    protected JSONBasedControl spawnWizardControlByHisName(String szClassName ){
        JSONBasedControl obj = null;
        try {
            Class<?> pVoid = Class.forName( szClassName );
            try{
                Constructor<?> constructor = pVoid.getConstructor( HostMatrix.class );
                obj = (JSONBasedControl) constructor.newInstance( (HostMatrix)this.mParentMatrix );
            }
            catch (NoSuchMethodException | InvocationTargetException e1){
                e1.printStackTrace();
            }
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException e){
            System.err.println( "Summon Error: [" + e.toString() + "]" );
            //e.printStackTrace();
        }

        return obj;
    }

    @Overrideable
    public JSONBasedModel      spawnWizardModel(String szNickName ) {
        return this.spawnWizardModelByCallHisName( this.spawnFullModelPrototypeName( szNickName ) );
    }

    @Overrideable
    public JSONBasedControl    spawnWizardControl(String szNickName ) {
        return this.spawnWizardControlByHisName( this.spawnFullControlPrototypeName( szNickName ) );
    }


    @Overrideable
    public Wizard summon( String szNickName ) throws ServletException, IOException {
        JSONBasedControl hControl = this.spawnWizardControl( szNickName );
        JSONBasedModel hModel     = this.spawnWizardModel( szNickName );

        WizardArchetype hArchetype = this.revealArchetype( hModel ,hControl );
        if( hArchetype == null ){
            return null;
        }
        hArchetype.beforeSummon();
        hArchetype.typeSoulBounded( hModel, hControl );

        this.mLastWizard = (Wizard) hArchetype;
        return this.mLastWizard;
    }

    @Overrideable
    @Override
    public void executeAfterSummonSequence() throws ServletException, IOException {
        if( this.mLastWizard != null ){
            ((WizardArchetype)this.mLastWizard).summoning();
            ((WizardArchetype)this.mLastWizard).afterSummon();
        }
    }

    @Overrideable
    public void summonAndExecute( String szNickName ) throws ServletException, IOException {
        if( this.summon( szNickName ) == null ){
            this.mParentMatrix.getSystemDispatcher().traceSystem404Error();
        }
        this.executeAfterSummonSequence();
    }


    @Overrideable
    public WizardArchetype revealArchetype( JSONBasedModel hModel, JSONBasedControl hControl ){
        if( hModel instanceof WizardArchetype ){
            return (WizardArchetype)hModel;
        }
        else if( hControl instanceof WizardArchetype ){
            return (WizardArchetype)hControl;
        }
        return null;
    }

}
