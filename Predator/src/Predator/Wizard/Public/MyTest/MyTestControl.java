package Predator.Wizard.Public.MyTest;

import Pinecone.Framework.Debug.Debug;
import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedControl;
import Pinecone.Framework.Util.Net.Illumination.MultipartFile.MultipartException;
import Pinecone.Framework.Util.Net.Illumination.MultipartFile.MultipartFile;

import java.util.Map;

public class MyTestControl extends MyTest implements JSONBasedControl {
    public MyTestControl() {
    }

    public MyTestControl(HostMatrix parentSystem ){
        super(parentSystem);
    }


    public void defaultGenie() throws Exception {

//        CommonsFileUploadDispatcher commonsFileUploadDispathcher = new CommonsFileUploadDispatcher( this.parent() );
//        CommonsMultipartResolver commonsMultipartResolver = commonsFileUploadDispathcher.getMultipartResolver();
        try{
            Map<String, MultipartFile > mR = this.$_FILES();
            Debug.trace( mR );
            //Debug.trace( ((CommonsMultipartFile)mR.getFile("fileA")).getStoragePath() );

        }
        catch (MultipartException e){
            e.printStackTrace();
        }



        Debug.trace( this.currentHttpMethod() );
        Debug.trace( this.$_GSC() );
    }

    public void shit(){
        Debug.trace("Control Shit");
    }
}
