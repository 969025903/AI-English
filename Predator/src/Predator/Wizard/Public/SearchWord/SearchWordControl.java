package Predator.Wizard.Public.SearchWord;

import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedControl;
import Predator.System.Predator;
import Predator.Util.PredatorControlHelper;

import java.io.IOException;
import java.sql.SQLException;

public class SearchWordControl extends SearchWord implements JSONBasedControl {
    public SearchWordControl() {
    }

    public SearchWordControl(HostMatrix parentSystem) {
        super(parentSystem);
    }

    @Override
    public void defaultGenie() throws Exception{
        super.defaultGenie();
    }


}