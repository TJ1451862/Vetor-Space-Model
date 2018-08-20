import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DocSet {

    private String folderPath;
    private Map<String, ArrayList<Integer>> indexmap=new HashMap<>();
    private Map<String, Double> idfmap=new HashMap<>();

    public DocSet(String folderPath){
        this.folderPath=folderPath;
    }

    public String getFolderPath(){
        return folderPath;
    }

    /**
     * 建立倒排索引
     * @return
     */

    public  Map<String, ArrayList<Integer>> createIndex()throws Exception{
        File file = new File(folderPath);
        if (file.exists())
        {
            File[] files = file.listFiles();
            int ID=10;
            for (File file2 : files) {
                String FilePath=file2.getAbsolutePath();
                document doc=new document(FilePath,ID);
                //doc.countTf();
                ArrayList<String> sw=doc.RemoveStopWords();
                for (String string : sw) {
                    ArrayList<Integer> list=new ArrayList<>();
                    if (!indexmap.containsKey(string)) {
                        list.add(ID);
                        indexmap.put(string, list);
                    }else {
                        list=indexmap.get(string);
                        if (!list.contains(ID)) {
                            list.add(ID);
                        }
                    }
                }
                ID++;
            }
        }
        //String File=readToString(FilePath);
        //System.out.print(File);
        //System.out.println(indexmap);
        FileWriter inIn = new FileWriter(new File("intertedIndex.txt"));
        inIn.write(indexmap.toString());
        inIn.close();
        return indexmap;
    }//建立倒排索引

    public Map<String, Double> countIdf()throws Exception{
        File file = new File(folderPath);
        if (file.exists())
        {
            File[] files = file.listFiles();
            int ID=10;
            for (File file2 : files) {
                String FilePath=file2.getAbsolutePath();
                document doc=new document(FilePath,ID);
                ArrayList<String> sw=doc.RemoveStopWords();
                for (String string : sw) {
                    double count;
                    if (!idfmap.containsKey(string)) {
                        idfmap.put(string, 1.0);
                    }else {
                        count=idfmap.get(string)+1;
                        idfmap.put(string, count);
                    }
                }
                ID++;
            }
        }
        int N=idfmap.size();
        double idf;
        for (String key:idfmap.keySet()) {
            idf=Math.log(N/(idfmap.get(key)));
            idfmap.put(key,idf);
        }
        //System.out.println(idfmap);
        FileWriter inIn = new FileWriter(new File("idfIndex.txt"));
        inIn.write(idfmap.toString());
        inIn.close();
        return idfmap;
    }//求Idf,idf=log(N/df)
}
