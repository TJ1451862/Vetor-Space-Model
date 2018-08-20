import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class question {
    private String question;
    private Map<String, Double> tfmap = new HashMap<>();

    public question (String question) {
        this.question = question;
    }


    public void printQuestion()//输出源文件
    {
        System.out.println("查询问题：" + question + "/n");
    }

    public String nonPucuation()//去标点
    {
        String docs = question;
        docs = docs.replaceAll("[\\pP\\n\\t\\s]", "");
        docs = docs.replaceAll("　　", "");
        return docs;
    }

    public List<Term> Segmentation()//标准分词
    {
        String docs = nonPucuation();
        HanLP.Config.ShowTermNature = false;//停用词性显示
        List<Term> termList = HanLP.segment(docs);
        return termList;
    }

    public ArrayList RemoveStopWords() throws Exception//去停用词
    {
        File stopWordFile = new File("E:\\code\\idea\\VSMTest3\\src\\ChineseStopWord.txt");//停用词
        BufferedReader stopWordBR = new BufferedReader(new FileReader(stopWordFile));//构造一个BufferedReader类来读取ChineseStopWord文件
        String stopword;
        ArrayList<String> stopwordAL = new ArrayList();
        while ((stopword = stopWordBR.readLine()) != null) {//使用readLine方法，一次读一行 读取停用词
            stopwordAL.add(stopword);
        }
        stopWordBR.close();

        ArrayList<String> TermList = new ArrayList();
        List<Term> termList = Segmentation();
        for (int i = 0; i < termList.size(); i++) {
            TermList.add(termList.get(i).toString());
        }
        TermList.removeAll(stopwordAL);
        return TermList;
    }

    public Map<String, Double> countTf() throws Exception {
        ArrayList<String> termList = RemoveStopWords();
        for (String string : termList) {
            if (!tfmap.containsKey(string)) {
                tfmap.put(string, 1.0);
            } else {
                double tf = 1 + Math.log(tfmap.get(string) + 1);
                tfmap.put(string, tf);
            }
        }
        return tfmap;
    }//计算tf,tf=1+log(tf)

    public double[] setVctor(Map<String, Double> idfmap) throws Exception {
        Map<String, Double> tfmap = countTf();
        int idflength = idfmap.size();
        double[] vector = new double[idflength + 1];
        int num = 0;
        for (String in : idfmap.keySet()) {
            if(tfmap.containsKey(in)){
                double tf = tfmap.get(in);
                double idf = idfmap.get(in);
                vector[num] = tf * idf;
            }else {
                num++;
            }
        }
        double sum=0;
        for (double j:vector) {
            sum+=Math.pow(j,2);
        }
        sum=Math.pow(sum,0.5);
        double a=0;
        for (int i = 0; i <vector.length ; i++) {
            vector[i]=vector[i]/sum;
            a+=Math.pow(vector[i],2);
        }
        return vector;
    }//求文档向量的单位向量


}
