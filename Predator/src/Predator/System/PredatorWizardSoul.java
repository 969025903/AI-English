package Predator.System;

import Pinecone.Framework.Debug.Console;
import Pinecone.Framework.Debug.Debug;
import Pinecone.Framework.System.Prototype.Prototype;
import Pinecone.Framework.Util.JSON.JSONArray;
import Pinecone.Framework.Util.JSON.JSONException;
import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.Net.Illumination.*;
import Pinecone.Framework.Util.Net.Illumination.MultipartFile.MultipartFile;
import Pinecone.Framework.Util.Net.Illumination.http.HttpMethod;
import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedModel;
import Pinecone.Framework.Util.Net.Illumination.prototype.Wizard;
import Pinecone.Framework.Util.RDB.MySQL.MySQLExecutor;
import Pinecone.Pinecone;
import Predator.System.Prototype.JasperFertilizer;
import Predator.System.Prototype.JasperYokedSoul;
import Predator.System.Prototype.TraceableSoul;
import Predator.Util.PredatorHelper;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

/**
 * Like Javascript it is prototype chain.
 * system Mirror Class. (All frequently-used method and function can be clone. )
 * Extending this class will allow you invoke such $_GET, $_GPC, $_POST anywhere like PHP.
 */

public abstract class PredatorWizardSoul extends PredatorWizardArchetype implements JasperYokedSoul, TraceableSoul {
    protected enum Occupation {
        W_FREE, W_FORCE_FREE, W_ENCHANTER, W_FERTILIZER
    }

    protected InfrastructureRender    mInfrastructureRender         =  null  ;

    protected CurrencyTemplate        mCurrencyTemplate             =  null  ;

    protected WizardGeniesInvoker     mWizardGeniesInvoker          =  null  ;

    protected boolean                 mbGlobalFertilized            =  false ;

    protected PredatorHelper          mGlobalHelper                 =  null  ;



    /** Base **/
    @Override
    protected void init() {
        super.init();
        if( this instanceof JSONBasedModel){
            this.mbGlobalFertilized = this.prospectIsDefaultFertilizer();
        }
        this.mInfrastructureRender = new InfrastructureRender( this );
        this.mCurrencyTemplate     = new CurrencyTemplate( this );
        this.mWizardGeniesInvoker  = new WizardGeniesInvoker( this, this.parent() );
        this.mGlobalHelper         = new PredatorHelper( this.parent() );
    }

    @Override
    protected void appendDefaultPageDate(){
        super.appendDefaultPageDate();
        this.$_REQUEST().setAttribute( "Prototype", this );
    }

    public PredatorWizardSoul(){
        this.init();
    }

    public PredatorWizardSoul( HostMatrix parentSystem ){
        this.mParentMatrix = (Predator) parentSystem;
        this.init();
    }



    @Override
    public PredatorDispatcher getSystemDispatcher() {
        return this.parent().getSystemDispatcher();
    }

    public JSONObject $_GSC() {
        return this.getSystemDispatcher().$_GPC();
    }

    public JSONObject $_GET  ( boolean bSafe ) {
        return this.getSystemDispatcher().$_GET( bSafe );
    }

    public JSONObject $_POST ( boolean bSafe ) {
        return this.getSystemDispatcher().$_POST( bSafe );
    }

    public HttpMethod currentHttpMethod(){
        return this.parent().getSystemDispatcher().currentHttpMethod();
    }

    public Map<String, MultipartFile> $_FILES() {
        return this.parent().getSystemDispatcher().$_FILES();
    }

    public boolean isDebugMode() {
        return this.parent().getHostSystemConfig().optBoolean( "DebugMode" );
    }



    /** Template **/
    protected InfrastructureRender getInfrastructureRender(){
        return this.mInfrastructureRender;
    }

    protected CurrencyTemplate getCurrencyTemplate(){
        return this.mCurrencyTemplate;
    }

