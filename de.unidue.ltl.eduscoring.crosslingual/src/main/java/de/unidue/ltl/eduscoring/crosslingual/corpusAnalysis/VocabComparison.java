package de.unidue.ltl.eduscoring.crosslingual.corpusAnalysis;

import java.util.HashSet;
import java.util.Set;

public class VocabComparison {
	
	public VocabComparison() {
		
	}
	
	public void compareVocabularies(String nameOne, String nameTwo, Set<String> vocabOne, Set<String> vocabTwo) {
		int overlap = 0;
		Set<String> overlapVocab = new HashSet<String>();
		for(String entry : vocabOne) {
			if(vocabTwo.contains(entry)) {
				overlap++;
				overlapVocab.add(entry);
			}
		}
 		System.out.println(nameOne + " \t " + vocabOne.size() + " \t " + overlap + " \t " + vocabTwo.size() + " \t " + nameTwo);
	}

}
