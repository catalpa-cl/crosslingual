package de.unidue.ltl.eduscoring.crosslingual.translate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Translator {
	public abstract void translateFile(String filepathInput, String filepathOutput);
	
	protected List<String> readFile(String path) {
		// Reads a file and returns a list
		List<String> lines = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			String[] lineArray;

			while ((line = br.readLine()) != null) {
				lineArray = line.split("\t");

				if (lineArray.length == 5) {
					lines.add(lineArray[4]);
				} else if (lineArray.length == 1) {
					lines.add(line);
				} else {
					System.out.println("Error in reading input file.");
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("Something went wrong finding the file to read: ");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Something went wrong reading the file: ");
			e.printStackTrace();
		}
		return lines;
	}
}
