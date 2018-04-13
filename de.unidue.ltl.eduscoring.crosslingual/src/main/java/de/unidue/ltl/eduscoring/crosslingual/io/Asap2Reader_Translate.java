package de.unidue.ltl.eduscoring.crosslingual.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;
import org.dkpro.tc.api.type.TextClassificationOutcome;
import org.dkpro.tc.api.type.TextClassificationTarget;

import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;
import de.tudarmstadt.ukp.dkpro.core.api.resources.ResourceUtils;
import de.unidue.ltl.escrito.io.shortanswer.Asap2Item;


public class Asap2Reader_Translate extends JCasCollectionReader_ImplBase {
	public static final Integer[] essaySetIds = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

	protected static final String DEFAULT_LANGUAGE = "en";

	public static final String PARAM_INPUT_FILE = "InputFile";
	@ConfigurationParameter(name = PARAM_INPUT_FILE, mandatory = true)
	protected String inputFileString;
	protected URL inputFileURL;


	public static final String PARAM_DICTIONARY_EN = "dict_en";
	@ConfigurationParameter(name = PARAM_DICTIONARY_EN, mandatory = true)
	protected String dict_en;

	public static final String PARAM_DICTIONARY_DE = "dict_de";
	@ConfigurationParameter(name = PARAM_DICTIONARY_DE, mandatory = true)
	protected String dict_de;

	public static final String PARAM_LANGUAGE = "Language";
	@ConfigurationParameter(name = PARAM_LANGUAGE, mandatory = false, defaultValue = DEFAULT_LANGUAGE)
	protected String language;

	public static final String PARAM_ENCODING = "Encoding";
	@ConfigurationParameter(name = PARAM_ENCODING, mandatory = false, defaultValue = "UTF-8")
	private String encoding;

	public static final String PARAM_SEPARATOR = "Separator";
	@ConfigurationParameter(name = PARAM_SEPARATOR, mandatory = false, defaultValue = "\t")
	private String separator;

	public static final String PARAM_ESSAY_SET_ID = "EssaySetId";
	@ConfigurationParameter(name = PARAM_ESSAY_SET_ID, mandatory = false)
	protected Integer requestedEssaySetId; 

	protected int currentIndex;

	protected Map<String, List<Asap2Item>> itemMap;

	protected Queue<Asap2Item> asap2Items;

	@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException
	{

		Map<String, String> translations = new HashMap<String, String>();
		try {
			BufferedReader br_en = new BufferedReader(new FileReader(dict_en));
			BufferedReader br_de = new BufferedReader(new FileReader(dict_de));
			String line_en;
			String line_de;
			while ((line_en = br_en.readLine()) != null){
				line_de = br_de.readLine();
				String text_en = line_en.trim().replace("\"", "");
				String text_de = line_de.trim().replace("\"", "");
				// HOTFIX for Issue 445 in DKPro Core
				text_en = text_en.replace("’", "'");
				text_de = text_de.replace("’", "'");
				translations.put(text_en, text_de);
				//			System.out.println(text_en+"\t"+text_de);
			}
			br_en.close();
			br_de.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		itemMap = new HashMap<String, List<Asap2Item>>();
		asap2Items = new LinkedList<Asap2Item>();

		if (requestedEssaySetId != null && requestedEssaySetId < 0) {
			getLogger().warn("Invalid essaySetId - using all documents");
			requestedEssaySetId = null;
		}


		try {
			inputFileURL = ResourceUtils.resolveLocation(inputFileString, this, aContext);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(
							inputFileURL.openStream(),
							encoding
							)
					);
			String nextLine;

			while ((nextLine = reader.readLine()) != null) {
				// skip the header
				if (nextLine.startsWith("Id")) {
					nextLine = reader.readLine();
				}

				int textId       = -1;
				int essaySetId   = -1;
				String goldClass = null;
				String valClass  = null;
				String text      = null;


				String[] nextItem = nextLine.split(separator);

				if (nextItem.length == 5) {
					textId     = Integer.parseInt(nextItem[0]);
					essaySetId = Integer.parseInt(nextItem[1]);
					goldClass  = nextItem[2];
					valClass   = nextItem[3];
					text       = nextItem[4];
				}
				else if (nextItem.length == 3) {
					textId     = Integer.parseInt(nextItem[0]);
					essaySetId = Integer.parseInt(nextItem[1]);
					text       = nextItem[2];
				}
				else {
					throw new IOException("Wrong file format.");
				} 


				text = text.trim().replace("\"", "");
				// HOTFIX for Issue 445 in DKPro Core
				text = text.replace("’", "'");
				if (translations.containsKey(text)){
					text = translations.get(text);
					//		System.out.println("translation: "+text);
					// HOTFIX for Issue 445 in DKPro Core
					text = text.replace("’", "'");

					Asap2Item newItem = new Asap2Item(textId, essaySetId, goldClass, valClass, text);

					// if validEssaySetId is set, then skip if not equal with current 
					if (requestedEssaySetId != null && requestedEssaySetId != essaySetId) {
						continue;
					}

					List<Asap2Item> itemList;
					if (itemMap.containsKey(goldClass)) {
						itemList = itemMap.get(goldClass);
					}
					else {
						itemList = new ArrayList<Asap2Item>();
					}
					itemList.add(newItem);
					itemMap.put(goldClass, itemList);

					asap2Items.add(newItem);
				} else { 
					System.err.println("No translation found in "+dict_en+" for "+text);
					//System.exit(-1);
				}
			}
		}
		catch (Exception e) {
			throw new ResourceInitializationException(e);
		}

		currentIndex = 0;
	}

	public Progress[] getProgress()
	{
		return new Progress[] { new ProgressImpl(currentIndex, currentIndex, Progress.ENTITIES) };
	}


	public boolean hasNext()
			throws IOException 
	{
		return !asap2Items.isEmpty();
	}


	public void getNext(JCas jcas)
			throws IOException, CollectionException
	{
		Asap2Item asap2Item = asap2Items.poll();
		getLogger().debug(asap2Item);
		String itemId = asap2Item.getPromptId()+"_"+asap2Item.getTextId(); 

		try
		{
			if (language != null) {
				jcas.setDocumentLanguage(language);
			}
			else {
				jcas.setDocumentLanguage(DEFAULT_LANGUAGE);
			}

			jcas.setDocumentText(asap2Item.getText());

			DocumentMetaData dmd = DocumentMetaData.create(jcas);
			dmd.setDocumentId(itemId); // + "-" + asap2Item.getEssaySetId());
			dmd.setDocumentTitle(itemId);
			dmd.setDocumentUri(inputFileURL.toURI().toString());
			dmd.setCollectionId(itemId);

		} 
		catch (URISyntaxException e) {
			throw new CollectionException(e);
		}

		TextClassificationTarget unit = new TextClassificationTarget(jcas, 0, jcas.getDocumentText().length());
		// will add the token content as a suffix to the ID of this unit 
		unit.setSuffix(itemId);
		unit.addToIndexes();

		// The gold score is always assigned to the container CAS
		TextClassificationOutcome outcome = new TextClassificationOutcome(jcas, 0, jcas.getDocumentText().length());
		outcome.setOutcome(asap2Item.getGoldClass());
		outcome.addToIndexes();

		currentIndex++;
	}
}

