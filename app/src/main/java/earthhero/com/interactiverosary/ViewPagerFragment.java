package earthhero.com.interactiverosary;


import android.util.Log;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Calendar.DAY_OF_WEEK;

/**
 * Created by Admin on 2/3/18.
 */

public class ViewPagerFragment extends Fragment {
    int NUM_ITEMS = 72;
    String[] prayerList;
    static Counter myCounter = new Counter(1);
    static int currPage=0;
    static int prevPage=0;
    static int nextPage=0;
    static public HashMap<String, Integer> PAGES = new HashMap<>();

    @Override
   public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.vpfragment_layout,null);
        ViewPager pager = (ViewPager) v.findViewById(R.id.vp);
        TextView tv_Counter = (TextView) pager.findViewById(R.id.tv_counter);
        final Integer[] HailMary = new Integer[53];
        final List<Integer> l_HM = new ArrayList<Integer>();
        //Initialize l_HM with all pages that contain the HailMary Prayer
        for (int i =0;i<=52;i++){
            if (i<=2){
                l_HM.add(i+2);
            }
            else if (i<=12){
                l_HM.add( i+5);
            }
            else if (i<=22){
                l_HM.add( i+8);
            }
            else if (i<=32){
                l_HM.add( i+11);
            }
            else if (i<=42){
                l_HM.add( i+14);
            }
            else {
                l_HM.add( i+17);
            }
        }

        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position){
                Boolean inHM = l_HM.contains(position);
                String sPos = Integer.toString(position);
                currPage = position;
                if(currPage == 0) prevPage = 0; else prevPage = currPage - 1;
                if(currPage == 52) nextPage = 0; else nextPage = currPage + 1;

                Toast.makeText(getContext(),sPos, Toast.LENGTH_LONG).show();

                TextView tv_Counter = (TextView) getActivity().findViewById(R.id.tv_counter);
                tv_Counter.setText(myCounter.getStringCount());

                if (inHM){
                    //If the hailMary page hasn't been added go ahead and add it.
                    if(!PAGES.containsKey(sPos) && !PAGES.isEmpty()){
                        PAGES.put(sPos, myCounter.getCount());
                        tv_Counter.setText(PAGES.get(sPos));
                        myCounter.inc();
                    }
                    if (!PAGES.isEmpty())
                   Log.v("Pages Value:",Integer.toString(PAGES.get(sPos)));
                }


            }
        });
        FragmentStatePagerAdapter mAdapter = new MyAdapter(getChildFragmentManager());
        pager.setAdapter(mAdapter);
        return v;
    }

    public class MyAdapter extends FragmentStatePagerAdapter {
        //===Reusable Prayers ===
        private String[] mainPrayers = getResources().getStringArray(R.array.MainPrayers);
        private final String OURFATHER = mainPrayers[1];
        private final String HAILMARY = mainPrayers[2];
        private final String GLORYBE = mainPrayers[3];
        private final String HHQ = mainPrayers[4];
        //===Mysteries===
        private String[] MonSat = getResources().getStringArray(R.array.MonSat);
        private String[] TuesFri = getResources().getStringArray(R.array.TuesFri);
        private String[] WedSun = getResources().getStringArray(R.array.WedSun);
        private String[] Thurs = getResources().getStringArray(R.array.Thursday);
        //===Get day of week===
        private int day = Calendar.getInstance().get(DAY_OF_WEEK);
        //===Constructor===
        private MyAdapter(FragmentManager fm){
            super(fm);
        }
        @Override
        public int getCount(){
            return NUM_ITEMS;
        }
        @Override
        public Fragment getItem(int position){
            Fragment pf;
            ViewPager vp = (ViewPager) getActivity().findViewById(R.id.vp);
            //Creed, Our father
            if (position <= 1 ){
                pf = prayerFragment.newInstance(mainPrayers[position], PAGES);
            }
            //hail mary x3
            else if(position <=4 ) {
                pf = prayerFragment.newInstance(HAILMARY, PAGES,true);
            }
            //glory be
            else if (position == 5 || position == 18 || position == 31 || position == 44 || position == 57 || position ==70 ){
               // getView().findViewById(R.id.tv_counter).setVisibility(View.GONE);
                pf = prayerFragment.newInstance(GLORYBE, PAGES);
            }
            //mystery
            else if(position == 6 || position == 19 || position == 32|| position == 45 || position == 58){
                switch(day) {
                    case 1:
                        pf = prayerFragment.newInstance(WedSun[((position + 6) / 12) - 1], PAGES);
                        break;
                    case 2:
                        pf = prayerFragment.newInstance(MonSat[((position + 6) / 12) - 1], PAGES);
                        break;
                    case 3:
                        pf = prayerFragment.newInstance(TuesFri[((position + 6) / 12) - 1], PAGES);
                        break;
                    case 4:
                        pf = prayerFragment.newInstance(WedSun[((position + 6) / 12) - 1], PAGES);
                        break;
                    case 5:
                        pf = prayerFragment.newInstance(Thurs[((position + 6) / 12) - 1], PAGES);
                        break;
                    case 6:
                        pf = prayerFragment.newInstance(TuesFri[((position + 6) / 12) - 1], PAGES);
                        break;
                    case 7:
                        pf = prayerFragment.newInstance(MonSat[((position + 6) / 12) - 1], PAGES);
                        break;
                    default:
                        pf = prayerFragment.newInstance(MonSat[((position + 6) / 12) - 1], PAGES);
                }
            }
            //Our father
            else if (position == 7 || position == 20 || position == 33 || position == 46 || position == 59){

                pf = prayerFragment.newInstance(OURFATHER, PAGES);
            }
            //HAIL MARY x 10
            else if (position<=17 || position <= 30 || position <= 43 || position <= 56 || position <= 69){
                pf = prayerFragment.newInstance(HAILMARY,PAGES,true);
            }
            else {
                pf = prayerFragment.newInstance(HHQ, PAGES);
            }
            currPage = vp.getCurrentItem();
            return pf;
        }
    }

    public static class prayerFragment extends Fragment {
        HashMap <String, Integer> mPages;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            View v = inflater.inflate(R.layout.pagerfragment_layout,container,false);
            TextView tv = (TextView) v.findViewById(R.id.text);
            tv.setText(getArguments().getString("text"));
            ViewPager vp = (ViewPager)getActivity().findViewById(R.id.vp);
            mPages = (HashMap<String, Integer>) getArguments().getSerializable("Pages");

            TextView tv_Counter = (TextView) v.findViewById(R.id.tv_counter);
            if (getArguments().getBoolean("visibility")){
                tv_Counter.setVisibility(View.VISIBLE);
            }

            if(mPages != null && !mPages.isEmpty())
            tv_Counter.setText(mPages.get(Integer.toString(vp.getCurrentItem()))); //<---------------This needs to grab the right count after the map has been made.
            else tv_Counter.setText(myCounter.getStringCount());
            Drawable rosary = ContextCompat.getDrawable(this.getContext(), R.drawable.rosary);
            rosary.setBounds(50, 50, 50, 50);
            return v;
        }
        @Override
        public void onViewCreated(View view, Bundle savedInstanceState){
            ViewPager vp = (ViewPager) getActivity().findViewById(R.id.vp);
        }
        @Override
        public void onActivityCreated(Bundle savedInstanceState){
            super.onActivityCreated(savedInstanceState);
            final Button cancelBtn = (Button) getView().findViewById(R.id.BTN_Cancel);
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack();
                    getActivity().findViewById(R.id.BTN_Start).setVisibility(View.VISIBLE);
                    cancelBtn.setVisibility(View.GONE);
                }
            });
        }
        static prayerFragment newInstance(String text, HashMap <String, Integer> PAGES ){
            prayerFragment f = new prayerFragment();
            Bundle b = new Bundle();
            b.putSerializable("Pages", PAGES);
            b.putString("text", text);
            f.setArguments(b);
            return f;
        }
        static prayerFragment newInstance(String text, Map <String, Integer> PAGES, boolean visible){
            prayerFragment f = new prayerFragment();
            Bundle b = new Bundle();
            b.putString("text", text);
            b.putBoolean("visibility",visible);
            f.setArguments(b);
            return f;
        }
        @Override
        public void onStop(){
            super.onStop();
            getActivity().invalidateOptionsMenu();
        }
    }
}
