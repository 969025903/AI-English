package Pinecone.Framework.Util.Net.Illumination;

import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  Like Fast CGI, Here will handle all request and response.
 */

public class IlluminationServlet extends HttpServlet implements JSONBasedServlet {
    private HostMatrix mServletMatrix  = null             ;

    private String mszClassPath                           ;

    private String mszServletMatrix                       ;



    public String getClassPath() {
        return this.mszClassPath;
    }

    public String getServletMatrixConfig() {
        return this.mszServletMatrix;
    }

    public HostMatrix getIlluminationSystem(){
        return this.mServletMatrix;
    }

    public SystemDispatcher getServletMatrix() {
        return this.mServletMatrix.getSystemDispatcher();
    }




    @Override
    public void init() throws ServletException {
        this.mszClassPath = HostMatrix.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        this.mszServletMatrix = this.getServletContext().getInitParameter("ServletMatrix");

        this.mServletMatrix = IlluminationSystemSpawner.spawnMatrix( this.mszServletMatrix, this );
        this.mServletMatrix.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.mServletMatrix.getSystemDispatcher().afterCommandChanged( request, response );
        this.mServletMatrix.getSystemDispatcher().handleGet();
        this.mServletMatrix.getSystemDispatcher().parameterDispatchBus();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.mServletMatrix.getSystemDispatcher().afterCommandChanged( request, response );
        this.mServletMatrix.getSystemDispatcher().handlePost();
        this.mServletMatrix.getSystemDispatcher().parameterDispatchBus();
    }

}

