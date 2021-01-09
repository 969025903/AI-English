package Pinecone.Framework.Algorithm.Text.Parse;

import java.util.*;

public class TextVectorized {
    private Vector<String> tokenArrayA;

    private Vector<String> tokenArrayB;

    private HashSet<String> unionTokenSet;

    private Vector<Integer> tokenVectorA;

    private Vector<Integer> tokenVectorB;

    public TextVectorized(Vector<String> tokenArrayA, Vector<String> tokenArrayB){
        this.unionTokenSet = new HashSet<>();
        this.tokenVectorA = new Vector<>();
        this.tokenVectorB = new Vector<>();
        this.tokenArrayA = tokenArrayA;
        this.tokenArrayB = tokenArrayB;
        this.unionTokenSet.addAll(this.tokenArrayA);
        this.unionTokenSet.addAll(this.tokenArrayB);
        this.analysis();
    }

    public Vector<Integer> getResultA(){
        return this.tokenVectorA;
    }

    public Vector<Integer> getResultB(){
        return this.tokenVectorB;
    }

    private void analysis(){
        HashMap<String,Integer> tokenMapA = new HashMap<>();
        HashMap<String,Integer> tokenMapB = new HashMap<>();
        for (String item:this.tokenArrayA){
            int tempInt = 0;
            if(tokenMapA.containsKey(item)){
                tempInt = tokenMapA.get(item);
            }else {
                tokenMapA.put(item,0);
            }
            tokenMapA.replace(item,++tempInt);
        }
        for (String item:this.tokenArrayB){
            int tempInt = 0;
            if(tokenMapB.containsKey(item)){
                tempInt = tokenMapB.get(item);
            }else {
                tokenMapB.put(item,0);
            }
            tokenMapB.replace(item,++tempInt);
        }
        for(String item: this.unionTokenSet){
            if(!tokenMapA.containsKey(item)){
                tokenMapA.put(item,0);
            }
            if(!tokenMapB.containsKey(item)){
                tokenMapB.put(item,0);
            }
            this.tokenVectorA.add(tokenMapA.get(item));
            this.tokenVectorB.add(tokenMapB.get(item));
        }
    }
}
