package Pinecone.Framework.Util.Net.Illumination;

import Pinecone.Framework.Debug.Debug;
import Pinecone.Framework.System.Prototype.Overrideable;
import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedControl;
import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedModel;
import Pinecone.Framework.Util.Net.Illumination.prototype.MVCYokedWizardAllyType;
import Pinecone.Framework.Util.Net.Illumination.prototype.WizardSoul;

import javax.servlet.ServletException;
import java.io.IOException;

public abstract class WizardArchetype implements WizardSoul, MVCYokedWizardAllyType {
    protected HostMatrix         mParentMatrix    =  null  ;

    private JSONBasedModel       mYokedModel      =  null  ;

    private JSONBasedControl     mYokedControl    =  null  ;


    public void typeSoulBounded( JSONBasedModel model, JSONBasedControl control ){
        this.mYokedModel   = model;
        this.mYokedControl = control;
    }

    public JSONBasedModel   revealYokedModel(){
        return this.mYokedModel;
    }

    public JSONBasedControl revealYokedControl(){
        return this.mYokedControl;
    }



    @Overrideable
    public void beforeSummon() {
    }

    @Overrideable
    public void summoning() throws ServletException, IOException {
        try{
            if( this.mYokedControl != null ){
                this.mYokedControl.beforeDispatch();
                this.mYokedControl.dispatch();
                this.mYokedControl.afterDispatch();
            }

            if( this.mYokedModel != null ){
                this.mYokedModel.beforeDispatch();
                this.mYokedModel.dispatch();
                this.mYokedModel.render();
                this.mYokedModel.afterDispatch();
            }

        }
        catch ( TerminateSessionException e ){
            System.out.println( "Wizard: One of caught session or sequence has been terminated." );
        }
    }

    @Overrideable
    public void afterSummon() {}

}
