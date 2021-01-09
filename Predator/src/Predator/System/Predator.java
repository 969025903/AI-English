package Predator.System;

import Pinecone.Framework.Debug.Debug;
import Pinecone.Framework.System.util.ReflectionUtils;
import Pinecone.Framework.System.util.StringUtils;
import Pinecone.Framework.Util.JSON.JSONArray;
import Pinecone.Framework.Util.JSON.JSONException;
import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.JSON.prototype.JSONType;
import Pinecone.Framework.Util.Net.Illumination.IlluminationServlet;
import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.IlluminationSystemSpawner;
import Pinecone.Framework.Util.Net.Illumination.SystemDispatcher;
import Pinecone.Framework.Util.RDB.MySQL.MySQLExecutor;
import Pinecone.Pinecone;
import Predator.Util.PredatorHelper;

import javax.servlet.ServletException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Predator extends HostMatrix {
    public static final String TABLE_NEWS_PAGE             = "news_page";
    public static final String TABLE_INDEX_ASSETS          = "index_assets";
    public static final String TABLE_MUTUAL_WORDS          = "mutual_words";
    public static final String TABLE_MUTUAL_USERS          = "mutual_users";
    public static final String TABLE_EN2CH_DICTIONARY      = "dict_en2cn";
    public static final String TABLE_MAIN_WORD_LEVELS      = "mainword_levels";
    public static final String TABLE_MUTUAL_GLOSSARY       = "mutual_glossary";
    public static final String TABLE_MUTUAL_WORD_ROOTS     = "mutual_wordroots";
    public static final String TABLE_USER_WORDS            = "user_words";
    public static final String TABLE_GLOSSARY_TYPE         = "glossary_type";
    public static final String TABLE_USER_PLAN             = "user_plan";
    public static final String TABLE_MUTUAL_DICTIONARY     = "mutual_dictionary";



    private JSONObject mSitesConfig           = null;

    private String     mHostConfigPath        = null;

    private String     mszWizardPackageName;

    private String     mszSystemDispatcher;

    private boolean    mbStaticRunMode = false;

    private PredatorMenu mMenuSpawner = null;

    protected JSONObject mRDBConfig              = null;

    private MySQLExecutor mysql = null;

    public Predator( String szResourcesPath, String szConfigFileName ) throws IOException {
        super( szResourcesPath, szConfigFileName );
    }

    public Predator( String szResourcesPath, String szConfigFileName, boolean bStaticRunMode ) throws IOException {
        super( szResourcesPath, szConfigFileName );
        this.setStaticRunMode( bStaticRunMode );
    }

    public Predator( IlluminationServlet servlet ) throws IOException {
        super( servlet );
    }




    public MySQLExecutor mysql() {
        return this.mysql;
    }

    private void initMySQL(){
        this.mRDBConfig           = this.getHostSystemConfig().getJSONObject("RDB");
        try{
            this.mysql = new MySQLExecutor(
                    this.getRDBConfig().getString("Location"),
                    this.getRDBConfig().getString("Username"),
                    this.getRDBConfig().getString("Password")
            );
        }
        catch ( SQLException e ){
            e.printStackTrace();
        }

        this.mysql.setTablePrefix( this.getRDBConfig().getString("TablePrefix") );
    }

    public PredatorMenu menu(){ return this.mMenuSpawner; }




    public String getMainMenuConfig(){
        return this.getHostSystemConfig().getString("MainMenu");
    }

    public String getFullMenuPath(){
        return this.getResourcesPath() + this.getMainMenuConfig();
    }

    public String getHostConfigPath() {
        if( this.mHostConfigPath == null ){
            this.mHostConfigPath = this.getResourcesPath() + this.getHosts().getString( this.prototypeName() );
        }
        return this.mHostConfigPath;
    }

    public String getWizardPackageName(){
        return this.mszWizardPackageName;
    }

    public String get16BitInnerPassword() {
        return this.getHostSystemConfig().getString("16BitInnerPassword");
    }

    public JSONObject getSitesConfig() {
        return this.mSitesConfig;
    }

    public JSONObject getHostSystemConfig(){
        return this.mSitesConfig.getJSONObject( "System" );
    }

    public JSONObject getWizardsConfig(){
        return this.mSitesConfig.getJSONObject( "WizardsList" );
    }

    public JSONObject getPluginsConfig(){
        return this.mSitesConfig.getJSONObject( "Plugins" );
    }

    public JSONObject getPredatorUploadConfig(){
        return this.mSitesConfig.getJSONObject( "UploadConfig" );
    }

    public JSONObject getRDBConfig(){
        return this.mRDBConfig;
    }

    public JSONObject getWizardProto( String prototypeName ) {
        return this.getWizardsConfig().getJSONObject( prototypeName );
    }

    public String     getSystemDispatcherConfig(){
        return this.mszSystemDispatcher;
    }

    @Override
    public PredatorDispatcher getSystemDispatcher() {
        return (PredatorDispatcher)this.mSystemDispatcher;
    }

    public void setStaticRunMode ( boolean bState ){
        this.mbStaticRunMode = bState;
    }


    @Override
    public String getWizardSummonerConfig() { return this.getHostSystemConfig().getString("WizardSummoner"); }

    @Override
    public void init() throws ServletException {
        try {
            this.mSitesConfig         = new JSONObject( this.readFileContentAll( this.getHostConfigPath() ) );
            this.mszWizardPackageName = this.getHostSystemConfig().getString( "WizardPackageName");
            this.mszSystemDispatcher  = this.getHostSystemConfig().getString( "SystemDispatcher" );
            if ( !this.mbStaticRunMode && this.mszSystemDispatcher != null && !this.mszSystemDispatcher.isEmpty() ){
                SystemDispatcher systemDispatcher = IlluminationSystemSpawner.spawnDispatcher( this.mszSystemDispatcher, this  );
                if( systemDispatcher != null ){
                    this.setSystemDispatcher( systemDispatcher );
                }
            }
            this.initMySQL();
        }
        catch ( IOException | JSONException e ){
            e.printStackTrace();
        }

        this.mMenuSpawner = new PredatorMenu( this );
        this.mMenuSpawner.loadMenu();

        System.err.println( "----------------------------------------------" );
        System.err.println( "Bean Nuts Hazelnut Predator Has Been Initiated" );
        System.err.println( "Time: " + ( new SimpleDateFormat("yyyy-MM-dd HH：mm：ss") ).format(new Date()) );
        System.err.println( "----------------------------------------------" );
    }

    public String prototypeName() {
        return this.getClass().getSimpleName();
    }


    public static void main(String[] args) throws Exception {
        //long startTime = System.currentTimeMillis();//Nop
//        Pinecone.initialize();
//
//        Predator predator = new Predator( "E:/MyFiles/CodeScript/Project/Hazelnut/Predator/Predator/src/Resources/", "config.json5", true );
//        predator.init();
//        Debug.trace( predator.mysql().select( "SELECT * FROM " + predator.mysql().tableName("test") ) );
//
//
//
//        System.out.println("\nRuntime : " + (System.currentTimeMillis() - startTime) + "ms");
    }

}
