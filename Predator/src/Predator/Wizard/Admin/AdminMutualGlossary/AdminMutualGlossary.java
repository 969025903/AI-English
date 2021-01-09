package Predator.Wizard.Admin.AdminMutualGlossary;

import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.Wizard;
import Predator.System.ConsciousPredatorSoul;

public class AdminMutualGlossary extends ConsciousPredatorSoul implements Wizard {
    public AdminMutualGlossary(){

    }

    public AdminMutualGlossary( HostMatrix parentSystem ){
        super(parentSystem);
    }

    public String prototypeName(){
        return this.getClass().getSuperclass().getSimpleName();
    }
}