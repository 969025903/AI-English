package Predator.Wizard.Public.UserLogin;

import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.Wizard;
import Predator.System.ConsciousPredatorSoul;

public class UserLogin extends ConsciousPredatorSoul implements Wizard {

    public UserLogin(){
    }

    public  UserLogin(HostMatrix parentSystem ){
        super(parentSystem);
    }

    public String prototypeName(){
        return this.getClass().getSuperclass().getSimpleName();
    }
}
