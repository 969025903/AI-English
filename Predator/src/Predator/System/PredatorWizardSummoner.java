package Predator.System;

import Pinecone.Framework.Util.JSON.JSONException;
import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.Net.Illumination.WizardSummoner;
import Pinecone.Framework.Util.Net.Illumination.prototype.QueryStringBasedMVCMatrix;

public class PredatorWizardSummoner extends WizardSummoner {
    public PredatorWizardSummoner( QueryStringBasedMVCMatrix hMatrix ) {
        super(hMatrix);
    }

    public String getWizardRoleName( String szNickName ){
        String szRole = "Public";
        JSONObject proto = null;

        try {
            proto = ((Predator)this.mParentMatrix).getWizardProto( szNickName );
            szRole = proto.getString("role");
        }
        catch (JSONException e1){
            return szRole;
        }

        return szRole;
    }

    @Override
    public String spawnNamespace( String szNickName ){
        return this.mParentMatrix.getWizardPackageName() + "." + this.getWizardRoleName(szNickName) + "." + szNickName;
    }
}
