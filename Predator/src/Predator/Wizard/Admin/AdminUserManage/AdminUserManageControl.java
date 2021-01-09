package Predator.Wizard.Admin.AdminUserManage;

import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedControl;
import Predator.System.Predator;
import Predator.Util.PredatorControlHelper;

import java.io.IOException;
import java.sql.SQLException;

public class AdminUserManageControl extends AdminUserManage implements JSONBasedControl {
    public AdminUserManageControl() {
    }

    public AdminUserManageControl(HostMatrix parentSystem) {
        super(parentSystem);
    }

    @Override
    public void defaultGenie() throws Exception{
        super.defaultGenie();
    }

    public void appendNewUser() throws SQLException, IOException  {
        JSONObject $_SPOST = this.$_POST(true);

        String szNewUser = $_SPOST.optString("username");
        if( this.mysql().countFromTable(
                String.format(
                        "SELECT COUNT(*) FROM %s WHERE `username` = '%s'" ,
                        this.tableName(Predator.TABLE_MUTUAL_USERS), szNewUser )
        ) > 0 ){
            this.alert( "User '"  + szNewUser +"' has been existed.", 0, -1 );
        }

        String szRealPassword = this.helper().cipher().simpleEncrypt( $_SPOST.optString( "password" ).getBytes( this.parent().getServerCharset() ) ) ;
        $_SPOST.put( "password", szRealPassword );
        boolean bRes = this.mysql().insertWithArray( Predator.TABLE_MUTUAL_USERS, $_SPOST.getMap() ) > 0;
        this.checkResult( bRes, null, this.spawnActionQuerySpell() );
    }

    public void modifyOneUser() throws IOException, SQLException {
        JSONObject $_SPOST = this.$_POST( true );

        JSONObject condition  = $_SPOST.yokedSharedFromKey( "id" );
        String szRealPassword = this.helper().cipher().simpleEncrypt( $_SPOST.optString( "password" ).getBytes( this.parent().getServerCharset() ) ) ;
        $_SPOST.put( "password", szRealPassword );
        boolean bRes = this.mysql().updateWithArray( Predator.TABLE_MUTUAL_USERS , $_SPOST.getMap(), condition.getMap() ) > 0;
        this.checkResult( bRes );
    }

    public void deleteOneUser() throws IOException,SQLException {
        this.checkResult(
                this.deleteOneUser( null )
        );
    }

    public boolean deleteOneUser( String szID ) throws IOException, SQLException {
        return this.helper().control().simpleDeleteSingleObject( "id", szID, Predator.TABLE_MUTUAL_USERS );
    }

    public void userMassDelete() throws IOException, SQLException {
        PredatorControlHelper.commonMassDelete( this, "id[]", "deleteOneUser" );
    }
}