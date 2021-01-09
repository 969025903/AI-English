package Pinecone.Framework.System;

import Pinecone.Pinecone;

import java.util.concurrent.Callable;

public class PineconeSystem {
    public static int inti ( Callable pInletFun ){
        int nRetNum = 0;

        try{
            Pinecone.initialize();
            nRetNum = (int) pInletFun.call();

        }
        catch ( Throwable throwable ){
            throwable.printStackTrace();
            nRetNum = -1;
        }

        return nRetNum;
    }
}
