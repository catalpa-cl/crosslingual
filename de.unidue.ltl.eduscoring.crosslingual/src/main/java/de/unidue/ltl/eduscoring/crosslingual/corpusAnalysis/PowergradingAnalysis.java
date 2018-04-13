package de.unidue.ltl.eduscoring.crosslingual.corpusAnalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PowergradingAnalysis implements CorpusAnalysis{

	private String path = "src/main/resource/datasets/powergrading";

	public void analyzeCorpus() {
		System.out.println("#########################################################");
		System.out.println("Beginning analysis of Powergrading data set: ");
		int numberQuestions = 20;
		int numberAnswers = 0;
		int totalLengthOfAnswers = 0;
		
		File folder = new File(path);
		for(File file : folder.listFiles()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line = "";
				while((line = br.readLine()) != null) {
					if(!line.startsWith("student")) {
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
