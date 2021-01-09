package Pinecone.Framework.Util.Net.Illumination.prototype;

import javax.servlet.ServletException;
import java.io.IOException;

public interface DistributableDispatcher {
    void dispatch() throws IOException, ServletException;

    void stop() throws RuntimeException;

}
