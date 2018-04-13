package de.unidue.ltl.eduscoring.crosslingual.corpusAnalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;

public class AsapAnalysis implements CorpusAnalysis {
	
	private String dataSetName;	
	private AsapStatistics asapStatistics;
	
	public AsapAnalysis(String name) {
		this.dataSetName = name;
		this.asapStatistics = new AsapStatistics();
	}
	
	public void analyzeCorpus() {
		System.out.println("#########################################################");
		System.out.println("Beginning analysis of " + this.dataSetName + " data set: ");
		
		for(int id : this.asapStatistics.getIds()) {
			System.out.println("ID: " + id);
			System.out.println("Label\t#Answers \tAvgAnswerLength\tAvgWordLength\tTypeTokenRatio");
			
			for(int label : this.asapStatistics.getLabelsOfId(id)) {
				System.out.println(label + "\t" + this.asapStatistics.getAnswerCount(id, label) + "\t\t" + new DecimalFormat("##.####").format(this.asapStatistics.getAverageAnswerLength(id, label)) + "\t\t" + new DecimalFormat("##.####").format(this.asapStatistics.getAverageWordLength(id, label))+ "\t\t" + new DecimalFormat("##.####").format(this.asapStatistics.getTypeTokenRatio(id, label)));
			}
			System.out.println();
		}
		
		System.out.println("In Total (per Label): ");
		System.out.println("Label\tTotal#Answers \tAvgAnswerLength\tAvgWordLength\tTypeTokenRatio");
		for(int label : this.asapStatistics.getLabels()) {
			System.out.println(label + "\t" + this.asapStatistics.getAnswerCount(label, 1,2,10) + "\t\t" + new DecimalFormat("##.####").format(this.asapStatistics.getAverageAnswerLength(label, 1,2,10)) + "\t\t" + new DecimalFormat("##.####").format(this.asapStatistics.getAverageWordLength(label, 1,2,10))+ "\t\t" + new DecimalFormat("##.####").format(this.asapStatistics.getTypeTokenRatio(label, 1,2,10)));
		}
		System.out.println();
		
		System.out.println("In Total: ");
		System.out.println("Number of questions: " + this.asapStatistics.getIds().size());
		System.out.println("Number of Answers: " + this.asapStatistics.getAnswerCount());
		System.out.println("Avg. #answers/question: " + (double)this.asapStatistics.getAnswerCount()/this.asapStatistics.getIds().size());
		System.out.println("Avg. answer length: " + this.asapStatistics.getAverageAnswerLength());
		System.out.println("Avg. word length: " + this.asapStatistics.getAverageWordLength());
		System.out.println("Avg. type token ratio: " + this.asapStatistics.getTypeTokenRatio());
		
		System.out.println();
		System.out.println("Vocabulary size: " + this.asapStatistics.getVocabularyOfIds().getB());
		System.out.println("#########################################################");
		System.out.println();
	}
	
	public Set<String> getVocabulary(Integer...integers){
		return this.asapStatistics.getVocabularyOfIds(integers).getKeys();
	}
	
	public Set<String> getTopKVocabs(int k, Integer...ids){
		return new HashSet<String>(this.asapStatistics.getVocabDistribution(ids).getMostFrequentSamples(k));
	}
	
	public FrequencyDistribution<String> getVocabDistribution (Integer...ids){
		return this.asapStatistics.getVocabDistribution(ids);
	}
	
	public void readOriginalAsapFile(String path) {
		try {
			File fileToBeRead = new File(path);
			System.out.println("Reading file: " + fileToBeRead.getName());
			BufferedReader br = new BufferedReader(new FileReader(fileToBeRead));
			String line = "";
			while((line = br.readLine()) != null) {
				if(!line.isEmpty() && Character.isDigit(line.charAt(0))) {
					String[] lineArray = line.split("\t");
					
					if(lineArray.length == 5) {
						int id = Integer.parseInt(lineArray[1]);
						int label = Integer.parseInt(lineArray[2]);
						
						this.asapStatistics.incrementAnswerCount(id, label);
						
						//remove punctuation and multiple whitespaces
						String[] words = lineArray[4].toLowerCase().replaceAll("\\p{P}", "").replaceAll("\\s+", " ").trim().split(" ");
						
						this.asapStatistics.incrementTotalTokenCount(id, label, words.length);
						
						this.asapStatistics.incrementTotalCharacterCount(id, label, words);
						
					} else {
						System.out.println("Wrong line format!");
					}
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readTranslatedAsapFile(String originalAsapPath, String translatedAsapPath) {
		File originalAsapFile = new File(originalAsapPath);
		File translatedAsapFile = new File(translatedAsapPath);
		System.out.println("Reading file: " + originalAsapFile.getName() + " with translation: " + translatedAsapFile.getName());
		try {
			BufferedReader brOrig = new BufferedReader(new FileReader(originalAsapFile));
			BufferedReader brTrans = new BufferedReader(new FileReader(translatedAsapFile));
			String originalLine = "";
			String translatedLine = "";
			
			while((originalLine = brOrig.readLine()) != null) {
				translatedLine = brTrans.readLine();
				
				if(translatedLine.equals("EssayText")) {
					translatedLine = brTrans.readLine();
				}
				
				if(originalLine.startsWith("Id\tEssaySet")) {
					originalLine = brOrig.readLine();
				}
				
				String[] lineArray = new String[5];
				String[] originalLineArray = originalLine.split("\t");
				lineArray[0] = originalLineArray[0];
				lineArray[1] = originalLineArray[1];
				lineArray[2] = originalLineArray[2];
				lineArray[3] = originalLineArray[3];
				lineArray[4] = translatedLine;
			
				if(lineArray.length == 5) {
					int id = Integer.parseInt(lineArray[1]);
					int label = Integer.parseInt(lineArray[2]);
					
					this.asapStatistics.incrementAnswerCount(id, label);
					
					//remove punctuation and multiple whitespaces
					String[] words = lineArray[4].toLowerCase().replaceAll("\\p{P}", "").replaceAll("\\s+", " ").trim().split(" ");
					
					this.asapStatistics.incrementTotalTokenCount(id, label, words.length);
					
					this.asapStatistics.incrementTotalCharacterCount(id, label, words);
					
				} else {
					System.out.println("Wrong line format!");
				}
			}
			
			brOrig.close();
			brTrans.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
