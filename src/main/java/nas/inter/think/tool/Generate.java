package nas.inter.think.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Generate {

    public static void main(String[] args) {
        if(args.length != 1){
            System.err.println("Usage: generate_ast <output directory>");
            System.exit(64);
        }
        String outputDir = args[0];
    }
    private static void defineAst(String outputDir, String baseName, List<String> types){
        String path = outputDir + "/" + baseName + ".java";

        try {
            PrintWriter writer = new PrintWriter(path, "UTF-8");

            writer.println("package nas.inter.think.nas;");
            writer.println();
            writer.println("import java.util.List;");
            writer.println();
            writer.println("abstract class " + baseName + " {");

            writer.println("}");
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
