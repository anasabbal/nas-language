package nas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class Nas {

    static boolean hadError = false;


    public static void main(String[] args) throws IOException {
        if(args.length > 1){
            System.out.print("Usage: Nas [script]");
            System.exit(64);
        }else if(args.length == 1){
            runFile(args[0]);
        }else
            runPrompt();
    }
    // run it interactively
    private static void runPrompt() throws IOException{
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for(;;){
            System.out.print("> ");
            String line = reader.readLine();
            if(line == null)
                break;
            run(line);
            hadError = false;
        }
    }

    // Both the prompt and the file runner are thin wrappers around this core function
    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        // For now, just print the tokens
        for(Token token: tokens){
            System.out.printf(String.valueOf(token));
        }
    }

    // start Nas from the command line
    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));

        if(hadError)
            System.exit(65);
    }

    //Error Handling
    // Helper tells the user some syntax error
    static void error(int line, String message){
        report(line, "", message);
    }
    // Print the line error in the code
    private static void report(int line, String where, String message){
        System.err.println("[line " + line + "] Error" + where + ": " + message);
        hadError = false;
    }
}
