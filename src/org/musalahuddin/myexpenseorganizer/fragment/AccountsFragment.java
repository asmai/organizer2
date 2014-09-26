package org.musalahuddin.myexpenseorganizer.fragment;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;


import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.widget.AdapterView;

import org.musalahuddin.myexpenseorganizer.MyApplication;
import org.musalahuddin.myexpenseorganizer.R;
import org.musalahuddin.myexpenseorganizer.database.AccountTable;

public class AccountsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,AdapterView.OnItemClickListener {
	
	Activity activity = getActivity();
	
	private LoaderManager mManager;
	private SimpleCursorAdapter mAdapter;

	public AccountsFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_accounts, container, false);
         
        ListView lv = (ListView) rootView.findViewById(R.id.list);
        
        // Create an array to specify the fields we want to display in the list
	    String[] from = new String[]{AccountTable.COLUMN_ACCOUNT_CATEGORY_NAME};

	    // and an array of the fields we want to bind those fields to 
	    int[] to = new int[]{R.id.account_category};
	    //int[] to = new int[]{android.R.id.text1};
	    
	    
	    // Now create a simple cursor adapter and set it to display
	    mAdapter = new SimpleCursorAdapter(MyApplication.getInstance(),R.layout.account_row,null,from,to,0){

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				
				View row = super.getView(position, convertView, parent);
				Cursor c = getCursor();
				c.moveToPosition(position);
				
				DecimalFormat f = new DecimalFormat("0.00");
				
				//get cursor values
				String name = c.getString(c.getColumnIndex(AccountTable.COLUMN_NAME));
				int number = c.getInt(c.getColumnIndex(AccountTable.COLUMN_NUMBER));
				double balance = c.getDouble(c.getColumnIndex(AccountTable.COLUMN_INIT_BALANCE))/100;
				long due = c.getLong(c.getColumnIndex(AccountTable.COLUMN_DUE_DATE));
				double payment = c.getDouble(c.getColumnIndex(AccountTable.COLUMN_MONTHLY_PAYMENT))/100;
				double limit = c.getDouble(c.getColumnIndex(AccountTable.COLUMN_CREDIT_LIMIT))/100;
				
				
				//get views
				TextView accountName = (TextView) row.findViewById(R.id.account_name);
				TextView accountBalance = (TextView) row.findViewById(R.id.account_balance);
				TextView accountPayment = (TextView) row.findViewById(R.id.account_payment);
				TextView accountLimit = (TextView) row.findViewById(R.id.account_limit);
				TextView accountDue = (TextView) row.findViewById(R.id.account_due);
				
				//set name
				accountName.setText(name);
				if(number > 0){
					accountName.setText(name+"(...."+number+")");
				}
				
				//set balance
				accountBalance.setText("$"+f.format(Math.abs(balance)));
				if(balance < 0){
					accountBalance.setTextColor(Color.RED);
				}
				else if(balance > 0){
					accountBalance.setTextColor(Color.GREEN);
				}
				
				//set payment
				accountPayment.setText("$"+f.format(Math.abs(payment)));
				
				//set limit
				accountLimit.setText("$"+f.format(Math.abs(limit)));
				
				//set due date
				if(due > 0L){
					//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					
					Calendar currCalendar = Calendar.getInstance();
					currCalendar.setTimeInMillis(System.currentTimeMillis());
					
					Calendar dueCalendar = Calendar.getInstance();
					dueCalendar.setTimeInMillis(due);
					
					if(dueCalendar.getTimeInMillis() >= currCalendar.getTimeInMillis()){
						accountDue.setText(sdf.format(dueCalendar.getTime()));
					}
					else{
						Calendar currCal = Calendar.getInstance();
						currCal.set(currCalendar.get(currCalendar.YEAR),currCalendar.get(currCalendar.MONTH),currCalendar.get(currCalendar.DAY_OF_MONTH));
						
						Calendar dueCal = Calendar.getInstance();
						if(dueCalendar.get(dueCalendar.DAY_OF_MONTH) > currCalendar.getActualMaximum(currCalendar.DAY_OF_MONTH)){
							dueCal.set(currCalendar.get(currCalendar.YEAR),currCalendar.get(currCalendar.MONTH),currCalendar.get(currCalendar.DAY_OF_MONTH));		
						}
						else{
							dueCal.set(currCalendar.get(currCalendar.YEAR),currCalendar.get(currCalendar.MONTH),dueCalendar.get(dueCalendar.DAY_OF_MONTH));
						}
						
						if(dueCal.getTimeInMillis() < currCal.getTimeInMillis()){
							dueCal.add(Calendar.MONTH, 1);
						}
						accountDue.setText(sdf.format(dueCal.getTime()));
						
						//accountDue.setText(String.valueOf(due));
					}
				}
				
				
				return row;
			}
	    	
	    };
	    
	    mManager = getLoaderManager();
	    
	    mManager.initLoader(0, null, this);
	    
	    lv.setAdapter(mAdapter);
	    
	    lv.setOnItemClickListener(this);
	    registerForContextMenu(lv);
        
        return rootView;
    }
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		//return null;
		
		String selection;
		String[] selectionArgs, projection;
		String sortOrder;
		
		selection = null;
		selectionArgs=null;
		projection = new String[]{
				AccountTable.COLUMN_ID, 
				AccountTable.COLUMN_NAME,
				AccountTable.COLUMN_DESCRIPTION,
				AccountTable.COLUMN_ACCOUNT_CATEGORY_ID,
				AccountTable.COLUMN_ACCOUNT_CATEGORY_NAME, 
				AccountTable.COLUMN_NUMBER, 
				AccountTable.COLUMN_INIT_BALANCE,
				AccountTable.COLUMN_MONTHLY_PAYMENT,
				AccountTable.COLUMN_CREDIT_LIMIT,
				AccountTable.COLUMN_DUE_DATE};
		
		sortOrder = "LOWER("+AccountTable.COLUMN_NAME+")" + " ASC ";
		
		CursorLoader cursorLoader = new CursorLoader(MyApplication.getInstance(),
				AccountTable.CONTENT_URI, projection, selection, selectionArgs, sortOrder);
		    return cursorLoader;
		    
		    
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor c) {
		mAdapter.swapCursor(c);
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> c) {
		mAdapter.swapCursor(null);
		
	}
}
