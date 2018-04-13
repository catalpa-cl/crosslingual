package de.unidue.ltl.eduscoring.crosslingual.crowdflower;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResultExtraction {
	
	static String path = "src/main/resources/crowdflower/resultsCrowdflower.txt";
	
	//Works for Crowdflower data of the following format:
	//farbe,begruendung_farbe,polymere,saurer_regen
	//
	//Crowdflower seems to surround answers that contain commata with "", because commata separate the
	//answers of the different tasks from each other
	//This was used to format the results from the main collection done on Crowdflower

	public static void main(String[] args) throws Exception {
		String header = "EssaySet\tEssayText";
		List<String> lines = new ArrayList<String>();
		List<String> resultsId1 = new ArrayList<String>();
		List<String> resultsId2 = new ArrayList<String>();
		List<String> resultsId10 = new ArrayList<String>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String readLine = "";
			
			while((readLine = br.readLine()) != null) {
				if(readLine.startsWith("farbe")) {
					readLine = br.readLine();
				}
				
				//Answers that contain a comma are surrounded with a "", to indicate this.
				//Split is used to check how many answers are affected
				String[] array = readLine.split(",\"");
				String[] answers = new String[4];
				
				switch (array.length) {
				case 1: //Answer does not contain a comma.
					answers = readLine.split(",");
					if(answers.length != 4) {
						throw new Exception("Wrong line format: " + readLine);
					} 
					break;
					
				case 2: //One answer contains a comma, so it is surrounded by "".
					int end = array[1].indexOf("\",");
					if(!array[0].contains(",")) { 
						
						//Answer looks like this (|| indicates what is used as delimiter and where the string is split):
						//farbe|,"|begruendung_farbe",polymere,saurer_regen
						//array[0]|array[1]
						
						answers[0] = array[0];
						answers[1] = array[1].substring(0, end);	
						String[] tmp = array[1].substring(end+2).split(",");
						if(tmp.length != 2) {
							for(int i = 0; i < tmp.length; i++) {
								System.out.println(tmp[i]);
							}
							throw new Exception("Wrong line format: " + readLine);
						}
						answers[2] = tmp[0];
						answers[3] = tmp[1];
					} else {
						if(!array[1].contains("\",")) { 
							
							//Answer looks like this:
							//farbe,begruendung_farbe,polymere|,"|saurer_regen"
							//array[0]						    |array[1]
							
							if(array[1].endsWith("\"")) {
								answers[3] = array[1].substring(0, (array[1].length()-1));
							} else {
								answers[3] = array[1];
							}
							String[] tmp = array[0].split(",");
							if(tmp.length != 3) {
								throw new Exception("Wrong line format: " + readLine);
							}
							answers[0] = tmp[0];
							answers[1] = tmp[1];
							answers[2] = tmp[2];
						} else {
							
							//Answer looks like this:
							//farbe,begruendung_farbe|,"|polymere",saurer_regen
							//array[0]					|array[1]
							
							answers[2] = array[1].substring(0, end);
							answers[3] = array[1].substring((end + 2));
							String[] tmp = array[0].split(",");
							if(tmp.length != 2) {
								throw new Exception("Wrong line format: " + readLine);
							}
							answers[0] = tmp[0];
							answers[1] = tmp[1];
						}
					}
					break;
					
				case 3: //Two answers contain commata and are therefore surrounded by ""
					if(!array[0].contains(",") && array[2].contains("\",")) { 
						
						//Answer looks like this:
						//farbe|,"|begruendung_farbe"|,"|polymere",saurer_regen
						//array[0]|array[1]				|array[2]
						
						answers[0] = array[0];
						
						if(array[1].endsWith("\"")) {
							answers[1] = array[1].substring(0, (array[1].length() - 1));
						} else {
							answers[1] = array[1];
						}
						String[] tmp = array[2].split("\",");
						answers[2] = tmp[0];
						answers[3] = tmp[1];
					} else if (!array[0].contains(",") && array[1].contains("\",")) {
						
						//Answer looks like this:
						//farbe|,"|begruendung_farbe",polymere|,"|saurer_regen"
						//array[0]|array[1]						|array[2]
						
						answers[0] = array[0];
						
						String[] tmp = array[1].split("\",");
						answers[1] = tmp [0];
						answers[2] = tmp [1];
						
						if(array[2].endsWith("\"")) {
							answers[3] = array[2].substring(0, (array[2].length() - 1));
						} else {
							answers[3] = array[2];
						}
					} else if (array[0].contains(",")) {
						
						//Answer looks like this:
						//farbe,begruendung_farbe|,"|polymere"|,"|saurer_regen"
						//array[0]					|array[1]	|array[2]
						
						String[] tmp = array[0].split(",");
						answers[0] = tmp[0];
						answers[1] = tmp[1];
						
						if(array[1].endsWith("\"")) {
							answers[2] = array[1].substring(0, (array[1].length() - 1));
						} else {
							answers[2] = array[1];
						}
						
						if(array[2].endsWith("\"")) {
							answers[3] = array[2].substring(0, (array[2].length() - 1));
						} else {
							answers[3] = array[2];
						}
					}
					break;
				
				case 4: //Similar case as 1. Here ," can be used as delimiter
					answers = readLine.split(",\"");
					if(answers.length != 4) {
						throw new Exception("Wrong line format: " + readLine);
					} else {
						if(answers[1].endsWith("\"")) {
							answers[1] = answers[1].substring(0, (answers[1].length()-1));
						}
						if(answers[2].endsWith("\"")) {
							answers[2] = answers[2].substring(0, (answers[2].length()-1));
						}
						if(answers[3].endsWith("\"")) {
							answers[3] = answers[3].substring(0, (answers[3].length()-1));
						}
						
					}
					break;
				default: 
					throw new Exception("Something went wrong: " + readLine);
				}
				
				resultsId10.add("10\t" + answers[0] + " :: " + answers[1]);
				resultsId2.add("2\t" + answers[2]);
				resultsId1.add("1\t" + answers[3]);
			}
			
			lines.add(header);
			lines.addAll(resultsId1);
			lines.addAll(resultsId2);
			lines.addAll(resultsId10);
			
			FileWriter writer = new FileWriter("src/main/resources/crowdflower/mainrunFormatted.txt");
			for(String line : lines) {
				System.out.println(line);
				//To be sure that nothing is overwritten!
//				writer.write(line + "\n");
			}
			System.out.println(lines.size());
			writer.close();
			
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
