package cs310;

import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.Reader;

public class Annotator {
	public static void main( String [ ] args ) {
		JavaTokenizer jToken = new Tokenizer(new InputStreamReader(System.in));
		String currId;
		while ((currId = jToken.getNextID()) != "") {
			System.out.printf("%s[%s]", jToken.skippedText(), currId);
		}		
	}
}

