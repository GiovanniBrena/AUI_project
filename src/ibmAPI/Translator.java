package ibmAPI;

import com.ibm.watson.developer_cloud.language_translator.v2.*;
import com.ibm.watson.developer_cloud.language_translator.v2.model.Language;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslationResult;

import client.Constants;

public class Translator {
	
	
	
	public static String translateString(String input){
		
		LanguageTranslator service = new LanguageTranslator();
		service.setUsernameAndPassword(Constants.WATSON_TRANSLATOR_USER, Constants.WATSON_TRANSLATOR_PSW);
		TranslationResult translationResult=service.translate(input, Language.ITALIAN, Language.ENGLISH).execute();
	
		return translationResult.getTranslations().get(0).getTranslation();
	}
	
	
	
}
