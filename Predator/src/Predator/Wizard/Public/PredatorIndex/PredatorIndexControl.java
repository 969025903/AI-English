package Predator.Wizard.Public.PredatorIndex;

import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedControl;

import javax.servlet.ServletException;
import java.io.IOException;

public class PredatorIndexControl extends PredatorIndex implements JSONBasedControl {
    public PredatorIndexControl() {
    }

    public PredatorIndexControl(HostMatrix parentSystem ){
        super(parentSystem);
    }

    @Override
    public void dispatch() throws IOException, ServletException {

    }
}
