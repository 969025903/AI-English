package Pinecone.Framework.Util.Net.Illumination.prototype;

import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.Net.Illumination.MultipartFile.MultipartFile;
import Pinecone.Framework.Util.Net.Illumination.NaughtyGenieInvokedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public interface WizardSoul {
    JSONObject $_GPC();

    JSONObject $_GET();

    JSONObject $_POST();

    PrintWriter out();

    HttpServletRequest $_REQUEST();

    HttpServletResponse $_RESPONSE();

    Map<String, MultipartFile> $_FILES();

    void redirect( String szURL ) throws IOException;

    String spawnActionQuerySpell( String szActionFunctionName ) ;

    String spawnControlQuerySpell( String szControlFunctionName ) ;

    Object summonNormalGenieByCallHisName( String szGenieName ) throws NaughtyGenieInvokedException;

    String getWizardCommand();

    String getModelCommand();

    String getControlCommand();

    String prototypeName();



    /** Summoner **/
    void stop() throws RuntimeException;

    boolean prospectIsDefaultEnchanter();

    void setEnchanterRole( boolean bRole );

    boolean isEnchanter();
}
