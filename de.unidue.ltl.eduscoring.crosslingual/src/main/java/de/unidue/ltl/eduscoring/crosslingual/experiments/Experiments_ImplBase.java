package de.unidue.ltl.eduscoring.crosslingual.experiments;


import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.component.NoOpAnnotator;
import org.apache.uima.resource.ResourceInitializationException;
import org.dkpro.lab.Lab;
import org.dkpro.lab.task.BatchTask.ExecutionPolicy;
import org.dkpro.lab.task.Dimension;
import org.dkpro.lab.task.ParameterSpace;
import org.dkpro.tc.core.Constants;
import org.dkpro.tc.ml.ExperimentCrossValidation;
import org.dkpro.tc.ml.ExperimentTrainTest;
import org.dkpro.tc.ml.report.BatchCrossValidationReport;
import org.dkpro.tc.ml.report.BatchRuntimeReport;
import org.dkpro.tc.ml.report.BatchTrainTestReport;
import org.dkpro.tc.ml.weka.WekaAdapter;

import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpLemmatizer;
import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpParser;
import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpSegmenter;
import de.tudarmstadt.ukp.dkpro.core.jazzy.JazzyChecker;
import de.tudarmstadt.ukp.dkpro.core.matetools.MateLemmatizer;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stopwordremover.StopWordRemover;
import de.unidue.ltl.escrito.core.learningcurve.LearningCurveAdapter;
import de.unidue.ltl.escrito.core.learningcurve.LearningCurveReport;
import de.unidue.ltl.escrito.core.report.CvEvaluationReport;
import de.unidue.ltl.escrito.core.report.GradingEvaluationReport;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.SMO;

