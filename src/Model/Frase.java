package Model;

import java.util.Date;

import com.ibm.watson.developer_cloud.alchemy.v1.model.AlchemyLanguageGenericModel;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Concepts;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Dates;
import com.ibm.watson.developer_cloud.alchemy.v1.model.DocumentEmotion;
import com.ibm.watson.developer_cloud.alchemy.v1.model.DocumentSentiment;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Entities;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Keywords;
import com.ibm.watson.developer_cloud.alchemy.v1.model.SAORelations;
import com.ibm.watson.developer_cloud.alchemy.v1.model.TypedRelations;
import com.ibm.watson.developer_cloud.alchemy.v1.model.DocumentEmotion.Emotion;

public class Frase {

	private String fraseOriginale;
	private String fraseTradotta;
	private Date data;

	DocumentSentiment sentiment;
	SAORelations relations;
	TypedRelations typedRelation;
	Concepts conceptAlchemy;
	Entities entitiesAlchemy;
	Keywords keyWordAlchemy;
	DocumentEmotion emotion;
	
	private AlchemyLanguageGenericModel[] dati={sentiment,relations,typedRelation,conceptAlchemy,entitiesAlchemy,keyWordAlchemy,emotion};
	
	public Frase(String fraseOriginale, String fraseTradotta, Date data, DocumentSentiment sentiment,
			SAORelations relations, TypedRelations typedRelation, Concepts conceptAlchemy, Entities entitiesAlchemy,
			Keywords keyWordAlchemy, DocumentEmotion emotion) {
		super();
		this.fraseOriginale = fraseOriginale;
		this.fraseTradotta = fraseTradotta;
		this.data = data;
		this.sentiment = sentiment;
		this.relations = relations;
		this.typedRelation = typedRelation;
		this.conceptAlchemy = conceptAlchemy;
		this.entitiesAlchemy = entitiesAlchemy;
		this.keyWordAlchemy = keyWordAlchemy;
		this.emotion = emotion;
		
	}
	public String getFraseOriginale() {
		return fraseOriginale;
	}
	public AlchemyLanguageGenericModel[] getDati() {
		return dati;
	}
	public void setDati(AlchemyLanguageGenericModel[] dati) {
		this.dati = dati;
	}
	public void setFraseOriginale(String fraseOriginale) {
		this.fraseOriginale = fraseOriginale;
	}
	public String getFraseTradotta() {
		return fraseTradotta;
	}
	public void setFraseTradotta(String fraseTradotta) {
		this.fraseTradotta = fraseTradotta;
	}
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	public DocumentSentiment getSentiment() {
		return sentiment;
	}
	public void setSentiment(DocumentSentiment sentiment) {
		this.sentiment = sentiment;
	}
	public SAORelations getRelations() {
		return relations;
	}
	public void setRelations(SAORelations relations) {
		this.relations = relations;
	}
	public TypedRelations getTypedRelation() {
		return typedRelation;
	}
	public void setTypedRelation(TypedRelations typedRelation) {
		this.typedRelation = typedRelation;
	}
	public Concepts getConceptAlchemy() {
		return conceptAlchemy;
	}
	public void setConceptAlchemy(Concepts conceptAlchemy) {
		this.conceptAlchemy = conceptAlchemy;
	}
	public Entities getEntitiesAlchemy() {
		return entitiesAlchemy;
	}
	public void setEntitiesAlchemy(Entities entitiesAlchemy) {
		this.entitiesAlchemy = entitiesAlchemy;
	}
	public Keywords getKeyWordAlchemy() {
		return keyWordAlchemy;
	}
	public void setKeyWordAlchemy(Keywords keyWordAlchemy) {
		this.keyWordAlchemy = keyWordAlchemy;
	}
	public DocumentEmotion getEmotion() {
		return emotion;
	}
	public void setEmotion(DocumentEmotion emotion) {
		this.emotion = emotion;
	}
	
	
	

	
	
	
	
	
	

}
