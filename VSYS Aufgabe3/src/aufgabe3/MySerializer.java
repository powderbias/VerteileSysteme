package aufgabe3;

import java.io.*;

public class MySerializer {
	private MySerializableClass mySerializableClass;
	
	MySerializer(MySerializableClass serializableClass) {
		mySerializableClass=serializableClass;
	}
	
	private String readFilename() throws IOException {
		String filename;
		BufferedReader reader=new BufferedReader(new InputStreamReader(System.in )); 
		
		System.out.print("filename> ");
		filename=reader.readLine();
		
		return filename;
	}
	
	public void write(String text) throws IOException {
		mySerializableClass.set(text);
		
		FileOutputStream fileOut =
		         new FileOutputStream("C:/Users/tobia/eclipse-workspace/VSYS Aufgabe3/output.txt");
		         ObjectOutputStream out = new ObjectOutputStream(fileOut);
		         out.writeObject(mySerializableClass);
		         out.close();
		         fileOut.close();
		         System.out.printf("Serialized data is saved to ");
		
		
		
	}
	
	public String read() throws IOException, ClassNotFoundException {
		
		FileInputStream fileIn = new FileInputStream("C:/Users/tobia/eclipse-workspace/VSYS Aufgabe3/output.txt");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        mySerializableClass= (MySerializableClass) in.readObject();
		
		return mySerializableClass.toString();
	}
} 
	