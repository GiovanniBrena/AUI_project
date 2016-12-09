package Model;

import java.util.Date;
import java.util.List;

import com.ibm.watson.developer_cloud.alchemy.v1.model.AlchemyLanguageGenericModel;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Concept;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Concepts;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Dates;
import com.ibm.watson.developer_cloud.alchemy.v1.model.DocumentEmotion;
import com.ibm.watson.developer_cloud.alchemy.v1.model.DocumentSentiment;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Entities;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Entity;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Keyword;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Keywords;
import com.ibm.watson.developer_cloud.alchemy.v1.model.SAORelations;
import com.ibm.watson.developer_cloud.alchemy.v1.model.TypedRelation;
import com.ibm.watson.developer_cloud.alchemy.v1.model.TypedRelations;
import com.ibm.watson.developer_cloud.alchemy.v1.model.DocumentEmotion.Emotion;

public class Frase {

	private String fraseOriginale;
	private String fraseTradotta;
	private Date data;

	DocumentSentiment sentiment;
	DocumentEmotion emotion;
	List<TypedRelation> typedRelation;
	List<Concept> conceptAlchemy;
	List<Entity> entitiesAlchemy;
	List<Keyword> keyWordAlchemy;
	
	
	
	
	//private AlchemyLanguageGenericModel[] datiAlchemy={sentiment,emotion,typedRelation,conceptAlchemy,entitiesAlchemy,keyWordAlchemy};
	
	public Frase(String fraseOriginale, String fraseTradotta, Date data, DocumentSentiment sentiment,
			DocumentEmotion emotion, List<TypedRelation> typedRelation, List<Concept> conceptAlchemy, List<Entity> entitiesAlchemy,
			List<Keyword> keyWordAlchemy) {
		super();
		this.fraseOriginale = fraseOriginale;
		this.fraseTradotta = fraseTradotta;
		this.data = data;
		this.sentiment = sentiment;
		
		this.typedRelation = typedRelation;
		this.conceptAlchemy = conceptAlchemy;
		this.entitiesAlchemy = entitiesAlchemy;
		this.keyWordAlchemy = keyWordAlchemy;
		this.emotion = emotion;
		
		
		
		
		
	}
	public String getFraseOriginale() {
		return fraseOriginale;
	}
	public void setFraseOriginale(String input) {
		// TODO Auto-generated method stub
		this.fraseOriginale=input;
		
	}
	public String getFraseTradotta() {
		return fraseTradotta;
	}
	public void setFraseTradotta(String fraseTradotta) {
		this.fraseTradotta = fraseTradotta;
	}
	public DocumentSentiment getSentiment() {
		return sentiment;
	}
	public void setSentiment(DocumentSentiment sentiment) {
		this.sentiment = sentiment;
	}
	public DocumentEmotion getEmotion() {
		return emotion;
	}
	public void setEmotion(DocumentEmotion emotion) {
		this.emotion = emotion;
	}
	public List<TypedRelation> getTypedRelation() {
		return typedRelation;
	}
	public void setTypedRelation(List<TypedRelation> typedRelation) {
		this.typedRelation = typedRelation;
	}
	public List<Concept> getConceptAlchemy() {
		return conceptAlchemy;
	}
	public void setConceptAlchemy(List<Concept> conceptAlchemy) {
		this.conceptAlchemy = conceptAlchemy;
	}
	public List<Entity> getEntitiesAlchemy() {
		return entitiesAlchemy;
	}
	public void setEntitiesAlchemy(List<Entity> entitiesAlchemy) {
		this.entitiesAlchemy = entitiesAlchemy;
	}
	public List<Keyword> getKeyWordAlchemy() {
		return keyWordAlchemy;
	}
	public void setKeyWordAlchemy(List<Keyword> keyWordAlchemy) {
		this.keyWordAlchemy = keyWordAlchemy;
	}
	

	

	
	
	
	
	
	

}
