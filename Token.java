/***********************************************************************
CS361 - Scanner HW
by: Jackie Shao & Wissam Mateen
 ***************************************************************************/
public class Token {
	//String represnts Token
	
	
	private String type; 
	//identifier, keyword, literal, separator, operator, or Other
	private String value; 

	public void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}

	public String toString() {
		return "Value: " + this.getValue() + " " + "Type: " + this.getType();
	}

}