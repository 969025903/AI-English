package Predator.Wizard.Public.PersonWordBuild;

import Pinecone.Framework.Util.JSON.JSONArray;
import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedModel;
import Predator.System.Predator;
import Predator.System.Prototype.JasperFertilizer;
import Predator.Util.PredatorWordSortHelper;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@JasperFertilizer
public class PersonWordBuildModel extends PersonWordBuild implements JSONBasedModel {
    public PersonWordBuildModel() {
    }

    public PersonWordBuildModel(HostMatrix parentSystem) {
        super(parentSystem);
    }

    @Override
    public void defaultGenie() throws Exception {
        int a[][] = new int[10][15];
        System.out.println(a[0].length);
        super.defaultGenie();
        this.mutualUsersWordBuildList();
    }

    public void mutualUsersWordBuildList() throws SQLException {

      String username = this.getRealCookieName("U_ID");
      if(username != null) {
          String szModelUserBuildSQL = String.format(
                  "SELECT %%s From %s AS tGlossary WHERE %s = '%s'",
                  this.tableName(Predator.TABLE_MUTUAL_GLOSSARY),
                  "username",
                  username
          );

          szModelUserBuildSQL = this.filter(szModelUserBuildSQL);

          this.helper().model().simplePaginationPreTreat(this, szModelUserBuildSQL, "");

          this.mPageData.put(
                  "UserWordBuildList", this.mysql().select(
                          String.format(
                                  szModelUserBuildSQL,
                                  "g_name,create_data,g_authority,g_note,id,class_id")
                  )
          );
      }
    }

    public void showWordBuildContent() throws SQLException{

        String class_id = $_GSC().optString("class_id");
        String szSortType = $_GSC().optString("sort_type");

        String szWordBuildContent = String.format(
                "SELECT tEn2cn.* ,tMutualWord.* From" +
                        " %s AS tEn2cn,%s AS tGlossary,%s AS tWord,%s AS tMutualWord" +
                        " WHERE tWord.class_id = tGlossary.class_id AND" +
                        " tEn2cn.en_word = tWord.en_word AND" +
                        " tMutualWord.en_Word = tWord.en_word AND"+
                        " tGlossary.class_id=\'%s\' ",this.tableName( Predator.TABLE_EN2CH_DICTIONARY ),this.tableName( Predator.TABLE_MUTUAL_GLOSSARY ),this.tableName( Predator.TABLE_USER_WORDS ),this.tableName(Predator.TABLE_MUTUAL_WORDS),class_id
        );
        if(!$_GSC().optString("en_word").equals("")){
            szWordBuildContent += "AND tWord.en_word LIKE '"+$_GSC().optString("en_word")+"%'";
        }
        szWordBuildContent += " ORDER BY sort_id";

        JSONArray BuildWordList = this.mysql().select( szWordBuildContent );


        switch (szSortType){
            case "0":{
                BuildWordList = this.helper().sort().sortShuffle( BuildWordList );break;
            }
            case "1":{
                BuildWordList = this.mysql().select(String.format(
                        "SELECT tEn2ch.*,tMutual.w_level FROM %s as tUword,%s as tEn2ch,%s as tMutual " +
                                "WHERE tEn2ch.en_word=tMutual.en_word AND tUword.en_word = tEn2ch.en_word AND tUword.en_word = tMutual.en_word AND class_id = \'%s\' AND tUword.en_word LIKE '%s' " +
                                "ORDER BY en_word %s"
                        ,this.tableName(Predator.TABLE_USER_WORDS),this.tableName(Predator.TABLE_EN2CH_DICTIONARY),this.tableName(Predator.TABLE_MUTUAL_WORDS),class_id,$_GSC().optString("en_word")+"%","ASC"
                ));break;
            }
            case "2":{
                BuildWordList = this.helper().sort().sortQuickWordLength(BuildWordList);break;
            }
            default:break;
        }

        this.sortStream = BuildWordList;

        this.mPageData.put(
                "WordBuildContent",BuildWordList
        );

        this.mPageData.put(
                "szSortType",szSortType
        );

        this.mPageData.put(
                "szSortEnWord",$_GSC().optString("en_word")
        );

        this.mPageData.put(
                "WordBuildName",this.mysql().select( String.format("SELECT g_name,class_id From predator_mutual_glossary WHERE class_id=\'%s\'",class_id)
        ));


    }

    public void appendNewWordBuild(){
    }

    public void appendNewWord(){
        this.mPageData.put("class_id",$_GSC().optString("class_id"));
    }

    public JSONObject getPageData(){
        return this.mPageData;
    }

    public String toJSONString(){
        return this.mPageData.toString();
    }
}