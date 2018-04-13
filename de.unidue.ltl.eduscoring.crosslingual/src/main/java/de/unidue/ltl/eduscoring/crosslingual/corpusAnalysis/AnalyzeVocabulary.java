package de.unidue.ltl.eduscoring.crosslingual.corpusAnalysis;

import java.util.ArrayList;
import java.util.List;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;
import de.unidue.ltl.eduscoring.crosslingual.experiments.DataSetPaths;

public class AnalyzeVocabulary implements DataSetPaths {

	public static void main(String[] args) {
		
		VocabComparison comparison = new VocabComparison();
		
		AsapAnalysis enTrain = new AsapAnalysis("EN_TRAIN");
		enTrain.readOriginalAsapFile(EN_TRAIN);
		
		AsapAnalysis enTest = new AsapAnalysis("EN_TEST");
		enTest.readOriginalAsapFile(EN_TEST);
		
		AsapAnalysis enDeTrain = new AsapAnalysis("EN_DE_Train");
		enDeTrain.readTranslatedAsapFile(EN_TRAIN, GER_GOOGLE_TRAIN);
		
		AsapAnalysis enDeTest = new AsapAnalysis("EN_DE_Test");
		enDeTest.readTranslatedAsapFile(EN_TEST, GER_GOOGLE_TEST);
		
		AsapAnalysis enDoubleTrain = new AsapAnalysis("EN_DOUBLE_TRAIN");
		enDoubleTrain.readTranslatedAsapFile(EN_TRAIN, EN_TRAIN_DOUBLE_TRANSLATED);
		
		AsapAnalysis enDoubleTest = new AsapAnalysis("EN_DOUBLE_TEST");
		enDoubleTest.readTranslatedAsapFile(EN_TEST, EN_TEST_DOUBLE_TRANSLATED);
		
		AsapAnalysis deEn = new AsapAnalysis("DE_EN");
		deEn.readTranslatedAsapFile(GER_ASAP, EN_GOOGLE_ASAP);
		
		AsapAnalysis de = new AsapAnalysis("DE");
		de.readOriginalAsapFile(GER_ASAP);
		
		AsapAnalysis deDouble = new AsapAnalysis("DE_DOUBLE");
		deDouble.readTranslatedAsapFile(GER_ASAP, GER_ASAP_DOUBLE_TRANSLATED);
		
		
		
//		for(String line : getTopNComparison(deEn)) {
//			System.out.println(line);
//		}
		
		System.out.println();
//		System.out.println("Vocabulary size of EN_TRAIN: " + enTrain.getVocabulary(1,2,10).size());
//		System.out.println("Vocabulary size of EN_TEST: " + enTest.getVocabulary(1,2,10).size());
//		System.out.println("Vocabulary size of EN_TRAIN_DOUBLE: " + enDoubleTrain.getVocabulary(1,2,10).size());
//		System.out.println("Vocabulary size of EN_TRAIN_DOUBLE: " + enDoubleTest.getVocabulary(1,2,10).size());
//		System.out.println("Vocabulary size of EN_DE_TRAIN: " + enDeTrain.getVocabulary(1,2,10).size());
//		System.out.println("Vocabulary size of EN_DE_TEST: " + enDeTest.getVocabulary(1,2,10).size());
//		System.out.println("Vocabulary size of DE: " + de.getVocabulary(1,2,10).size());
//		System.out.println("Vocabulary size of DE_DOUBLE: " + deDouble.getVocabulary(1,2,10).size());
//		System.out.println("Vocabulary size of DE_EN: " + deEn.getVocabulary(1,2,10).size());
//		System.out.println();
//		
//		comparison.compareVocabularies("EN_TRAIN", "EN_TEST", enTrain.getTopKVocabs(1000, 1,2,10), enTest.getTopKVocabs(1000,1,2,10));
//		comparison.compareVocabularies("EN_DE_TRAIN", "EN_DE_TEST", enDeTrain.getTopKVocabs(1000, 1,2,10), enDeTest.getTopKVocabs(1000, 1,2,10));
//		comparison.compareVocabularies("EN_TRAIN_DOUBLE", "EN_TEST_DOUBLE", enDoubleTrain.getTopKVocabs(1000, 1,2,10), enDoubleTest.getTopKVocabs(1000, 1,2,10));
//		comparison.compareVocabularies("DE", "DE_DOUBLE", de.getTopKVocabs(1000, 1,2,10), deDouble.getTopKVocabs(1000, 1,2,10));
//		System.out.println();
//		comparison.compareVocabularies("EN_TRAIN", "DE_EN", enTrain.getTopKVocabs(1000, 1,2,10), deEn.getTopKVocabs(1000, 1,2,10));
//		comparison.compareVocabularies("EN_TRAIN_DOUBLE", "DE_EN", enDoubleTrain.getTopKVocabs(1000, 1,2,10), deEn.getTopKVocabs(1000, 1,2,10));
//		comparison.compareVocabularies("DE", "EN_DE_TEST", de.getTopKVocabs(1000, 1,2,10), enDeTest.getTopKVocabs(1000, 1,2,10));
//		comparison.compareVocabularies("DE_DOUBLE", "EN_DE_TEST", deDouble.getTopKVocabs(1000, 1,2,10), enDeTest.getTopKVocabs(1000, 1,2,10));
//		System.out.println();
//		comparison.compareVocabularies("EN_DE_TRAIN", "DE", enDeTrain.getTopKVocabs(1000, 1,2,10), de.getTopKVocabs(1000, 1,2,10));
//		comparison.compareVocabularies("EN_DE_TRAIN", "DE_DOUBLE", enDeTrain.getTopKVocabs(1000, 1,2,10), deDouble.getTopKVocabs(1000, 1,2,10));
//		comparison.compareVocabularies("DE_EN", "EN_TEST", deEn.getTopKVocabs(1000, 1,2,10), enTest.getTopKVocabs(1000, 1,2,10));
//		comparison.compareVocabularies("DE_EN", "EN_TEST_DOUBLE", deEn.getTopKVocabs(1000, 1,2,10), enDoubleTest.getTopKVocabs(1000, 1,2,10));	
//		
//		System.out.println();
//		comparison.compareVocabularies("EN_TRAIN_ID2", "EN_TEST_ID2", enTrain.getTopKVocabs(1000, 2), enTest.getTopKVocabs(1000,2));
//		comparison.compareVocabularies("EN_DE_TRAIN_ID2", "EN_DE_TEST_ID2", enDeTrain.getTopKVocabs(1000, 2), enDeTest.getTopKVocabs(1000, 2));
//		comparison.compareVocabularies("EN_TRAIN_DOUBLE_ID2", "EN_TEST_DOUBLE_ID2", enDoubleTrain.getTopKVocabs(1000, 2), enDoubleTest.getTopKVocabs(1000, 2));
//		comparison.compareVocabularies("DE_ID2", "DE_DOUBLE_ID2", de.getTopKVocabs(1000, 2), deDouble.getTopKVocabs(1000, 2));
//		System.out.println();
//		comparison.compareVocabularies("EN_TRAIN_ID2", "DE_EN_ID2", enTrain.getTopKVocabs(1000, 2), deEn.getTopKVocabs(1000, 2));
//		comparison.compareVocabularies("EN_TRAIN_DOUBLE_ID2", "DE_EN_ID2", enDoubleTrain.getTopKVocabs(1000, 2), deEn.getTopKVocabs(1000, 2));
//		comparison.compareVocabularies("DE_ID2", "EN_DE_TEST_ID2", de.getTopKVocabs(1000, 2), enDeTest.getTopKVocabs(1000, 2));
//		comparison.compareVocabularies("DE_DOUBLE_ID2", "EN_DE_TEST_ID2", deDouble.getTopKVocabs(1000, 2), enDeTest.getTopKVocabs(1000, 2));
//		System.out.println();
//		comparison.compareVocabularies("EN_DE_TRAIN_ID2", "DE_ID2", enDeTrain.getTopKVocabs(1000, 2), de.getTopKVocabs(1000, 2));
//		comparison.compareVocabularies("EN_DE_TRAIN_ID2", "DE_DOUBLE_ID2", enDeTrain.getTopKVocabs(1000, 2), deDouble.getTopKVocabs(1000, 2));
//		comparison.compareVocabularies("DE_EN_ID2", "EN_TEST_ID2", deEn.getTopKVocabs(1000, 2), enTest.getTopKVocabs(1000, 2));
//		comparison.compareVocabularies("DE_EN_ID2", "EN_TEST_DOUBLE_ID2", deEn.getTopKVocabs(1000, 2), enDoubleTest.getTopKVocabs(1000, 2));	
//		
		System.out.println();
		comparison.compareVocabularies("EN_TRAIN_ID1", "EN_TEST_ID1", enTrain.getTopKVocabs(1000, 1), enTest.getTopKVocabs(1000,1));
		comparison.compareVocabularies("EN_TRAIN_ID2", "EN_TEST_ID2", enTrain.getTopKVocabs(1000, 2), enTest.getTopKVocabs(1000,2));
		comparison.compareVocabularies("EN_TRAIN_ID10", "EN_TEST_ID10", enTrain.getTopKVocabs(1000, 10), enTest.getTopKVocabs(1000,10));
		comparison.compareVocabularies("EN_TRAIN_ID1", "EN_TEST_ID1", enTrain.getVocabulary(1), enTest.getVocabulary(1));
		comparison.compareVocabularies("EN_TRAIN_ID2", "EN_TEST_ID2", enTrain.getVocabulary(2), enTest.getVocabulary(2));
		comparison.compareVocabularies("EN_TRAIN_ID10", "EN_TEST_ID10", enTrain.getVocabulary(10), enTest.getVocabulary(10));
		
		System.out.println();
		comparison.compareVocabularies("EN_DE_TRAIN_ID1", "EN_DE_TEST_ID1", enDeTrain.getTopKVocabs(1000, 1), enDeTest.getTopKVocabs(1000,1));
		comparison.compareVocabularies("EN_DE_TRAIN_ID2", "EN_DE_TEST_ID2", enDeTrain.getTopKVocabs(1000, 2), enDeTest.getTopKVocabs(1000,2));
		comparison.compareVocabularies("EN_DE_TRAIN_ID10", "EN_DE_TEST_ID10", enDeTrain.getTopKVocabs(1000, 10), enDeTest.getTopKVocabs(1000,10));
		comparison.compareVocabularies("EN_DE_TRAIN_ID1", "EN_DE_TEST_ID1", enDeTrain.getVocabulary(1), enDeTest.getVocabulary(1));
		comparison.compareVocabularies("EN_DE_TRAIN_ID2", "EN_DE_TEST_ID2", enDeTrain.getVocabulary(2), enDeTest.getVocabulary(2));
		comparison.compareVocabularies("EN_DE_TRAIN_ID10", "EN_DE_TEST_ID10", enDeTrain.getVocabulary(10), enDeTest.getVocabulary(10));
		
		System.out.println();
		comparison.compareVocabularies("EN_DOUBLE_TRAIN_ID1", "EN_DOUBLE_TEST_ID1", enDoubleTrain.getTopKVocabs(1000, 1), enDoubleTest.getTopKVocabs(1000,1));
		comparison.compareVocabularies("EN_DOUBLE_TRAIN_ID2", "EN_DOUBLE_TEST_ID2", enDoubleTrain.getTopKVocabs(1000, 2), enDoubleTest.getTopKVocabs(1000,2));
		comparison.compareVocabularies("EN_DOUBLE_TRAIN_ID10", "EN_DOUBLE_TEST_ID10", enDoubleTrain.getTopKVocabs(1000, 10), enDoubleTest.getTopKVocabs(1000,10));
		comparison.compareVocabularies("EN_DOUBLE_TRAIN_ID1", "EN_DOUBLE_TEST_ID1", enDoubleTrain.getVocabulary(1), enDoubleTest.getVocabulary(1));
		comparison.compareVocabularies("EN_DOUBLE_TRAIN_ID2", "EN_DOUBLE_TEST_ID2", enDoubleTrain.getVocabulary(2), enDoubleTest.getVocabulary(2));
		comparison.compareVocabularies("EN_DOUBLE_TRAIN_ID10", "EN_DOUBLE_TEST_ID10", enDoubleTrain.getVocabulary(10), enDoubleTest.getVocabulary(10));
		
		System.out.println();
		comparison.compareVocabularies("DE_ID1", "DE_DOUBLE_ID1", de.getTopKVocabs(1000, 1), deDouble.getTopKVocabs(1000,1));
		comparison.compareVocabularies("DE_ID2", "DE_DOUBLE_ID2", de.getTopKVocabs(1000, 2), deDouble.getTopKVocabs(1000,2));
		comparison.compareVocabularies("DE_ID10", "DE_DOUBLE_ID10", de.getTopKVocabs(1000, 10), deDouble.getTopKVocabs(1000,10));
		comparison.compareVocabularies("DE_ID1", "DE_DOUBLE_ID1", de.getVocabulary(1), deDouble.getVocabulary(1));
		comparison.compareVocabularies("DE_ID2", "DE_DOUBLE_ID2", de.getVocabulary(2), deDouble.getVocabulary(2));
		comparison.compareVocabularies("DE_ID10", "DE_DOUBLE_ID10", de.getVocabulary(10), deDouble.getVocabulary(10));
		
		System.out.println();
		comparison.compareVocabularies("EN_DE_TRAIN_ID1", "DE_ID1", enDeTrain.getTopKVocabs(1000, 1), de.getTopKVocabs(1000,1));
		comparison.compareVocabularies("EN_DE_TRAIN_ID2", "DE_ID2", enDeTrain.getTopKVocabs(1000, 2), de.getTopKVocabs(1000,2));
		comparison.compareVocabularies("EN_DE_TRAIN_ID10", "DE_ID10", enDeTrain.getTopKVocabs(1000, 10), de.getTopKVocabs(1000,10));
		comparison.compareVocabularies("EN_DE_TRAIN_ID1", "DE_ID1", enDeTrain.getVocabulary(1), de.getVocabulary(1));
		comparison.compareVocabularies("EN_DE_TRAIN_ID2", "DE_ID2", enDeTrain.getVocabulary(2), de.getVocabulary(2));
		comparison.compareVocabularies("EN_DE_TRAIN_ID10", "DE_ID10", enDeTrain.getVocabulary(10), de.getVocabulary(10));

	}
	
	private static List<String> getTopNComparison(AsapAnalysis asap){
		int n = 100;
		
		List<String> topNWordList = new ArrayList<String>();
		FrequencyDistribution<String> id1 = asap.getVocabDistribution(1);
		FrequencyDistribution<String> id2 = asap.getVocabDistribution(2);
		FrequencyDistribution<String> id10 = asap.getVocabDistribution(10);
		
		topNWordList.add("ID 1\t\t\tID 2\t\t\tID 10");
		
		for(String entry : id1.getMostFrequentSamples(n)) {
			topNWordList.add(entry + " (" + id1.getCount(entry) + ")\t\t\t");
		}
		
		int i = 1;
		for(String entry : id2.getMostFrequentSamples(n)) {
			String tmpEntry = topNWordList.get(i);
			tmpEntry += entry + " (" + id2.getCount(entry)+")\t\t\t";
			topNWordList.set(i, tmpEntry);
			i++;
		}
		
		i = 1;
		for(String entry : id10.getMostFrequentSamples(n)) {
			String tmpEntry = topNWordList.get(i);
			tmpEntry += entry + " (" + id10.getCount(entry)+")";
			topNWordList.set(i, tmpEntry);
			i++;
		}
		return topNWordList;
	}

}
