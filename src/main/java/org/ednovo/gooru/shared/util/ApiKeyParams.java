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

package org.ednovo.gooru.shared.util;

public interface ApiKeyParams {
	
	String API_KEY = "apiKey";
	
	/**
	 * Search related parameters.
	 */
	String TYPE = "type";
	String Q = "q";
	String START = "start";
	String LENGTH = "length";
	String ACCESS_TYPE = "accessType";
	String QUERY_TYPE = "queryType";
	String ALLOW_DUPLICATES = "allowDuplicates";
	String FETCH_HITS_IN_MULTI ="fetchHitsInMulti";
	String ALLOW_SCRIPTING = "allowScripting";
	String PROTOCOL_SUPPORTED = "protocolSupported";
	String CATEGORY = "category";
	String FILTER_RES_GOORU_OID = "flt.resourceGooruOIds";
	String BOOSTFIELD_HASNO_THUMBNAIL = "boostField.hasNoThumbnail";
	String SHOW_CANONICAL_ONLY = "showCanonicalOnly";
	String QUERY = "query";
	String CONTEXT = "context";
	String SEARCH_TERM = "searchTerm";
	String EVENT = "event";
	String CONTENT_GOORU_OID = "contentGooruOid";
	String OFFSET = "offset";
	String LIMIT = "limit";

}