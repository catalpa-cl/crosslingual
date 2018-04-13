package de.unidue.ltl.eduscoring.crosslingual.corpusAnalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;

public class AsapStatistics {

	// Internal representation of the statistics.
	// Data is organized as follows:
	// First the ID leads to a second Map that stores the labels of the IDs and a
	// Double Array that is used for counting
	// The Double Array is organized as follows:
	// First double: counter for number of answers
	// Second double: counter for number of tokens
	// Third double: counter for characters
	private Map<Integer, Map<Integer, Double[]>> statistics;
	private Map<Integer, Map<Integer, FrequencyDistribution<String>>> vocabulary;
	private Map<Integer, Map<Integer, List<String[]>>> answers;

	private Set<Integer> labelSet;

	public AsapStatistics() {
		this.statistics = new HashMap<Integer, Map<Integer, Double[]>>();
		this.vocabulary = new HashMap<Integer, Map<Integer, FrequencyDistribution<String>>>();
		this.answers = new HashMap<Integer, Map<Integer, List<String[]>>>();
		this.labelSet = new HashSet<Integer>();
	}
	
	public FrequencyDistribution<String> getVocabularyOfIds(Integer... ids){
		if(ids.length == 0) {
			ids = this.getIds().toArray(new Integer[this.getIds().size()]);
		}
		FrequencyDistribution<String> resultVocabulary = new FrequencyDistribution<String>();
		for(int id : ids) {
			for(int label : this.getLabels()) {
				if(this.vocabulary.get(id).containsKey(label)) {
					for(String key : this.vocabulary.get(id).get(label).getKeys()) {
						resultVocabulary.addSample(key, this.vocabulary.get(id).get(label).getCount(key));
					}
				}	
			}
		}
		return resultVocabulary;
	}

	/**
	 * Returns overall answer count for distinct labels. No subdivision for IDs or
	 * labels.
	 * 
	 * @param label
	 * @return
	 */
	public int getAnswerCount() {
		int answerCount = 0;
		for (int id : this.statistics.keySet()) {
			for (int label : this.statistics.get(id).keySet()) {
				answerCount += this.statistics.get(id).get(label)[0].intValue();
			}
		}
		return answerCount;
	}

	/**
	 * Returns overall token answer ratio for distinct labels. No subdivision for
	 * IDs or labels.
	 * 
	 * @param label
	 * @return
	 */
	public double getAverageAnswerLength() {
		double numberOfTokens = 0.0;
		double numberOfAnswers = 0.0;
		for (int id : this.statistics.keySet()) {
			for (int label : this.statistics.get(id).keySet()) {
				numberOfTokens += this.statistics.get(id).get(label)[1];
				numberOfAnswers += this.statistics.get(id).get(label)[0];
			}
		}
		return (numberOfTokens / numberOfAnswers);
	}

	/**
	 * Returns overall type token ratio for distinct labels. No subdivision for IDs
	 * or labels.
	 * 
	 * @param label
	 * @return
	 */
	public double getAverageWordLength() {
		double numberOfCharacters = 0.0;
		double numberOfTokens = 0.0;
		for (int id : this.statistics.keySet()) {
			for (int label : this.statistics.get(id).keySet()) {
				numberOfTokens += this.statistics.get(id).get(label)[1];
				numberOfCharacters += this.statistics.get(id).get(label)[2];
			}
		}
		return (numberOfCharacters / numberOfTokens);
	}
	
	/**
	 * Returns the overall type token ratio.
	 * @return
	 */
	public double getTypeTokenRatio() {
		List<String[]> answers = new ArrayList<String[]>();

		for(int id : this.answers.keySet()) {
			for(int label : this.answers.get(id).keySet()) {
				answers.addAll(this.answers.get(id).get(label));
			}
		}
		return this.computeTypeTokenRatio(answers);
	}

	/**
	 * Returns overall answer count for distinct labels. No subdivision for IDs.
	 * 
	 * @param label
	 * @return
	 */
	public int getAnswerCount(int label, Integer... ids) {
		int answerCount = 0;
		if(ids.length == 0) {
			ids = this.getIds().toArray(new Integer[this.getIds().size()]);
		}
		for (int id : ids) {
			if (this.statistics.get(id).containsKey(label)) {
				answerCount += this.statistics.get(id).get(label)[0].intValue();
			}

		}
		return answerCount;
	}

	/**
	 * Returns overall token answer ratio for distinct labels. No subdivision for
	 * IDs.
	 * 
	 * @param label
	 * @return
	 */
	public double getAverageAnswerLength(int label, Integer... ids) {
		double numberOfTokens = 0.0;
		double numberOfAnswers = 0.0;
		if(ids.length == 0) {
			ids = this.getIds().toArray(new Integer[this.getIds().size()]);
		}
		for (int id : ids) {
			if (this.statistics.get(id).containsKey(label)) {
				numberOfTokens += this.statistics.get(id).get(label)[1];
				numberOfAnswers += this.statistics.get(id).get(label)[0];
			}
		}
		return (numberOfTokens / numberOfAnswers);
	}

