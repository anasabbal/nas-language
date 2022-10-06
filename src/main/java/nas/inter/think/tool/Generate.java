package nas.inter.think.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class Generate {

    public static void main(String[] args) {
        if(args.length != 1){
            System.err.println("Usage: generate_ast <output directory>");
            System.exit(64);
        }
        String outputDir = args[0];
        defineAst(outputDir, Arrays.asList(
                "Binary   : Expr left, Token operator, Expr right",
                "Grouping : Expr expression",
                "Literal  : Object value",
                "Unary    : Token operator, Expr right"
        ));
    }
    // defineAst needs to do is output the base Expr class
    private static void defineAst(String outputDir, List<String> types){
        String path = outputDir + "/" + "Expression" + ".java";

        try {
            PrintWriter writer = new PrintWriter(path, StandardCharsets.UTF_8);

            writer.println("package nas.inter.think.nas;");
            writer.println();
            writer.println("import java.util.List;");
            writer.println();
            writer.println("abstract class " + "Expression" + " {");

            // The AST classes.
            for(String type: types){
                String className = type.split(":")[0].trim();
                String fields = type.split(":")[1].trim();
                defineType(writer, className, fields);
            }
            writer.println("}");
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    // It declares each field in the class body. It defines a constructor for the class with parameters for each field and initializes them in the body
    private static void defineType(PrintWriter writer, String className, String fields) {
        writer.println("  static class " + className + " extends " +
                "Expression" + " {");
        // Constructor
        writer.println("    " + className + "(" + fields + ") {");
        // Store parameters in fields.
        String[] _fields = fields.split(", ");
        for (String field : _fields) {
            String name = field.split(" ")[1];
            writer.println("      this." + name + " = " + name + ";");
        }
        writer.println("    }");
        // Fields.
        writer.println();
        for (String field : _fields) {
            writer.println("    final " + field + ";");
        }

        writer.println("  }");
    }
}
