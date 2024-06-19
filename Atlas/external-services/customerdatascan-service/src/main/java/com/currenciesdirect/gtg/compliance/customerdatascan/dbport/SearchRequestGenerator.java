package com.currenciesdirect.gtg.compliance.customerdatascan.dbport;

import org.elasticsearch.index.query.BoolQueryBuilder;

import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.DigitalFootPrint;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.Name;


/**
 * The Class SearchRequestGenerator.
 * 
 * @author Rajesh
 */
public class SearchRequestGenerator {

	/**
	 * Gets the elastic search request.
	 * 
	 * @param document
	 *            the document
	 * @return the elastic search request
	 */
	public String getElasticSearchRequest(DBModel document) {
		SearchQueryBuilder builder = SearchQueryBuilder.getInstance();
		BoolQueryBuilder boolQueryBuilder = builder.getBoolenQueryBuilder();
		builder.addShouldWithTerm(boolQueryBuilder, "sfAccountID",
				document.getSfAccountID());
		builder.addShouldWithTerm(boolQueryBuilder, "auroraAccountID",
				document.getAuroraAccountID());
		if (document.getName() != null) {
			Name name = document.getName();
			builder.addShouldWithTerm(boolQueryBuilder, "name.title",
					name.getTitle());
			builder.addShouldWithTerm(boolQueryBuilder, "name.preferedName",
					name.getPreferedName());
			builder.addShouldWithTerm(boolQueryBuilder, "name.foreName",
					name.getForeName());
			builder.addShouldWithTerm(boolQueryBuilder, "name.middleName",
					name.getMiddleName());
			builder.addShouldWithTerm(boolQueryBuilder, "name.surName",
					name.getSurName());
			builder.addShouldWithTerm(boolQueryBuilder, "name.secondSurname",
					name.getSecondSurname());
		}
		builder.addShouldWithTerm(boolQueryBuilder, "address",
				document.getAddress());
		builder.addShouldWithTerm(boolQueryBuilder, "nationality",
				document.getNationality());
		builder.addShouldWithTerm(boolQueryBuilder, "placeOfBirth",
				document.getPlaceOfBirth());
		builder.addShouldWithTerm(boolQueryBuilder, "familyNameAtBirth",
				document.getFamilyNameAtBirth());
		builder.addShouldWithTerm(boolQueryBuilder, "familyNameAtCitizenship",
				document.getFamilyNameAtCitizenship());
		builder.addShouldWithTerm(boolQueryBuilder, "gender",
				document.getGender());
		builder.addShouldWithTerm(boolQueryBuilder, "dobDay",
				document.getDobDay());
		builder.addShouldWithTerm(boolQueryBuilder, "dobMonth",
				document.getDobMonth());
		builder.addShouldWithTerm(boolQueryBuilder, "dobYear",
				document.getDobYear());
		builder.addShouldWithTerm(boolQueryBuilder, "phone",
				document.getPhone());
		builder.addShouldWithTerm(boolQueryBuilder, "fax", document.getFax());
		builder.addShouldWithTerm(boolQueryBuilder, "email",
				document.getEmail());
		builder.addShouldWithTerm(boolQueryBuilder, "ipAddress",
				document.getIpAddress());
		if (document.getDigitalFootPrint() != null) {
			DigitalFootPrint digitalFootPrint = document.getDigitalFootPrint();
			builder.addShouldWithTerm(boolQueryBuilder,
					"digitalFootPrint.deviceId", digitalFootPrint.getDeviceId());
		}
		builder.addMustWithTerm(boolQueryBuilder, "isDeleted", "false");
		return builder.build(boolQueryBuilder);
	}
}
