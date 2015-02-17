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
 
package org.ednovo.gooru.client.mvp.folder.toc;

import java.util.List;

import org.ednovo.gooru.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.client.mvp.folder.toc.util.FolderCollectionView;
import org.ednovo.gooru.client.mvp.search.AddResourceContainerView.CollectionTreeItem;
import org.ednovo.gooru.client.mvp.shelf.list.TreeMenuImages;
import org.ednovo.gooru.client.uc.H3Panel;
import org.ednovo.gooru.shared.i18n.MessageProperties;
import org.ednovo.gooru.shared.model.folder.FolderDo;
import org.ednovo.gooru.shared.model.folder.FolderTocDo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
/**
 * @fileName : FolderTocView.java
 *
 * @description : 
 *
 * @version : 1.3
 *
 * @date: 06-02-2015
 *
 * @Author Gooru Team
 *
 * @Reviewer: 
 */
public class FolderTocView extends BaseViewWithHandlers<FolderTocUiHandlers> implements IsFolderTocView {

	private static FolderTocViewUiBinder uiBinder = GWT
			.create(FolderTocViewUiBinder.class);

	interface FolderTocViewUiBinder extends UiBinder<Widget, FolderTocView> {
	}
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	@UiField HTMLPanel floderTreeContainer,marginDiv;
	@UiField Label lblBigIdeas,lblEssentalQuestions,lblPerformanceTasks;
	@UiField H3Panel lblFolderTitle;
	
	final String FOLDER="folder";
	final String SCOLLECTION="scollection";
	
	private Tree folderTocTree = new Tree(new TreeMenuImages()) {
		@Override
		public void onBrowserEvent(Event event) {
			int eventType = DOM.eventGetType(event);
			if (!(eventType == Event.ONKEYUP || eventType == Event.ONKEYPRESS || eventType == Event.ONKEYDOWN)) {
				super.onBrowserEvent(event);
			}
		}
	};
	private CollectionTreeItem previousSelectedItem = null;
	private FolderTreeItem currentFolderSelectedTreeItem = null;
	private FolderTreeItem previousFolderSelectedTreeItem = null;
	
