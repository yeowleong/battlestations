import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericDataManager {
    public static Map[] retrieveAll(String filename) {
        List all = new ArrayList();
        try {

//            BufferedReader br = new BufferedReader(new FileReader("data/" + filename + ".csv"));
            BufferedReader br = new BufferedReader(new InputStreamReader(GenericDataManager.class.getResourceAsStream("data/" + filename + ".csv")));
            String str;
            String[] headers = br.readLine().split(",");
            while ((str = br.readLine()) != null) {
                //System.out.println(str);
                Map m = new HashMap();
                String[] values = str.split(",");
                for (int i = 0; i < values.length; i++) {
                    m.put(headers[i], values[i]);
                }
                all.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (Map[]) all.toArray(new Map[all.size()]);

    }


}
