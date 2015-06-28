package prog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class NLG {

	public static void main(String[] args) {
		Builder builder;
		BufferedReader br;
		String type = "";
		String parameters = "";
		//Read message from file
		try {
			FileReader fr = new FileReader("ingredients.txt");
			br = new BufferedReader(fr);

			String[] strs = br.readLine().split(":");
			//everything before the ":" is the type of message
			type = strs[0];
			//everything else is the message itself
			parameters = strs[1];
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Check, what type of message we have, then start the sentence builder
		if (type.startsWith("instructions") && !parameters.isEmpty()) {
			builder = new InstructionBuilder(parameters);
		} else if (type.startsWith("ingredients") && !parameters.isEmpty()) {
			builder = new IngredientBuilder(parameters);
		} else if (type.startsWith("error") && !parameters.isEmpty()) {
			builder = new ErrorBuilder(parameters);	
		} else {
			// TODO What is the right behavior?
			throw new IllegalArgumentException(
					"No type was specified in ingredients.txt or there was an error reading from it");
		}
		if (builder != null)
			builder.start();

	}

}