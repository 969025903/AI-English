package Pinecone.Framework.Algorithm.Text.Parse;

import java.util.ArrayList;
import java.util.List;

public class TextParseUtil {
    public static List<String> vocabularyMerge(List<String[]> textSetList){
        List<String> rVocabList = new ArrayList<String>();
        for (String[] item : textSetList) {
            for (int i = 0; i < item.length; i++) {
                if (!rVocabList.contains(item[i])){
                    rVocabList.add(item[i]);
                }
            }
        }
        return rVocabList;
    }

    public static double[] textVectorizedInOrder_ParseOneLine(List<String> vocabList,String[] targetTokenArray){
        double[] rVectorArray = new double[vocabList.size()];
        for(int i=0; i< targetTokenArray.length; i++){
            int index = vocabList.indexOf(targetTokenArray[i]);
            if(index > 0){
                rVectorArray[index] = 1.0;
            }
        }
        return rVectorArray;
    }

    public static List<double[]> textVectorizedInOrder(List<String> vocabList, List<String[]> targetTextSet){
        List<double[]> rVectorSet = new ArrayList<>();
        for(String[]item : targetTextSet){
            rVectorSet.add(TextParseUtil.textVectorizedInOrder_ParseOneLine(vocabList,item));
        }
        return rVectorSet;
    }

}
