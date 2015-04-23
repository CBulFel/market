/**
 * @auther mazhao
 * TODO
 *
 */
package com.mark.market.ui;


import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.mark.market.R;
import com.mark.market.util.SearchSuggestionsProvider;

/**
 * @author mazhao
 * @describ 
 */
public class Fragment_searchsug extends Fragment implements OnClickListener {
	private static final String TAG="market->fragment_search_suggestion";
	private Button clear_recent;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.fragment_suggestion, null);
		clear_recent=(Button)view.findViewById(R.id.search_clear);
		clear_recent.setOnClickListener(this);
		return view;
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		SearchRecentSuggestions suggestions = new SearchRecentSuggestions(getActivity(),
				SearchSuggestionsProvider.AUTHORITY,
				SearchSuggestionsProvider.MODE);
		suggestions.clearHistory();
		Log.w(TAG, "history cleared");
	}

}
