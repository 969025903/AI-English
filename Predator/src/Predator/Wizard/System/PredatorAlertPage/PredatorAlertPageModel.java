package Predator.Wizard.System.PredatorAlertPage;

import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedModel;
import Predator.System.Prototype.JasperFertilizer;

@JasperFertilizer
public class PredatorAlertPageModel extends PredatorAlertPage implements JSONBasedModel {
    public PredatorAlertPageModel() {
    }

    public PredatorAlertPageModel(HostMatrix parentSystem ){
        super(parentSystem);
    }

    @Override
    public void defaultGenie() throws Exception {
        super.defaultGenie();

        if( $_GSC().optString("title") == null || $_GSC().optString("title").isEmpty() ){
            this.mParentMatrix.getSystemDispatcher().traceSystem500Error( "You have no permission to access this file ! <p style='margin-bottom: 2%;'>Access Denied !</p>" );
            this.stop();
        }

        this.mPageData.put( "title", "操作成功" );
        this.mPageData.put( "url", -1 );
        this.mPageData.put( "state", -1 );
    }

}
