package features;

/**
 * Class, which represents one feature of a word. Every feature has a name and a value.
 *
 */
public class Feature {

	private String key;
	private String value;
	
	public Feature(String key, String value){
		this.key = key;
		this.value = value;
	}
	
	public String getKey(){
		return key;
	}
	
	public String getValue(){
		return value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Feature){
			Feature f = (Feature) obj;
			if(f.key.equals(key) && f.value.equals(value)){
				return true;
			}
		}
		return false;
	}
}
