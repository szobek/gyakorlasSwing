package orabolt;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;


public class FileHandle {

	public static void writeFile(List<Ora> lista) {
		try(OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("orak.txt"), "UTF-8");) {

			String delimiter = ";";
			for(int i=0;i<lista.size();i++) {
				String sor = lista.get(i).getMegnevezes()+delimiter+Integer.valueOf(lista.get(i).getAr())+delimiter+lista.get(i).isVizallo()+delimiter+lista.get(i).getTipus()+"\n";
				writer.write(sor);
			}
		} catch (IOException  e) {
			e.printStackTrace();
		}
		
	}
	
	public static void readFile(List<Ora> lista) {
		String file = "orak.txt";
		String delimiter = ";";
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))){

			
			while (br.ready()) {
				lista.add(new Ora(br.readLine().split(delimiter)));
			}
			
		} catch(IOException e) {}
		
	}
}
