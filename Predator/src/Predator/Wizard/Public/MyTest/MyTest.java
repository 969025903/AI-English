package Predator.Wizard.Public.MyTest;

import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.Wizard;
import Predator.System.ConsciousPredatorSoul;

public class MyTest extends ConsciousPredatorSoul implements Wizard {
    public MyTest(){

    }

    public MyTest(HostMatrix parentSystem ){
        super(parentSystem);
    }

    public String prototypeName(){
        return this.getClass().getSuperclass().getSimpleName();
    }

}
