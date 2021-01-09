package Pinecone.Framework.Util.Net.Illumination;

import Pinecone.Framework.System.Prototype.Overrideable;
import Pinecone.Framework.Unit.LinkedMultiValueMap;
import Pinecone.Framework.Util.JSON.JSONException;
import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.Net.Illumination.MultipartFile.MultipartFile;
import Pinecone.Framework.Util.Net.Illumination.MultipartFile.commons.CommonsFileUploadDispatcher;
import Pinecone.Framework.Util.Net.Illumination.http.CommonHttpEntityParser;
import Pinecone.Framework.Util.Net.Illumination.http.HttpEntityParser;
import Pinecone.Framework.Util.Net.Illumination.http.HttpMethod;
import Pinecone.Framework.Util.Net.Illumination.prototype.DistributableRootDispatcher;
import Pinecone.Pinecone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class SystemDispatcher implements DistributableRootDispatcher {
    protected WizardSummoner      mWizardSummoner;

    protected HttpServletRequest  mCurrentRequest;

    protected HttpServletResponse mCurrentResponse;

    protected HostMatrix mMatrix;


    protected JSONObject mGlobalParameterContainer  = null; /** GPC  **/

    protected JSONObject mGETMapContainer           = null; /** GET  **/

    protected JSONObject mPOSTMapContainer          = null; /** POST **/

    protected PrintWriter mGlobalOutPrinter         = null; /** out  **/

    protected HttpMethod mCurrentHttpMethod         = HttpMethod.GET;

    protected CommonsFileUploadDispatcher mUploadDispatcher = null ;

    protected Map<String, MultipartFile > mFileMapContainer = new LinkedMultiValueMap() ;

    protected HttpEntityParser mHttpEntityParser            = null ;




    protected String mszWizardCommand   = null;

    protected String mszModelCommand    = null;

    protected String mszControlCommand  = null;




    protected void initDispatcher() {
        this.mWizardSummoner = IlluminationSystemSpawner.spawnWizardSummoner( this.mMatrix.getWizardSummonerConfig(), this.mMatrix );
        if( this.getHostMatrix().isAutoInterceptUploadFile() ){
            this.mUploadDispatcher = new CommonsFileUploadDispatcher( this.getHostMatrix() );
        }
        this.mHttpEntityParser = new CommonHttpEntityParser( this.mMatrix.getServerCharset() );
    }

    public SystemDispatcher(){
    }

    public SystemDispatcher( HostMatrix system ){
        this.mMatrix   = system;
        this.initDispatcher();
    }

    public SystemDispatcher( HttpServletRequest request, HttpServletResponse response, HostMatrix system ){
        this.mCurrentRequest  = request;
        this.mCurrentResponse = response;
        this.mMatrix   = system;
        this.initDispatcher();
    }



    public HostMatrix getHostMatrix(){
        return this.mMatrix;
    }

    public HttpEntityParser getHttpEntityParser(){
        return this.mHttpEntityParser;
    }

    public JSONObject $_GPC(){
        return this.mGlobalParameterContainer;
    }

    public JSONObject $_GET(){
        return this.mGETMapContainer;
    }

    public JSONObject $_POST(){
        return this.mPOSTMapContainer;
    }

    public PrintWriter out(){
        return this.mGlobalOutPrinter;
    }

    public HttpServletRequest $_REQUEST(){
        return this.mCurrentRequest;
    }

    public HttpServletResponse $_RESPONSE(){
        return this.mCurrentResponse;
    }

    private void checkAutoInterceptUploadFile(){
        if( !this.getHostMatrix().isAutoInterceptUploadFile() ){
            throw new IllegalStateException( "IllegalStateException: Auto intercept upload file is disable." );
        }
    }

    public CommonsFileUploadDispatcher getFileUploadDispatcher() {
        this.checkAutoInterceptUploadFile();
        return this.mUploadDispatcher;
    }

    public Map<String, MultipartFile> $_FILES() {
        this.checkAutoInterceptUploadFile();
        return this.mFileMapContainer;
    }




    public HttpMethod currentHttpMethod(){
        return this.mCurrentHttpMethod;
    }

    public WizardSummoner getWizardSummoner() {
        return this.mWizardSummoner;
    }



    public String getWizardCommand() {
        return this.mszWizardCommand;
    }

    public String getModelCommand() {
        return this.mszModelCommand;
    }

    public String getControlCommand() {
        return this.mszControlCommand;
    }



    @Overrideable
    public void afterCommandChanged(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        response.setCharacterEncoding( this.mMatrix.getServerCharset() );
        this.mGlobalOutPrinter = response.getWriter();
        this.mCurrentRequest = request;
        this.mCurrentResponse = response;
    }

    /** Http Method Handler **/
    public void handleGet() {
        this.mCurrentHttpMethod     = HttpMethod.GET;
        this.mGETMapContainer       = this.mHttpEntityParser.parseQueryString( this.mCurrentRequest.getQueryString(), false );
        this.mPOSTMapContainer      = new JSONObject();
        this.mGlobalParameterContainer = this.mHttpEntityParser.requestMapJsonify( this.mCurrentRequest, false );
    }

    public void handlePost() {
        this.mCurrentHttpMethod     = HttpMethod.POST;
        this.mGETMapContainer       = this.mHttpEntityParser.parseQueryString( this.mCurrentRequest.getQueryString(), false );
        this.mPOSTMapContainer      = this.mHttpEntityParser.siftPostFromParameterMap( this.mCurrentRequest, false );
        this.mGlobalParameterContainer = this.mHttpEntityParser.requestMapJsonify( this.mCurrentRequest,false );

        if( this.getHostMatrix().isAutoInterceptUploadFile() && this.mUploadDispatcher != null ){
            this.mUploadDispatcher.interceptMultipartFiles();
        }
    }

    public void handleHead() {
        this.mCurrentHttpMethod = HttpMethod.HEAD;
    }

    public void handleOptions() {
        this.mCurrentHttpMethod = HttpMethod.OPTIONS;
    }

    public void handlePut(){
        this.mCurrentHttpMethod = HttpMethod.PUT;
    }

    public void handlePatch(){
        this.mCurrentHttpMethod = HttpMethod.PATCH;
    }

    public void handleDelete(){
        this.mCurrentHttpMethod = HttpMethod.DELETE;
    }

    public void handleTrace(){
        this.mCurrentHttpMethod = HttpMethod.TRACE;
    }


    @Overrideable
    public void stop() throws RuntimeException {
        throw new TerminateSessionException("This session or sequence has been terminated.");
    }

    public void setHostMatrix (HostMatrix matrix){
        this.mMatrix = matrix;
    }

    public void jspRenderPage( String szDispatcherPath ) throws IOException, ServletException {
        this.mCurrentRequest.getRequestDispatcher( szDispatcherPath ).forward(this.mCurrentRequest, this.mCurrentResponse);
    }

    public void jspTPLRenderPage( String szTemplatePath ) throws IOException, ServletException {
        this.mCurrentRequest.getRequestDispatcher( this.mMatrix.getRealTemplatePath() + szTemplatePath ).forward(this.mCurrentRequest, this.mCurrentResponse);
    }




    @Overrideable
    public void traceSystemErrorMsg( String szTitle, String szErrorMsg ) throws IOException, ServletException {
        this.mCurrentRequest.setAttribute("pineVersion", Pinecone.VERSION);
        this.mCurrentRequest.setAttribute("pineReleaseDate", Pinecone.RELEASE_DATE);
        this.mCurrentRequest.setAttribute("javaVersion", System.getProperty("java.version"));
        this.mCurrentRequest.setAttribute("pageTitle", szTitle);
        this.mCurrentRequest.setAttribute("errorMsg", szErrorMsg);

        this.jspRenderPage( this.mMatrix.getDefaultErrorPagePath() );
    }

    @Overrideable
    public void traceSystemErrorMsg( int nErrorID, String szTitle, String szErrorMsg ) throws IOException, ServletException {
        this.mCurrentResponse.setStatus(nErrorID);
        this.traceSystemErrorMsg( szTitle,szErrorMsg );
    }

    @Overrideable
    public void traceSystem404Error() throws IOException, ServletException {
        this.traceSystem404Error("<h2>You are trying to access an undefined file !</h2>" );
    }

    @Overrideable
    public void traceSystem404Error( String szErrorMsg ) throws IOException, ServletException {
        this.traceSystemErrorMsg( 404,"SERVER 404 ERROR",szErrorMsg );
    }

    @Overrideable
    public void traceSystem500Error( String szErrorMsg ) throws IOException, ServletException {
        this.traceSystemErrorMsg( 500,"SERVER 500 ERROR",szErrorMsg );
    }

    @Overrideable
    public void echoIndexPage() throws IOException, ServletException {
        this.traceSystemErrorMsg( "WELCOME TO PINECONE JAVA" ,"<h1>Everything should be fine.</h1>" );
    }




    @Overrideable
    public void parameterDispatchBus() throws ServletException, IOException {
        this.requestReceived();
        this.dispatch();
    }

    @Overrideable
    public void dispatch() throws IOException, ServletException {
        this.invokeAutoSummonSequence();
        this.invokeMCSequence();
        this.invokeViewRenderSequence();
        this.afterMVCSequence();
    }

    @Overrideable
    public void requestReceived() throws ServletException, IOException {
        try {
            this.mszWizardCommand = this.mGETMapContainer.getString(this.mMatrix.getWizardParameter());
        } catch (JSONException e){ this.mszWizardCommand = ""; }
        try {
            this.mszModelCommand = this.mGETMapContainer.getString(this.mMatrix.getModelParameter());
        } catch (JSONException e){ this.mszModelCommand = ""; }
        try {
            this.mszControlCommand = this.mGETMapContainer.getString(this.mMatrix.getControlParameter());
        }
        catch (JSONException e){ this.mszControlCommand = ""; }
    }

    @Overrideable
    public void invokeAutoSummonSequence() throws ServletException, IOException {
        switch ( this.mszWizardCommand ){
            case "":{
                this.echoIndexPage();
                break;
            }
            default:{
                this.mWizardSummoner.summonAndExecute( this.mszWizardCommand );
                break;
            }
        }
    }

    @Overrideable
    public void invokeMCSequence() throws ServletException, IOException {
    }

    @Overrideable
    public void invokeViewRenderSequence() throws ServletException, IOException {

    }

    @Overrideable
    public void afterMVCSequence() throws ServletException, IOException {

    }


}
