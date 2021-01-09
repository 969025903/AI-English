package Pinecone.Framework.Util.Net.Illumination.MultipartFile.commons;

import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.MultipartFile.MultipartException;
import Pinecone.Framework.Util.Net.Illumination.MultipartFile.MultipartFile;
import Pinecone.Framework.Util.Net.Illumination.MultipartFile.MultipartHttpServletRequest;
import Pinecone.Framework.Util.Net.Illumination.SystemDispatcher;
import Pinecone.Framework.Util.Net.Illumination.io.PathResource;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public class CommonsFileUploadDispatcher {
    protected HostMatrix mMatrix;

    protected CommonsMultipartResolver       mMultipartResolver;

    protected MultipartHttpServletRequest    mCurrentMultipartHttpServletRequest = null;

    protected Map<String, MultipartFile>     mFilesMap = null;

    public CommonsFileUploadDispatcher( HostMatrix matrix ) {
        this.mMatrix = matrix;
        this.init();
    }




    public HostMatrix getHostMatrix(){
        return this.mMatrix;
    }

    public SystemDispatcher getSystemDispathcher(){
        return this.mMatrix.getSystemDispatcher();
    }

    public CommonsMultipartResolver getMultipartResolver(){
        return this.mMultipartResolver;
    }




    private void init(){
        this.mMultipartResolver = new CommonsMultipartResolver( this.mMatrix.getSystemServlet().getServletContext() );

        this.mMultipartResolver.setSingleUploadSize( this.mMatrix.getSingleFileSizeMax() );
        this.mMultipartResolver.setMaxUploadSize( this.mMatrix.getSumFileSizeMax() );
        this.mMultipartResolver.setDefaultEncoding( this.mMatrix.getUploadEncode() );

        String szUploadTempDir = this.mMatrix.getUploadTempDir();
        if( szUploadTempDir != null && !szUploadTempDir.isEmpty() ){
            try {
                this.mMultipartResolver.setUploadTempDir( new PathResource(szUploadTempDir) );
            }
            catch ( IOException e ) {
                e.printStackTrace();
            }
        }

    }

    private HttpServletRequest getHttpServletRequest(){
        return this.mMatrix.getSystemDispatcher().$_REQUEST();
    }

    public boolean isMultipart(){
        return this.mMultipartResolver.isMultipart( this.getHttpServletRequest() );
    }

    public void interceptMultipartFiles() throws MultipartException {
        if ( this.isMultipart() ){
            this.mCurrentMultipartHttpServletRequest = this.mMultipartResolver.resolveMultipart( this.getHttpServletRequest() );
             this.mFilesMap = this.mCurrentMultipartHttpServletRequest.getFileMap();
        }
    }

    public MultipartHttpServletRequest getCurrentMultipartRequest(){
        return this.mCurrentMultipartHttpServletRequest;
    }

    public Map<String, MultipartFile> getCurrentFilesMap(){
        return this.mFilesMap;
    }

}
