package Pinecone.Framework.Util.Net.Illumination.prototype;

import Pinecone.Framework.System.Prototype.Overrideable;
import Pinecone.Framework.Util.Net.Illumination.prototype.DistributableDispatcher;
import Pinecone.Pinecone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface DistributableRootDispatcher extends DistributableDispatcher {
    void afterCommandChanged( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException;

    void parameterDispatchBus() throws ServletException, IOException ;

    void requestReceived() throws ServletException, IOException ;


    /** Http Method Handler **/
    void handleGet();

    void handlePost();

    void handleHead();

    void handleOptions();

    void handlePut();

    void handlePatch();

    void handleDelete();

    void handleTrace();


    /** Tracer **/
    void traceSystemErrorMsg( String szTitle, String szErrorMsg ) throws IOException, ServletException;

    void traceSystemErrorMsg( int nErrorID, String szTitle, String szErrorMsg ) throws IOException, ServletException;

    void traceSystem404Error() throws IOException, ServletException;

    void traceSystem404Error( String szErrorMsg ) throws IOException, ServletException;

    void traceSystem500Error( String szErrorMsg ) throws IOException, ServletException;

}
