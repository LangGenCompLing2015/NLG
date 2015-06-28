package prog;

public class ErrorBuilder implements Builder {
	private static final String[] ERROROUTPUTLIST = { 
		"Sorry, I don't understand, could you say it again, please?", 
		"I still don't understand, could you speak clearly, please.", 
		"Come on, try harder!" };
	
	private final String error;

	public ErrorBuilder(String err) {
		error = err;
	}

	@Override
	public void start() {
	/*  List<List<String>> sentenceList = new ArrayList<List<String>>();
		List<String> splitBySemicolon;
		String line = error;
		line.split(":");
	*/
		int errorCount = Integer.parseInt(error);
		if (errorCount <= ERROROUTPUTLIST.length){	
			System.out.println(ERROROUTPUTLIST[errorCount -1]);
		}
		else {
			System.out.println("I give up. Ask your mother.");
		}
	}

}
