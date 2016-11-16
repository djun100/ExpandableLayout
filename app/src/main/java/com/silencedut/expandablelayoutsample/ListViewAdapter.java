package com.silencedut.expandablelayoutsample;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.cy.DataStructure.UtilDummyData;
import com.cy.adapter.AdapterCommonSingleStyle;
import com.cy.adapter.DemoBean;
import com.cy.adapter.ViewHolder;
import com.silencedut.expandablelayout.ExpandableLayout;

import java.util.HashSet;

/**
 * Created by SilenceDut on 16/6/7.
 */
public class ListViewAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private HashSet<Integer> mExpandedPositionSet = new HashSet<>();
     ListViewAdapter(Context context) {
        mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 16;
    }


    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ExpandableLayout expandableLayout;
        ListView mlv;
//        if(convertView==null) {
            convertView=mLayoutInflater.inflate(position%2==0?R.layout.item_jinx:R.layout.item_ezreal,parent,false);

            mlv= (ListView) convertView.findViewById(R.id.mlv);
            mlv.setAdapter(new AdapterCommonSingleStyle<DemoBean>(UtilDummyData.makeDummyData(DemoBean.class, position+1,3,8), R.layout.lv_item) {
                @Override
                protected void convert(ViewHolder vh, DemoBean item) {
                    vh.setText(R.id.mtv,item.getType());
                }
            });
            expandableLayout = (ExpandableLayout) convertView.findViewById(R.id.expandable_layout);
            expandableLayout.setExpandWithParentScroll(false);
            convertView.setTag(expandableLayout);
//        }else {
//            expandableLayout =(ExpandableLayout) convertView.getTag();
//        }

        if(expandableLayout !=null) {
            expandableLayout.setOnExpandListener(new ExpandableLayout.OnExpandListener() {
                @Override
                public void onExpand(boolean expanded) {
                    registerExpand(position);
                }
            });
        }
//        expandableLayout.setExpand(mExpandedPositionSet.contains(position));
//        expandableLayout.expand();
        expandableLayout.setExpand(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                expandableLayout.setExpand(mExpandedPositionSet.contains(position));
            };
        },10);
        return convertView;
    }

    private void registerExpand(int position) {
        if (mExpandedPositionSet.contains(position)) {
            removeExpand(position);
        }else {
            addExpand(position);
        }
    }

    private void removeExpand(int position) {
        mExpandedPositionSet.remove(position);
    }

    private void addExpand(int position) {
        mExpandedPositionSet.add(position);
    }
}
