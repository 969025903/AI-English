package Predator.Wizard.Admin.AdminMutualWordDepositor;

import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.Wizard;
import Predator.System.ConsciousPredatorSoul;

public class AdminMutualWordDepositor extends ConsciousPredatorSoul implements Wizard {
    public AdminMutualWordDepositor(){

    }

    public AdminMutualWordDepositor(HostMatrix parentSystem ){
        super(parentSystem);
    }

    public String prototypeName(){
        return this.getClass().getSuperclass().getSimpleName();
    }

}
