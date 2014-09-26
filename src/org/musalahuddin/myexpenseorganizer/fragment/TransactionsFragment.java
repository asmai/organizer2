package org.musalahuddin.myexpenseorganizer.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.musalahuddin.myexpenseorganizer.R;

public class TransactionsFragment extends Fragment {
	
	public TransactionsFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_transactions, container, false);
         
        return rootView;
    }
}
