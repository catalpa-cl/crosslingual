package de.unidue.ltl.eduscoring.crosslingual.evaluation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaselineEvaluation {

	public static void main(String[] args) throws IOException {

		// System.out.println("Start evaluation");
		// String resultsDir =
		// System.getenv("DKPRO_HOME")+"/org.dkpro.lab/CrossLingual/Powergrading_ORIG";
		// String resultsDir =
		// System.getenv("DKPRO_HOME")+"/org.dkpro.lab/CrossLingual/Powergrading_TRANSLATE";
		String resultsDir = System.getenv("DKPRO_HOME")+"/org.dkpro.lab/repository";
		// iterate through all files in that dir, look at directories
		// get only Init dirs

		File inputFile = new File(resultsDir);
		File[] fileArray = inputFile.listFiles();

		List<Evaluation> results = new ArrayList<Evaluation>();
		for (File f : fileArray) {
			if (f.isDirectory() && f.getName().startsWith("Weka") && !f.getName().contains("CV")) {
				// System.out.println(f.getName());
				// get the last four blocks that identify the experiment
				if (f.getName().contains("English_Train_Test")) {
					results.add(new WekaEvaluation("English Train English Test", f));
				}  else if (f.getName().contains("English_Train_Translated_Test")) {
					results.add(new WekaEvaluation("English Train Translated Test", f));
				} else if (f.getName().contains("German_Train_Translated_Test")) {
					results.add(new WekaEvaluation("German Train Translated Test", f));
				} else if (f.getName().contains("English_Translated_Train_Original_Test")) {
					results.add(new WekaEvaluation("English Translated Train English Test", f));
				} else if (f.getName().contains("German_Translated_Train_German_Test")) {
					results.add(new WekaEvaluation("German Translated Train German Test", f));
				} else if (f.getName().contains("English_Train_Double_Translated_Test")) {
					results.add(new WekaEvaluation("English Train Double Translated Test", f));
				} else if (f.getName().contains("German_Translated_Train_Translated_Test")) {
					results.add(new WekaEvaluation("Train and Test translated into German", f));
				} else if(f.getName().contains("English_Double_Translated_Train_DoubleTranslated_Test")) {
					results.add(new WekaEvaluation("English Train and Test Double Translated", f));
				} else if(f.getName().contains("English_Double_Translated_Train_Translated_Test")) {
					results.add(new WekaEvaluation("English double translated Train translated Test", f));
				} else if(f.getName().contains("German_double_translated_Train_Translated_Test")) {
					results.add(new WekaEvaluation("German double translated Train translated Test", f));
				} else if(f.getName().contains("English_Translated_Train_double_translated_Test")) {
					results.add(new WekaEvaluation("English translated Train double translated Test", f));
				} else if(f.getName().contains("German_Translated_Train_double_translated_German_Test")) {
					results.add(new WekaEvaluation("German translated Train double translated Test", f));
				} else if(f.getName().contains("English_Double_Translated_Train_English_Test")) {
					results.add(new WekaEvaluation("English double translated Train English Test", f));
				}
				
			} else if (f.isDirectory() && f.getName().startsWith("Experiment") && f.getName().contains("CV")) {
				if (f.getName().contains("German_CV")) {
					results.add(new WekaCvEvaluation("German CV", f));
				} else if (f.getName().contains("English_Translated_CV")) {
					results.add(new WekaCvEvaluation("English translated CV", f));
				} else if (f.getName().contains("German_Double_Translated_CV")) {
					results.add(new WekaCvEvaluation("German double translated CV", f));
				}
				
				
			} else if (f.isDirectory() && f.getName().startsWith("Learning")) {
				if (f.getName().contains("English_270Train_Translated_Test")) {
					results.add(new LearningCurveEvaluation("English 270Train Translated Test", f));
				} else if (f.getName().contains("German_Translated_270Train_German_Test")) {
					results.add(new LearningCurveEvaluation("German Translated 270 Train German Test", f));
				} else if (f.getName().contains("German_Translated_270Train_Translated_Test")) {
					results.add(new LearningCurveEvaluation("German translated 270 Train translated Test", f));
				} else if (f.getName().contains("English_270Train_Test")) {
					results.add(new LearningCurveEvaluation("English 270 Train Englich Test", f));
				} else if (f.getName().contains("English_double_translated_270Train_Translated_Test")) {
					results.add(new LearningCurveEvaluation("English double translated 270 Train translated Test", f));
				} else if (f.getName().contains("German_Translated_270Train_double_translated_German_Test")) {
					results.add(new LearningCurveEvaluation("German translated 270 Train double translated German Test", f));
				} else if (f.getName().contains("English_270Train_Double_Translated_Test")) {
					results.add(new LearningCurveEvaluation("English 270 Train double translated English Test", f));
				} else if (f.getName().contains("English_Double_Translated_270Train_DoubleTranslated_Test")) {
					results.add(new LearningCurveEvaluation("English 270 double translated Train double translated English Test", f));
				} else if (f.getName().contains("English_Double_Translated_270Train_English_Test")) {
					results.add(new LearningCurveEvaluation("English 270 double translated Train English Test", f));
				}
			}
		}
		
		Collections.sort(results);
		
		for (Evaluation result : results) {
			// for my German google doc
			System.out.println(result.getResult().replaceAll("\\.", ","));
		}
		// System.out.println("Finished evaluation");
	}
}
