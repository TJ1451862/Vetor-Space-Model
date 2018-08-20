import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        List<String> stop = new ArrayList<>();
        List<String> term = new ArrayList<>();
        stop.add("一个");
        stop.add("实验室");
        term.add("实验室");
        term.add("实验室");
        term.add("qwe");
        term.removeAll(stop);
        System.out.println(term.size());
    }
}
