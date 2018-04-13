package de.unidue.ltl.eduscoring.crosslingual.corpusAnalysis;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasConsumer_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.dkpro.tc.api.exception.TextClassificationException;
import org.dkpro.tc.features.ngram.util.NGramUtils;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.NGram;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.ngrams.util.NGramStringListIterable;

public class FDWriter extends JCasConsumer_ImplBase{


	public static final String PARAM_NGRAM_N = "ngram length";
	@ConfigurationParameter(name = PARAM_NGRAM_N, mandatory = false, defaultValue = "1")
	private int n;



	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		FrequencyDistribution<String> fd = new FrequencyDistribution<String>();
		if (n>=1){
			try {
				fd = getDocumentNgrams(aJCas, n, n);
			} catch (TextClassificationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			for (Token t : JCasUtil.select(aJCas, Token.class)) {
				fd.inc(t.getCoveredText());
			}
		}
		try {
			fd.save(new File("tmp.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static FrequencyDistribution<String> getDocumentNgrams(JCas jcas, int minN, int maxN) throws TextClassificationException{
		FrequencyDistribution<String> documentNgrams = new FrequencyDistribution<String>();
		for (Sentence s : JCasUtil.select(jcas, Sentence.class)) {
			List<String> strings = NGramUtils.valuesToText(jcas, s, Token.class.getName());
			for (List<String> ngram : new NGramStringListIterable(strings, minN, maxN)) {         
			//	ngram = NGramUtils.lower(ngram);
				String ngramString = StringUtils.join(ngram, "_");
				documentNgrams.inc(ngramString);
			}
		}
		return documentNgrams;
	}






}