	/**
	 * Returns overall type token ratio for distinct labels. No subdivision for IDs.
	 * 
	 * @param label
	 * @return
	 */
	public double getAverageWordLength(int label, Integer...ids) {
		double numberOfCharacters = 0.0;
		double numberOfTokens = 0.0;
		if(ids.length == 0) {
			ids = this.getIds().toArray(new Integer[this.getIds().size()]);
		}
		for (int id : ids) {
			if (this.statistics.get(id).containsKey(label)) {
				numberOfTokens += this.statistics.get(id).get(label)[1];
				numberOfCharacters += this.statistics.get(id).get(label)[2];
			}
		}
		return (numberOfCharacters / numberOfTokens);
	}

	/**
	 * Returns type token ratio for the distinct labels.
	 * 
	 * @param label
	 * @return
	 */
	public double getTypeTokenRatio(int label, Integer...ids) {
		List<String[]> answers = new ArrayList<String[]>();
		if(ids.length == 0) {
			ids = this.getIds().toArray(new Integer[this.getIds().size()]);
		}
		for(int id : ids) {
			if(this.answers.get(id).containsKey(label)) {
				answers.addAll(this.answers.get(id).get(label));
			}
		}
		return this.computeTypeTokenRatio(answers);
	}

	/**
	 * Retrieve the set of available labels
	 * 
	 * @return
	 */
	public Set<Integer> getLabels() {
		return this.labelSet;
	}

	/**
	 * Retrieve the set of available IDs
	 * 
	 * @return
	 */
	public Set<Integer> getIds() {
		return this.statistics.keySet();
	}

	/**
	 * Retrieve the set of labels assigned to an ID
	 * 
	 * @param id
	 * @return
	 */
	public Set<Integer> getLabelsOfId(int id) {
		return this.statistics.get(id).keySet();
	}

	/**
	 * Returns the overall answer count for a certain ID and corresponding label
	 * 
	 * @param id
	 * @param label
	 * @return
	 */
	public int getAnswerCount(int id, int label) {
		return this.statistics.get(id).get(label)[0].intValue();
	}

	/**
	 * Token answer ratio for a certain ID and corresponding label is computed by
	 * dividing the total token count by the number of answers
	 * 
	 * @param id
	 * @param label
	 * @return
	 */
	public double getAverageAnswerLength(int id, int label) {
		return (this.statistics.get(id).get(label)[1] / this.statistics.get(id).get(label)[0]);
	}

	/**
	 * Average word length for a certain ID and corresponding label is computed by
	 * dividing the total character count by the total token count
	 * 
	 * @param id
	 * @param label
	 * @return
	 */
	public double getAverageWordLength(int id, int label) {
		return (this.statistics.get(id).get(label)[2] / this.statistics.get(id).get(label)[1]);
	}

	/**
	 * Type token ratio for a certain ID and corresponding label
	 * 
	 * @param id
	 * @param label
	 * @return
	 */
	public double getTypeTokenRatio(int id, int label) {
		return this.computeTypeTokenRatio(this.answers.get(id).get(label));
	}
	
	public List<String> getTopKVocabsIdLabel(int id, int label, int k) {
		return this.vocabulary.get(id).get(label).getMostFrequentSamples(k);
	}
	
	public FrequencyDistribution<String> getVocabDistribution(Integer... ids){
		if(ids.length == 0) {
			ids = this.getIds().toArray(new Integer[this.getIds().size()]);
		}
		FrequencyDistribution<String> resultVocabulary = new FrequencyDistribution<String>();
		for(int id : ids) {
			for(int label : this.getLabels()) {
				if(this.vocabulary.get(id).containsKey(label)) {
					for(String key : this.vocabulary.get(id).get(label).getKeys()) {
						resultVocabulary.addSample(key, this.vocabulary.get(id).get(label).getCount(key));
					}
				}	
			}
		}
		return resultVocabulary;
	}

	/**
	 * Updates the internal statistics and total statistics by incrementing the
	 * total number of answers for a certain ID and corresponding label by 1.
	 * 
	 * @param id
	 * @param label
	 */
	public void incrementAnswerCount(int id, int label) {
		this.labelSet.add(label);

		// Make sure the ID exists in mapping
		if (!this.statistics.containsKey(id)) {
			this.statistics.put(id, new HashMap<Integer, Double[]>());
		}

		// Get the corresponding labels to ID.
		Map<Integer, Double[]> internalIdMap = this.statistics.get(id);

		// Make sure the label exists in mapping
		if (!internalIdMap.containsKey(label)) {
			internalIdMap.put(label, new Double[] { 0.0, 0.0, 0.0 });
		}

		// Get the counter corresponding to the labels
		Double[] internalIdLabelArray = internalIdMap.remove(label);

		// Increment the counters
		internalIdLabelArray[0]++;

		// Write mappings back!
		internalIdMap.put(label, internalIdLabelArray);
		this.statistics.put(id, internalIdMap);
	}

