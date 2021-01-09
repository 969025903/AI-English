package Pinecone.Framework.Util.Net.Illumination;

import Pinecone.Framework.System.Prototype.Overrideable;
import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.Net.Illumination.MultipartFile.MultipartFile;
import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedModel;
import Pinecone.Framework.Util.Net.Illumination.prototype.ModelEnchanter;
import Pinecone.Framework.Util.Net.Illumination.prototype.Wizard;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.Map;

public abstract class WizardSoulFerryman extends WizardArchetype {
    protected JSONObject  mPageData                     =  null  ;

    protected boolean     mbGlobalEnchanter             =  false ;


    @Overrideable
    protected void init() {
        this.mPageData = new JSONObject();
        if( this instanceof JSONBasedModel){
            this.mbGlobalEnchanter = this.prospectIsDefaultEnchanter();
        }
        this.appendDefaultPageDate();
    }

    @Overrideable
    protected void appendDefaultPageDate(){
        this.mPageData.put( "PrototypeName", this.prototypeName() );
        this.mPageData.put( "szMainTitle", ((Wizard)this).getTitle() );
        this.mPageData.put( "szWizardRole", ((Wizard)this).getModularRole() );
    }

    public HostMatrix parent(){
        return this.mParentMatrix;
    }

    @Overrideable
    public SystemDispatcher getSystemDispatcher() {
        return this.parent().getSystemDispatcher();
    }

    public JSONObject $_GPC(){
        return this.parent().getSystemDispatcher().$_GPC();
    }

    public JSONObject $_GET(){
        return this.parent().getSystemDispatcher().$_GET();
    }

    public JSONObject $_POST(){
        return this.parent().getSystemDispatcher().$_POST();
    }

    public PrintWriter out(){
        return this.parent().getSystemDispatcher().out();
    }

    public HttpServletRequest $_REQUEST(){
        return this.parent().getSystemDispatcher().$_REQUEST();
    }

    public HttpServletResponse $_RESPONSE(){
        return this.parent().getSystemDispatcher().$_RESPONSE();
    }


    public Map<String, MultipartFile> $_FILES() {
        throw new IllegalStateException("Notice: $_FILES() is abstract.");
    }




    @Overrideable
    public void beforeDispatch() throws IOException, ServletException {}

    @Overrideable
    public void afterDispatch() throws IOException, ServletException {}

    @Overrideable
    public void stop() throws RuntimeException {
        this.parent().getSystemDispatcher().stop();
    }


    @Overrideable
    public String getWizardCommand() {
        return this.parent().getSystemDispatcher().getWizardCommand();
    }

    @Overrideable
    public String getModelCommand() {
        return this.parent().getSystemDispatcher().getModelCommand();
    }

    @Overrideable
    public String getControlCommand() {
        return this.parent().getSystemDispatcher().getControlCommand();
    }

    @Overrideable
    public void redirect( String szURL ) throws IOException {
        this.$_RESPONSE().sendRedirect( szURL );
    }


    @Overrideable
    public String spawnActionQuerySpell( String szActionFunctionName ){
        String szQueryString = "?" + this.parent().getWizardParameter() + "=" + this.prototypeName();
        if( szActionFunctionName != null && !szActionFunctionName.isEmpty() ){
            return szQueryString + "&" + this.parent().getModelParameter() + "=" + szActionFunctionName;
        }
        return szQueryString;
    }

    @Overrideable
    public String spawnControlQuerySpell( String szControlFunctionName ) {
        String szQueryString = "?" + this.parent().getWizardParameter() + "=" + this.prototypeName();
        if( szControlFunctionName != null && !szControlFunctionName.isEmpty() ){
            return szQueryString + "&" + this.parent().getControlParameter() + "=" + szControlFunctionName;
        }
        return szQueryString;
    }

    @Overrideable
    public Object summonNormalGenieByCallHisName(String szGenieName) throws NaughtyGenieInvokedException {
        throw new IllegalStateException("Notice: summonNormalGenieByCallHisName() is abstract.");
    }




    @Overrideable
    public boolean prospectIsDefaultEnchanter() {
        Annotation[] annotations = this.getClass().getAnnotations();
        for( Annotation annotation : annotations ){
            if( annotation instanceof ModelEnchanter ){
                return ((ModelEnchanter) annotation).value();
            }
        }
        return false;
    }

    @Overrideable
    public void setEnchanterRole( boolean bRole ){
        this.mbGlobalEnchanter = bRole;
    }

    @Overrideable
    public boolean isEnchanter() {
        return this.mbGlobalEnchanter;
    }

    @Overrideable
    public void render() throws ServletException, IOException {
        if( this instanceof JSONBasedModel && this.mbGlobalEnchanter ){
            this.out().print( ((JSONBasedModel)this).toJSONString() );
        }
    }
}
