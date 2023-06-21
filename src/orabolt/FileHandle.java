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
			for (Ora element : lista) {
				String sor = element.getMegnevezes()+delimiter+Integer.valueOf(element.getAr())+delimiter+element.isVizallo()+delimiter+element.getTipus()+"\n";
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
