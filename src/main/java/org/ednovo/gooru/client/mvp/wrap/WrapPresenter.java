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
package org.ednovo.gooru.client.mvp.wrap;

import org.ednovo.gooru.client.event.ActivateSearchBarEvent;
import org.ednovo.gooru.client.event.ActivateSearchBarHandler;
import org.ednovo.gooru.client.event.InvokeGooruGuideBubbleEvent;
import org.ednovo.gooru.client.event.InvokeGooruGuideBubbleHandler;
import org.ednovo.gooru.client.event.InvokeLoginEvent;
import org.ednovo.gooru.client.event.InvokeLoginHandler;
import org.ednovo.gooru.client.event.InvokeRegisterHandler;
import org.ednovo.gooru.client.gin.BasePresenter;
import org.ednovo.gooru.client.mvp.home.event.HeaderTabType;
import org.ednovo.gooru.client.mvp.home.event.HomeEvent;
import org.ednovo.gooru.client.mvp.home.event.HomeHandler;
import org.ednovo.gooru.client.mvp.home.event.PreFilterEvent;
import org.ednovo.gooru.client.mvp.home.event.PreFilterEventHandler;
import org.ednovo.gooru.client.mvp.home.event.SetDiscoverLinkEvent;
import org.ednovo.gooru.client.mvp.home.event.SetDiscoverLinkHandler;
import org.ednovo.gooru.client.mvp.home.presearchstandards.AddStandardsPreSearchPresenter;
import org.ednovo.gooru.client.mvp.prime.PrimePresenter;
import org.ednovo.gooru.client.mvp.profilepage.event.UpdateProfileHeaderImageEvent;
import org.ednovo.gooru.client.mvp.profilepage.event.UserHeaderImageEventHandler;
import org.ednovo.gooru.client.mvp.wrap.WrapPresenter.IsWrapProxy;
import org.ednovo.gooru.shared.model.user.UserDo;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

/**
 * @author Search Team
 * 
 */
public class WrapPresenter extends BasePresenter<IsWrapView, IsWrapProxy> implements InvokeLoginHandler, InvokeRegisterHandler, ActivateSearchBarHandler, InvokeGooruGuideBubbleHandler,HomeHandler,SetDiscoverLinkHandler,PreFilterEventHandler,UserHeaderImageEventHandler{
	
	AddStandardsPreSearchPresenter addStandardsPresenter = null;
	
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_VIEW = new Type<RevealContentHandler<?>>();

	@ProxyStandard
	public interface IsWrapProxy extends Proxy<WrapPresenter> {
	}

	@Inject
	public WrapPresenter(IsWrapView view, IsWrapProxy proxy,AddStandardsPreSearchPresenter addStandardsPresenter) {
		super(view, proxy);
		this.addStandardsPresenter = addStandardsPresenter;
		addRegisteredHandler(InvokeLoginEvent.TYPE, this);
		addRegisteredHandler(ActivateSearchBarEvent.TYPE, this);
		addRegisteredHandler(InvokeGooruGuideBubbleEvent.TYPE, this);
		addRegisteredHandler(HomeEvent.TYPE, this);
		addRegisteredHandler(SetDiscoverLinkEvent.TYPE, this);
		addRegisteredHandler(PreFilterEvent.TYPE, this);
		addRegisteredHandler(UpdateProfileHeaderImageEvent.TYPE, this);
		
		showPrefilterPopup();
	}

	@Override
	protected final void revealInParent() {
		RevealContentEvent.fire(this, PrimePresenter.TYPE_VIEW, this);
	}

	@Override
	public void invokeLogin() {
		getView().invokeLogin();
	}
	
	public void setLoginData(UserDo user) {
		getView().setLoginData(user);
	}

	@Override
	public void invokeRegister() {
		getView().invokeRegister();
	}

	@Override
	public void activateSearchBar(boolean activate) {
		getView().activateSearchBar(activate);
	}

	@Override
	public void invokeGooruGuideBubble() {
//		getView().invokeGooruGuideBubble();
		
	}

	@Override
	public void activateClassicButton(boolean activate) {
		getView().activateClassicButton(activate);
		
	}

	@Override
	public void setSelected(HeaderTabType tabType) {
			
			getView().setDotsPanelSelection(tabType);
			
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.home.event.SetDiscoverLinkHandler#setDiscoverLink(java.lang.String)
	 */
	@Override
	public void setDiscoverLink(String discoverLink) {
		getView().setDiscoverLinkFromLibrary(discoverLink);
	}
	
	
	private void showPrefilterPopup() {
		getView().showPrefilter(addStandardsPresenter);
	}

	@Override
	public void openPreFilterPopup() {
		getView().openPreFilter();
	}

	@Override
	public void setUserHeaderProfileImage(String imageUrl) {
		getView().updateUserHeaderProfileImage(imageUrl);
	}
}
