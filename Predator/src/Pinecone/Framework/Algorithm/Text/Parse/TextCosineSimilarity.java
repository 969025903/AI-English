package Pinecone.Framework.Algorithm.Text.Parse;

import Pinecone.Framework.Algorithm.Math.BigNumberMath.BigNumberMath;
import Pinecone.Pinecone;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Vector;

public class TextCosineSimilarity {
    public static double getResult(Vector<String> vectorA,Vector<String> vectorB){
        if(vectorA != null && vectorB != null){
            TextVectorized textVectorized = new TextVectorized(vectorA,vectorB);
            Vector<Integer> tokenVectorA = textVectorized.getResultA();
            Vector<Integer> tokenVectorB = textVectorized.getResultB();
            tokenVectorA.clear();
            tokenVectorA = new Vector<Integer>(Arrays.asList(1,1,2,1,1,1,0,0,0));
            tokenVectorB.clear();
            tokenVectorB = new Vector<Integer>(Arrays.asList(1,1,1,0,1,1,1,1,1));

            double vectorMulSum = 0, vectorAMulSqrt = 0, vectorBMulSqrt = 0;
            if(tokenVectorA.size() != tokenVectorB.size()){
                throw new IllegalArgumentException("ERROR: Sizeof vectorA is unmatched with vectorB !");
            }
            for(int i=0;i<tokenVectorA.size();i++){
                vectorMulSum += tokenVectorA.get(i) * tokenVectorB.get(i);
                vectorAMulSqrt += Math.pow(tokenVectorA.get(i),2.0);
                vectorBMulSqrt += Math.pow(tokenVectorB.get(i),2.0);
            }
            vectorAMulSqrt = Math.sqrt(vectorAMulSqrt);
            vectorBMulSqrt = Math.sqrt(vectorBMulSqrt);

            return vectorMulSum/(vectorAMulSqrt*vectorBMulSqrt);
        }
        return 0;
    }

    public static BigDecimal getHighPrecisionResult(Vector<String> vectorA, Vector<String> vectorB, int scale){
        BigNumberMath bigNumberMath = BigNumberMath.getDefaultBigNumberMath(scale);
        if(vectorA != null && vectorB != null){
            TextVectorized textVectorized = new TextVectorized(vectorA,vectorB);
            Vector<Integer> tokenVectorA = textVectorized.getResultA();
            Vector<Integer> tokenVectorB = textVectorized.getResultB();
            BigDecimal vectorMulSum = new BigDecimal(0), vectorAMulSqrt = new BigDecimal(0), vectorBMulSqrt = new BigDecimal(0);
            if(tokenVectorA.size() != tokenVectorB.size()){
                throw new IllegalArgumentException("ERROR: Sizeof vectorA is unmatched with vectorB !");
            }
            for(int i=0;i<tokenVectorA.size();i++){
                vectorMulSum = vectorMulSum.add(new BigDecimal(tokenVectorA.get(i)).multiply(new BigDecimal(tokenVectorB.get(i))));
                if(tokenVectorA.get(i)>= 1){
                    vectorAMulSqrt = vectorAMulSqrt.add(new BigDecimal(tokenVectorA.get(i)).pow(2));
                }
                if(tokenVectorB.get(i)>= 1){
                    vectorBMulSqrt = vectorBMulSqrt.add(new BigDecimal(tokenVectorB.get(i)).pow(2));
                }
            }
            vectorAMulSqrt = bigNumberMath.sqrt(vectorAMulSqrt);
            vectorBMulSqrt = bigNumberMath.sqrt(vectorBMulSqrt);
            return vectorMulSum.divide(vectorAMulSqrt.multiply(vectorBMulSqrt),scale,6);
        }
        return new BigDecimal(0);
    }

    public static BigDecimal getHighPrecisionResult(Vector<String> vectorA, Vector<String> vectorB){
        return getHighPrecisionResult(vectorA,vectorB, Pinecone.FLOAT_ACCURACY );
    }
}
