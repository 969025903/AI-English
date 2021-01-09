package Predator.Util;

import Pinecone.Framework.Util.JSON.JSONArray;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PredatorTimeHelper extends PredatorHelper {

    public static String getSystemTime(String szTimeType){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(szTimeType );
        return simpleDateFormat.format( date );
    }

}
