package Predator.Wizard.Public.ReciteWord;

import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.Wizard;
import Predator.System.ConsciousPredatorSoul;

public class ReciteWord extends ConsciousPredatorSoul implements Wizard {
    public  ReciteWord(){
    }

    public  ReciteWord(HostMatrix parentSystem ){
        super(parentSystem);
    }

    public String prototypeName(){
        return this.getClass().getSuperclass().getSimpleName();
    }
}
