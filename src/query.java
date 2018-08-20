/*import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class query {
    private DocSet docs;
    private question qs;

    public query( question qs,DocSet docs){
        this.docs=docs;
        this.qs=qs;
    }

    public double countSimilarty(question qs,document doc) throws Exception{
        double[] docVector=doc.setVctor(docs);
        double[] qsVetor=qs.setVctor(docs);
        double similarty=0;
        for (int i = 0; i <docVector.length ; i++) {
          similarty+=docVector[i]*qsVetor[i];
        }
        return similarty;
    }

    public Map<Integer,Double> runquery() throws Exception{
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
                similarty=countSimilarty(qs,doc);
                idMap.put(ID,similarty);
            }
        }
        System.out.print(idMap);
        return idMap;
    }
}
*/