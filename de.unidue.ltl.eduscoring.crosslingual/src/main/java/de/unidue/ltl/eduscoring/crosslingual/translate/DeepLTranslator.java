package de.unidue.ltl.eduscoring.crosslingual.translate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class DeepLTranslator extends Translator {
	
	private String sourceLanguage;
	private String targetLanguage;
	
	public DeepLTranslator(String sourceLanguage, String targetLanguage) {
		this.sourceLanguage = sourceLanguage;
		this.targetLanguage = targetLanguage;
	}
	
	public void translateFile(String filepathInput, String filepathOutput) {
		List<String> linesForTranslation = this.readFile(filepathInput);
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filepathOutput));
		
		
			for(String line : linesForTranslation) {
				//Remove some things DeepL does not like to translate
				String lineForTranslation = line.replaceAll("\"", "").replaceAll("^p", "").replaceAll(" +", " ").replaceAll("\\\\", "");
				
				bw.write(this.translate(lineForTranslation, this.sourceLanguage, this.targetLanguage) + "\n");
				bw.flush();
			}
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private String translate(String lineToTranslate, String sourceLang, String targetLang) {
		// Sends a JSON object via Http Post to DeepL and gets a JSON object as response
		HttpClient httpClient = new DefaultHttpClient();
		lineToTranslate = lineToTranslate.replaceAll("\\s+", " ");
		System.out.println(lineToTranslate);
		String line = "";

		try {
			HttpPost request = new HttpPost("https://www.deepl.com/jsonrpc");
			StringEntity params = this.getJson(lineToTranslate, sourceLang, targetLang);
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			line = rd.readLine();
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String response = getTranslationFromJson(line);
		System.out.println(response);
		return response;
	}
	
	private StringEntity getJson(String textForTranslation, String sourceLang, String targetLang) {
		// Standard creation of json. See https://stackoverflow.com/a/46007620 for more information.
		StringEntity json = null;
		try {
			json = new StringEntity("{"
					+ "\"jsonrpc\": \"2.0\"," 
					+ "\"method\": \"LMT_handle_jobs\","
					+ "\"params\": {"
						+ "\"jobs\": [{"
							+ "\"kind\":\"default\","
							+ "\"raw_en_sentence\":\"" + textForTranslation 
						+ "\"}]," 
						+ "\"lang\":{" 
							+ "\"user_preferred_langs\": [" 
							+ "\"" + sourceLang +"\"," 
							+ "\"" + targetLang +"\"],"
					+ "\"source_lang_user_selected\":\"" + sourceLang + "\", " 
					+ "\"target_lang\":\"" + targetLang +"\"}," 
					+ "\"priority\": -1}}");
			
		} catch (UnsupportedEncodingException e) {
			System.out.println("An error occured during creation of json.");
			e.printStackTrace();
		}
		return json;
	}

	private String getTranslationFromJson(String translatedJson) {
		// Get the first translation from json-response
//		System.out.println(translatedJson);
		try {
		translatedJson = translatedJson.substring(translatedJson.indexOf("postprocessed_sentence"),
				translatedJson.indexOf("\",\"score\":"));
		} catch(StringIndexOutOfBoundsException e) {
			System.out.println(translatedJson);
		}
		return StringEscapeUtils.unescapeJava(translatedJson.replace("postprocessed_sentence\":\"", ""));
	}

}
