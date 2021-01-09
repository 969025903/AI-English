package Predator.Wizard.Admin.AdminUserManage;

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
public class AdminUserManageModel extends AdminUserManage implements JSONBasedModel {
    public AdminUserManageModel() {
    }

    public AdminUserManageModel( HostMatrix parentSystem ) {
        super(parentSystem);
    }

    @Override
    public void defaultGenie() throws Exception {
        super.defaultGenie();
        this.mutualUsersList();
    }

    public void mutualUsersList() throws SQLException {
        String szModularBasicSQL = String.format(
                "SELECT %%s FROM %s %%s",
                this.tableName( Predator.TABLE_MUTUAL_USERS )
        );


        String szAuthority = this.$_GSC().optString( "authority" ), szNameID = this.$_GSC().optString( "nameID" );
        String szSelectAuthoritySQL = PredatorHelper.assertString( szAuthority ) ? " `authority` = '" + szAuthority + "'" : "";
        String szSelectNameIDSQL = PredatorHelper.assertString( szNameID ) ?  String.format(
                " %s (nickname like '%%%s%%' or username like '%%%s%%')",
                szSelectAuthoritySQL.isEmpty() ? "" : "AND",
                szNameID, szNameID
        ) : "";

        String szConditionSQL = szSelectAuthoritySQL.isEmpty() && szSelectNameIDSQL.isEmpty() ? "" : " WHERE " + szSelectAuthoritySQL + szSelectNameIDSQL;

        this.helper().model().simplePaginationPreTreat( this, szModularBasicSQL, szConditionSQL );

        this.mPageData.put(
                "UsersList", this.mysql().select(
                        String.format(
                                szModularBasicSQL ,
                                "`id`, `username`, `nickname`, `authority`, `registration_date`",
                                szConditionSQL + " " + PredatorHelper.formatLimitSentence(
                                        this.mPageData.optLong( "nBeginNum" ), this.mPageData.optLong( "nPageLimit" )
                                )
                        )
                )
        );
    }

    public void appendNewUser() throws SQLException {}

    public void userEditor() throws IOException, ServletException, SQLException {
        String szModularBasicSQL = String.format(
                "SELECT %%s FROM %s %%s",
                this.tableName( Predator.TABLE_MUTUAL_USERS )
        );

        this.mPageData.put(
                "CurrentUserInfo", this.assertSelectResult(this.mysql().select(
                        String.format(
                                szModularBasicSQL ,
                                "`id`, `username`, `nickname`, `authority`, `password`, `avatar`,`registration_date`",
                                " WHERE id = '" + this.assertString( this.$_GSC().optString("id") ) + "'"
                        )
                )
                ).optJSONObject(0)
        );

        String szRealPassword = new String( this.helper().cipher().simpleDecrypt( this.mPageData.optJSONObject("CurrentUserInfo").optString( "password" ) ), this.parent().getServerCharset() ) ;
        this.mPageData.optJSONObject("CurrentUserInfo").put( "password", szRealPassword );
    }
}