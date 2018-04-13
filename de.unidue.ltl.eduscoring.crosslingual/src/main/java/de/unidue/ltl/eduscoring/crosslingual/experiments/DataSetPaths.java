package de.unidue.ltl.eduscoring.crosslingual.experiments;

public interface DataSetPaths {
	
	public static final String EN_TRAIN = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_en/train_repaired.txt";
	public static final String EN_TEST = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_en/test_public_repaired.txt";
	public static final String EN_TRAIN_DOUBLE_TRANSLATED = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_en_double_translated/train_double_translated_clean.txt";
	public static final String EN_TEST_DOUBLE_TRANSLATED = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_en_double_translated/test_double_translated_clean.txt";
	
	public static final String EN_TRAIN_DICT = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_en/asap_train_textsForTranslation.tsv";
	public static final String EN_TEST_DICT = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_en/asap_test_public_textsForTranslation.txt";
	
	public static final String GER_GOOGLE_TRAIN = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_en_translated/asap_train_de_translated_clean.txt";
	public static final String GER_DEEPL_TRAIN = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_en_translated/asap_train_de_deepl_translated_clean.txt";
	public static final String GER_GOOGLE_TEST = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_en_translated/asap_test_public_de_translated_clean.txt";
	public static final String GER_DEEPL_TEST = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_en_translated/asap_test_public_de_deepl_translated_clean.txt";
	
	public static final String RU_GOOGLE_TRAIN = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_en_translated/asap_train_ru_translated_clean.txt";
	public static final String RU_GOOGLE_TEST = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_en_translated/asap_test_public_text_ru_translated_clean.txt";
	
	public static final String GER_ASAP = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_de/germanAsap.txt";
	public static final String GER_ASAP_DOUBLE_TRANSLATED = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_de_double_translated/germanAsap_double_translated_clean.txt";
	public static final String EN_GOOGLE_ASAP = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_de_translated/germanAsap_en_translated_clean.txt";
	public static final String EN_DEEPL_ASAP = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_de_translated/germanAsap_en_deepl_translated_clean.txt";
	
	public static final String GER_DICT = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_de/germanAsap_Dict.txt";
	
	
	
//	public static final String EN_TRAIN = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_en/train_repaired.txt";
//	public static final String EN_TEST = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_en/test_public_repaired.txt";
//	public static final String EN_TRAIN_DOUBLE_TRANSLATED = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_en_double_translated/train_double_translated.txt";
//	public static final String EN_TEST_DOUBLE_TRANSLATED = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_en_double_translated/test_double_translated.txt";
//	
//	public static final String EN_TRAIN_DICT = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_en/asap_train_textsForTranslation.tsv";
//	public static final String EN_TEST_DICT = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_en/asap_test_public_textsForTranslation.txt";
//	
//	public static final String GER_GOOGLE_TRAIN = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_en_translated/asap_train_de_translated.txt";
//	public static final String GER_DEEPL_TRAIN = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_en_translated/asap_train_de_deepl_translated.txt";
//	public static final String GER_GOOGLE_TEST = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_en_translated/asap_test_public_de_translated.txt";
//	public static final String GER_DEEPL_TEST = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_en_translated/asap_test_public_de_deepl_translated.txt";
//	
//	public static final String RU_GOOGLE_TRAIN = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_en_translated/asap_train_ru_translated.txt";
//	public static final String RU_GOOGLE_TEST = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_en_translated/asap_test_public_text_ru_translated.txt";
//	
//	public static final String GER_ASAP = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_de/germanAsap.txt";
//	public static final String GER_ASAP_DOUBLE_TRANSLATED = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_de_double_translated/germanAsap_double_translated.txt";
//	public static final String EN_GOOGLE_ASAP = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_de_translated/germanAsap_en_translated.txt";
//	public static final String EN_DEEPL_ASAP = System.getenv("DKPRO_HOME")+"/datasets/asap/crosslingual/asap_de_translated/germanAsap_en_deepl_translated.txt";
//	
//	public static final String GER_DICT = System.getenv("DKPRO_HOME")+"/datasets/asap/asap_de/germanAsap_Dict.txt";
	

}
