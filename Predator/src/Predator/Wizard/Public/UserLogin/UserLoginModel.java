package Predator.Wizard.Public.UserLogin;

import Pinecone.Framework.Util.JSON.JSONException;
import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedModel;
import Predator.System.Prototype.JasperFertilizer;

import javax.servlet.http.Cookie;
import java.io.IOException;

@JasperFertilizer
public class UserLoginModel extends UserLogin implements JSONBasedModel {

    public UserLoginModel() {
    }

    public UserLoginModel(HostMatrix parentSystem) {
        super(parentSystem);
    }

    @Override
    public void defaultGenie() throws Exception {
        super.defaultGenie();
        this.UserLogin();
    }

    public void UserRegister() { this.WaringText(); }

    public void UserLogin() {
        this.WaringText();
    }

    public void WaringText() throws JSONException{
        this.mPageData.put("WaringText",$_POST().optString("waringText"));
    }

    public JSONObject getPageData(){
        return this.mPageData;
    }

    public String toJSONString(){
        return this.mPageData.toString();
    }
}
