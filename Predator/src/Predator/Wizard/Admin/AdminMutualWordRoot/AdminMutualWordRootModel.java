package Predator.Wizard.Admin.AdminMutualWordRoot;

import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedModel;
import Predator.System.Predator;
import Predator.System.Prototype.JasperFertilizer;
import Predator.Util.PredatorHelper;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;

@JasperFertilizer
public class AdminMutualWordRootModel extends AdminMutualWordRoot implements JSONBasedModel {
    public AdminMutualWordRootModel() {
    }

    public AdminMutualWordRootModel(HostMatrix parentSystem ){
        super(parentSystem);
    }


    @Override
    public void defaultGenie() throws Exception {
        super.defaultGenie();
        this.mutualWordsList();
    }

    public void mutualWordsList() throws SQLException {
        String szModularBasicSQL = String.format(
                "SELECT %%s FROM %s AS tMutual LEFT JOIN %s AS tDictEn2Cn ON tMutual.en_word = tDictEn2Cn.en_word %%s",
                this.tableName( Predator.TABLE_MUTUAL_WORD_ROOTS ),
                this.tableName( Predator.TABLE_EN2CH_DICTIONARY )
        );

        this.mPageData.put( "nPageLimit" , this.getModularConfig().optInt("pageLimit") );
        this.mPageData.put( "nSumOfPage" , this.mysql().countFromTable( String.format( szModularBasicSQL, "COUNT(*)", "" ) ) );
        this.mPageData.put( "nBeginNum" ,  PredatorHelper.getPageBeginDefault( this.$_GSC().opt("pageid"), this.mPageData.optInt( "nSumOfPage" ), this.mPageData.optInt("nPageLimit")) );

        this.mPageData.put(
                "WordsList", this.mysql().select(
                        String.format(
                                szModularBasicSQL ,
                                "tMutual.*, tDictEn2Cn.cn_simple_mean",
                                PredatorHelper.formatLimitSentence(
                                        this.mPageData.optLong( "nBeginNum" ), this.mPageData.optLong( "nPageLimit" )
                                )
                        )
                )
        );
    }

    public void appendNewWord(){
      
    }



    public void wordEditor() throws IOException, ServletException, SQLException {
        String szModularBasicSQL = String.format(
                "SELECT %%s FROM %s AS tMutual LEFT JOIN %s AS tDictEn2Cn ON tMutual.en_word = tDictEn2Cn.en_word %%s",
                this.tableName( Predator.TABLE_MUTUAL_WORD_ROOTS ),
                this.tableName( Predator.TABLE_EN2CH_DICTIONARY )
        );

        this.mPageData.put(
                "CurrentWordInfo", this.assertSelectResult(this.mysql().select(
                        String.format(
                                szModularBasicSQL ,
                                "tMutual.*, tDictEn2Cn.cn_simple_mean",
                                " WHERE tMutual.id = '" + this.assertString( this.$_GSC().optString("id") ) + "'"
                        )
                        )
                ).optJSONObject(0)
        );
    }

    public JSONObject getPageData(){
        return this.mPageData;
    }

    public String toJSONString(){
        return this.mPageData.toString();
    }

}
