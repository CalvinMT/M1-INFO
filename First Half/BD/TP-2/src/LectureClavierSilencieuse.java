import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Eric Leschinski
 * 
 * @see <a href="https://stackoverflow.com/questions/2893218/how-to-use-scanner-to-read-silently-from-stdin-in-java">
 * 		StackOverflow: How to use scanner to read silently from stdin in java</a>
 *
 */
public class LectureClavierSilencieuse {
	
    public static String readPassword(String format, Object... args)
            throws IOException {
        if (System.console() != null)
            return System.console().readPassword(format, args).toString();
        return readLine(format, args);
    }
    
    private static String readLine(String format, Object... args) throws IOException {
        if (System.console() != null) {
            return System.console().readLine(format, args);
        }
        System.out.print(String.format(format, args));
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                System.in));
        return reader.readLine();
    }
	
}
