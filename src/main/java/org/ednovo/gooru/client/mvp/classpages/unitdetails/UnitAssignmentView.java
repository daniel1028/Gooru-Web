/*******************************************************************************
 * Copyright 2013 Ednovo d/b/a Gooru. All rights reserved.
 * 
 *  http://www.goorulearning.org/
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining
 *  a copy of this software and associated documentation files (the
 *  "Software"), to deal in the Software without restriction, including
 *  without limitation the rights to use, copy, modify, merge, publish,
 *  distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to
 *  the following conditions:
 * 
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 * 
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/
package org.ednovo.gooru.client.mvp.classpages.unitdetails;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ednovo.gooru.client.PlaceTokens;
import org.ednovo.gooru.client.gin.AppClientFactory;
import org.ednovo.gooru.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.client.mvp.classpages.tabitem.assignments.collections.CollectionsView;
import org.ednovo.gooru.shared.i18n.MessageProperties;

import org.ednovo.gooru.shared.model.content.CollectionItemDo;

import org.ednovo.gooru.shared.model.content.ClasspageListDo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
public class UnitAssignmentView extends BaseViewWithHandlers<UnitAssignmentUiHandlers> implements IsUnitAssignmentView{

	@UiField HTMLPanel assignmentContainer;


	private static UnitAssignmentViewUiBinder uiBinder = GWT.create(UnitAssignmentViewUiBinder.class);

	interface UnitAssignmentViewUiBinder extends UiBinder<Widget, UnitAssignmentView> {
		
	}
	
	@UiField HTMLPanel unitPanel;
	
	@UiField Label lblMoreUnits;
	
	@UiField Anchor unitSetupButton;
	
	UnitAssignmentCssBundle res;
	
	ClasspageListDo classpageListDo;
	
	private int limit = 5;
	private int offSet = 0;
		
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	@UiField HTMLPanel circleContainerPanel;
	@UiField Label generalLabel,requiredLabel,optionalLabel;
	Image leftArrow = new Image();
	Image rightArrow = new Image();
		
	private String ORDER_BY="sequence";
	private int limit_circle=10;
	private int offset=0;
	UnitAssigmentReorder unitAssigmentReorder = new UnitAssigmentReorder();
	private HandlerRegistration leftHandler;
	private HandlerRegistration rightHandler;
	
	@Inject
	public UnitAssignmentView(){
		setWidget(uiBinder.createAndBindUi(this));
		assignmentContainer.add(new CollectionsView(null, 0));
		this.res = UnitAssignmentCssBundle.INSTANCE;
		res.unitAssignment().ensureInjected();
		unitSetupButton.addClickHandler(new UnitSetupEvents());
	}
	@Override
	public void getPathwayItems(){
		getUiHandlers().getPathwayItems("c8afe3ee-8d98-4aa6-a161-9d7cb0626bb2", "25509399-83ab-42f1-b774-c1e424b132d0", ORDER_BY, limit_circle, offset);

//		showUnitNames();
		//setCircleData();

	}
	
	
	public class UnitChangeEvent implements ClickHandler{
		private UnitWidget unitsWidget;
		public UnitChangeEvent(UnitWidget unitsWidget){
			this.unitsWidget=unitsWidget;
		}
		@Override
		public void onClick(ClickEvent event) {
			removeUnitSelectedStyle();
			addUnitSelectStyle(unitsWidget);
		}
	}
	public void removeUnitSelectedStyle(){
		Iterator<Widget> widgets = unitPanel.iterator();
		while (widgets.hasNext()) {
			 Widget widget = widgets.next();
			if (widget instanceof UnitWidget) {
				UnitWidget unitsWidget=(UnitWidget)widget;
				unitsWidget.getUnitNameContainer().removeStyleName(res.unitAssignment().unitMenuActive());
			}
		}		
		
	}
	
	public void addUnitSelectStyle(UnitWidget unitsWidget){
		unitsWidget.getUnitNameContainer().addStyleName(res.unitAssignment().unitMenuActive());
	}
	
	
	public void setCircleData(ArrayList<CollectionItemDo> itemSequence)
	{
		generalLabel.setText("General");
		requiredLabel.setText("Required");
		optionalLabel.setText("Optional");
		try{
			if(leftHandler!=null) {
				leftHandler.removeHandler();
			}
		}catch (AssertionError ae) { }
		try{
			if(rightHandler!=null) {
				rightHandler.removeHandler();
			}
		}catch (AssertionError ae) { }
		leftHandler=leftArrow.addClickHandler(new cleckOnNext("left"));
		rightHandler=rightArrow.addClickHandler(new cleckOnNext("right"));
		circleContainerPanel.clear();
		leftArrow.setUrl("images/leftSmallarrow.png");
		circleContainerPanel.add(leftArrow);
		System.out.println("itemSequence.size()"+itemSequence.size());
		for(int i=0;i<itemSequence.size();i++){
			final UnitCricleView unitCricleViewObj =new UnitCricleView(true,itemSequence.get(i).getItemSequence());
			unitCricleViewObj.getElement().setId(i+"");
			circleContainerPanel.add(unitCricleViewObj);
			unitCricleViewObj.addMouseOverHandler(new UnitSeqMouseOverHandler());
			unitCricleViewObj.addMouseOutHandler(new UnitSeqMouseOutHandler());
		}
		rightArrow.setUrl("images/rightSmallarrow.png");
		circleContainerPanel.add(rightArrow);
		
		
		Iterator<Widget> widgets = circleContainerPanel.iterator();
		
		while (widgets.hasNext()) {
			final Widget widget = widgets.next();
			if (widget instanceof UnitCricleView) {
				((UnitCricleView) widget).addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						final Iterator<Widget> widgetsPanel = circleContainerPanel.iterator();
						while (widgetsPanel.hasNext()) {
							 widgetsPanel.next().removeStyleName(res.unitAssignment().active());
						}
						widget.addStyleName(res.unitAssignment().active());
					}
					
			});
		}
		}
		
	}
	
	@Override
	public void setInSlot(Object slot, Widget content) {
		if (content != null) {
//			 if(slot==UnitAssignmentPresenter._SLOT){
//				 assignmentContainer.clear();
//				 assignmentContainer.add(content);
//			}else{
//				assignmentContainer.setVisible(false);
//			}
		}
	}
	public class cleckOnNext implements ClickHandler{
		String value;
		private cleckOnNext(String value){
			this.value = value;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			if(value=="right"){
				getUiHandlers().getPathwayItems("c8afe3ee-8d98-4aa6-a161-9d7cb0626bb2", "25509399-83ab-42f1-b774-c1e424b132d0", ORDER_BY, limit_circle, 10);
			}
			else{
				getUiHandlers().getPathwayItems("c8afe3ee-8d98-4aa6-a161-9d7cb0626bb2", "25509399-83ab-42f1-b774-c1e424b132d0", ORDER_BY, limit_circle, offset);
			}
		}
	}
	
	@Override
	public void getSequence(ArrayList<CollectionItemDo> getSeq) {
		setCircleData(getSeq);
	}
	public class UnitSeqMouseOverHandler implements MouseOverHandler{

		@Override
		public void onMouseOver(MouseOverEvent event) {
			unitAssigmentReorder.setPopupPosition(event.getRelativeElement().getAbsoluteLeft()+40,event.getRelativeElement().getAbsoluteTop()+40);
			unitAssigmentReorder.show();
		}
		}
	public class UnitSeqMouseOutHandler implements MouseOutHandler{

		@Override
		public void onMouseOut(MouseOutEvent event) {
			unitAssigmentReorder.hide();
			
		}
		}

	@Override
	public void showUnitNames(ClasspageListDo classpageListDo) {
		// TODO Auto-generated method stub
		this.classpageListDo=classpageListDo;
		int totalCount=classpageListDo.getTotalHitCount()!=null?classpageListDo.getTotalHitCount():0;
		int size =classpageListDo.getSearchResults().size() ;

		if(Math.abs(totalCount-offSet) > size){
			showMoreUnitsLink();
		}else{
			hideMoreUnitsLink();
		}
		for(int i=0; i<size; i++){
			String unitName=classpageListDo.getSearchResults().get(i).getResource().getTitle();
			int number=classpageListDo.getSearchResults().get(i).getItemSequence();
			String sequenceNumber=Integer.toString(number);
			UnitWidget unitsWidget=new UnitWidget(sequenceNumber, unitName);
			unitsWidget.addClickHandler(new UnitChangeEvent(unitsWidget));
			unitsWidget.getElement().setId(sequenceNumber);
			unitPanel.add(unitsWidget);
		}

	}

	@UiHandler("lblMoreUnits")
	public void clickOnMoreUnits(ClickEvent event){
		offSet=offSet+limit;
		getUiHandlers().getPathwayUnits(limit, offSet);
	}

	@Override
	public void hideMoreUnitsLink() {
		lblMoreUnits.setVisible(false);
	}

	 private class UnitSetupEvents implements ClickHandler{
			@Override
			public void onClick(ClickEvent event) {
				revealPlace("unitsetup");
			}
		}
		 public void revealPlace(String tabName){
				Map<String,String> params = new HashMap<String,String>();
				String pageSize=AppClientFactory.getPlaceManager().getRequestParameter("pageSize", null);
				String classpageid=AppClientFactory.getPlaceManager().getRequestParameter("classpageid", null);
				String pageNum=AppClientFactory.getPlaceManager().getRequestParameter("pageNum", null);
				String pos=AppClientFactory.getPlaceManager().getRequestParameter("pos", null);
				params.put("pageSize", pageSize);
				params.put("classpageid", classpageid);
				params.put("pageNum", pageNum);
				params.put("pos", pos);
				if(tabName!=null){
					params.put("tab", tabName);
				}
				PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.EDIT_CLASSPAGE, params);
				AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
		 }
		
	
	public void showMoreUnitsLink(){
		lblMoreUnits.setVisible(true);
	}
	
}