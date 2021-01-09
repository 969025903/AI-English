package Predator.Wizard.Public.ReciteWord;

import Pinecone.Framework.Util.JSON.JSONArray;
import Pinecone.Framework.Util.JSON.JSONException;
import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedModel;
import Predator.System.Predator;
import Predator.System.Prototype.JasperFertilizer;
import java.sql.SQLException;

@JasperFertilizer
public class ReciteWordModel extends  ReciteWord implements JSONBasedModel {
    public  ReciteWordModel() {
    }

    public  ReciteWordModel(HostMatrix parentSystem) {
        super(parentSystem);
    }

    @Override
    public void defaultGenie() throws Exception {
        super.defaultGenie();
        this.ReciteWordCenter();
    }

    public void ReciteWordCenter() throws SQLException,JSONException{
       this.WordBookList();
       this.listUserPlan();
       this.MyBookList();
    }

    public void WordBookList()throws SQLException{
        String szDictionarySQL = String.format("Select %s FROM %s","*",this.tableName(Predator.TABLE_MUTUAL_DICTIONARY));

        this.mPageData.put("WordBookSelect",this.mysql().select(szDictionarySQL));
    }
    public void MyBookList() throws SQLException{
        String szUsername = this.getRealCookieName("U_ID");
        String szMyBookListSQL = String.format("Select %s,%s,Plan.%s,Plan.%s,d_word_sum FROM %s AS Plan,%s AS Dictionary WHERE Plan.d_id = Dictionary.d_id AND username = '%s'","d_name","imageurl","d_id","plan_word",this.tableName(Predator.TABLE_USER_PLAN),this.tableName(Predator.TABLE_MUTUAL_DICTIONARY),szUsername);
        this.mPageData.put("MyBookList",this.mysql().select(szMyBookListSQL));
    }

    public void listUserPlan() throws SQLException,JSONException{

        String szRealUserName = this.getRealCookieName("U_ID");
        this.mPageData.put("UserPlan",this.mysql().select(String.format("Select d_id,total_recited,plan_word FROM %s WHERE username = '%s' AND now_plan = '1'",this.tableName(Predator.TABLE_USER_PLAN),szRealUserName)));

    }

    public void listReciteWord( String szD_id) throws SQLException, JSONException{
        String szUserName = this.getRealCookieName("U_ID");
        String szWordLevel = this.mysql().select(String.format("Select id FROM %s WHERE m_level = '%s'",this.tableName(Predator.TABLE_MAIN_WORD_LEVELS),szD_id)).getJSONObject(0).getString("id");
        JSONObject ReciteWordPlan = this.mysql().select(String.format("Select * FROM %s WHERE username ='%s' AND d_id = '%s'",this.tableName(Predator.TABLE_USER_PLAN),szUserName,szD_id)).getJSONObject(0);
        String szRecitingWord = ReciteWordPlan.getString("plan_word");
        String szRecitedWord = ReciteWordPlan.getString("total_recited");
        this.mPageData.put("ReciteWordList",this.mysql().select(String.format("Select tMutual.en_word,tMutual.w_character,tMutual.us_phonetic_symbol,tEn2ch.cn_simple_mean FROM %s AS tMutual,%s AS tEn2ch WHERE" +
                " tMutual.en_word = tEn2ch.en_word AND w_level LIKE '%s' LIMIT %s,%s",this.tableName(Predator.TABLE_MUTUAL_WORDS),this.tableName(Predator.TABLE_EN2CH_DICTIONARY),"%"+szWordLevel+"%",szRecitedWord,szRecitingWord)));
        String szFakeWordSql_1 = String.format("SELECT en_word,cn_simple_mean FROM %s WHERE en_word not in",this.tableName(Predator.TABLE_EN2CH_DICTIONARY));
        String szFakeWordSql_2 = String.format("(SELECT en_word FROM (SELECT en_word FROM %s WHERE w_level LIKE '%s' LIMIT %s,%s)as en_word)",this.tableName(Predator.TABLE_MUTUAL_WORDS),"%"+szWordLevel+"%",szRecitedWord,szRecitingWord);
        this.mPageData.put("FakeReciteWordList",this.mysql().select(szFakeWordSql_1+szFakeWordSql_2));

    }

    public void ReciteWord() throws SQLException{

        String szD_id = $_GSC().optString("d_id");
        String szUserName = this.getRealCookieName("U_ID");
        this.listUserPlan();
        this.listReciteWord( szD_id );

    }

    public JSONObject getPageData(){
        return this.mPageData;
    }

    public String toJSONString(){
        return this.mPageData.toString();
    }
}
