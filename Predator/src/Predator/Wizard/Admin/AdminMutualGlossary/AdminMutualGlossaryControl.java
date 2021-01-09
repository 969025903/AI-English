package Predator.Wizard.Admin.AdminMutualGlossary;


import Pinecone.Framework.Algorithm.Random.SeniorRandom;
import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedControl;
import Predator.System.Predator;
import Predator.Util.PredatorControlHelper;
import Predator.Util.PredatorTimeHelper;

import java.io.IOException;
import java.sql.SQLException;


public class AdminMutualGlossaryControl extends AdminMutualGlossary implements JSONBasedControl {
    public AdminMutualGlossaryControl() {
    }

    public AdminMutualGlossaryControl(HostMatrix parentSystem) {
        super(parentSystem);
    }

    @Override
    public void defaultGenie() throws Exception {
        super.defaultGenie();
    }

    public void appendNewGlossary() throws IOException,SQLException {
        JSONObject $_SPOST = this.$_POST( true );
        JSONObject en2ch_data = $_SPOST.yokedSharedFromKey( new String[]{ "username", "g_name","g_note","g_authority","g_type" } );
        en2ch_data.put("create_data", PredatorTimeHelper.getSystemTime("yyyy-MM-dd"));
        String szClassId = new SeniorRandom().nextString(8);
        do{
            szClassId = new SeniorRandom().nextString(8);
        }
        while(this.mysql().countFromTable(String.format("SELECT COUNT(*) FROM %s WHERE `class_id` = '%s'", this.tableName(Predator.TABLE_MUTUAL_GLOSSARY),szClassId ))>0);
        en2ch_data.put("class_id",szClassId);
        String szNewGlossaryName = $_SPOST.optString("g_name");
        String szUsername=$_SPOST.optString("username");
        //String szDate=this.mysql().getSystemTime();
        //en2ch_data.put("create_data",szDate);
        if( this.mysql().countFromTable(
                String.format(
                        "SELECT COUNT(*) FROM %s WHERE `username` = '%s' AND `g_name` = '%s'" ,
                        this.tableName(Predator.TABLE_MUTUAL_GLOSSARY), szUsername,szNewGlossaryName )
        ) > 0 ){
            this.alert( "'"+szUsername+"'"+"的"+"'"  + szNewGlossaryName +"'单词本已经存在", 0, -1 );
        }

        boolean bRes = this.mysql().insertWithArray( Predator.TABLE_MUTUAL_GLOSSARY, en2ch_data.getMap() ) > 0;
        this.checkResult( bRes, null, this.spawnActionQuerySpell() );
    }

    public void deleteOneGlossary() throws IOException,SQLException {
        this.checkResult(
                this.deleteOneGlossary( null )
        );
    }

    public void modifyOneGlossary() throws IOException, SQLException {
        JSONObject $_SPOST = this.$_POST( true );

        JSONObject condition  = $_SPOST.yokedSharedFromKey( "class_id" );
        System.out.println(condition);
        JSONObject glossary_data = $_SPOST.yokedSharedFromKey( new String[]{ "g_name","g_authority","g_note" } );
        boolean bRes = this.mysql().updateWithArray( Predator.TABLE_MUTUAL_GLOSSARY, glossary_data.getMap(), condition.getMap() ) > 0;

        this.checkResult( bRes );
    }

    public boolean deleteOneGlossary( String szID ) throws IOException, SQLException {
        return this.helper().control().simpleDeleteSingleObject( "id", szID, Predator.TABLE_MUTUAL_GLOSSARY );
    }

    public void glossaryMassDelete() throws IOException, SQLException {
        PredatorControlHelper.commonMassDelete( this, "id[]", "deleteOneGlossary" );
    }
}