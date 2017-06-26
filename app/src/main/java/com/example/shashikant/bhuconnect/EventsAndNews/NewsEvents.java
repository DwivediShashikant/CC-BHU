package com.example.shashikant.bhuconnect.EventsAndNews;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shashikant.bhuconnect.MainActivity;
import com.example.shashikant.bhuconnect.R;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static android.R.attr.data;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.example.shashikant.bhuconnect.Holidays.HolidaysActivity.LOG_TAG;
import static com.example.shashikant.bhuconnect.Holidays.HolidaysActivity.adapter;
import static com.example.shashikant.bhuconnect.R.id.container;


public class NewsEvents extends AppCompatActivity {
    public static String NEWS_REQUEST_URL;
    public static String EVENTS_REQUEST_URL;
    public static newsAdapter nAdapter;
    public static eventsAdapter eAdapter;
    public static Context mcontext ;
    public static Activity activity = null;
    public static int list_id;
    public static Loader mLoader;
    public static ListView listView_news;
    public static ListView listView_events;
    private static TextView mEmptyEvents;
    private static TextView mEmptyNews;
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
        mEmptyEvents = (TextView)findViewById(R.id.evets_emptyView);
        mEmptyNews = (TextView)findViewById(R.id.news_emptyView);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_events);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            NewsEvents.this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NewsList.newsArrayList.clear();
        EventsList.eventsArrayList.clear();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_events, menu);
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
    public static class PlaceholderFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<news>> {
        ProgressDialog progressDialog = new ProgressDialog(mcontext);
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
            View rootView = inflater.inflate(R.layout.news_list, container, false);
            mEmptyNews = (TextView)rootView.findViewById(R.id.news_emptyView);
            // condition for switching to student login in TabWidget for registering complain

            ConnectivityManager cm = (ConnectivityManager)mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                NEWS_REQUEST_URL = "http://14.139.41.140/android/fetch_news.php";
                listView_news = (ListView)rootView.findViewById(R.id.newsList);
                listView_news.setEmptyView(mEmptyNews);
                if (!isConnected){
                    mEmptyNews.setText(R.string.no_internet);
                    listView_news.setVisibility(View.GONE);
                    mEmptyNews.setVisibility(View.VISIBLE);
                }
                else {
                    nAdapter = new newsAdapter(activity, new ArrayList<news>(), 0);
                    listView_news.setAdapter(nAdapter);
                    loaderManager.initLoader(1, null, this);
                }
            }
            //condition for switching to employee login in Tabwidget for registering complain
            else{
                rootView = inflater.inflate(R.layout.events_list, container, false);
                mEmptyEvents = (TextView)rootView.findViewById(R.id.evets_emptyView);
                ListView listView = (ListView)rootView.findViewById(R.id.eventsList);
                if(!isConnected){
                    mEmptyEvents.setText(R.string.no_internet);
                    listView.setVisibility(View.GONE);
                    mEmptyEvents.setVisibility(View.VISIBLE);
                }
                else {
                    EVENTS_REQUEST_URL = "http://14.139.41.140/android/fetch_events.php";
                    EventsAsyncTask task = new EventsAsyncTask();
                    task.execute(EVENTS_REQUEST_URL);
                }
            }
            return rootView;
        }
        @Override
        public Loader<List<news>> onCreateLoader(int id, Bundle args) {
            NewsList.newsArrayList.clear();
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            mLoader = new NewsLoader(activity,NEWS_REQUEST_URL);
            return mLoader;
        }

        @Override
        public void onLoadFinished(Loader<List<news>> loader, final List<news> data) {
            nAdapter.clear();
            progressDialog.dismiss();
            if(data!=null && !data.isEmpty()){
                nAdapter.addAll(data);
                nAdapter.notifyDataSetChanged();
                listView_news.setOnItemClickListener( new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        news currentNews = nAdapter.getItem(position);
                        int newsId = currentNews.getNewsId();

                        startActivity( new Intent(mcontext,newsdetailActivity.class));
                    }
                });
                final FloatingActionButton button = (FloatingActionButton) activity.findViewById(R.id.fabNews);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ArrayList<news> topFive = new ArrayList<news>();
                        PopupMenu popupMenuNews = new PopupMenu(mcontext,button);
                        // inflate popup with menu
                        popupMenuNews.getMenuInflater().inflate(R.menu.pop_up_menu_news, popupMenuNews.getMenu());
                        final ProgressDialog progressDialog1 = new ProgressDialog(mcontext);
                        popupMenuNews.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                if(item.getItemId()== R.id.top_five_news){
                                   for(int i=0;i<5;i++){
                                       topFive.add(i,data.get(i));
                                   }
                                   newsAdapter topNewsAdapter = new newsAdapter(activity, topFive,0);
                                   listView_news.setAdapter(topNewsAdapter);
                               }
                                else if(item.getItemId() == R.id.sort_asc) {
                                   Collections.sort(data, new Comparator<news>() {
                                       @Override
                                       public int compare(news o1, news o2) {
                                           SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("yyyy-MM-dd");
                                           simpleDateFormat.setLenient(false);
                                           return o1.getDate().compareTo(o2.getDate());
                                       }
                                   });
                                   newsAdapter newsAdapterSorted =  new newsAdapter(activity,(ArrayList<news>)data,0);
                                   listView_news.setAdapter(newsAdapterSorted);
                               }
                               else if(item.getItemId() == R.id.sort_desc) {
                                   Collections.sort(data, new Comparator<news>() {
                                       @Override
                                       public int compare(news o1, news o2) {
                                           SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("yyyy-MM-dd");
                                           simpleDateFormat.setLenient(false);
                                           return o2.getDate().compareTo(o1.getDate());
                                       }
                                   });
                                   newsAdapter newsAdapterSortedReverse =  new newsAdapter(activity,(ArrayList<news>)data,0);
                                   listView_news.setAdapter(newsAdapterSortedReverse);
                               }
                                return false;
                            }

                        });
                        popupMenuNews.show();
                    }
                });
            }
            else{
                mEmptyNews.setText(R.string.no_news_list);
            }
        }

        @Override
        public void onLoaderReset(Loader<List<news>> loader) {
            nAdapter.clear();
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
                    return "News";
                case 1:
                    return "Events";
            }
            return null;
        }
    }
    public static class EventsAsyncTask extends AsyncTask<String,Void,ArrayList<events>>{
        @Override
        protected ArrayList<events> doInBackground(String... params) {
            EventsList.eventsArrayList.clear();
            return eventUtils.fetchEvents(params[0]);
        }
        @Override
        protected void onPreExecute(){
        }
        @Override
        protected  void onPostExecute(final ArrayList<events> eventsList){
            // progressDialog.hide();
            final ListView listView = (ListView) activity.findViewById(R.id.eventsList);
            if(eventsList!=null && !eventsList.isEmpty()) {
                eAdapter = new eventsAdapter(activity, eventsList, 0);
                listView.setAdapter(eAdapter);
                final FloatingActionButton newsButton = (FloatingActionButton)activity.findViewById(R.id.fabEvents);
                newsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu eventsPopupMenu = new PopupMenu(activity,newsButton);
                        Log.e("NewsEvents","inflating the events menu");
                        eventsPopupMenu.getMenuInflater().inflate(R.menu.pop_up_menu_news,eventsPopupMenu.getMenu());
                        eventsPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                if(item.getItemId()== R.id.top_events){
                                    Collections.sort(eventsList, new Comparator<events>() {
                                        @Override
                                        public int compare(events o1, events o2) {
                                            return o1.getStartDate().compareTo(o2.getStartDate());
                                        }
                                    });
                                    eventsAdapter adapterSorted = new eventsAdapter(activity,eventsList,0);
                                    listView.setAdapter(adapterSorted);
                                }
                                else if(item.getItemId()== R.id.past_events){
                                    Collections.sort(eventsList, new Comparator<events>() {
                                        @Override
                                        public int compare(events o1, events o2) {
                                            return o2.getStartDate().compareTo(o1.getStartDate());
                                        }
                                    });
                                    eventsAdapter adapterSoretedReverse = new eventsAdapter(activity,eventsList,0);
                                    listView.setAdapter(adapterSoretedReverse);
                                }
                                return false;
                            }
                        });
                    }
                });
            }
            else{
                mEmptyEvents = (TextView)activity.findViewById(R.id.evets_emptyView);
                listView.setVisibility(View.GONE);
                mEmptyEvents.setText(R.string.no_events_list);
                mEmptyEvents.setVisibility(View.VISIBLE);
            }
        }
    }
}
