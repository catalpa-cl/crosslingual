//package de.unidue.ltl.eduscoring.crosslingual.evaluation;
//
//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Scanner;
//
//
//import de.unidue.ltl.eduscoring.core.report.ConfusionMatrix;
//
//
//public class ConfusionMatrixExtractor {
//
//	public static void main(String[] args) {
//		Scanner sc = new Scanner(System.in);
//		String line = "";
//		ConfusionMatrixExtractor cme = new ConfusionMatrixExtractor();
//		
//		System.out.println("Please enter the path to the id2outcome file (enter 'exit' to stop):");
//		while(!(line = sc.nextLine()).equals("exit")){
//			
//			ConfusionMatrix cm = cme.extract(line);
//			if(cm != null) {
//				cm.printConfusionMatrix();
//			} 
//			
//			System.out.println();
//			System.out.println("Please enter the path to the id2outcome file (enter 'exit' to stop):");
//		}
//		sc.close();
//
//	}
//	
//	private ConfusionMatrix extract(String path) {
//		List<Integer> predictedScores = new ArrayList<Integer>();
//		List<Integer> goldStandard = new ArrayList<Integer> ();
//		List<Integer> scores = new ArrayList<Integer>();
//		
//		try {
//			BufferedReader br = new BufferedReader(new FileReader(path));
//			String line;
//			while((line = br.readLine()) != null) {
//				//be sure that meta-information is not considered
//				while(line.startsWith("#")) {
//					line = br.readLine();
//				}
//				
//				String[] actualScores = line.split("=")[1].split(";");
//				
//				int predicted = Integer.parseInt(actualScores[0]);
//				int gold = Integer.parseInt(actualScores[1]);
//				
//				predictedScores.add(predicted);
//				goldStandard.add(gold);
//				
//				if(!scores.contains(predicted)) {
//					scores.add(predicted);
//				}
//				if(!scores.contains(gold)) {
//					scores.add(gold);
//				}
//			}
//			
//			br.close();
//		} catch (FileNotFoundException e) {
//			System.out.println("Unable to find the file: " + path);
//			return null;
//		} catch (IOException e) {
//			System.out.println("Error while reading file: " + path);
//			return null;
//		}
//		
//		Collections.sort(scores);
//		return new ConfusionMatrix(goldStandard, predictedScores, scores.toArray(new Integer[scores.size()]));
//	}
//
//}
