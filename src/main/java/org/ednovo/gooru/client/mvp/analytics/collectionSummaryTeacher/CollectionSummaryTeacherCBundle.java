package org.ednovo.gooru.client.mvp.analytics.collectionSummaryTeacher;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;

public interface CollectionSummaryTeacherCBundle extends ClientBundle{
	static final CollectionSummaryTeacherCBundle INSTANCE = GWT.create(CollectionSummaryTeacherCBundle.class);
	public interface CollectionSummaryCss extends CssResource{
		    String alignCenterAndBackground();
		    String setMarginAuto();
	        
	        String category_new_type_audio();
	        String category_new_type_image();
	        String category_new_type_other();
	        String category_new_type_interactive();
	        String category_new_type_text();
	        String category_new_type_video();
	        String category_new_type_webpage();
	        
	        String setProgressBar();
	        String setIncorrectProgressBar();
	        String viewResponseTextOpended();
	        
	        //Table css
	        String tableMain();
	        String tableHeader();
	        String tableRowEven();
	        String tableRowOdd();
	        String assignment_quesiton_ans_bar();
	        String wrongSelectStyle();
	        String barGraphCharacter();
	        String tickMarkImgCss();
	        String tickMarkImg();
	        
	        String displayMessageTextForScoredQuestions();
	        String displayMessageTextForOEQuestions();
	        String setOETextPopupCenter();
			String alignLeft();
	        
	}
	@NotStrict
	@Source("collectionSummaryTeacher.css")
	CollectionSummaryCss css();
}