	/**
	 * Updates the internal statistics and total statistics by incrementing the
	 * total token count for a certain ID and corresponding label by the given
	 * value.
	 * 
	 * @param id
	 * @param label
	 * @param answerLength
	 */
	public void incrementTotalTokenCount(int id, int label, double answerLength) {
		this.labelSet.add(label);

		// Make sure the ID exists in mapping
		if (!this.statistics.containsKey(id)) {
			this.statistics.put(id, new HashMap<Integer, Double[]>());
		}

		// Get the corresponding labels to ID.
		Map<Integer, Double[]> internalIdMap = this.statistics.get(id);

		// Make sure the label exists in mapping
		if (!internalIdMap.containsKey(label)) {
			internalIdMap.put(label, new Double[] { 0.0, 0.0, 0.0 });
		}

		// Get the counter corresponding to the labels
		Double[] internalIdLabelArray = internalIdMap.remove(label);

		// Increment the counters
		internalIdLabelArray[1] += answerLength;

		// Write mappings back!
		internalIdMap.put(label, internalIdLabelArray);
		this.statistics.put(id, internalIdMap);
	}

	/**
	 * Updates the internal statistics and total statistics by incrementing the
	 * total character count for a certain ID and corresponding label by the given
	 * value.
	 * 
	 * @param id
	 * @param label
	 * @param characterCount
	 */
	public void incrementTotalCharacterCount(int id, int label, String[] words) {
		this.labelSet.add(label);

		// Make sure the ID exists in mapping
		if (!this.statistics.containsKey(id)) {
			this.statistics.put(id, new HashMap<Integer, Double[]>());
		}

		if (!this.vocabulary.containsKey(id)) {
			this.vocabulary.put(id, new HashMap<Integer, FrequencyDistribution<String>>());
		}
		
		if(!this.answers.containsKey(id)) {
			this.answers.put(id, new HashMap<Integer, List<String[]>>());
		}

		// Get the corresponding labels to ID.
		Map<Integer, Double[]> internalIdMap = this.statistics.get(id);
		Map<Integer, FrequencyDistribution<String>> internalIdVocab = this.vocabulary.get(id);
		Map<Integer, List<String[]>> internalIdAnswers = this.answers.get(id);

		// Make sure the label exists in mapping
		if (!internalIdMap.containsKey(label)) {
			internalIdMap.put(label, new Double[] { 0.0, 0.0, 0.0 });
		}

		if (!internalIdVocab.containsKey(label)) {
			internalIdVocab.put(label, new FrequencyDistribution<String>());
		}
		
		if(!internalIdAnswers.containsKey(label)) {
			internalIdAnswers.put(label, new ArrayList<String[]>());
		}

		// Get the counter corresponding to the labels
		Double[] internalIdLabelArray = internalIdMap.remove(label);
		FrequencyDistribution<String> internalVocabulary = internalIdVocab.remove(label);
		List<String[]> internalAnswers = internalIdAnswers.remove(label);

		// Increment the counters
		for (int i = 0; i < words.length; i++) {
			String tmpItem = words[i].toLowerCase().replaceAll("\\p{P}", "").replaceAll("\\s+", " ").trim();
			internalIdLabelArray[2] += tmpItem.length();
			internalVocabulary.inc(tmpItem);	
		}
		
		internalAnswers.add(words);

		// Write mappings back!
		internalIdMap.put(label, internalIdLabelArray);
		internalIdVocab.put(label, internalVocabulary);
		internalIdAnswers.put(label, internalAnswers);
		this.statistics.put(id, internalIdMap);
		this.vocabulary.put(id, internalIdVocab);
		this.answers.put(id, internalIdAnswers);
	}
	
	//Computes the type token ratio every 900 token and returns an average over all type token ratios
	private double computeTypeTokenRatio(List<String[]> answers) {
		double typeTokenRatio = 0.0;
		int iterationCounter = 0;
		
		Set<String> types = new HashSet<String>();
		double tokenCount = 0.0;
		
		for(String[] answer : answers) {
			
			//Count types
			for(int i = 0; i < answer.length; i++) {
				String tmpItem = answer[i].toLowerCase().replaceAll("\\p{P}", "").replaceAll("\\s+", " ").trim();
				tokenCount++;
				types.add(tmpItem);
				if(tokenCount == 900) {
					typeTokenRatio += (types.size()/tokenCount);
					types = new HashSet<String>();
					tokenCount = 0.0;
					iterationCounter++;
				}
			}
		}
//		if(tokenCount != 0) {
//			typeTokenRatio += (types.size()/tokenCount);
//			iterationCounter++;
//		}
		
		return (typeTokenRatio/iterationCounter);
	}
	
}
