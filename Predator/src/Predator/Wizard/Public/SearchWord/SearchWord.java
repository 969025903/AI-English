package Predator.Wizard.Public.SearchWord;

import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.Wizard;
import Predator.System.ConsciousPredatorSoul;

public class SearchWord extends ConsciousPredatorSoul implements Wizard {
    public SearchWord(){

    }

    public SearchWord(HostMatrix parentSystem ){
        super(parentSystem);
    }

    public String prototypeName(){
        return this.getClass().getSuperclass().getSimpleName();
    }

}