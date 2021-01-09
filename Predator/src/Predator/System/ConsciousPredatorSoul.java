package Predator.System;

import Pinecone.Framework.System.Prototype.Overrideable;
import Pinecone.Framework.Util.Net.Illumination.*;
import Pinecone.Framework.Util.Net.Illumination.prototype.ConsciousWizardSoul;
import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedControl;
import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedModel;
import Pinecone.Framework.Util.Net.Illumination.prototype.ModelEnchanter;
import Predator.System.Prototype.JasperFertilizer;

import javax.servlet.ServletException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public abstract class ConsciousPredatorSoul extends PredatorWizardSoul implements ConsciousWizardSoul {
    protected String mszCurrentModelGenie = null;

    public ConsciousPredatorSoul(){
        super();
    }

    public ConsciousPredatorSoul(HostMatrix parentSystem ){
        super(parentSystem);
    }

    @Override
    public void dispatch() throws IOException, ServletException {
        this.commandArrived();
        try{
            if( this instanceof JSONBasedModel){
                String szModelCommand = this.getModelCommand();
                this.summonNormalGenieByCallHisName( szModelCommand );
                this.mszCurrentModelGenie = szModelCommand;
            }
            else if( this instanceof JSONBasedControl){
                this.summonNormalGenieByCallHisName( this.getControlCommand() );
            }
        }
        catch ( NaughtyGenieInvokedException e ){
            if( e.getType() == NaughtyGenieInvokedException.NaughtyGenieType.N_HETEROGENEOUS ){
                if( e.getCause() != null && e.getCause().getCause() != null ){
                    this.handleException( (Exception)e.getCause().getCause() );
                    this.stop();
                }
            }

            try{
                this.defaultGenie();
                this.mszCurrentModelGenie = "defaultGenie";
            }
            catch ( TerminateSessionException e1 ){
                throw e1;
            }
            catch ( Exception allExp ){
                this.handleException( allExp );
            }

        }
        this.afterDispatchInvoke();
    }

    @Override
    public void defaultGenie() throws Exception {

    }

    @Override
    public void commandArrived() throws IOException, ServletException {}

    @Override
    public void afterDispatchInvoke() throws IOException, ServletException {}



    /** JasperYokedSoul **/
    protected Occupation prospectGlobalOccupation() {
        if( this.mbGlobalEnchanter && !this.mbGlobalFertilized ){
            return Occupation.W_ENCHANTER;
        }
        else if( this.mbGlobalFertilized ){
            return Occupation.W_FERTILIZER;
        }
        return Occupation.W_FREE;
    }

    private Occupation prospectGenieOccupation( String szGenieName ) {
        Occupation wizardOccupation = Occupation.W_FREE;

        if( szGenieName != null && !szGenieName.isEmpty() ){
            try {
                Method method = this.getClass().getMethod(szGenieName);
                Annotation[] annotations = method.getAnnotations();
                boolean bFertilizerValue = false;
                boolean bEnchanterValue  = false;
                boolean bFertilizerFound = false;
                boolean bEnchanterFound  = false;
                for( Annotation a : annotations ){
                    if( a instanceof JasperFertilizer ){
                        bFertilizerFound = true;
                        bFertilizerValue = ((JasperFertilizer)a).value();
                    }
                    else if( a instanceof ModelEnchanter){
                        bEnchanterFound  = true;
                        bEnchanterValue  = ((ModelEnchanter)a).value();
                    }
                }

                if( bFertilizerValue ){
                    wizardOccupation = Occupation.W_FERTILIZER;
                }
                else if( bEnchanterValue ){
                    wizardOccupation = Occupation.W_ENCHANTER;
                }

                if ( wizardOccupation == Occupation.W_FREE && (bEnchanterFound || bFertilizerFound) ){
                    wizardOccupation = Occupation.W_FORCE_FREE;
                }

            }
            catch ( NoSuchMethodException e ){
                e.printStackTrace();
                this.stop();
            }
        }

        return wizardOccupation;
    }

    @Overrideable
    public void render() throws ServletException, IOException {
        if( this instanceof JSONBasedModel){
            Occupation finalOccupation = this.prospectGenieOccupation( this.mszCurrentModelGenie );
            if( finalOccupation == Occupation.W_FREE ){
                finalOccupation = this.prospectGlobalOccupation();
            }
            switch ( finalOccupation ){
                case W_ENCHANTER:{
                    this.out().print( ((JSONBasedModel)this).toJSONString() );
                    break;
                }
                case W_FERTILIZER:{
                    this.fertilizedHF();
                    break;
                }
                case W_FORCE_FREE:
                default:{
                    break;
                }
            }

            this.mszCurrentModelGenie = null;
        }
    }
}
