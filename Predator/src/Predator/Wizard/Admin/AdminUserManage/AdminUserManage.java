package Predator.Wizard.Admin.AdminUserManage;

import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.Wizard;
import Predator.System.ConsciousPredatorSoul;

public class AdminUserManage extends ConsciousPredatorSoul implements Wizard {
    public AdminUserManage(){

    }

    public AdminUserManage( HostMatrix parentSystem ){
        super(parentSystem);
    }

    public String prototypeName(){
        return this.getClass().getSuperclass().getSimpleName();
    }

}