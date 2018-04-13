package de.unidue.ltl.eduscoring.crosslingual.experiments;



import org.dkpro.lab.task.Dimension;
import org.dkpro.tc.api.features.TcFeatureFactory;
import org.dkpro.tc.api.features.TcFeatureSet;
import org.dkpro.tc.core.Constants;
import org.dkpro.tc.features.ngram.CharacterNGram;
import org.dkpro.tc.features.ngram.WordNGram;




public class FeatureSettings implements Constants{


	/*
	 * Standard Baseline feature set for prompt-specific scoring 
	 */
	public static Dimension<TcFeatureSet> getFeatureSetsDimBaseline()
	{
		Dimension<TcFeatureSet> dimFeatureSets = Dimension.create(
				DIM_FEATURE_SET,
				new TcFeatureSet(
//						TcFeatureFactory.create(NrOfTokens.class),
						TcFeatureFactory.create(
								WordNGram.class,
								WordNGram.PARAM_NGRAM_MIN_N, 1,
								WordNGram.PARAM_NGRAM_MAX_N, 3,
								WordNGram.PARAM_NGRAM_USE_TOP_K, 10000
								),
						TcFeatureFactory.create(
								CharacterNGram.class,
								CharacterNGram.PARAM_NGRAM_MIN_N, 2,
								CharacterNGram.PARAM_NGRAM_MAX_N, 5,
								CharacterNGram.PARAM_NGRAM_USE_TOP_K, 10000
								)
						)
				);
		return dimFeatureSets;
	}

	

	
	public static Dimension<TcFeatureSet> getFeatureSetsDimBaselineForAttributeSelection()
	{
		Dimension<TcFeatureSet> dimFeatureSets = Dimension.create(
				DIM_FEATURE_SET,
				new TcFeatureSet(
					//	TcFeatureFactory.create(NrOfTokens.class),
						TcFeatureFactory.create(
								WordNGram.class,
								WordNGram.PARAM_NGRAM_MIN_N, 1,
								WordNGram.PARAM_NGRAM_MAX_N, 3,
								WordNGram.PARAM_NGRAM_USE_TOP_K, 10000
								),
						TcFeatureFactory.create(
								CharacterNGram.class,
								CharacterNGram.PARAM_NGRAM_MIN_N, 1,
								CharacterNGram.PARAM_NGRAM_MAX_N, 4,
								CharacterNGram.PARAM_NGRAM_USE_TOP_K, 10000
								)
						)
				);
		return dimFeatureSets;
	}


	public static Dimension<TcFeatureSet> getFeatureSetsDimNGram(int topK)
	{
		Dimension<TcFeatureSet> dimFeatureSets = Dimension.create(
				DIM_FEATURE_SET,
				new TcFeatureSet(
						TcFeatureFactory.create(
								WordNGram.class,
								WordNGram.PARAM_NGRAM_MIN_N, 1,
								WordNGram.PARAM_NGRAM_MAX_N, 3,
								WordNGram.PARAM_NGRAM_USE_TOP_K, topK
								)
						)
				);
		return dimFeatureSets;
	}
	
	
	
	
	
	public static Dimension<TcFeatureSet> getFeatureSetsDimCharNGram(int topK)
	{
		Dimension<TcFeatureSet> dimFeatureSets = Dimension.create(
				DIM_FEATURE_SET,
				new TcFeatureSet(
						TcFeatureFactory.create(
								CharacterNGram.class,
								CharacterNGram.PARAM_NGRAM_MIN_N, 2,
								CharacterNGram.PARAM_NGRAM_MAX_N, 4,
								CharacterNGram.PARAM_NGRAM_USE_TOP_K, topK
								)
						)
				);
		return dimFeatureSets;
	}
	
	
	
	
	



	


}