public abstract class Experiments_ImplBase
implements Constants
{
	public static final String LANGUAGE_CODE = "en";

	public static final Boolean[] toLowerCase = new Boolean[] { true };

	public static final String stopwordList = "classpath:/stopwords/english_stopwords.txt";
	//    public static final String stopwordList = "classpath:/stopwords/english_empty.txt";

	public static final String SPELLING_VOCABULARY = "classpath:/vocabulary/en_US_dict.txt";

	public static final int NUM_FOLDS = 10;

	public static final boolean useTagger = false;
	public static final boolean useChunker = false;
	public static final boolean useParsing = false;
	public static final boolean useSpellChecking = false;
	public static final boolean useQuestionMaterialAnnotator = false;
	public static final boolean removeStopwords = false;


//	@SuppressWarnings("unchecked")
//	public static Dimension<List<String>> getClassificationArgsDim()
//	{
//		Dimension<List<String>> dimClassificationArgs = Dimension.create(
//				Constants.DIM_CLASSIFICATION_ARGS,
//				Arrays.asList(new String[] { SMO.class.getName() })
//				//                ,
//				//                Arrays.asList(new String[] { NaiveBayes.class.getName() })
//				);
//		return dimClassificationArgs;
//	}
//
//	@SuppressWarnings("unchecked")
//	public static Dimension<List<String>> getRegressionArgsDim()
//	{
//		Dimension<List<String>> dimClassificationArgs = Dimension.create(
//				Constants.DIM_CLASSIFICATION_ARGS,
//				Arrays.asList(new String[] { LinearRegression.class.getName() })
//				);
//		return dimClassificationArgs;
//	}



	public static AnalysisEngineDescription getPreprocessing(String languageCode)
			throws ResourceInitializationException
	{
		AnalysisEngineDescription tagger       = createEngineDescription(NoOpAnnotator.class);
		AnalysisEngineDescription lemmatizer   = createEngineDescription(NoOpAnnotator.class);
		AnalysisEngineDescription chunker      = createEngineDescription(NoOpAnnotator.class);
		AnalysisEngineDescription spellChecker = createEngineDescription(NoOpAnnotator.class);
		AnalysisEngineDescription parser       = createEngineDescription(NoOpAnnotator.class);
		AnalysisEngineDescription stopwordRemoval = createEngineDescription(NoOpAnnotator.class);

		if (useTagger) {
			System.out.println("Running tagger ...");
			tagger = createEngineDescription(OpenNlpPosTagger.class, OpenNlpPosTagger.PARAM_LANGUAGE, languageCode);
			if(languageCode.equals("de")){
				lemmatizer = createEngineDescription(MateLemmatizer.class, MateLemmatizer.PARAM_LANGUAGE, languageCode);
			} else {
				lemmatizer = createEngineDescription(ClearNlpLemmatizer.class);
			}
		}

		if (useSpellChecking) {
			spellChecker = createEngineDescription(
					JazzyChecker.class,
					JazzyChecker.PARAM_MODEL_LOCATION, SPELLING_VOCABULARY
					);
		}

		if (useParsing) {
			System.out.println("Running parser ...");
			parser = createEngineDescription(
					ClearNlpParser.class,
					ClearNlpParser.PARAM_LANGUAGE, LANGUAGE_CODE,
					ClearNlpParser.PARAM_VARIANT, "ontonotes"
					);
		}

		if (removeStopwords){
			System.out.println("Ignore stopwords");
			stopwordRemoval = createEngineDescription(
					StopWordRemover.class,
					StopWordRemover.PARAM_MODEL_LOCATION, stopwordList
					);
		}

		return createEngineDescription(
				createEngineDescription(
						ClearNlpSegmenter.class
						),
				spellChecker,
				tagger,
				lemmatizer,
				chunker,
				parser,
				stopwordRemoval
				);
	}


//	// ##### TRAIN-TEST #####
//	protected void runTrainTest(ParameterSpace pSpace, String name, AnalysisEngineDescription aed)
//			throws Exception
//	{
//		ExperimentTrainTest batch = new ExperimentTrainTest(name + "-TrainTest",
//				WekaClassificationAdapter.class);
//		batch.setPreprocessing(aed);
//		batch.addInnerReport(GradingEvaluationReport.class);
//		batch.setParameterSpace(pSpace);
//		batch.setExecutionPolicy(ExecutionPolicy.RUN_AGAIN);
//		//batch.setExecutionPolicy(ExecutionPolicy.USE_EXISTING);
//		batch.addReport(BatchTrainTestReport.class);
//
//		// Run
//		Lab.getInstance().run(batch);
//	}
//
//
//	// ##### CV #####
//		protected void runCrossValidation(ParameterSpace pSpace, String name, AnalysisEngineDescription aed, int numFolds)
//				throws Exception
//		{
//			ExperimentCrossValidation batch = new ExperimentCrossValidation(name + "-CV",
//					WekaClassificationAdapter.class, numFolds);
//			batch.setPreprocessing(aed);
//			batch.addInnerReport(GradingEvaluationReport.class);
//			batch.setParameterSpace(pSpace);
//			batch.setExecutionPolicy(ExecutionPolicy.RUN_AGAIN);
//			batch.addReport(BatchCrossValidationReport.class);
//			batch.addReport(CvEvaluationReport.class);
//
//			// Run
//			Lab.getInstance().run(batch);
//		}
//	
//		// ##### LEARNING-CURVE #####
//		public void runLearningCurve(ParameterSpace pSpace, String name, String languageCode)
//				throws Exception
//		{
//			//	ExperimentLearningCurve batch = new ExperimentLearningCurve(name + "-LearningCurve", WekaClassificationAdapter.class);
//			ExperimentTrainTest batch = new ExperimentTrainTest(name + "-LearningCurve", LearningCurveAdapter.class);
//			batch.setPreprocessing(getPreprocessing(languageCode));
//			batch.addInnerReport(LearningCurveReport.class);    
//			batch.setParameterSpace(pSpace);
//			batch.setExecutionPolicy(ExecutionPolicy.RUN_AGAIN);
//			// TODO: wieso wird der nicht ausgeführt?
//			batch.addReport(BatchRuntimeReport.class);
//
//			// Run
//			Lab.getInstance().run(batch);
//		}
//		
//		
		
		
		
		@SuppressWarnings("unchecked")
		public static Dimension<Map<String, Object>> getStandardWekaClassificationArgsDim()
		{	
			Map<String, Object> config = new HashMap<String, Object>();
			config.put(DIM_CLASSIFICATION_ARGS, new Object[] { new WekaAdapter(), SMO.class.getName()});
			config.put(DIM_DATA_WRITER, new WekaAdapter().getDataWriterClass());
			config.put(DIM_FEATURE_USE_SPARSE, new WekaAdapter().useSparseFeatures());
			Dimension<Map<String, Object>> mlas = Dimension.createBundle("config", config);					
			return mlas;
		}
		
		
		@SuppressWarnings("unchecked")
		public static Dimension<Map<String, Object>> getWekaLearningCurveClassificationArgsDim()
		{	
			Map<String, Object> config = new HashMap<String, Object>();
			config.put(DIM_CLASSIFICATION_ARGS, new Object[] { new LearningCurveAdapter(), SMO.class.getName()});
			config.put(DIM_DATA_WRITER, new LearningCurveAdapter().getDataWriterClass());
			Dimension<Map<String, Object>> mlas = Dimension.createBundle("config", config);					
			return mlas;
		}





		// ######### EXPERIMENTAL SETUPS ##########
		// ##### TRAIN-TEST #####
		protected static void runTrainTest(ParameterSpace pSpace, String name, AnalysisEngineDescription aed)
				throws Exception
		{
			System.out.println("Running experiment "+name);
			ExperimentTrainTest batch = new ExperimentTrainTest(name + "-TrainTest");
			batch.setPreprocessing(aed);
			batch.addInnerReport(GradingEvaluationReport.class);
			batch.setParameterSpace(pSpace);
			batch.setExecutionPolicy(ExecutionPolicy.RUN_AGAIN);
			batch.addReport(BatchTrainTestReport.class);
			// Run
			Lab.getInstance().run(batch);
		}
		
		// ##### CV #####
				protected static void runCrossValidation(ParameterSpace pSpace, String name, AnalysisEngineDescription aed, int numFolds)
						throws Exception
				{
					ExperimentCrossValidation batch = new ExperimentCrossValidation(name + "-CV", numFolds);
					batch.setPreprocessing(aed);
					batch.addInnerReport(GradingEvaluationReport.class);
					batch.setParameterSpace(pSpace);
					batch.setExecutionPolicy(ExecutionPolicy.RUN_AGAIN);
					batch.addReport(BatchCrossValidationReport.class);
					batch.addReport(CvEvaluationReport.class);

					// Run
					Lab.getInstance().run(batch);
				}
				
				

		// ##### LEARNING-CURVE #####
		public static void runLearningCurve(ParameterSpace pSpace, String name, String languageCode)
				throws Exception
		{
			System.out.println("Running experiment "+name);
			ExperimentTrainTest batch = new ExperimentTrainTest(name + "-LearningCurve");
			batch.setPreprocessing(getPreprocessing(languageCode));
			batch.addInnerReport(LearningCurveReport.class);    
			batch.setParameterSpace(pSpace);
			batch.setExecutionPolicy(ExecutionPolicy.RUN_AGAIN);
			// TODO: wieso wird der nicht ausgeführt?
			batch.addReport(BatchRuntimeReport.class);
			// Run
			Lab.getInstance().run(batch);
		}

		
		
		
		

}
