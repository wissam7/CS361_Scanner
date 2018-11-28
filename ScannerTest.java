/***********************************************************************
CS361 - Scanner HW
by: Jackie Shao & Wissam Mateen
 ***************************************************************************/
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ScannerTest {
private static String file = "C:\\Users\\Jackie\\Desktop\\scannerTestSyntax.txt";
	
	private static int counter = 1;

	public static void main(String args[]) throws IOException {
		
		TokenStream ts = new TokenStream(file);

		Token t;
		while(!ts.isEndofFile()) {
			t = ts.nextToken();
			t.toString();
			System.out.println("Token " + counter + " - "  + "Type: " + t.getType() + " - " + "Value: " + t.getValue());
			counter = counter + 1;
			
		}
	}

}
