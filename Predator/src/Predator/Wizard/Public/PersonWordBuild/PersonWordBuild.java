package Predator.Wizard.Public.PersonWordBuild;

import Pinecone.Framework.Util.JSON.JSONArray;
import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.Wizard;
import Predator.System.ConsciousPredatorSoul;

public class PersonWordBuild extends ConsciousPredatorSoul implements Wizard {
    public static JSONArray sortStream = null;
    public PersonWordBuild(){
    }

    public PersonWordBuild(HostMatrix parentSystem ){
        super(parentSystem);
    }

    public String prototypeName(){
        return this.getClass().getSuperclass().getSimpleName();
    }

}