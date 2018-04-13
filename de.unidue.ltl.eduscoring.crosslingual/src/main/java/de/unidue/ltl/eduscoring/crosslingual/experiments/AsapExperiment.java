package de.unidue.ltl.eduscoring.crosslingual.experiments;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.dkpro.lab.task.Dimension;
import org.dkpro.lab.task.ParameterSpace;

import de.unidue.ltl.eduscoring.crosslingual.io.Asap2Reader_Translate;
import de.unidue.ltl.escrito.io.shortanswer.Asap2Reader;

public class AsapExperiment extends Experiments_ImplBase {

	protected void runAsapBaselineExperiment(String experimentName, String trainData, String testData,
			String languageCode, boolean useCV, boolean useLearningCurve, Integer... questionIds) throws Exception {
		for (int id : questionIds) {
			CollectionReaderDescription readerTrain = CollectionReaderFactory.createReaderDescription(Asap2Reader.class,
					Asap2Reader.PARAM_INPUT_FILE, trainData, Asap2Reader.PARAM_PROMPT_IDS, id);

			CollectionReaderDescription readerTest = CollectionReaderFactory.createReaderDescription(Asap2Reader.class,
					Asap2Reader.PARAM_INPUT_FILE, testData, Asap2Reader.PARAM_PROMPT_IDS, id);
			if(useLearningCurve) {
				runLearningCurveExperiment(experimentName, readerTrain, readerTest, languageCode);
			} else {
				runBaselineExperiment(experimentName + "_" + id + "", readerTrain, readerTest, useCV, languageCode);
			}
		}
	}

	protected void runAsapTranslate(String experimentName, String languageCode, boolean useCV, boolean useLearningCurve, String trainData,
			String trainDict, String trainTranslation, String testData, String testDict, String testTranslation,
			Integer... questionIds) throws Exception {
		for (int id : questionIds) {

			CollectionReaderDescription readerTrain = CollectionReaderFactory.createReaderDescription(
					Asap2Reader_Translate.class, Asap2Reader_Translate.PARAM_INPUT_FILE, trainData,
					Asap2Reader_Translate.PARAM_ESSAY_SET_ID, id, Asap2Reader_Translate.PARAM_DICTIONARY_EN, trainDict,
					Asap2Reader_Translate.PARAM_DICTIONARY_DE, trainTranslation);

			CollectionReaderDescription readerTest = CollectionReaderFactory.createReaderDescription(
					Asap2Reader_Translate.class, Asap2Reader_Translate.PARAM_INPUT_FILE, testData,
					Asap2Reader_Translate.PARAM_ESSAY_SET_ID, id, Asap2Reader_Translate.PARAM_DICTIONARY_EN, testDict,
					Asap2Reader_Translate.PARAM_DICTIONARY_DE, testTranslation);

			if(useLearningCurve) {
				runLearningCurveExperiment(experimentName, readerTrain, readerTest, languageCode);
			} else {
				runBaselineExperiment(experimentName + "_" + id + "", readerTrain, readerTest, useCV, languageCode);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void runBaselineExperiment(String experimentName, CollectionReaderDescription readerTrain,
			CollectionReaderDescription readerTest, boolean useCV, String languageCode) throws Exception {
		// configure training and test data reader dimension
		// train/test will use both, while cross-validation will only use the train part
		Map<String, Object> dimReaders = new HashMap<String, Object>();
		dimReaders.put(DIM_READER_TRAIN, readerTrain);
		if(!useCV) {
			dimReaders.put(DIM_READER_TEST, readerTest);
		}

		Dimension<String> learningDims = Dimension.create(DIM_LEARNING_MODE, LM_SINGLE_LABEL);
		Dimension<Map<String, Object>> learningsArgsDims = getStandardWekaClassificationArgsDim();

		ParameterSpace pSpace = null;
		if (useCV) {
			pSpace = new ParameterSpace(Dimension.createBundle("readers", dimReaders), learningDims,
					Dimension.create(DIM_FEATURE_MODE, FM_UNIT), Dimension.create("DIMENSION_ITERATIONS", 1000),
					Dimension.create("DIMENSION_NUMBER_OF_TRAINING_INSTANCES", 270),
					FeatureSettings.getFeatureSetsDimBaseline(),
					// FeatureSettings.getFeatureSetsDimBaselineStacking(),
					learningsArgsDims);
			this.runCrossValidation(pSpace, experimentName, getPreprocessing(languageCode), 10);
		} else {
			pSpace = new ParameterSpace(Dimension.createBundle("readers", dimReaders), learningDims,
					Dimension.create(DIM_FEATURE_MODE, FM_UNIT), FeatureSettings.getFeatureSetsDimBaseline(),
					// FeatureSettings.getFeatureSetsDimBaselineStacking(),
					learningsArgsDims);
			this.runTrainTest(pSpace, experimentName, getPreprocessing(languageCode));
		}

	}
	
	@SuppressWarnings("unchecked")
	private void runLearningCurveExperiment(String experimentName, 
			CollectionReaderDescription readerTrain, 
			CollectionReaderDescription readerTest, String languageCode)
					throws Exception
	{     
		Map<String, Object> dimReaders = new HashMap<String, Object>();
		dimReaders.put(DIM_READER_TRAIN, readerTrain);
		dimReaders.put(DIM_READER_TEST, readerTest);

		Dimension<String> learningDims = Dimension.create(DIM_LEARNING_MODE, LM_SINGLE_LABEL);
		Dimension<Map<String, Object>> learningsArgsDims = getWekaLearningCurveClassificationArgsDim();
		int[] NUMBER_OF_TRAINING_INSTANCES = new int[] {270};

		ParameterSpace pSpace = new ParameterSpace(
				Dimension.createBundle("readers", dimReaders),
				learningDims,
				Dimension.create(DIM_FEATURE_MODE, FM_UNIT),
				Dimension.create("dimension_iterations", 100),
				Dimension.create("dimension_number_of_training_instances", NUMBER_OF_TRAINING_INSTANCES),
				FeatureSettings.getFeatureSetsDimBaseline(),
				learningsArgsDims
				);

		this.runLearningCurve(pSpace, experimentName, languageCode);
	}

}