    protected PredatorHelper helper() {
        return this.mGlobalHelper;
    }



    /** RDB **/
    protected MySQLExecutor mysql(){
        return this.parent().mysql();
    }

    protected String tableName( String szShortTableName ) {
        return this.mysql().tableName( szShortTableName );
    }

    protected String assembleSimpleStackInfo ( StackTraceElement[] stackTraceElements, boolean bOnlySite ){
        StringBuilder s = new StringBuilder();
        String szSiteName = this.parent().getSitesConfig().optString("name");
        for ( StackTraceElement ele: stackTraceElements ) {
            String szClassName = ele.getClassName();
            if( (!bOnlySite && szClassName.contains( "Pinecone" )) || szClassName.contains( szSiteName ) ){
                s.append("   ").append(szClassName).append(".").append(ele.getMethodName()).append("(").append(ele.getFileName()).append(":").append(ele.getLineNumber()).append(") \n");
            }
        }
        return s.toString();
    }

    protected void handleSQLException( SQLException e ) {
        try {
            this.mParentMatrix.getSystemDispatcher().traceSystem500Error(
                    String.format(
                            "<h3>Caught SQLException During Runtime: </h3>" +
                            "<h3>What: %s</h3>"+
                            "<p>Contact: %s</p>",
                            this.isDebugMode() ? e.getMessage() : "Query Compromised.", Pinecone.CONTACT_INFO
                    )
            );
            System.err.println(
                    String.format(
                            "****************** SQLException Caught ******************\n" +
                                    "ERROR SQL: %s\n" +
                                    "     What: %s\n" +
                                    "SQL State: (%s , %d)\n"+
                                    "       At: %s\n" +
                            "*********************************************************\n",
                            this.mysql().getLastSQLSentence(),
                            e.getMessage(),
                            e.getSQLState(), e.getErrorCode(),
                            this.assembleSimpleStackInfo( e.getStackTrace(), false )
                    )
            );
        }
        catch (ServletException|IOException e1){
            e1.printStackTrace();
        }
    }

    protected void handleException( Exception e ) {
        if( e instanceof SQLException ){
            this.handleSQLException( (SQLException)e );
            return;
        }

        try{
            this.mParentMatrix.getSystemDispatcher().traceSystem500Error( Prototype.prototypeName(e) + ":" + e.getMessage() );
            e.printStackTrace();
        }
        catch (ServletException|IOException e1){
            e1.printStackTrace();
        }
    }



    /** Model **/
    public JSONObject getPageData(){
        return this.mPageData;
    }

    public String toJSONString(){
        return this.mPageData.toString();
    }

    public void appendDefaultAttribute(String key, Object value){
        this.$_REQUEST().setAttribute( key,value );
    }

    public String filter( String szSQL ){
        Map map = $_GSC().getMap();
        for(Object key : map.keySet()) {
            String szValue = ( String )map.get( key );
            if( !key.equals("do") && !key.equals("action") && !key.equals("control")){
                szSQL+=" AND "+ key + " = " + "\""+szValue+"\"";
            }
        }
        return szSQL;
    }



    /** Render **/
    public String fertilizedHF() throws ServletException, IOException{
        this.appendDefaultAttribute( "StaticHead", this.mInfrastructureRender.includeStaticHead( (Wizard) this ,this.getRealCookieName("U_ID") ));
        this.appendDefaultAttribute( "StaticFooter", this.mInfrastructureRender.includeFooter() );
        this.appendDefaultAttribute( "szPageData",this.mPageData.toString() );
        this.appendDefaultAttribute( "StaticPageEnd","</body></html>" );
        return this.jspTPLRender(this.prototypeName());
    }

    public String jspTPLRender() throws ServletException, IOException {
        return this.jspTPLRender(this.prototypeName());
    }

