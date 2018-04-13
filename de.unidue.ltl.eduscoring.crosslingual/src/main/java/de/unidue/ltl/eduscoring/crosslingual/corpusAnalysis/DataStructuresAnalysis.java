package de.unidue.ltl.eduscoring.crosslingual.corpusAnalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataStructuresAnalysis implements CorpusAnalysis {
	
	private String path = "src/main/resource/datasets/datastructures";

	public void analyzeCorpus() {		
		System.out.println("#########################################################");
		System.out.println("Beginning analysis of DataStructures data set: ");
		
		int numberQuestions = 0;
		int numberAnswers = 0;
		int totalLengthOfAnswers = 0;
		
		File folder = new File(this.path);
		for(File file : folder.listFiles()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line = "";
				while((line = br.readLine()) != null) {
					if(line.contains("Question:")) {
						numberQuestions++;
					} else if(!line.isEmpty() && Character.isDigit(line.charAt(0))) {
						numberAnswers++;
						String[] answer = line.split("\t");
						totalLengthOfAnswers += answer[2].split(" ").length;
					}
				}
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Number of questions: " + numberQuestions);
		System.out.println("Number of answers: " + numberAnswers);
		System.out.println("Avg. #answers/question: " + (double)numberAnswers/numberQuestions);
		System.out.println("Avg. answer length: " + (double)totalLengthOfAnswers/numberAnswers);	
		System.out.println("#########################################################");

		System.out.println();
	}

}
