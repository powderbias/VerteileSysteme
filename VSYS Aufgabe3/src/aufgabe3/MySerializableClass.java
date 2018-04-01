package aufgabe3;

import java.io.Serializable;

public class MySerializableClass implements Serializable{
	private static final long serialVersionUID=1;
	private int id;
	private String string;
	
	//ohne transient wird exception geworfen, mit transient wird das objekt nicht serialisiert
	transient MyNonSerializableClass nonSerializableClass ;
	
	MySerializableClass(MyNonSerializableClass nonSerializableClass) {
		id=1234;
		this.nonSerializableClass=nonSerializableClass;
	}
	MySerializableClass(){
		id=1234;
		
	};
	
	public void set(String string) {
		this.string=string;
	}
	
	public String toString() {
		return "id: "+id+"; string: "+string ;
	}
} 
	