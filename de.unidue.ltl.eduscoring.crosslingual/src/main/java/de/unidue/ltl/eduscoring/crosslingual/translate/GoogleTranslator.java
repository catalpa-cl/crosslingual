package de.unidue.ltl.eduscoring.crosslingual.translate;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.translate.Translate;
import com.google.api.services.translate.model.TranslationsListResponse;
import com.google.api.services.translate.model.TranslationsResource;

public class GoogleTranslator extends Translator {

	private String apiKey;
	private String targetLanguage;

	public GoogleTranslator(String apiKey, String targetLanguage) {
		this.apiKey = apiKey;
		this.targetLanguage = targetLanguage;
	}

	public void translateFile(String filepathInput, String filepathOutput) {
		List<String> linesForTranslation = this.readFile(filepathInput);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filepathOutput));
			List<String> smallList = new ArrayList<String>();
			for (int i = 0; i < linesForTranslation.size(); i++) {
				smallList.add(linesForTranslation.get(i));
				if (i % 10 == 0) {
					try {
						TranslationsListResponse response = this.translate(smallList);
						for (TranslationsResource tr : response.getTranslations()) {
							System.out.println(tr.getTranslatedText());
							bw.write(tr.getTranslatedText() + "\n");
							bw.flush();
						}
					} catch (GoogleJsonResponseException e) {
						e.getMessage();
					}
					smallList.clear();
					TimeUnit.SECONDS.sleep(5);
				}
			}
			TranslationsListResponse response = translate(smallList);
			for (TranslationsResource tr : response.getTranslations()) {
				System.out.println(tr.getTranslatedText());
				bw.write(tr.getTranslatedText() + "\n");
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	private TranslationsListResponse translate(List<String> smallList)
			throws IOException, GeneralSecurityException {
		// See comments on
		// https://developers.google.com/resources/api-libraries/documentation/translate/v2/java/latest/
		// on options to set
		Translate t = new Translate.Builder(
				com.google.api.client.googleapis.javanet.GoogleNetHttpTransport.newTrustedTransport(),
				com.google.api.client.json.gson.GsonFactory.getDefaultInstance(), null)
						// Need to update this to your App-Name
						.setApplicationName("EduscoringTranslations").build();

		Translate.Translations.List list = t.new Translations().list(smallList, this.targetLanguage);

		list.setKey(this.apiKey);
		return list.execute();
	}

}
