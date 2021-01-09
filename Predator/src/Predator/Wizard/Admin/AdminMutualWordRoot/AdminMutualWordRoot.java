package Predator.Wizard.Admin.AdminMutualWordRoot;

import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.Wizard;
import Predator.System.ConsciousPredatorSoul;

public class AdminMutualWordRoot extends ConsciousPredatorSoul implements Wizard {
    public AdminMutualWordRoot(){

    }

    public AdminMutualWordRoot(HostMatrix parentSystem ){
        super(parentSystem);
    }

    public String prototypeName(){
        return this.getClass().getSuperclass().getSimpleName();
    }

}
