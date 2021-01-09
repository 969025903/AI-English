package Predator.Wizard.Public.PredatorIndex;

import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.Wizard;
import Predator.System.ConsciousPredatorSoul;

public class PredatorIndex extends ConsciousPredatorSoul implements Wizard {
    public PredatorIndex(){

    }

    public PredatorIndex( HostMatrix parentSystem ){
        super(parentSystem);
    }

    public String prototypeName(){
        return this.getClass().getSuperclass().getSimpleName();
    }

}
