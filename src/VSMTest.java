import java.io.File;
import java.util.*;
import java.util.ArrayList;

import java.util.Arrays;

import java.util.Collections;

import java.util.Comparator;

import java.util.HashMap;

import java.util.List;

import java.util.Map;

import java.util.Map.Entry;


public class VSMTest {

    public static void main(String[] args) throws Exception {

        String FolderPath = "E:\\code\\idea\\VSMTest3\\text";
        DocSet ds = new DocSet(FolderPath);

        Map<String, ArrayList<Integer>> dsindex=new HashMap<>();
        dsindex=ds.createIndex();

        Map<String, Double> dsidf=new HashMap<>();
        dsidf=ds.countIdf();

        question question1=new question("微软公司和苹果公司哪个更厉害？");
        double[] qsVetor;
        qsVetor=question1.setVctor(dsidf);

        //ds.createIndex();
        //ds.countIdf();
        //document N10=new document("E:\\code\\idea\\VSMTest3\\text\\10_UTF-8.txt",10);
        //N10.setVctor(ds);
        query(qsVetor,ds,dsidf);
    }

    public static double countSimilarty(double[] qsVetor,document doc,Map<String, Double> idfmap) throws Exception{
        double[] docVector=doc.setVctor(idfmap);
        double similarty=0;
        for (int i = 0; i <docVector.length ; i++) {
            similarty+=docVector[i]*qsVetor[i];
        }
        if(similarty!=0)
        System.out.println(doc.getDocID()+": "+similarty);
        return similarty;
    }

    public static void query(double[] qsVector,DocSet docs,Map<String, Double> idfmap) throws Exception{
        Map<Integer,Double> idMap=new HashMap<>();
        String folderPath=docs.getFolderPath();
        File file = new File(folderPath);
        if (file.exists())
        {
            File[] files = file.listFiles();
            int ID=10;
            double similarty;
            for (File file2 : files) {
                String FilePath=file2.getAbsolutePath();
                document doc=new document(FilePath,ID);
                similarty=countSimilarty(qsVector,doc,idfmap);
                idMap.put(ID,similarty);
                ID++;
            }
        }
        System.out.print(idMap);

        //将map.entrySet()转换成list
        List<Map.Entry<Integer, Double>> list = new ArrayList<Map.Entry<Integer, Double>>(idMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
            //降序排序
            @Override
            public int compare(Entry<Integer, Double> o1, Entry<Integer, Double> o2) {
                //return o1.getValue().compareTo(o2.getValue());
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        for (Map.Entry<Integer, Double> mapping : list) {
            if(mapping.getValue()!=0)
            System.out.println(mapping.getKey() + ":" + mapping.getValue());
        }


        //return idMap;
    }


}