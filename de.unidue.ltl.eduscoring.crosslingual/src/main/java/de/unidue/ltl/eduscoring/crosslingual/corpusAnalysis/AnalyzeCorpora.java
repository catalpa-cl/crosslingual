package de.unidue.ltl.eduscoring.crosslingual.corpusAnalysis;

import de.unidue.ltl.eduscoring.crosslingual.experiments.DataSetPaths;

public class AnalyzeCorpora implements DataSetPaths{

	public static void main(String[] args){
		
		DataStructuresAnalysis dataStructuresAnalysis = new DataStructuresAnalysis();
		dataStructuresAnalysis.analyzeCorpus();
		
		SraAnalysis sraAnalysis = new SraAnalysis();
		sraAnalysis.analyzeCorpus();
		
		PowergradingAnalysis powergradingAnalysis = new PowergradingAnalysis();
		powergradingAnalysis.analyzeCorpus();
		
		AsapAnalysis originalAsap = new AsapAnalysis("original English ASAP");
		originalAsap.readOriginalAsapFile(EN_TRAIN);
		originalAsap.readOriginalAsapFile(EN_TEST);
		originalAsap.analyzeCorpus();
		
		AsapAnalysis germanAsapTranslatedToEnglish = new AsapAnalysis("google English ASAP");
		germanAsapTranslatedToEnglish.readTranslatedAsapFile(GER_ASAP, EN_GOOGLE_ASAP);
		germanAsapTranslatedToEnglish.analyzeCorpus();
		
		AsapAnalysis germanAsap = new AsapAnalysis("original German ASAP");
		germanAsap.readOriginalAsapFile(GER_ASAP);
		germanAsap.analyzeCorpus();
		
		AsapAnalysis originalAsapTranslatedToGerman = new AsapAnalysis("google German ASAP");
		originalAsapTranslatedToGerman.readTranslatedAsapFile(EN_TRAIN, GER_GOOGLE_TRAIN);
		originalAsapTranslatedToGerman.readTranslatedAsapFile(EN_TEST, GER_GOOGLE_TEST);
		originalAsapTranslatedToGerman.analyzeCorpus();
		
	}

}
