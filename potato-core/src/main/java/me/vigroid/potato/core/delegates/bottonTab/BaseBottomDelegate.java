package me.vigroid.potato.core.delegates.bottonTab;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import me.vigroid.potato.core.R;
import me.vigroid.potato.core.R2;
import me.vigroid.potato.core.delegates.PotatoDelegate;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by vigroid on 10/12/17.
 */

public abstract class BaseBottomDelegate extends PotatoDelegate implements View.OnClickListener{

    private final ArrayList<BottomItemDelegate> ITEM_DELEGATES = new ArrayList<>();
    private final ArrayList<BottomTabBean> TAB_BEANS = new ArrayList<>();
    private final LinkedHashMap<BottomTabBean,BottomItemDelegate> ITEMS = new LinkedHashMap<>();
    protected int mCurrentDelegate = 0;
    private int mIndexDelegate = 0;
    private int mClickedColor = Color.RED;

    @BindView(R2.id.bottom_bar)
    LinearLayoutCompat mBottomBar = null;

    public abstract LinkedHashMap<BottomTabBean,BottomItemDelegate> setItems(ItemBuilder builder);

    public abstract int setIndexDelegate();

    @ColorInt
    public abstract int setClickedColor();

    @Override
    public Object setLayout() {
        return R.layout.delegate_bottom;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set up index page and clicked color
        if (savedInstanceState!=null){
            mIndexDelegate = savedInstanceState.getInt("current_delegate");
            mCurrentDelegate = mIndexDelegate;
        }else {
            mIndexDelegate = setIndexDelegate();
        }
        if (setClickedColor() != 0){
            mClickedColor = setClickedColor();
        }

        final ItemBuilder builder = ItemBuilder.builder();
        final LinkedHashMap<BottomTabBean,BottomItemDelegate> items = setItems(builder);
        ITEMS.putAll(items);
        for (Map.Entry<BottomTabBean, BottomItemDelegate> item:ITEMS.entrySet()){
            final BottomTabBean key = item.getKey();
            final BottomItemDelegate value = item.getValue();
            TAB_BEANS.add(key);
            ITEM_DELEGATES.add(value);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        final int size = ITEMS.size();
        for (int i=0;i<size;i++) {
            LayoutInflater.from(getContext()).inflate(R.layout.bottom_item_icon_text_layout, mBottomBar);
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            //set onclicklistener for each tab
            item.setTag(i);
            item.setOnClickListener(this);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            final BottomTabBean bean = TAB_BEANS.get(i);
            //set data(Icon and text) for each tab on bottom
            itemIcon.setText(bean.getIcon());
            itemTitle.setText(bean.getTitle());
            if (i == mIndexDelegate) {
                itemIcon.setTextColor(mClickedColor);
                itemTitle.setTextColor(mClickedColor);
            }
        }
        //convert delegate arraylist to array for use of fragmentation
        final ISupportFragment[] delegateArray = ITEM_DELEGATES.toArray(new ISupportFragment[size]);
        getSupportDelegate().loadMultipleRootFragment(R.id.bottom_bar_delegate_container, mIndexDelegate, delegateArray);
    }

    //reset all bar item to gray
    private void resetColor(){
        final int count = mBottomBar.getChildCount();
        for (int i =0;i<count;i++){
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            itemIcon.setTextColor(Color.GRAY);
            final AppCompatTextView itemTitle = (AppCompatTextView)item.getChildAt(1);
            itemTitle.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("current_delegate", mCurrentDelegate);
        Log.d("yo", Integer.toString(mCurrentDelegate));

    }

    @Override
    public void onClick(View view) {
        final int tag = (int) view.getTag();
        //reset all the color
        resetColor();
        final RelativeLayout item = (RelativeLayout) view;
        final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
        //set clicked color for clicked item
        itemIcon.setTextColor(mClickedColor);
        final AppCompatTextView itemTitle = (AppCompatTextView)item.getChildAt(1);
        itemTitle.setTextColor(mClickedColor);
        //show first, hide second
        getSupportDelegate().showHideFragment(ITEM_DELEGATES.get(tag), ITEM_DELEGATES.get(mCurrentDelegate));
        mCurrentDelegate = tag;

        //Log.d("yo", Integer.toString(mCurrentDelegate));
    }
}
