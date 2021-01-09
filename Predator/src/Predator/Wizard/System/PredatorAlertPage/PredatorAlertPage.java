package Predator.Wizard.System.PredatorAlertPage;

import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.Wizard;
import Predator.System.ConsciousPredatorSoul;

public class PredatorAlertPage extends ConsciousPredatorSoul implements Wizard {
    public PredatorAlertPage(){

    }

    public PredatorAlertPage(HostMatrix parentSystem ){
        super(parentSystem);
    }

    public String prototypeName(){
        return this.getClass().getSuperclass().getSimpleName();
    }

}