	public FolderTocView() {
		setWidget(uiBinder.createAndBindUi(this));
		FolderContainerCBundle.INSTANCE.css().ensureInjected();
		setData();
		//This will handle the window resize
		Window.addResizeHandler(new ResizeLogicEvent());
	}
	/**
	 * This method is used to set folder TOC Data.
	 */
	public void setData() {
		floderTreeContainer.clear();
		floderTreeContainer.add(folderTocTree);
		folderTocTree.addItem(getLoadingImage());
		folderTocTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
			@Override
			public void onSelection(SelectionEvent<TreeItem> event) {
				final TreeItem item = (TreeItem) event.getSelectedItem();
				Widget folderWidget = item.getWidget();
				FolderTreeItem folderTreeItemWidget = null;
				if (folderWidget instanceof FolderTreeItem) {
					folderTreeItemWidget = (FolderTreeItem) folderWidget;
					if (folderTreeItemWidget.isOpen()) {
						folderTreeItemWidget.removeStyleName(FolderContainerCBundle.INSTANCE.css().open());
						folderTreeItemWidget.arrowLabel.removeStyleName(FolderContainerCBundle.INSTANCE.css().arrowActive());
						folderTreeItemWidget.setOpen(false);
					} else {
						folderTreeItemWidget.addStyleName(FolderContainerCBundle.INSTANCE.css().open());
						folderTreeItemWidget.arrowLabel.addStyleName(FolderContainerCBundle.INSTANCE.css().arrowActive());
						folderTreeItemWidget.setOpen(true);
					}
					removePreviousSelectedItem();
					currentFolderSelectedTreeItem = folderTreeItemWidget;
					previousFolderSelectedTreeItem = currentFolderSelectedTreeItem;
					currentFolderSelectedTreeItem
							.addStyleName(FolderContainerCBundle.INSTANCE
									.css().selected());
					TreeItem parent = item.getParentItem();
					item.getTree().setSelectedItem(parent, false); 
					if (!folderTreeItemWidget.isApiCalled()) {
						folderTreeItemWidget.setApiCalled(true);
						item.addItem(loadingTreeItem());
						getFolderItems(item, folderTreeItemWidget.getGooruOid());
					}
					if (parent != null)
						parent.setSelected(false); 
						item.setState(!item.getState(), false);
				} 
			}
		});
	}
	@Override
	public void setFolderItems(FolderTocDo  foldersTocObj) {
		folderTocTree.clear();
		setFolderMetaData(foldersTocObj);
		if(foldersTocObj!=null){
			List<FolderDo> foldersArrayList=foldersTocObj.getCollectionItems();
			 if(foldersArrayList.size()>0){
				 for(int i=0;i<foldersArrayList.size();i++){
					 FolderDo floderDo=foldersArrayList.get(i);
					 if(FOLDER.equalsIgnoreCase(floderDo.getType())){
						 TreeItem folderItem=new TreeItem(new FolderTreeItem(null,floderDo.getTitle(),floderDo.getGooruOid()));
						 folderTocTree.addItem(folderItem);
						 adjustTreeItemStyle(folderItem);
					 }else if(SCOLLECTION.equalsIgnoreCase(floderDo.getType())){
						 TreeItem folderItem=new TreeItem(new FolderCollectionView(null,floderDo));
						 folderTocTree.addItem(folderItem);
						 adjustTreeItemStyle(folderItem);
					 }
				 }
			 }
		}
		floderTreeContainer.clear();
		floderTreeContainer.add(folderTocTree);
	}
	/**
	 * This method will set the folder meta data on the toc page.
	 * @param foldersTocObj
	 */
	void setFolderMetaData(FolderTocDo  foldersTocObj){
		if(foldersTocObj!=null){
			lblBigIdeas.setText(foldersTocObj.getIdeas()!=null?foldersTocObj.getIdeas():"");
			lblFolderTitle.setText(foldersTocObj.getTitle()!=null?foldersTocObj.getTitle():"");
			lblEssentalQuestions.setText(foldersTocObj.getQuestions()!=null?foldersTocObj.getQuestions():"");
			lblPerformanceTasks.setText(foldersTocObj.getPerformanceTasks()!=null?foldersTocObj.getPerformanceTasks():"");
		}
	}

	/**
	 * @function adjustTreeItemStyle 
	 * 
	 * @created_date : 10-02-2015
	 * 
	 * @description
	 * 
	 * @parm(s) : @param uiObject
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
   	private  void adjustTreeItemStyle(final UIObject uiObject) {
	      if (uiObject instanceof TreeItem) {
	         if (uiObject != null && uiObject.getElement() != null) {
	            Element element = uiObject.getElement();
	            element.getStyle().setPadding(0, Unit.PX);
	            element.getStyle().setMarginLeft(0, Unit.PX);
	         }
	      } else {
	         if (uiObject != null && uiObject.getElement() != null && uiObject.getElement().getParentElement() != null
	               && uiObject.getElement().getParentElement().getParentElement() != null
	               && uiObject.getElement().getParentElement().getParentElement().getStyle() != null) {
	            Element element = uiObject.getElement().getParentElement().getParentElement();
	            element.getStyle().setPadding(0, Unit.PX);
	            element.getStyle().setMarginLeft(0, Unit.PX);
	         }
	      }
   	}

	/**
	 * 
	 * @fileName : FolderTocView.java
	 *
	 * @description : 
	 *
	 * @version : 1.3
	 *
	 * @date: 10-02-2015
	 *
	 * @Author Gooru
	 *
	 * @Reviewer:
	 */
	public class FolderTreeItem extends Composite{
		private FlowPanel folderContainer=null;
		private String gooruOid=null;
		Label floderName=null;
		Label arrowLabel=null;
		private boolean isOpen=false;
		private boolean isApiCalled=false;
		private int folerLevel=1;
		public FolderTreeItem(){
			initWidget(folderContainer=new FlowPanel());
			folderContainer.setStyleName(FolderContainerCBundle.INSTANCE.css().folderLevel());
			arrowLabel=new Label();
			arrowLabel.setStyleName(FolderContainerCBundle.INSTANCE.css().folderTitlearrow());
			folderContainer.add(arrowLabel);
			floderName=new Label();
			floderName.setStyleName(FolderContainerCBundle.INSTANCE.css().folderTitleStyle());
			folderContainer.add(floderName);
		}
		public FolderTreeItem(String levelStyleName,String folderTitle,String gooruOid){
			this();
			if(levelStyleName!=null){
				folderContainer.addStyleName(levelStyleName);
			}
			this.gooruOid=gooruOid;
			floderName.setText(folderTitle);
		}
		public boolean isOpen() {
			return isOpen;
		}
		public void setOpen(boolean isOpen) {
			this.isOpen = isOpen;
		}
		public String getGooruOid(){
			return gooruOid;
		}
		public boolean isApiCalled() {
			return isApiCalled;
		}
		public void setApiCalled(boolean isApiCalled) {
			this.isApiCalled = isApiCalled;
		}
		public int getFolerLevel() {
			return folerLevel;
		}
		public void setFolerLevel(int folerLevel) {
			this.folerLevel = folerLevel;
		}
	}
	private void removePreviousSelectedItem() {
		if (previousFolderSelectedTreeItem != null) {
			previousFolderSelectedTreeItem
					.removeStyleName(FolderContainerCBundle.INSTANCE.css()
							.selected());
		}
		if (previousSelectedItem != null) {
			previousSelectedItem
					.removeStyleName(FolderContainerCBundle.INSTANCE.css()
							.selected());
		}
	}
	/**
	 * This method is used to get the child folder and collections.
	 * @param item
	 * @param parentId
	 */
	private void getFolderItems(TreeItem item, String parentId) {
		getUiHandlers().getFolderItems(item, parentId);
	}

	@Override
	public void setFolderItems(TreeItem item,FolderTocDo foldersTocObj) {
		displayWorkspaceData(item, foldersTocObj);
	}
	/**
	 * This method is used to set folders and collections for selected tree item.
	 * @param item
	 * @param folderListDo
	 */
	private void displayWorkspaceData(TreeItem item,FolderTocDo foldersTocObj) {
		//This will remove the loading text for the child items.
		if(item.getChildCount() > 0){
			item.getChild(0).remove();
		}
		//This will set the data to the selected tree item.
		if(foldersTocObj!=null){
			List<FolderDo> foldersArrayList=foldersTocObj.getCollectionItems();
			if (foldersArrayList != null && foldersArrayList.size() > 0) {
					FolderTreeItem folderTreeItemWidget = (FolderTreeItem) item
							.getWidget();
					int folderLevel = folderTreeItemWidget.getFolerLevel();
					for (int i = 0; i < foldersArrayList.size(); i++) {
						FolderDo floderDo = foldersArrayList.get(i);
						 if(FOLDER.equalsIgnoreCase(floderDo.getType())){
								String styleName = FolderContainerCBundle.INSTANCE.css().child();
								FolderTreeItem innerFolderTreeItem = new FolderTreeItem(
										styleName, floderDo.getTitle(),
										floderDo.getGooruOid());
								innerFolderTreeItem.setFolerLevel(folderLevel + 1);
								TreeItem folderItem = new TreeItem(
										innerFolderTreeItem);
								item.addItem(folderItem);
								adjustTreeItemStyle(folderItem);
						 }else if(SCOLLECTION.equalsIgnoreCase(floderDo.getType())){
							 	String styleName = getTreeItemStyleName(folderLevel);
							 	TreeItem folderItem = new TreeItem(new  FolderCollectionView(null,floderDo));
							 	folderItem.addStyleName(styleName);
							 	item.addItem(folderItem);
								adjustTreeItemStyle(folderItem);
						 }
					}
					item.setState(folderTreeItemWidget.isOpen());
				}
		}
	}
	/**
	 * This method is used to set the style for collections based on the folder level.
	 * @param folderLevel
	 * @return
	 */
	private String getTreeItemStyleName(int folderLevel) {
		if (folderLevel == 1) {
			return FolderContainerCBundle.INSTANCE.css().collectionChild1();
		} else if (folderLevel == 2) {
			return FolderContainerCBundle.INSTANCE.css().collectionChild2();
		}
		return ""; 
	}
	
	/**
	 * To clear the Tree panels
	 */
	@Override
	public void clearTocData() {
		floderTreeContainer.clear();
		currentFolderSelectedTreeItem=null;
		previousSelectedItem=null;
	}
	/**
	 * This method is used to display loading text to the user.
	 * @return
	 */
	public TreeItem loadingTreeItem() {
		Label loadingText = new Label(i18n.GL3120());
		loadingText.setStyleName(FolderContainerCBundle.INSTANCE.css().loadingText());
		return new TreeItem(loadingText);
	}
	/**
	 * This method is used to display loading text to the user.
	 * @return
	 */
	public TreeItem getLoadingImage() {
		HTMLPanel loadingImg= new HTMLPanel(i18n.GL3120());
		loadingImg.setStyleName(FolderContainerCBundle.INSTANCE.css().loadingImageMainDiv());
		Label loadingText = new Label("");
		loadingText.setStyleName(FolderContainerCBundle.INSTANCE.css().loadingImageForSelfEdit());
		loadingImg.add(loadingText);
		return new TreeItem(loadingImg);
	}
	/**
	 * This method will give the margin value based on the window width and it will set to the main div (For Responsive UI)
	 * @return
	 */
	public int getTotalViewableWidth(){
		int totalClientWidth=Window.getClientWidth();
		int getMargin=0;
		if(totalClientWidth>767){
			getMargin=100;
		}else{
			getMargin=10;
		}
		return getMargin;
	}
	/**
	 * This inner class is used to handle the resize logic
	 */
	public class ResizeLogicEvent implements ResizeHandler{
		@Override
		public void onResize(ResizeEvent event) {
			marginDiv.getElement().getStyle().setMarginTop(0, Unit.PX);
			marginDiv.getElement().getStyle().setMarginBottom(0,  Unit.PX);
			marginDiv.getElement().getStyle().setMarginLeft(getTotalViewableWidth(),  Unit.PX);
			marginDiv.getElement().getStyle().setMarginRight(getTotalViewableWidth(),  Unit.PX);
		}
	}
}