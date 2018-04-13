package de.unidue.ltl.eduscoring.crosslingual.experiments;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.dkpro.lab.task.Dimension;
import org.dkpro.lab.task.ParameterSpace;

import de.unidue.ltl.eduscoring.crosslingual.io.Asap2Reader_Translate;
import de.unidue.ltl.eduscoring.crosslingual.io.PowerGradingReader_Translate;
import de.unidue.ltl.escrito.io.shortanswer.Asap2Reader;
import de.unidue.ltl.escrito.io.shortanswer.PowerGradingReader;





public class BaselineExperiments
extends Experiments_ImplBase
{
	
	private static final String ResourcesFilePath = "src/main/dkpro";
	private static final String ASAP_CORPUS_LABEL = "ASAP";
	public static final String POWER_GRADING_CORPUS_LABEL = "PowerGrading";

	public static void main(String[] args) 
			throws Exception
	{
	//	System.setProperty("DKPRO_HOME", "src/main/dkpro");

		runAsap(false, Asap2Reader.promptIds);	
		runAsapTranslate(false, "asap_train_de_translated.txt", "asap_test_public_de_translated.txt", "DE_Translate", Asap2Reader.promptIds);
		runAsapTranslate(false, "asap_train_de_deepl_translated.txt", "asap_test_public_de_deepl_translated.txt", "DE_DeepL_Translate", Asap2Reader.promptIds);
		runAsapTranslate(false, "asap_train_ru_translated.txt", "asap_test_public_text_ru_translated.txt", "RU_Translate", Asap2Reader.promptIds);	
	}



	private static void runAsap(boolean useCV, Integer ... questionIds) 
			throws Exception
	{
		for (int id : questionIds) {
			String trainData           = ResourcesFilePath+"/datasets/asap/train_repaired.txt";
			String testData            = ResourcesFilePath+"/datasets/asap/test_public_repaired.txt";

			CollectionReaderDescription readerTrain = CollectionReaderFactory.createReaderDescription(
					Asap2Reader.class,
					Asap2Reader.PARAM_INPUT_FILE, trainData,
					Asap2Reader.PARAM_PROMPT_IDS, id);

			CollectionReaderDescription readerTest = CollectionReaderFactory.createReaderDescription(
					Asap2Reader.class,
					Asap2Reader.PARAM_INPUT_FILE, testData,
					Asap2Reader.PARAM_PROMPT_IDS, id);
			runBaselineExperiment(ASAP_CORPUS_LABEL+"_"+id+"", readerTrain, readerTest, useCV);
		}
	}

	private static void runAsapTranslate(boolean useCV, String translatedTrain, String translatedTest, String experimentName, Integer ... questionIds) 
			throws Exception
	{
		for (int id : questionIds) {
			String trainData           = ResourcesFilePath+"/datasets/asap/train_repaired.txt";
			String testData            = ResourcesFilePath+"/datasets/asap/test_public_repaired.txt";

			CollectionReaderDescription readerTrain = CollectionReaderFactory.createReaderDescription(
					Asap2Reader_Translate.class,
					Asap2Reader_Translate.PARAM_INPUT_FILE, trainData,
					Asap2Reader_Translate.PARAM_ESSAY_SET_ID, id,
					Asap2Reader_Translate.PARAM_DICTIONARY_EN, "src/main/resources/textsForTranslations/asap_train_textsForTranslation.tsv",
					Asap2Reader_Translate.PARAM_DICTIONARY_DE, "src/main/resources/translatedTexts/" + translatedTrain);

			CollectionReaderDescription readerTest = CollectionReaderFactory.createReaderDescription(
					Asap2Reader_Translate.class,
					Asap2Reader_Translate.PARAM_INPUT_FILE, testData,
					Asap2Reader_Translate.PARAM_ESSAY_SET_ID, id,
					Asap2Reader_Translate.PARAM_DICTIONARY_EN, "src/main/resources/textsForTranslations/asap_test_public_text.txt",
					Asap2Reader_Translate.PARAM_DICTIONARY_DE, "src/main/resources/translatedTexts/" + translatedTest);

			runBaselineExperiment(ASAP_CORPUS_LABEL+"_"+id+"_"+experimentName, readerTrain, readerTest, useCV);
		}
	}


	
	
	@SuppressWarnings("unused")
	private static void runPowerGradingTranslate(boolean useCV, Integer ... questionIds) 
			throws Exception
	{
		String trainData = ResourcesFilePath+"/datasets/powergrading/train_70.txt";
		String testData = ResourcesFilePath+"/datasets/powergrading/test_30.txt";
		for (int id : questionIds) {
			CollectionReaderDescription readerTrain = CollectionReaderFactory.createReaderDescription(
					PowerGradingReader_Translate.class,
					PowerGradingReader_Translate.PARAM_INPUT_FILE, trainData,
					PowerGradingReader_Translate.PARAM_QUESTION_ID, id,
					PowerGradingReader_Translate.PARAM_DICTIONARY_EN, "src/main/resources/textsForTranslations/all_unique.txt",
					PowerGradingReader_Translate.PARAM_DICTIONARY_DE, "src/main/resources/translatedTexts/all_unique_translated.txt");

			CollectionReaderDescription readerTest = CollectionReaderFactory.createReaderDescription(
					PowerGradingReader_Translate.class,
					PowerGradingReader_Translate.PARAM_INPUT_FILE, testData,
					PowerGradingReader_Translate.PARAM_QUESTION_ID, id,
					PowerGradingReader_Translate.PARAM_DICTIONARY_EN, "src/main/resources/textsForTranslations/all_unique.txt",
					PowerGradingReader_Translate.PARAM_DICTIONARY_DE, "src/main/resources/translatedTexts/all_unique_translated.txt");

			runBaselineExperiment(POWER_GRADING_CORPUS_LABEL+"_"+id+"_Translate", readerTrain, readerTest, useCV);
		}
	}


	@SuppressWarnings("unused")
	private static void runPowerGrading(boolean useCV, Integer ... questionIds) 
			throws Exception
	{
		for (int id : questionIds) {
			String trainData = ResourcesFilePath+"/datasets/powergrading/train_70.txt";
			String testData  = ResourcesFilePath+"/datasets/powergrading/test_30.txt";
			CollectionReaderDescription readerTrain = CollectionReaderFactory.createReaderDescription(
					PowerGradingReader.class,
					PowerGradingReader.PARAM_INPUT_FILE, trainData,
					PowerGradingReader.PARAM_PROMPT_IDS, id);

			CollectionReaderDescription readerTest = CollectionReaderFactory.createReaderDescription(
					PowerGradingReader.class,
					PowerGradingReader.PARAM_INPUT_FILE, testData,
					PowerGradingReader.PARAM_PROMPT_IDS, id);
			runBaselineExperiment(POWER_GRADING_CORPUS_LABEL+"_"+id+"", readerTrain, readerTest, useCV);
		}
	}



	private static void runBaselineExperiment(String experimentName, 
			CollectionReaderDescription readerTrain, 
			CollectionReaderDescription readerTest, 
			boolean useCV)
					throws Exception

	{     
		// configure training and test data reader dimension
		// train/test will use both, while cross-validation will only use the train part
		Map<String, Object> dimReaders = new HashMap<String, Object>();
		dimReaders.put(DIM_READER_TRAIN, readerTrain);
		dimReaders.put(DIM_READER_TEST, readerTest);


		Dimension<String> learningDims = Dimension.create(DIM_LEARNING_MODE, LM_SINGLE_LABEL);
		Dimension<Map<String, Object>> learningsArgsDims = getStandardWekaClassificationArgsDim();

		@SuppressWarnings("unchecked")
		ParameterSpace pSpace = new ParameterSpace(
				Dimension.createBundle("readers", dimReaders),
				learningDims,
				Dimension.create(DIM_FEATURE_MODE, FM_UNIT),
				FeatureSettings.getFeatureSetsDimBaseline(),
				//FeatureSettings.getFeatureSetsDimBaselineStacking(),
				learningsArgsDims
				);

		BaselineExperiments experiment = new BaselineExperiments();
		experiment.runTrainTest(pSpace, experimentName, getPreprocessing("en"));

	}




}
