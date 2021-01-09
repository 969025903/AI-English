package Pinecone.Framework.Algorithm.Random;

import Pinecone.Pinecone;

import java.util.Random;

public class SeniorRandom {
    Random mRandomSpawner = null;

    public SeniorRandom(){
        this.mRandomSpawner = new Random();
    }

    public SeniorRandom( Random randomSpawner ){
        this.mRandomSpawner = randomSpawner;
    }


    public String nextString(char from, char to, int scale) {
        if(from > to){
            throw new IllegalArgumentException("'from' char can't beyond 'to' char !");
        }
        if(scale > Pinecone.COMMON_ACCURACY_LIMIT){
            throw new ArithmeticException("SeniorRandom scale is too big limit '" + Pinecone.COMMON_ACCURACY_LIMIT + "' !");
        }
        String randomDict = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int fromIndex = randomDict.indexOf(from), toIndex = randomDict.indexOf(to);
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0; i<scale; i++){
            stringBuffer.append(randomDict.charAt( this.mRandomSpawner.nextInt(toIndex - fromIndex + 1) + fromIndex) ) ;
        }
        return stringBuffer.toString();
    }

    public String nextString(int scale){
        return nextString('0','z',scale);
    }

    public String nextString(){
        return nextString(10);
    }
}
