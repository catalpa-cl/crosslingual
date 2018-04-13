package de.unidue.ltl.eduscoring.crosslingual.experiments;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.unidue.ltl.evaluation.measure.agreement.ConfusionMatrix;

public class BaselineTranslationAnalysis {
	
	private String resultsDir;
	private String classificationFileName;
	private String filePath = "src/main/dkpro/datasets/asap/";
	
	HashMap<Integer, String> original;
	HashMap<Integer, String> deepL;
	HashMap<Integer, String> google;
	
	public BaselineTranslationAnalysis(String resultsDir, String classificationFileName) {
		this.resultsDir = resultsDir;
		this.classificationFileName = classificationFileName;
		
		this.original = new HashMap<Integer, String>();
		this.deepL = new HashMap<Integer, String>();
		this.google = new HashMap<Integer, String>();
	}

	public static void main(String[] args) throws Exception {
		
		BaselineTranslationAnalysis bta = new BaselineTranslationAnalysis("src/main/dkpro/org.dkpro.lab/repository", "id2outcome.txt");
		bta.createIdToTextsHashMaps("test_public_repaired.txt", "asap_test_public_de_deepl_translated.txt","asap_test_public_de_translated.txt", 2);
		
		List<String> deepL = bta.getClassification("ASAP", "2", "DE_DeepL_Translate");
		List<String> google = bta.getClassification("ASAP", "2", "DE_Translate");
		
		List<String> evaluationResults = bta.compareClassificationResults(deepL, google);
		//List could be serialized. Maybe useful for a later version.
		for(String item : evaluationResults) {
			System.out.println(item);
		}
	}
	
