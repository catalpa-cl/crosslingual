package de.unidue.ltl.eduscoring.crosslingual.translate;

import de.unidue.ltl.eduscoring.crosslingual.experiments.DataSetPaths;

public class Translate implements DataSetPaths {
	
	// Set your API-Key from https://console.developers.google.com/

	static String dummyKey = "I'm a dummy and no real key.";  //insert your own key here

	public static void main(String[] args) {
		//Example usage of the DeepL translator. Google Translator is used in a very similar way.
//		DeepLTranslator deepl = new DeepLTranslator("DE", "EN");
//		deepl.translateFile(GER_ASAP, EN_DEEPL_ASAP);
		
		GoogleTranslator google = new GoogleTranslator(dummyKey, "en");
		google.translateFile("src/main/resource/datasets/asap/train_texts_for_double_translation.txt", EN_TRAIN_DOUBLE_TRANSLATED);
	}

}
