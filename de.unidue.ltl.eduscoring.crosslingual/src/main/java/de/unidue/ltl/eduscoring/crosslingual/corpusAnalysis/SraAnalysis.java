package de.unidue.ltl.eduscoring.crosslingual.corpusAnalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SraAnalysis implements CorpusAnalysis {

	private String path = "src/main/resource/datasets/sra";

	public void analyzeCorpus() {
		System.out.println("#########################################################");
		System.out.println("Beginning analysis of SRA data set: ");
		File folder = new File(path);
		int numberQuestions = 0;
		int numberAnswers = 0;
		int totalLengthOfAnswers = 0;
		
		for(File file : folder.listFiles()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line = "";
				while((line = br.readLine()) != null) {
					if (line.contains("<question ")) {
						numberQuestions++;
					} else if(line.contains("<studentAnswer ")) {
						numberAnswers++;
						String[] answer = line.split("\">");
						String studentAnswer = answer[1].substring(0, (answer[1].length()-16));
						totalLengthOfAnswers += studentAnswer.split(" ").length;
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
