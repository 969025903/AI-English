package Predator.Wizard.Public.UserLogin;

import Pinecone.Framework.Util.JSON.JSONException;
import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedControl;
import Predator.System.Predator;
import Predator.Util.PredatorHelper;
import Predator.Util.PredatorTimeHelper;
import Predator.Wizard.Public.PersonWordBuild.PersonWordBuild;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.sql.SQLException;

public class UserLoginControl extends UserLogin implements JSONBasedControl {



    public UserLoginControl() {
    }

    public UserLoginControl(HostMatrix parentSystem) {
        super(parentSystem);
    }

    @Override
    public void defaultGenie() throws Exception{
        super.defaultGenie();

    }

    public void formCheck() throws SQLException, IOException , ServletException {
      JSONObject $S_POST = this.$_POST(true);
      String szPassword = $S_POST.optString("password");
      String szUsername = $S_POST.optString("username");
      String U_ID = this.helper().cipher().simpleEncrypt((szPassword + "&" + szUsername).getBytes(this.parent().getServerCharset() ));

      szPassword=this.helper().cipher().simpleEncrypt(( szPassword ).getBytes( this.parent().getServerCharset() ) ) ;
      if(this.mysql().countFromTable(String.format("SELECT COUNT(*) FROM %s WHERE username='%s'",this.tableName(Predator.TABLE_MUTUAL_USERS),szUsername))<=0){
          $_POST().put("waringText","没有该账号");
      }
      else {
          String szComparePassword = this.mysql().select(String.format("SELECT password FROM %s WHERE username=\'%s\'", this.tableName(Predator.TABLE_MUTUAL_USERS),szUsername)).getJSONObject(0).optString("password");
          if( szPassword.equals(szComparePassword) ){

              String szNickName = this.mysql().select(String.format("SELECT nickname FROM %s WHERE username=\'%s\'", this.tableName(Predator.TABLE_MUTUAL_USERS),szUsername)).getJSONObject(0).optString("nickname");

              this.setCookie("U_ID",U_ID,null);
              this.smartRedirect("PredatorIndex");
              this.stop();

          }
          else{
              $_POST().put("waringText","密码不正确");
          }
      }
    }

    public void userSignOut()throws IOException{

        this.setCookie("U_ID",null,0);
        this.smartRedirect("PredatorIndex");
        this.stop();

    }

    public void UserRegister()throws SQLException, JSONException,IOException{
        JSONObject $_SPOST = this.$_POST( true );
        JSONObject UserRegister_data = $_SPOST.yokedSharedFromKey( new String[]{ "username","nickname" } );

        if(this.mysql().countFromTable(String.format("SELECT COUNT(*) FROM %s WHERE username='%s'",this.tableName(Predator.TABLE_MUTUAL_USERS),UserRegister_data.optString("username")))>0){
            $_POST().put("waringText","已有该账号");
        }
        else if(this.mysql().countFromTable(String.format("SELECT COUNT(*) FROM %s WHERE nickname='%s'",this.tableName(Predator.TABLE_MUTUAL_USERS),UserRegister_data.optString("nickname")))>0){
            $_POST().put("waringText","已有该昵称");
        }
        else{
            UserRegister_data.put( "registration_date", PredatorTimeHelper.getSystemTime("yyyy-MM-dd"));
            UserRegister_data.put( "avatar",0);
            UserRegister_data.put("email","");
            UserRegister_data.put("authority",0);
            String szPassword = this.helper().cipher().simpleEncrypt( $_SPOST.optString( "first_password" ).getBytes( this.parent().getServerCharset() ) ) ;
            UserRegister_data.put("password",szPassword);
            boolean bRes = this.mysql().insertWithArray( Predator.TABLE_MUTUAL_USERS, UserRegister_data.getMap() ) > 0;
            this.checkResult( bRes, null, this.spawnActionQuerySpell() );
        }
    }
}
