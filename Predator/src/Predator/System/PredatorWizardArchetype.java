package Predator.System;

import Pinecone.Framework.Util.JSON.JSONArray;
import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.Net.Illumination.WizardSoulFerryman;

public abstract class PredatorWizardArchetype extends WizardSoulFerryman {
    public Predator parent(){
        return (Predator)(this.mParentMatrix instanceof Predator ? this.mParentMatrix : null);
    }

    public JSONObject getWizardProto( String prototypeName ) {
        return this.parent().getWizardsConfig().getJSONObject( prototypeName );
    }

    /** Role **/
    public int queryRoleIndex(String szRole ){
        return PredatorModularRoleInterpreter.interpret( szRole );
    }


    /** Wizard Archetype **/
    public String getTitle(){
        return this.getModularConfig().getString("title");
    }

    public JSONObject getPrivateTables(){
        return this.getModularConfig().getJSONObject("tables");
    }

    public JSONObject getModularConfig(){
        return this.getWizardProto( this.prototypeName() );
    }

    public String getModularRole(){
        return this.getModularConfig().getString("role");
    }

    public int getModularRoleIndex(){
        return this.queryRoleIndex( this.getModularRole() );
    }

    public JSONArray getMyNaughtyGenies() {
        return this.getModularConfig().optJSONArray("myNaughtyGenies");
    }
}