    public String jspTPLRender( String szJSPSimpleName ) throws ServletException, IOException {
        String szJSPFileName = ( (Wizard) this).getModularRole() + "/" + szJSPSimpleName + ".jsp";
        this.mParentMatrix.getSystemDispatcher().jspTPLRenderPage( szJSPFileName );
        return szJSPFileName;
    }



    /** Redirect And Helper **/
    protected void smartRedirectWithModelParameter( String szWizardName, String szFunModelName ) throws IOException {
        String szParameter = this.parent().getModelParameter() + "=" + szFunModelName;
        this.smartRedirect( szWizardName, szParameter );
    }

    protected void smartRedirect( String szWizardName, String szParameter ) throws IOException {
        String szRealURL = "?" + this.parent().getWizardParameter() + "=" + szWizardName;
        if( szParameter != null && !szParameter.isEmpty() ){
            szRealURL += "&" + szParameter;
        }
        System.out.println(szRealURL);
        this.redirect( szRealURL );
    }

    protected void smartRedirect( String szWizardName ) throws IOException {
        this.smartRedirect( szWizardName, null );
    }

    protected void alert( String szTitle, int nState, Object url ) throws IOException {
        this.redirect(
                String.format( "?%s=%s&title=%s&state=%d&url=%s",
                        this.parent().getWizardParameter(),
                        this.parent().getHostSystemConfig().getString("AlertPage"),
                        URLEncoder.encode( szTitle, this.parent().getServerCharset() ), nState,
                        URLEncoder.encode( url.toString(), this.parent().getServerCharset() )
                )
        );
        this.stop();
    }

    protected void checkResult( boolean bResult, String szTitle, Object url ) throws IOException {
        JSONObject proto = this.getWizardProto(this.parent().getHostSystemConfig().getString("AlertPage"));

        if( bResult ){
            if( szTitle == null ) {
                szTitle = proto.optString("defaultSuccessTitle");
            }
            this.alert( szTitle, 1 , url );
        }
        else {
            if( szTitle == null ) {
                szTitle = proto.optString("defaultFailTitle");
            }
            this.alert( szTitle, 0, url );
        }
    }

    protected void checkResult( boolean bResult, String szTitle ) throws IOException {
        this.checkResult( bResult, szTitle, -1 );
    }

    protected void checkResult( boolean bResult ) throws IOException {
        this.checkResult( bResult, null, -1 );
    }

    protected String assertString( String s ) throws IOException, ServletException {
        if( !PredatorHelper.assertString( s )  ){
            this.getSystemDispatcher().traceSystem500Error(
                    "<h3>Wizard was summoned with unexpected state.</h3>" +
                            "<h3>Please contact administrator[" + Pinecone.CONTACT_INFO + "] for more information.</h3>"
            );
            this.stop();
        }
        return s;
    }

    protected JSONArray assertSelectResult( JSONArray object ) throws IOException, ServletException {
        if( object == null || object.isEmpty() ){
            this.getSystemDispatcher().traceSystem500Error(
                    "<h3>Wizard was summoned with unexpected state.</h3>" +
                            "<h3>Please contact administrator[" + Pinecone.CONTACT_INFO + "] for more information.</h3>"
            );
            this.stop();
        }
        return object;
    }

    protected String getCookieValue(String szName){
        Cookie[] cookies = $_REQUEST().getCookies();
        for(Cookie c:cookies){
            if(c.getName().equals(szName)){
                return c.getValue();
            }
        }
        return null;
    }

    protected void setCookie(String szName,String szValue,Integer Age){
        Cookie cookie = new Cookie( szName,szValue );
        if(Age != null){
            cookie.setMaxAge(Age);
        }
        $_RESPONSE().addCookie( cookie );
    }

