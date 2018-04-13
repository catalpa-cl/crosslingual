package de.unidue.ltl.eduscoring.crosslingual.corpusAnalysis;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;
import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;


import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;

public class KLDistanceComparison {
	
	private static double eps = 0.001;

	public static void main(String[] args) throws UIMAException, IOException, ClassNotFoundException {
		String file1 = "id1_en_train_texts.txt";
		String file2 = "id1_en_test_texts.txt";
		computeKLDivergence(file1, file2);
		file1 = "id1_en_train_texts.txt";
		file2 = "id1_translatedToEn_texts.txt";
		computeKLDivergence(file1, file2);
		file1 = "id1_en_test_texts.txt";
		file2 = "id1_translatedToEn_texts.txt";
		computeKLDivergence(file1, file2);
		
		file1 = "id2_en_train_texts.txt";
		file2 = "id2_en_test_texts.txt";
		computeKLDivergence(file1, file2);
		file1 = "id2_en_train_texts.txt";
		file2 = "id2_translatedToEn_texts.txt";
		computeKLDivergence(file1, file2);
		file1 = "id2_en_test_texts.txt";
		file2 = "id2_translatedToEn_texts.txt";
		computeKLDivergence(file1, file2);
		
		file1 = "id10_en_train_texts.txt";
		file2 = "id10_en_test_texts.txt";
		computeKLDivergence(file1, file2);
		file1 = "id10_en_train_texts.txt";
		file2 = "id10_translatedToEn_texts.txt";
		computeKLDivergence(file1, file2);
		file1 = "id10_en_test_texts.txt";
		file2 = "id10_translatedToEn_texts.txt";
		computeKLDivergence(file1, file2);
		
//		file1 = "id1_en_train_texts.txt";
//		file2 = "id2_en_train_texts.txt";
//		computeKLDivergence(file1, file2);
//		file1 = "id1_en_train_texts.txt";
//		file2 = "id10_en_train_texts.txt";
//		computeKLDivergence(file1, file2);
//		file1 = "id2_en_train_texts.txt";
//		file2 = "id10_en_train_texts.txt";
//		computeKLDivergence(file1, file2);
		
	}


	private static void computeKLDivergence(String fileName1, String fileName2) throws UIMAException, ClassNotFoundException, IOException{
		FrequencyDistribution<String> fd1 = computeFrequencyDistribution(fileName1);
		FrequencyDistribution<String> fd2 = computeFrequencyDistribution(fileName2);
		double klDivergence = getDivergence(fd1, fd2);
		double klDivergence2 = getDivergence(fd2, fd1);
		System.out.println("KL Divergence between "+fileName1+" and "+fileName2+": "+klDivergence);
		System.out.println("KL Divergence between "+fileName2+" and "+fileName1+": "+klDivergence2);
	}

	private static FrequencyDistribution<String> computeFrequencyDistribution(String fileName) throws UIMAException, IOException, ClassNotFoundException {
		FrequencyDistribution<String> fd = new FrequencyDistribution<String>();
		CollectionReader reader = createReader(
				TextReader.class,
				TextReader.PARAM_SOURCE_LOCATION, new File(System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/"),
				TextReader.PARAM_LANGUAGE, "en",
				TextReader.PARAM_PATTERNS, new String[] { fileName });

		AnalysisEngineDescription seg = createEngineDescription(StanfordSegmenter.class);
		AnalysisEngineDescription writer = createEngineDescription(FDWriter.class,
				FDWriter.PARAM_NGRAM_N, 3);
		SimplePipeline.runPipeline(reader, seg, writer);
		fd.load(new File("tmp.txt"));
		return fd;
	}



	private static File File(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	private static double getDivergence(FrequencyDistribution<String> fd1,
			FrequencyDistribution<String> fd2) {
		Set<String> allKeys = new HashSet<String>();
		allKeys.addAll(fd1.getKeys());
		allKeys.addAll(fd2.getKeys());
		double size1 = 0;
		double size2 = 0;
		for (String key : fd1.getKeys()){
			size1 += fd1.getCount(key);
		}
		for (String key : fd2.getKeys()){
			size2 += fd2.getCount(key);
		}
//		System.out.println("Size1: "+size1);
//		System.out.println("Size2: "+size2);
		double sum = 0.0;
		for (String key : allKeys){
			double frequency1 = eps;
			double frequency2 = eps;
			if (fd1.contains(key)){
				frequency1 = fd1.getCount(key);
			}
			if (fd2.contains(key)){
				frequency2 = fd2.getCount(key);
			}
			frequency1 = frequency1 / size1; 
			frequency2 = frequency2 / size2; 
			sum += frequency1* (Math.log(frequency1 / frequency2) / Math.log(2));
		}
		return sum;
	}





}