	//Reads the original file and two translation files. Stores the texts and translations mapped to the IDs in
	//an internal HashMap
	private void createIdToTextsHashMaps(String originalFile, String deepLFile, String googleFile, int id) {
		try {
			BufferedReader br_or = new BufferedReader(new FileReader(filePath + originalFile));
			BufferedReader br_de1 = new BufferedReader(new FileReader(filePath + deepLFile));
			BufferedReader br_de2 = new BufferedReader(new FileReader(filePath + googleFile));
			String or_line;
			String deepL_line;
			String google_line;
			while((or_line = br_or.readLine()) != null) {
				deepL_line = br_de1.readLine();
				google_line = br_de2.readLine();
				
				//Omit first line
				if(or_line.startsWith(("Id"))) {
					or_line = br_or.readLine();
					deepL_line = br_de1.readLine();
					google_line = br_de2.readLine();
				}
				
				String[] line = or_line.split("\t");
				int essaySetId = Integer.parseInt(line[1]);
				//Only read the desired ID
				if(essaySetId == id) {
					int textId = Integer.parseInt(line[0]);
					
					this.original.put(textId, line[4]);
					this.deepL.put(textId, deepL_line);
					this.google.put(textId, google_line);
				}
				
			}
			br_or.close();
			br_de1.close();
			br_de2.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Actual analysis. 
	private List<String> compareClassificationResults(List<String> deepLClassification, List<String> googleClassification) throws Exception {
		
		if(deepLClassification.size() == googleClassification.size()) {
			//Lists that store the difference between the assigned system score and the gold standard
			List<Integer> noDifferenceDeepL = new ArrayList<Integer>();
			List<Integer> onePointDifferenceDeepL = new ArrayList<Integer>();
			List<Integer> twoPointsDifferenceDeepL = new ArrayList<Integer>();
			List<Integer> threePointsDifferenceDeepL = new ArrayList<Integer>();
			
			List<Integer> noDifferenceGoogle = new ArrayList<Integer>();
			List<Integer> onePointDifferenceGoogle = new ArrayList<Integer>();
			List<Integer> twoPointsDifferenceGoogle = new ArrayList<Integer>();
			List<Integer> threePointsDifferenceGoogle = new ArrayList<Integer>();
			
			//Mappings ID->Score
			HashMap<Integer, Integer> goldMap = new HashMap<Integer, Integer>();
			HashMap<Integer, Integer> predictionGoogleMap = new HashMap<Integer, Integer>();
			HashMap<Integer, Integer> predictionDeepLMap = new HashMap<Integer, Integer>();

			for (int i = 0; i < deepLClassification.size(); i++) {
				//Getting the IDs
				String[] tmpDeepL = deepLClassification.get(i).split("=");
				String[] tmpGoogle = googleClassification.get(i).split("=");

				int idDeepL = Integer.parseInt(tmpDeepL[0]);
				int idGoogle = Integer.parseInt(tmpGoogle[0]);
				
				if(idDeepL == idGoogle) {
					//Getting the assigned scores.
					int gold = Integer.parseInt(tmpDeepL[1].split(";")[1]);
					int predictionDeepL = Integer.parseInt(tmpDeepL[1].split(";")[0]);
					int predictionGoogle = Integer.parseInt(tmpGoogle[1].split(";")[0]);
					
					goldMap.put(idDeepL, gold);
					predictionDeepLMap.put(idDeepL, predictionDeepL);
					predictionGoogleMap.put(idGoogle, predictionGoogle);
					
					int differenceDeepL = Math.abs(gold - predictionDeepL);
					int differenceGoogle = Math.abs(gold - predictionGoogle);
					
					switch (differenceDeepL) {
					case 0:
						noDifferenceDeepL.add(idDeepL);
						break;
					case 1:
						onePointDifferenceDeepL.add(idDeepL);
						break;
					case 2:
						twoPointsDifferenceDeepL.add(idDeepL);
						break;
					case 3:
						threePointsDifferenceDeepL.add(idDeepL);
						break;
					}	
					
					switch (differenceGoogle) {
					case 0:
						noDifferenceGoogle.add(idGoogle);
						break;
					case 1:
						onePointDifferenceGoogle.add(idGoogle);
						break;
					case 2:
						twoPointsDifferenceGoogle.add(idGoogle);
						break;
					case 3:
						threePointsDifferenceGoogle.add(idGoogle);
						break;
					}	
				} else {
					throw new Exception("Different IDs!");
				}

				
			}
			
			//Generating output text
			List<String> evaluation = new ArrayList<String>();
			
			evaluation.add("Results of the Evaluation:");
			evaluation.add("");
			evaluation.add("DeepL: ");
			evaluation.add("Completely correct: " + noDifferenceDeepL.size() + " -> " + ((double)noDifferenceDeepL.size()/deepLClassification.size()*100) + "%");
			evaluation.add("One point difference: " + onePointDifferenceDeepL.size() + " -> " + ((double)onePointDifferenceDeepL.size()/deepLClassification.size()*100) + "%");
			evaluation.add("Two points difference: " + twoPointsDifferenceDeepL.size() + " -> " + ((double)twoPointsDifferenceDeepL.size()/deepLClassification.size()*100) + "%");
			evaluation.add("Three points difference: " + threePointsDifferenceDeepL.size() + " -> " + ((double)threePointsDifferenceDeepL.size()/deepLClassification.size()*100) + "%");
			
			evaluation.add("");
			evaluation.add("Confusion matrix of the DeepL system:");
			String confDeepL = new ConfusionMatrix(new ArrayList<Integer>(goldMap.values()), new ArrayList<Integer>(predictionDeepLMap.values()), 0,1,2,3).getConfusionMatrixSerialization();
			String[] matrixDeepL = confDeepL.split(",");
			String tmp = "\t";
			for(int i = 0; i < Math.sqrt(matrixDeepL.length); i++) {
				tmp+= i + "\t";
			}
			evaluation.add(tmp);
			int j = 0;
			tmp = j + "\t";
			for(int i = 0; i < matrixDeepL.length; i++) {
				tmp += matrixDeepL[i] + "\t";
				if((i+1)%Math.sqrt((double)matrixDeepL.length) == 0) {
					evaluation.add(tmp);
					j++;
					tmp = j + "\t";
				}
			}
			evaluation.add("");
			
			evaluation.add("Google: ");
			evaluation.add("Completely correct: " + noDifferenceGoogle.size() + " -> " + ((double)noDifferenceGoogle.size()/deepLClassification.size()*100) + "%");
			evaluation.add("One point difference: " + onePointDifferenceGoogle.size() + " -> " + ((double)onePointDifferenceGoogle.size()/deepLClassification.size()*100) + "%");
			evaluation.add("Two points difference: " + twoPointsDifferenceGoogle.size() + " -> " + ((double)twoPointsDifferenceGoogle.size()/deepLClassification.size()*100) + "%");
			evaluation.add("Three points difference: " + threePointsDifferenceGoogle.size() + " -> " + ((double)threePointsDifferenceGoogle.size()/deepLClassification.size()*100) + "%");
			
			evaluation.add("");
			evaluation.add("Confusion matrix of the Google system:");
			String confGoogle = new ConfusionMatrix(new ArrayList<Integer>(goldMap.values()), new ArrayList<Integer>(predictionGoogleMap.values()), 0,1,2,3).getConfusionMatrixSerialization();
			String[] matrixGoogle = confGoogle.split(",");
			tmp = "\t";
			for(int i = 0; i < Math.sqrt(matrixGoogle.length); i++) {
				tmp+= i + "\t";
			}
			evaluation.add(tmp);
			j = 0;
			tmp = j + "\t";
			for(int i = 0; i < matrixGoogle.length; i++) {
				tmp += matrixGoogle[i] + "\t";
				if((i+1)%Math.sqrt((double)matrixGoogle.length) == 0) {
					evaluation.add(tmp);
					j++;
					tmp = j + "\t";
				}
			}
			
			Integer[] twoPointDifferenceOnGoogle = new Integer[4];
			Integer[] threePointDifferenceOnGoogle = new Integer[4];
			for(int i = 0; i < 4; i++) {
				twoPointDifferenceOnGoogle[i] = 0;
				threePointDifferenceOnGoogle[i] = 0;
			}
			
			evaluation.add("");
			evaluation.add("DeepL translations with two points difference:");
			for(Integer id : twoPointsDifferenceDeepL) {
				evaluation.add("Gold: " + goldMap.get(id) + " - DeepL: " + predictionDeepLMap.get(id) + " - Google: " + predictionGoogleMap.get(id));
				evaluation.add("Original: " + this.original.get(id));
				evaluation.add("DeepL: " + this.deepL.get(id));
				evaluation.add("Google: " + this.google.get(id));
				evaluation.add("-----------------------------------------");
				if(noDifferenceGoogle.contains(id)) {
					twoPointDifferenceOnGoogle[0]++;
				} else if(onePointDifferenceGoogle.contains(id)) {
					twoPointDifferenceOnGoogle[1]++;
				} else if(twoPointsDifferenceGoogle.contains(id)) {
					twoPointDifferenceOnGoogle[2]++;
				} else if(threePointsDifferenceGoogle.contains(id)) {
					twoPointDifferenceOnGoogle[3]++;
				}
			}
			
			evaluation.add("Of the " + twoPointsDifferenceDeepL.size() + " short answers the DeepL system scored with two points difference to the gold standard, the Google system scored ");
			evaluation.add(twoPointDifferenceOnGoogle[0] + " correctly. -> " + (double)twoPointDifferenceOnGoogle[0]/twoPointsDifferenceDeepL.size() * 100 +"%");
			evaluation.add(twoPointDifferenceOnGoogle[1] + " with 1 point difference. -> " + (double)twoPointDifferenceOnGoogle[1]/twoPointsDifferenceDeepL.size() * 100 +"%");
			evaluation.add(twoPointDifferenceOnGoogle[2] + " also with 2 points difference. -> " + (double)twoPointDifferenceOnGoogle[2]/twoPointsDifferenceDeepL.size() * 100 +"%");
			evaluation.add(twoPointDifferenceOnGoogle[3] + " with 3 points difference. -> " + (double)twoPointDifferenceOnGoogle[3]/twoPointsDifferenceDeepL.size() * 100 +"%");
			
			evaluation.add("");
			evaluation.add("DeepL translations with three points difference:");
			for(Integer id : threePointsDifferenceDeepL) {
				evaluation.add("Gold: " + goldMap.get(id) + " - DeepL: " + predictionDeepLMap.get(id) + " - Google: " + predictionGoogleMap.get(id));
				evaluation.add("Original: " + this.original.get(id));
				evaluation.add("DeepL: " + this.deepL.get(id));
				evaluation.add("Google: " + this.google.get(id));
				evaluation.add("-----------------------------------------");
				
				if(noDifferenceGoogle.contains(id)) {
					threePointDifferenceOnGoogle[0]++;
				} else if(onePointDifferenceGoogle.contains(id)) {
					threePointDifferenceOnGoogle[1]++;
				} else if(twoPointsDifferenceGoogle.contains(id)) {
					threePointDifferenceOnGoogle[2]++;
				} else if(threePointsDifferenceGoogle.contains(id)) {
					threePointDifferenceOnGoogle[3]++;
				}
			}
			
			evaluation.add("Of the " + threePointsDifferenceDeepL.size() + " short answers the DeepL system scored with three points difference to the gold standard, the Google system scored ");
			evaluation.add(threePointDifferenceOnGoogle[0] + " correctly. -> " + (double)threePointDifferenceOnGoogle[0]/twoPointsDifferenceDeepL.size() * 100 +"%");
			evaluation.add(threePointDifferenceOnGoogle[1] + " with 1 point difference. -> " + (double)threePointDifferenceOnGoogle[1]/threePointsDifferenceDeepL.size() * 100 +"%");
			evaluation.add(threePointDifferenceOnGoogle[2] + " with 2 points difference. -> " + (double)threePointDifferenceOnGoogle[2]/threePointsDifferenceDeepL.size() * 100 +"%");
			evaluation.add(threePointDifferenceOnGoogle[3] + " also with 3 points difference. -> " + (double)threePointDifferenceOnGoogle[3]/threePointsDifferenceDeepL.size() * 100 +"%");
			
			return evaluation;
			
		} else {
			throw new Exception("Lists have different length!");
		}
	}
	
	//Reads the classifications into a list
	private List<String> getClassification(String dataSet, String id, String experimentName){
		List<String> classificationResults = new ArrayList<String>();
		File inputFile = new File(this.resultsDir);
		File[] fileArray = inputFile.listFiles();
		
		for(File f : fileArray) {
			if(f.isDirectory() && f.getName().startsWith("Weka")) {
				//Only read the files with the desired data set and the desired ID
				if(f.getName().contains(dataSet + "_" + id + "_" + experimentName)) {

					File classificationFile = new File(f.getAbsolutePath() + "/" + this.classificationFileName);
					try {
						BufferedReader br = new BufferedReader(new FileReader(classificationFile));
						String line;
						while((line = br.readLine()) != null) {
							if(line.contains("_")) {
								classificationResults.add(line.substring((line.lastIndexOf("_")+1), line.length()-3));
							}
						}
						br.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return classificationResults;
	}

}
