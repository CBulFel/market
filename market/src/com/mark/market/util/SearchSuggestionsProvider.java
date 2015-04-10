/**
 * @auther mazhao
 * TODO
 *
 */
package com.mark.market.util;

import android.content.SearchRecentSuggestionsProvider;

/**
 * @author mazhao
 * @describ 搜索提示provider类
 */
public class SearchSuggestionsProvider extends SearchRecentSuggestionsProvider {

	public static final String AUTHORITY=SearchSuggestionsProvider.class.getName();
	public static final int MODE=DATABASE_MODE_QUERIES;
	
	public SearchSuggestionsProvider(){
		setupSuggestions(AUTHORITY, MODE);
		
	}
}
