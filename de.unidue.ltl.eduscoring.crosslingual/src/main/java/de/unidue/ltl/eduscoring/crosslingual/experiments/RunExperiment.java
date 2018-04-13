package de.unidue.ltl.eduscoring.crosslingual.experiments;

public class RunExperiment implements DataSetPaths {
	
	private static final String ENGLISH_LANGUAGE_CODE = "en";
	private static final String GERMAN_LANGUAGE_CODE ="de";
	
	public static void main(String[] args) throws Exception{
		
		//System.setProperty("DKPRO_HOME", "/Users/Basti/dkpro");
		
		AsapExperiment experiment = new AsapExperiment();
		
		//#####Baselines#####
//		//original English Train Test
//		experiment.runAsapBaselineExperiment("English_Train_Test", EN_TRAIN, EN_TEST, ENGLISH_LANGUAGE_CODE ,false, false, 1, 2, 10);
//		//original English Train Test with 270 training instances
//		experiment.runAsapBaselineExperiment("English_270Train_Test", EN_TRAIN, EN_TEST, ENGLISH_LANGUAGE_CODE ,false, true, 1, 2, 10);
//		//German Cross-Validation
//		experiment.runAsapBaselineExperiment("German_CV", GER_ASAP, GER_ASAP, GERMAN_LANGUAGE_CODE, true, false, 1,2,10);
//		
//		//#####Translation baseline#####
//		//English translated into German Train Test
//		experiment.runAsapTranslate("German_Translated_Train_Translated_Test", GERMAN_LANGUAGE_CODE, false, false, EN_TRAIN, EN_TRAIN_DICT, GER_GOOGLE_TRAIN, EN_TEST, EN_TEST_DICT, GER_GOOGLE_TEST, 1,2,10);
//		//English translated into German Train Test with 270 training instances
//		experiment.runAsapTranslate("German_Translated_270Train_Translated_Test", GERMAN_LANGUAGE_CODE, false, true, EN_TRAIN, EN_TRAIN_DICT, GER_GOOGLE_TRAIN, EN_TEST, EN_TEST_DICT, GER_GOOGLE_TEST, 1,2,10);
//		//German translated into English CV
//		experiment.runAsapTranslate("English_Translated_CV", ENGLISH_LANGUAGE_CODE, true, false, GER_ASAP, GER_DICT, EN_GOOGLE_ASAP, "", "", "", 1,2,10);
//		
		//English translated into German Train Test deepL
		experiment.runAsapTranslate("German_Translated_Train_Translated_Test", GERMAN_LANGUAGE_CODE, false, false, EN_TRAIN, EN_TRAIN_DICT, GER_DEEPL_TRAIN, EN_TEST, EN_TEST_DICT, GER_DEEPL_TEST, 1,2,3,4,5,6,7,8,9,10);
		//English translated into German Train Test google
		experiment.runAsapTranslate("German_Translated_Train_Translated_Test_DeepL", GERMAN_LANGUAGE_CODE, false, false, EN_TRAIN, EN_TRAIN_DICT, GER_GOOGLE_TRAIN, EN_TEST, EN_TEST_DICT, GER_GOOGLE_TEST, 1,2,3,4,5,6,7,8,9,10);

		//English translated into Russian Train Test google
		experiment.runAsapTranslate("Russian_Translated_Train_Translated_Test", GERMAN_LANGUAGE_CODE, false, false, EN_TRAIN, EN_TRAIN_DICT, RU_GOOGLE_TRAIN, EN_TEST, EN_TEST_DICT, RU_GOOGLE_TEST, 1,2,3,4,5,6,7,8,9,10);


		
//		//#####Translated Test Data#####
//		//original English Train translated Test
//		experiment.runAsapTranslate("English_Train_Translated_Test", ENGLISH_LANGUAGE_CODE, false, false, EN_TRAIN, EN_TRAIN_DICT, EN_TRAIN, GER_ASAP, GER_DICT, EN_GOOGLE_ASAP, 1,2,10);	
//		//original English Train translated Test with 270 training instances
//		experiment.runAsapTranslate("English_270Train_Translated_Test", ENGLISH_LANGUAGE_CODE, false, true, EN_TRAIN, EN_TRAIN_DICT, EN_TRAIN, GER_ASAP, GER_DICT, EN_GOOGLE_ASAP, 1,2,10);
//		//German Train translated Test
//		experiment.runAsapTranslate("German_Train_Translated_Test", GERMAN_LANGUAGE_CODE, false, false, GER_ASAP, GER_DICT, GER_ASAP, EN_TEST, EN_TEST_DICT, GER_GOOGLE_TEST, 1,2,10);
//		
//		//#####Translated Train Data#####
//		//English translated Train original Test
//		experiment.runAsapTranslate("English_Translated_Train_Original_Test", ENGLISH_LANGUAGE_CODE, false, false, GER_ASAP, GER_DICT, EN_GOOGLE_ASAP, EN_TEST, EN_TEST_DICT, EN_TEST, 1,2,10);		
//		//German translated Train German Test
//		experiment.runAsapTranslate("German_Translated_Train_German_Test", GERMAN_LANGUAGE_CODE, false, false, EN_TRAIN, EN_TRAIN_DICT, GER_GOOGLE_TRAIN, GER_ASAP, GER_DICT, GER_ASAP, 1,2,10);
//		//German translated Train German Test with 270 training instances
//		experiment.runAsapTranslate("German_Translated_270Train_German_Test", GERMAN_LANGUAGE_CODE, false, true, EN_TRAIN, EN_TRAIN_DICT, GER_GOOGLE_TRAIN, GER_ASAP, GER_DICT, GER_ASAP, 1,2,10);
		
		
		//#####Double Translated Data#####
		//double translated German CV:
//		experiment.runAsapTranslate("German_Double_Translated_CV", GERMAN_LANGUAGE_CODE, true, false, GER_ASAP, GER_DICT, GER_ASAP_DOUBLE_TRANSLATED, "", "", "", 1,2,10);
//		//original English Train double translated Test (EN->DE->EN)
//		experiment.runAsapTranslate("English_Train_Double_Translated_Test", ENGLISH_LANGUAGE_CODE, false, false, EN_TRAIN, EN_TRAIN_DICT, EN_TRAIN, EN_TEST, EN_TEST_DICT, EN_TEST_DOUBLE_TRANSLATED, 1,2,10);
//		//original English Train double translated Test (EN->DE->EN)
//		experiment.runAsapTranslate("English_270Train_Double_Translated_Test", ENGLISH_LANGUAGE_CODE, false, true, EN_TRAIN, EN_TRAIN_DICT, EN_TRAIN, EN_TEST, EN_TEST_DICT, EN_TEST_DOUBLE_TRANSLATED, 1,2,10);
//		//double translated Train double translated Test (EN->DE->EN)
//		experiment.runAsapTranslate("English_Double_Translated_Train_DoubleTranslated_Test", ENGLISH_LANGUAGE_CODE, false, false, EN_TRAIN, EN_TRAIN_DICT, EN_TRAIN_DOUBLE_TRANSLATED, EN_TEST, EN_TEST_DICT, EN_TEST_DOUBLE_TRANSLATED, 1,2,10);
//		//double translated Train double translated Test (EN->DE->EN)
//		experiment.runAsapTranslate("English_Double_Translated_270Train_DoubleTranslated_Test", ENGLISH_LANGUAGE_CODE, false, true, EN_TRAIN, EN_TRAIN_DICT, EN_TRAIN_DOUBLE_TRANSLATED, EN_TEST, EN_TEST_DICT, EN_TEST_DOUBLE_TRANSLATED, 1,2,10);
//		//double translated English Train original Test (EN->DE->EN)
//		experiment.runAsapTranslate("English_Double_Translated_Train_English_Test", ENGLISH_LANGUAGE_CODE, false, false, EN_TRAIN, EN_TRAIN_DICT, EN_TRAIN_DOUBLE_TRANSLATED, EN_TEST, EN_TEST_DICT, EN_TEST, 1,2,10);
//		//double translated English Train original Test (EN->DE->EN)
//		experiment.runAsapTranslate("English_Double_Translated_270Train_English_Test", ENGLISH_LANGUAGE_CODE, false, true, EN_TRAIN, EN_TRAIN_DICT, EN_TRAIN_DOUBLE_TRANSLATED, EN_TEST, EN_TEST_DICT, EN_TEST, 1,2,10);
//		//double translated English Train translated Test
//		experiment.runAsapTranslate("English_Double_Translated_Train_Translated_Test", ENGLISH_LANGUAGE_CODE, false, false, EN_TRAIN, EN_TRAIN_DICT, EN_TRAIN_DOUBLE_TRANSLATED, GER_ASAP, GER_DICT, EN_GOOGLE_ASAP, 1,2,10);
//		//double translated English train translated Test with 270 training instances
//		experiment.runAsapTranslate("English_double_translated_270Train_Translated_Test", ENGLISH_LANGUAGE_CODE, false, true, EN_TRAIN, EN_TRAIN_DICT, EN_TRAIN_DOUBLE_TRANSLATED, GER_ASAP, GER_DICT, EN_GOOGLE_ASAP, 1,2,10);
//		//double translated German Train translated Test
//		experiment.runAsapTranslate("German_double_translated_Train_Translated_Test", GERMAN_LANGUAGE_CODE, false, false, GER_ASAP, GER_DICT, GER_ASAP_DOUBLE_TRANSLATED, EN_TEST, EN_TEST_DICT, GER_GOOGLE_TEST, 1,2,10);
//		//English translated Train double translated English Test
//		experiment.runAsapTranslate("English_Translated_Train_double_translated_Test", ENGLISH_LANGUAGE_CODE, false, false, GER_ASAP, GER_DICT, EN_GOOGLE_ASAP, EN_TEST, EN_TEST_DICT, EN_TEST_DOUBLE_TRANSLATED, 1,2,10);
//		//German translated Train double translated German Test
//		experiment.runAsapTranslate("German_Translated_Train_double_translated_German_Test", GERMAN_LANGUAGE_CODE, false, false, EN_TRAIN, EN_TRAIN_DICT, GER_GOOGLE_TRAIN, GER_ASAP, GER_DICT, GER_ASAP_DOUBLE_TRANSLATED, 1,2,10);
//		//German translated Train double translated German Test with 270 training instances
//		experiment.runAsapTranslate("German_Translated_270Train_double_translated_German_Test", GERMAN_LANGUAGE_CODE, false, true, EN_TRAIN, EN_TRAIN_DICT, GER_GOOGLE_TRAIN, GER_ASAP, GER_DICT, GER_ASAP_DOUBLE_TRANSLATED, 1,2,10);
	}
	
}
