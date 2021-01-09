package Predator.Wizard.Public.SearchWord;

import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedModel;
import Predator.System.Predator;
import Predator.System.Prototype.JasperFertilizer;
import Predator.Util.PredatorHelper;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;

@JasperFertilizer
public class SearchWordModel extends SearchWord implements JSONBasedModel {
    public SearchWordModel() {
    }

    public SearchWordModel(HostMatrix parentSystem ) {
        super(parentSystem);
    }

    @Override
    public void defaultGenie() throws Exception {
        super.defaultGenie();
        this.Search();
    }

    public void Search() throws SQLException {
        String szKeyWord=$_GSC().optString("KeyWord");
        System.out.println(szKeyWord);
        String szModelWordBuildSQL = String.format(
                "SELECT * From %s AS tGlossary WHERE en_word = '%s'",
                this.tableName( Predator.TABLE_MUTUAL_WORDS ),
                szKeyWord

        );

        String szModelChineseBuildSQL = String.format(
                "SELECT cn_n_mean,cn_vt_mean,cn_vi_mean,cn_adj_mean,cn_adv_mean,cn_art_mean,cn_conj_mean,cn_int_mean From %s AS chinese WHERE en_word = '%s' ",
                this.tableName( Predator.TABLE_EN2CH_DICTIONARY ),
                szKeyWord

                );

        this.helper().model().simplePaginationPreTreat( this, szModelWordBuildSQL,"" );
        this.helper().model().simplePaginationPreTreat( this, szModelChineseBuildSQL,"" );

        this.mPageData.put(
                "SearchWord",this.mysql().select(
                                szModelWordBuildSQL
                )
        );
        this.mPageData.put(
                "SearchChinese",this.mysql().select(
                                szModelChineseBuildSQL
                )
        );
    }


    public void appendNewUser() throws SQLException {}


}