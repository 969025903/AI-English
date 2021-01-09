package Predator.Wizard.Public.ReciteWord;

import Pinecone.Framework.Algorithm.Random.SeniorRandom;
import Pinecone.Framework.Util.JSON.JSONException;
import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedControl;
import Predator.System.Predator;
import Predator.Util.PredatorTimeHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ReciteWordControl extends ReciteWord implements JSONBasedControl {
    public ReciteWordControl() {
    }

    public ReciteWordControl(HostMatrix parentSystem) {
        super(parentSystem);
    }

    @Override
    public void defaultGenie() throws Exception{
        super.defaultGenie();
    }

    public void appendNewBook() throws SQLException,JSONException,IOException{
        String szD_id = $_GSC().optString("d_id");
        String szUsername = this.getRealCookieName("U_ID");
        JSONObject UserPlan = new JSONObject();
        UserPlan.put("username",szUsername);
        UserPlan.put("d_id",szD_id);
        UserPlan.put("total_recited",0);
        UserPlan.put("now_plan",0);
            if(this.mysql().countFromTable(String.format("SELECT COUNT(*) FROM %s WHERE username='%s' AND d_id = '%s'",this.tableName(Predator.TABLE_USER_PLAN),szUsername,szD_id))<=0){
                this.mysql().insertWithArray(Predator.TABLE_USER_PLAN,UserPlan.getMap());
                this.checkResult(true,"添加成功",this.spawnActionQuerySpell());
            }
            else {
                this.checkResult(false,"已添加该词汇本",this.spawnActionQuerySpell());
            }
    }

    public void deleteOneBook() throws SQLException,JSONException,IOException{
        String szD_id = $_GSC().optString("d_id");
        String szRealUserName = this.getRealCookieName("U_ID");
        this.mysql().deleteWithSQL(Predator.TABLE_USER_PLAN,String.format("WHERE d_id = '%s' AND username = '%s'",szD_id,szRealUserName));
        this.checkResult(true,"已删除该词汇本",this.spawnActionQuerySpell());
    }

    public void studyThisBook() throws SQLException,IOException{
        String szUserName = this.getRealCookieName("U_ID");
        Map OldMap = new HashMap();
        OldMap.put("now_plan",0);
        this.mysql().updateWithArray(Predator.TABLE_USER_PLAN,OldMap," WHERE now_plan =1 AND username = '"+szUserName+"'");
        String szD_id = $_GSC().optString("d_id");
        Map NewMap = new HashMap();
        NewMap.put("now_plan",1);
        this.mysql().updateWithArray(Predator.TABLE_USER_PLAN,NewMap,String.format(" WHERE d_id = '%s' AND username = '%s'",szD_id,szUserName));
        this.checkResult(true,"设置成功",this.spawnActionQuerySpell());
    }

    public void PlanThisBook()throws SQLException,IOException{
        String szUserName = this.getRealCookieName("U_ID");
        String szD_id = $_GSC().optString("d_id");
        String szDayWord = $_POST().optString("Day_Word");
        Map NeWMap = new HashMap();
        NeWMap.put("plan_word",szDayWord);
        this.mysql().updateWithArray(Predator.TABLE_USER_PLAN,NeWMap,String.format(" WHERE d_id = '%s' AND username = '%s'",szD_id,szUserName));
        this.studyThisBook();
    }

}
