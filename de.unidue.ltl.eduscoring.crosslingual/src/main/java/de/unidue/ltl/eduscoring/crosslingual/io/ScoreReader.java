//package de.unidue.ltl.eduscoring.crosslingual.io;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.uima.UimaContext;
//import org.apache.uima.collection.CollectionException;
//import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
//import org.apache.uima.fit.descriptor.ConfigurationParameter;
//import org.apache.uima.jcas.JCas;
//import org.apache.uima.resource.ResourceInitializationException;
//import org.apache.uima.util.Progress;
//
//import de.tudarmstadt.ukp.dkpro.core.api.resources.ResourceUtils;
//import de.unidue.ltl.evaluation.measures.agreement.QuadraticallyWeightedKappa;
//
//public class ScoreReader extends JCasCollectionReader_ImplBase {
//
//	public static final String PARAM_INPUT_FILE = "InputFile";
//	@ConfigurationParameter(name = PARAM_INPUT_FILE, mandatory = true)
//	protected String inputFileString;
//	protected URL inputFileURL;
//
//	public static final String PARAM_NUMBER_OF_LABELS = "LabelNumber";
//	@ConfigurationParameter(name = PARAM_NUMBER_OF_LABELS, mandatory = false, defaultValue = "4")
//	private String numberOfLabels;
//
//	public static final String PARAM_NUMBER_OF_SCORES = "ScoreAmount";
//	@ConfigurationParameter(name = PARAM_NUMBER_OF_SCORES, mandatory = false, defaultValue = "3")
//	private String numberOfScores;
//
//	public static final String PARAM_ENCODING = "Encoding";
//	@ConfigurationParameter(name = PARAM_ENCODING, mandatory = false, defaultValue = "UTF-8")
//	private String encoding;
//
//	public static final String PARAM_SEPARATOR = "Separator";
//	@ConfigurationParameter(name = PARAM_SEPARATOR, mandatory = false, defaultValue = "\t")
//	private String separator;
//
//	protected int currentIndex;
//	protected int numOfScores;
//
//	protected List<List<Integer>> essayScores;
//	protected List<String> labels;
//
//	@Override
//	public void initialize(UimaContext aContext) throws ResourceInitializationException {
//		try {
//
//			numOfScores = Integer.parseInt(numberOfScores);
//			essayScores = new ArrayList<List<Integer>>(numOfScores);
//			labels = new ArrayList<String>();
//
//			for (int i = 0; i < numOfScores; i++) {
//				essayScores.add(new ArrayList<Integer>());
//			}
//
//			inputFileURL = ResourceUtils.resolveLocation(inputFileString, this, aContext);
//			BufferedReader reader = new BufferedReader(new InputStreamReader(inputFileURL.openStream(), encoding));
//
//			String nextLine;
//
//			while ((nextLine = reader.readLine()) != null) {
//
//				//Get some labels to identify the computed Kappas
//				if (nextLine.startsWith("Id")) {
//					String[] tmp = nextLine.split(separator);
//					for (int i = 2; i < (numOfScores + 2); i++) {
//						labels.add(tmp[i]);
//					}
//					nextLine = reader.readLine();
//				} else {
//					for(int i = 1; i < (numOfScores+1); i++) {
//						labels.add(i+"");
//					}
//				}
//
//				List<Integer> scores = new ArrayList<Integer>(numOfScores);
//
//				String[] nextItem = nextLine.split(separator);
//				if (nextItem.length == (Integer.parseInt(numberOfScores) + 2)) {
//
//					for (int i = 0; i < numOfScores; i++) {
//						scores.add(Integer.parseInt(nextItem[(i + 2)]));
//					}
//
//				} else {
//					throw new IOException("Wrong file format.");
//				}
//
//				for (int i = 0; i < numOfScores; i++) {
//					essayScores.get(i).add(scores.get(i));
//				}
//			}
//
//			if (numberOfLabels.equals("4")) {
//				for (int i = 0; i < numOfScores; i++) {
//					for (int j = (i + 1); j < numOfScores; j++) {
//						System.out.println(labels.get(i) + " - " + labels.get(j) + ": "
//								+ QuadraticWeightedKappa.getKappa(essayScores.get(i), essayScores.get(j), 0, 1, 2, 3));
//						new ConfusionMatrix(essayScores.get(i), essayScores.get(j), 0, 1, 2, 3).printConfusionMatrix();
//					}
//				}
//
//			} else if (numberOfLabels.equals("3")) {
//				for (int i = 0; i < numOfScores; i++) {
//					for (int j = (i + 1); j < numOfScores; j++) {
//						System.out.println(labels.get(i) + " - " + labels.get(j) + ": "
//								+ QuadraticallyWeightedKappa.getKappa(essayScores.get(i), essayScores.get(j), 0, 1, 2));
//						new ConfusionMatrix(essayScores.get(i), essayScores.get(j), 0, 1, 2).printConfusionMatrix();
//					}
//				}
//			} else {
//				throw new IOException("Unsupported number of labels.");
//			}
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public Progress[] getProgress() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public boolean hasNext() throws IOException, CollectionException {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void getNext(JCas arg0) throws IOException, CollectionException {
//		// TODO Auto-generated method stub
//
//	}
//
//}