    protected String getRealCookieName(String szName){
        String szUID = this.getCookieValue(szName);
        if(szUID == null){
            return null;
        }
        szUID = new String(this.helper().cipher().simpleDecrypt(szUID));
        String szUIDs[] = szUID.split("&");
       if(szUIDs.length!=2){
           return null;
       }
       else{
           if(this.mysql().countFromTable(String.format("SELECT COUNT(*) FROM %s WHERE username='%s'",this.tableName(Predator.TABLE_MUTUAL_USERS),szUIDs[1]))<=0){
              return null;
           }
           else{
               try {
                   return this.mysql().select(String.format("SELECT nickname FROM %s WHERE username='%s'",this.tableName(Predator.TABLE_MUTUAL_USERS),szUIDs[1])).getJSONObject(0).getString("nickname");
               }catch (SQLException e){
                   e.printStackTrace();
               }
            return null;
           }
       }
    }



    /** QuerySpell **/
    public String spawnActionQuerySpell(){
        return this.spawnActionQuerySpell( null );
    }

    public String spawnControlQuerySpell(){
        return this.spawnControlQuerySpell( null );
    }

    public String spawnKeyValuesQueryPartSpell( Map keyValuesJsonMap ){
        StringBuilder partQueryString = new StringBuilder();

        Iterator iterator = keyValuesJsonMap.entrySet().iterator();
        Map.Entry entry;
        while ( iterator.hasNext() ) {
            entry = (Map.Entry) iterator.next();
            partQueryString.append( entry.getKey() ).append( "=" ).append( (String) entry.getValue() );
            if( iterator.hasNext() ){
                partQueryString.append( "&" );
            }
        }
        return partQueryString.toString();
    }

    public String spawnActionQuerySpell( String szActionFunctionName, Map keyValuesJsonMap ){
        return this.spawnActionQuerySpell( szActionFunctionName ) + "&" + this.spawnKeyValuesQueryPartSpell( keyValuesJsonMap );
    }

    public String spawnControlQuerySpell( String szControlFunctionName, Map keyValuesJsonMap ){
        return this.spawnControlQuerySpell( szControlFunctionName ) + "&" + this.spawnKeyValuesQueryPartSpell( keyValuesJsonMap );
    }



    /** JasperYokedSoul **/
    public Object summonNormalGenieByCallHisName( String szGenieName ) throws NaughtyGenieInvokedException {
        return this.mWizardGeniesInvoker.invokeNormalGenieByCallHisName( szGenieName );
    }

    public void setFertilizerRole( boolean bRole ) {
        this.mbGlobalFertilized = bRole;
    }

    public boolean isJasperYoked() {
        return this.mbGlobalFertilized;
    }

    public boolean prospectIsDefaultFertilizer() {
        Annotation[] annotations = this.getClass().getAnnotations();
        for( Annotation annotation : annotations ){
            if( annotation instanceof JasperFertilizer){
                return ((JasperFertilizer) annotation).value();
            }
        }
        return false;
    }



    /** Trace **/
    protected Console console = Debug.console();

    public Console trace( Object data, Object ...more ) {
        return Debug.trace( data, more );
    }

    public void spit ( Object data, Object ...more ) {
        StringBuilder debugInfo = new StringBuilder();
        debugInfo.append( "<h2>Bean Nuts Pinecone Tracer Information</h2>\n<h2>** SPIT INFORMATION **</h2>" );
        debugInfo.append("<p>").append(data).append("</p>");
        if( more != null ){
            for( Object row : more ){
                debugInfo.append("<p>").append(row).append("</p>");
            }
        }
        debugInfo.append( "\n<h2>** SPIT INFORMATION END **</h2>\n" );
        debugInfo.append( "<h3 style='color:red'>Waring: If you see this message not during debug scenarios, please contact author: " + Pinecone.CONTACT_INFO + "</h3>" );

        try {
            this.getSystemDispatcher().traceSystemErrorMsg( " Bean Nuts Pinecone Tracer Information</h1>", debugInfo.toString() );
        }
        catch ( IOException | ServletException e ){
            this.out().print( debugInfo.toString() );
        }
    }

    public void vomit( Object data, Object ...more ) {
        this.spit( data, more );
        this.stop();
    }
}
