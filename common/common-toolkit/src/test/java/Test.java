import org.apache.commons.io.FilenameUtils;

import java.io.File;

public class Test {

    public static void main(String[] args) {
        File file1 = new File("C:\\Users\\mao\\Desktop\\HYProj\\accs\\src\\main\\java\\com\\common\\utils");
        File file2 = new File("C:\\Users\\mao\\Desktop\\HYProj\\accs\\src\\main\\java\\com\\common\\utils/Encodes.java");

        System.out.println(file1.getPath());
        System.out.println(file2.getPath());
        System.out.println(FilenameUtils.getBaseName(file2.getAbsolutePath()));
    }
}
