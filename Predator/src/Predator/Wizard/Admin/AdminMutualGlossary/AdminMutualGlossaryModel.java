package Predator.Wizard.Admin.AdminMutualGlossary;

import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedModel;
import Predator.System.Predator;
import Predator.System.Prototype.JasperFertilizer;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;

@JasperFertilizer
public class AdminMutualGlossaryModel extends AdminMutualGlossary implements JSONBasedModel {
    public AdminMutualGlossaryModel() {
    }

    public AdminMutualGlossaryModel(HostMatrix parentSystem) {
        super(parentSystem);
    }


    @Override
    public void defaultGenie() throws Exception {
        super.defaultGenie();
        this.mutualGlossaryList();
    }

    public void mutualGlossaryList() throws SQLException {
        String szModularBasicSQL = String.format(
                "SELECT %%s FROM %s",
                this.tableName( Predator.TABLE_MUTUAL_GLOSSARY )
        );

        this.helper().model().simplePaginationPreTreat( this, szModularBasicSQL,"" );

        this.mPageData.put(
                "GlossaryList", this.mysql().select(
                        String.format(
                                szModularBasicSQL ,
                                "*"
                                )
                        )
        );
    }

    public void appendNewGlossary(){

    }

    public void glossaryEditor() throws IOException, ServletException, SQLException {
        String szModularBasicSQL = String.format(
                "SELECT %%s FROM %s %%s",
                this.tableName( Predator.TABLE_MUTUAL_GLOSSARY )
        );
        this.mPageData.put(
                "CurrentGlossaryInfo", this.assertSelectResult(this.mysql().select(
                        String.format(
                                szModularBasicSQL ,
                                "*",
                                " WHERE class_id = '" + this.assertString( this.$_GSC().optString("class_id") ) + "'"
                        )
                        )
                ).optJSONObject(0)
        );
    }

}