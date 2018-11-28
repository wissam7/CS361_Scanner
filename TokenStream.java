/***********************************************************************
CS361 - Scanner HW
by: Jackie Shao & Wissam Mateen
 ***************************************************************************/
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TokenStream {

	//checks the code

	private boolean isEof = false;
	
	private char nextChar = ' '; //next character of input stream
	
	private BufferedReader input;

	public boolean isEoFile() {
		return isEof;
	}

	// Pass a filename for the program text as a source for the TokenStream.
	public TokenStream(String fileName) {
		try {
			input = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("File not found:" + " " + fileName);
			// System.exit(1); //removed to allow ScannerDemo to continue
			//running after the input file is not found.
			isEof = true;
		}
	}

	public Token nextToken() { //return next token type and value.
		Token t = new Token();
		t.setType("Other");
		t.setValue("");

		//checks for white spaces and bypasses it
		skipWhiteSpace();

		//checks for a comment, and bypasses it
		while (nextChar == '/') {
			nextChar = readChar();
			if (nextChar == '/') { // If / is followed by another /
				while (!isEndOfLine(nextChar)) {
					nextChar = readChar();
				}
				while (isWhiteSpace(nextChar) || isEndOfLine(nextChar)) {
					nextChar = readChar();
				}
			} else {
				t.setValue("/");
				t.setType("Operator");
				return t;
			}
		}

		//check for an operator
		if (isOperator(nextChar)) {
			t.setType("Operator");
			t.setValue(t.getValue() + nextChar);
			switch (nextChar) {
			case '<':
			case '>':
			case '=':
			case '!':
				//check for double operators
				nextChar = readChar();
				if (nextChar == '=') {
					t.setValue(t.getValue() + nextChar);
					t.setType("Operator");
					nextChar = readChar();
				}
				return t;
				
			case '&':
			case '|':
				nextChar = readChar();
				if (nextChar == '&') {
					t.setValue(t.getValue() + nextChar);
					t.setType("Operator");
					nextChar = readChar();
				} else {
					t.setType("Other");
				}
				return t;

			default: //other operators
				nextChar = readChar();
				return t;
			}
		}

		//checks for a separator
		if (isSeparator(nextChar)) {
			t.setType("Separator");
			t.setValue(t.getValue() + nextChar);
			nextChar = readChar();

			return t;
		}

		//check for an identifier, keyword, or literal.
		if (isLetter(nextChar)) {
			t.setType("Identifier");
			while ((isLetter(nextChar) || isDigit(nextChar))) {
				t.setValue(t.getValue() + nextChar);
				nextChar = readChar();
			}
			//see if this is a keyword
			if (isKeyword(t.getValue()))
				t.setType("Keyword");
				t.setType("Literal");
			if (isEndOfToken(nextChar)) // If token is valid, returns.
				return t;
		}

		if (isDigit(nextChar)) { // check for integers
			t.setType("Literal");
			while (isDigit(nextChar)) {
				t.setValue(t.getValue() + nextChar);
				nextChar = readChar();
			}
			/*Integer-Literal is to be only followed by a space,
			an operator, or a separator. */
			if (isEndOfToken(nextChar)) // If token is valid, returns.
				return t;
		}

		if (isEof)
			return t;

		// Makes sure that the whole unknown token (Type: Other) is printed.
		while (!isEndOfToken(nextChar) && nextChar != 7) {
			if (nextChar == '!') {
				nextChar = readChar();
				if (nextChar == '=') { //looks for = after !
					nextChar = 7; //verifies next token is !=
					break;
				} else
					t.setValue(t.getValue() + "!");
			} else {
				t.setValue(t.getValue() + nextChar);
				nextChar = readChar();
			}
		}

		if (nextChar == 7) {
			if (t.getValue().equals("")) { //looks for a !=
				t.setType("Operator"); //If token is empty, sets != as token,
				t.setValue("!="); 
				nextChar = readChar();
			}

		} else
			t.setType("Other"); //otherwise, unknown token.

		return t;
	}

	private char readChar() {
		int i = 0;
		if (isEof)
			return (char) 0;
		System.out.flush();
		try {
			i = input.read();
		} catch (IOException e) {
			System.exit(-1);
		}
		if (i == -1) {
			isEof = true;
			return (char) 0;
		}
		return (char) i;
	}

	private boolean isKeyword(String s) {
		return (s.equals("boolean") || s.equals("else") || s.equals("main") || s.equals("void") || s.equals("if")
				|| s.equals("else") || s.equals("int") || s.equals("while"));
	}

	private boolean isWhiteSpace(char c) {
		return (c == ' ' || c == '\t' || c == '\r' || c == '\n' || c == '\f');
	}

	private boolean isEndOfLine(char c) {
		return (c == '\r' || c == '\n' || c == '\f');
	}

	private boolean isEndOfToken(char c) { // Is the value a separate token?
		return (isWhiteSpace(nextChar) || isOperator(nextChar) || isSeparator(nextChar) || isEof);
	}

	private void skipWhiteSpace() {
		// check for whitespace, and bypass it
		while (!isEof && isWhiteSpace(nextChar)) {
			nextChar = readChar();
		}
	}

	private boolean isSeparator(char c) {
		return (c == ')' || c == '(' || c == '{' || c == '}' || c == ',' || c == ';');
	}

	private boolean isOperator(char c) {

		return (c == '=' || c == '-' || c == '+' || c == '/' || c == '*' || c == '<' || c == '>' || c == '|' || c == '!'
				|| c == '&');
	}

	private boolean isLetter(char c) {
		return (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z');
	}

	private boolean isDigit(char c) {
		return (c >= '0' && c <= '9');
	}

	public boolean isEndofFile() {
		return isEof;
	}
}
	