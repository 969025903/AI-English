package Predator.System;

import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.Net.Illumination.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PredatorDispatcher extends SystemDispatcher {
    protected JSONObject              mGlobalSafePublicContainer    =  null  ;

    protected JSONObject              mGlobalSafeGETContainer       =  null  ;

    protected JSONObject              mGlobalSafePOSTContainer      =  null  ;


    public PredatorDispatcher(){
        super();
    }

    public PredatorDispatcher( HostMatrix system ){
        super(system);
    }

    public PredatorDispatcher( HttpServletRequest request, HttpServletResponse response, HostMatrix system){
        super( request,response,system );
    }

    public Predator predator(){
        return (Predator) this.mMatrix;
    }


    public JSONObject $_GSC() {
        return this.getHttpEntityParser().requestMapJsonify( this.$_REQUEST(), true );
    }

    public JSONObject $_GET  ( boolean bSafe ) {
        if( !bSafe ){
            return this.mGETMapContainer;
        }
        return this.getHttpEntityParser().parseQueryString( this.$_REQUEST().getQueryString(), true );
    }

    public JSONObject $_POST ( boolean bSafe ) {
        if( !bSafe ){
            return this.mPOSTMapContainer;
        }
        return this.getHttpEntityParser().parseFormData( this.$_REQUEST(), true );
    }




    @Override
    public void echoIndexPage() throws IOException, ServletException {
        this.mWizardSummoner.summonAndExecute( "PredatorIndex" );
    }

    @Override
    public void invokeAutoSummonSequence() throws ServletException, IOException {
        switch ( this.mszWizardCommand ){
            case "index": {
                this.echoIndexPage();
                break;
            }
            default:{
                super.invokeAutoSummonSequence();
                break;
            }
        }
    }

}
