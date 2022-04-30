import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MAIN {

    public static void main(String[] args) throws IOException {
        List<String> src = FileUtils.readLines(new File("C:\\Users\\xuemingwang\\Desktop\\ss"));
        List<String> dest = FileUtils.readLines(new File("C:\\Users\\xuemingwang\\Desktop\\tt"));
        for (String s : src) {
            if(StringUtils.isEmpty(s)) {
                continue;
            }
            boolean find = false;
            for (String s1 : dest) {
                if(s1.trim().equals(s.trim())) {
                    find = true;
                }
            }
            if(!find) {
                System.out.println(s);
            }

        }

    }

}
