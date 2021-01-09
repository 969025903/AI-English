package Predator.Wizard.System.SystemCoven;

import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.Wizard;
import Predator.System.ConsciousPredatorSoul;

public class SystemCoven extends ConsciousPredatorSoul implements Wizard {
    public SystemCoven(){
        super();
    }

    public SystemCoven( HostMatrix parentSystem ){
        super(parentSystem);
    }

    public String prototypeName(){
        return this.getClass().getSuperclass().getSimpleName();
    }
}
