package Predator.Wizard.System.SystemCoven;

import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedModel;
import Pinecone.Framework.Util.Net.Illumination.prototype.ModelEnchanter;

import java.sql.SQLException;

@ModelEnchanter
public class SystemCovenModel extends SystemCoven implements JSONBasedModel {
    public SystemCovenModel() {
    }

    public SystemCovenModel( HostMatrix parentSystem ){
        super(parentSystem);
    }

    @Override
    protected void appendDefaultPageDate(){

    }

    @Override
    @ModelEnchanter(false)
    public void defaultGenie() throws Exception {
        super.defaultGenie();
        this.out().print( "undefined" );
    }

    public void getMainWordLevels() throws SQLException {
        this.mPageData = this.helper().model().fetchDefaultMainWordLevelsMap();
    }

    public void getGlossaryType() throws SQLException {
        this.mPageData = this.helper().model().fetchDefaultGlossaryTypesMap();
    }

}
