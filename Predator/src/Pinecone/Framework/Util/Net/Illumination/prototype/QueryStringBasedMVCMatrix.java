package Pinecone.Framework.Util.Net.Illumination.prototype;

import Pinecone.Framework.Util.JSON.JSONObject;

import javax.servlet.ServletException;

public interface QueryStringBasedMVCMatrix {
    JSONObject getGlobalConfig() ;

    JSONObject getSystemConfig() ;

    JSONObject getPublicWizardConfig();

    String getControlParameter() ;

    String getWizardParameter()  ;

    String getModelParameter()   ;

    void init() throws ServletException;

    String getSystemPath();

    String getRootClassPath();



    String getWizardSummonerConfig();

    String getWizardPackageName();

    String getModelClassSuffix();

    String getControlClassSuffix();


    DistributableRootDispatcher getSystemDispatcher();
}
