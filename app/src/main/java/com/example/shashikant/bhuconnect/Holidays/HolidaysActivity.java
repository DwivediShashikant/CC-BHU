package com.example.shashikant.bhuconnect.Holidays;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shashikant.bhuconnect.R;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.ArrayList;
import java.util.List;
import static com.example.shashikant.bhuconnect.R.id.container;
import static com.example.shashikant.bhuconnect.R.id.visible;

public class HolidaysActivity extends AppCompatActivity {
    public static String HOLIDAY_REQUEST_URL;
    public static String rHOLIDAY_REQUEST_URL;
    public static holidayAdapter adapter;
    public static holidayAdapter rAdapter;
    public static final String LOG_TAG = HolidaysActivity.class.getSimpleName();
    public static Context mcontext ;
    public static Activity activity = null;
    public static int list_id;
    public static Loader mLoader;
    public static ListView listView_holiday;
    public static ListView listView_restrictedHoliday;

    private static TextView mEmptyHoliday;
    private static TextView mEmptyRestrictedHoliday;
    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private static boolean studentLogin = false;
    private static boolean employeeLogin = false;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mcontext = this;
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holidays);
        mEmptyHoliday = (TextView)findViewById(R.id.empty_view_gazettedholiday);
        mEmptyRestrictedHoliday = (TextView)findViewById(R.id.empty_view_restrcitedholiday);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            HolidaysActivity.this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        HolidayList.list.clear();
        rHolidayList.rList.clear();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_holidays, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<holidays>> {
        ProgressDialog progressDialog = new ProgressDialog(mcontext);
        ConnectivityManager cm = (ConnectivityManager)mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)  {
            LoaderManager loaderManager = getLoaderManager();
            View rootView = inflater.inflate(R.layout.holidays_list, container, false);
            mEmptyHoliday = (TextView)rootView.findViewById(R.id.empty_view_gazettedholiday);
            // condition for switching to student login in TabWidget for registering complain
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                HOLIDAY_REQUEST_URL = "http://14.139.41.140/android/fetch_holidays.php";
                listView_holiday = (ListView)rootView.findViewById(R.id.list);
                listView_holiday.setEmptyView(mEmptyHoliday);
                adapter = new holidayAdapter(activity, new ArrayList<holidays>(), 0);
                if(!isConnected && HolidayList.list.isEmpty()){
                    mEmptyHoliday.setText(R.string.no_internet);
                    listView_holiday.setVisibility(View.GONE);
                    mEmptyHoliday.setVisibility(View.VISIBLE);
                }
                else if(!isConnected && !HolidayList.list.isEmpty()){
                    adapter.addAll(HolidayList.list);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(mcontext,R.string.no_internet,Toast.LENGTH_LONG).show();
                }
                else {
                    listView_holiday.setAdapter(adapter);
                    loaderManager.initLoader(1, null, this);
                }
            }
            //condition for switching to employee login in Tabwidget for registering complain
            else{
                rootView = inflater.inflate(R.layout.restricted_holiday_list, container, false);
                mEmptyRestrictedHoliday = (TextView)rootView.findViewById(R.id.empty_view_restrcitedholiday);
                listView_restrictedHoliday = (ListView)rootView.findViewById(R.id.restricted_list);
                if(!isConnected){
                    mEmptyRestrictedHoliday.setText(R.string.no_internet);
                    listView_restrictedHoliday.setVisibility(View.GONE);
                    mEmptyRestrictedHoliday.setVisibility(View.VISIBLE);
                }
                else {
                    rHOLIDAY_REQUEST_URL = "http://14.139.41.140/android/fetch_restricted_holidays.php";
                    rHolidaysAsyncTask task = new rHolidaysAsyncTask();
                    task.execute(rHOLIDAY_REQUEST_URL);
                }
            }
            return rootView;
        }

        @Override
        public Loader<List<holidays>> onCreateLoader(int id, Bundle args) {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            mLoader = new HolidayLoader(activity,HOLIDAY_REQUEST_URL);
            return mLoader;
        }

        @Override
        public void onLoadFinished(Loader<List<holidays>> loader, List<holidays> holidays) {
            progressDialog.dismiss();
            adapter.clear();
                    if(holidays!=null && !holidays.isEmpty()){
                        adapter.addAll(holidays);
                        adapter.notifyDataSetChanged();
                    }
            else{
                        mEmptyHoliday.setText(R.string.no_gazetted_holidays);
                    }
            }

        @Override
        public void onLoaderReset(Loader<List<holidays>> loader) {
            adapter.clear();
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Gazetted Holidays";
                case 1:
                    return "Restricted Holidays";
            }
            return null;
        }
    }
    public static class rHolidaysAsyncTask extends AsyncTask<String,Void,ArrayList<holidays>>{
        //ProgressDialog progressDialog = new ProgressDialog(mcontext);
        @Override
        protected ArrayList<holidays> doInBackground(String... params) {
            rHolidayList.rList.clear();
            return restrictHolidayUtils.fetchHolidays(params[0]);
        }
        @Override
        protected void onPreExecute(){
            //progressDialog.setMessage("Loading...");
            //progressDialog.show();
            //progressDialog.setCancelable(false);
        }
        @Override
        protected  void onPostExecute(ArrayList<holidays> holidays){
                // progressDialog.hide();
            listView_restrictedHoliday = (ListView)activity.findViewById(R.id.restricted_list);
            if(holidays!=null && !holidays.isEmpty()){
                rAdapter = new holidayAdapter(activity,holidays,0);
                listView_restrictedHoliday.setAdapter(rAdapter);
                rAdapter.notifyDataSetChanged();
            }
            else{
                listView_restrictedHoliday.setEmptyView(mEmptyRestrictedHoliday);
                listView_restrictedHoliday.setVisibility(View.GONE);
                mEmptyRestrictedHoliday = (TextView)activity.findViewById(R.id.empty_view_restrcitedholiday);
                mEmptyRestrictedHoliday.setText(R.string.no_gazetted_holidays);
                mEmptyRestrictedHoliday.setVisibility(View.VISIBLE);
            }
        }
    }
}
