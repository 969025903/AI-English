package Predator.Wizard.Public.PersonWordBuild;

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

public class PersonWordBuildControl extends PersonWordBuild implements JSONBasedControl {
    public PersonWordBuildControl() {
    }

    public PersonWordBuildControl(HostMatrix parentSystem) {
        super(parentSystem);
    }

    @Override
    public void defaultGenie() throws Exception{
        super.defaultGenie();
    }

    public void appendNewWordBuild() throws IOException,SQLException{
        JSONObject $_SPOST = this.$_POST( true );
        JSONObject glossary_data = $_SPOST.yokedSharedFromKey( new String[]{ "g_name","g_note","g_authority"} );
        String username = this.getCookieValue("username");
        glossary_data.put("class_id",new SeniorRandom().nextString(8));
        glossary_data.put("username",username);
        glossary_data.put("g_type",1);
        String szNewGlossaryName = $_SPOST.optString("g_name");
        glossary_data.put("create_data", PredatorTimeHelper.getSystemTime("yyyy-MM-dd"));

        if( this.mysql().countFromTable(
                String.format(
                        "SELECT COUNT(*) FROM %s WHERE `username` = '%s' AND `g_name` = '%s'" ,
                        this.tableName(Predator.TABLE_MUTUAL_GLOSSARY), glossary_data.optString("username"),szNewGlossaryName )
        ) > 0 ){
            this.alert( "'"+ glossary_data.optString("username") +"'"+"的"+"'"  + szNewGlossaryName + "'单词本已经存在", 0, -1 );
        }

        boolean bRes = this.mysql().insertWithArray( Predator.TABLE_MUTUAL_GLOSSARY, glossary_data.getMap() ) > 0;
        this.checkResult( bRes, null, this.spawnActionQuerySpell() );
    }

    public void appendNewWord() throws IOException,SQLException{
        JSONObject $_SPOST = this.$_POST( true );
        JSONObject WordBuild_data = $_SPOST.yokedSharedFromKey( new String[]{ "en_word","class_id" } );

        WordBuild_data.put("word_id",0);
        WordBuild_data.put("append_date",PredatorTimeHelper.getSystemTime("yyyy-MM-dd"));

        if( this.mysql().countFromTable(
                String.format(
                        "SELECT COUNT(*) FROM %S WHERE en_word = '%s'",this.tableName(Predator.TABLE_MUTUAL_WORDS),WordBuild_data.optString("en_word")))<=0){
            this.alert( "不存在'"+WordBuild_data.optString("en_word") +"'该单词", 0, -1 );
        }
        if( this.mysql().countFromTable(
                        String.format(
                                "SELECT COUNT(*) FROM %S WHERE en_word = '%s' AND class_id = '%s'",this.tableName(Predator.TABLE_USER_WORDS),WordBuild_data.optString("en_word"),WordBuild_data.optString("class_id"))
                        )>0
                ){
            this.alert( "'"+WordBuild_data.optString("en_word") +"'在单词本中已经存在", 0, -1 );
        }

        boolean bRes = this.mysql().insertWithArray( Predator.TABLE_USER_WORDS, WordBuild_data.getMap() ) > 0;
        this.checkResult( bRes, null, this.spawnActionQuerySpell() );
    }

    public void deleteOneWordBuild()throws IOException, SQLException {
        this.checkResult(
               this.deleteOneWordBuild(null)
        );
    }

    public boolean deleteOneWordBuild(String szID)throws IOException,SQLException{
        this.mysql().deleteWithSQL( Predator.TABLE_USER_WORDS,String.format(" WHERE class_id = \'%s\'", $_GSC().optString("class_id")));
        return this.helper().control().simpleDeleteSingleObject( "class_id", szID, Predator.TABLE_MUTUAL_GLOSSARY );
    }

    public void deleteOneWord()throws IOException, SQLException {
        String szEnWord = $_GSC().optString("en_word");
        String szClassId = $_GSC().optString("class_id");
        this.checkResult(
                this.mysql().deleteWithSQL( Predator.TABLE_USER_WORDS,String.format("WHERE en_word=\'%s\' AND class_id=\'%s\'",szEnWord,szClassId)) > 0
        );
    }

    public void modifyOneWordBuild(){

    }

    public void saveSortId() throws SQLException, JSONException {
        String szClass_Id = $_GSC().optString("class_id");
        for(int i=0;i < sortStream.length();i++){
            Map map1 = new HashMap();
            Map map2 = new HashMap();
            map2.put("sort_id",i);
            map1.put("class_id",szClass_Id);
            map1.put("en_word",sortStream.getJSONObject(i).optString("en_word"));
            this.mysql().updateWithArray(Predator.TABLE_USER_WORDS,map2,map1);
        }
    }

}