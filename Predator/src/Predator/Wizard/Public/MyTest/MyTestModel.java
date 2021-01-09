package Predator.Wizard.Public.MyTest;

import Pinecone.Framework.Debug.Debug;
import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.JSON.prototype.JSONType;
import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedModel;
import Pinecone.Framework.Util.Net.Illumination.prototype.ModelEnchanter;
import Predator.System.Prototype.JasperFertilizer;

import javax.servlet.ServletException;
import java.io.IOException;

//@ModelEnchanter
@JasperFertilizer
public class MyTestModel extends MyTest implements JSONBasedModel {
    public MyTestModel() {

    }

    public MyTestModel(HostMatrix parentSystem ){
        super(parentSystem);
//        this.setFertilizerRole(true);
    }

    public void defaultGenie() throws IOException, ServletException {
        this.mPageData.put( "debug", "default" );
        console.log("Default Call");

        //this.fertilizedHF();
    }

    public void ha() {
        this.mPageData.put( "debug", "Ha from java" );
    }

    public void fa() {
        this.mPageData.put( "debug", "fa from java" );
    }

}
