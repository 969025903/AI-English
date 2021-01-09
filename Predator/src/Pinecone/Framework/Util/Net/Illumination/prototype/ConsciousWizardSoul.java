package Pinecone.Framework.Util.Net.Illumination.prototype;

import javax.servlet.ServletException;
import java.io.IOException;

public interface ConsciousWizardSoul extends WizardSoul, DistributableDispatcher {
    void dispatch() throws IOException, ServletException;

    void defaultGenie() throws Exception ;

    void commandArrived() throws IOException, ServletException;

    void afterDispatchInvoke() throws IOException, ServletException;
}
