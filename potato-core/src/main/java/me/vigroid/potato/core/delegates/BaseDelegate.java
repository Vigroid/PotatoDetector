package me.vigroid.potato.core.delegates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.vigroid.potato.core.activities.ProxyActivity;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by vigroid on 10/12/17.
 * Base Fragment for our OAMF system
 */

public abstract class BaseDelegate extends SwipeBackFragment {

    private Unbinder mUnBinder = null;

    public abstract Object setLayout();

    public abstract void onBindView(@Nullable Bundle saveInstanceState, View rootView);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView;
        //use setLayout() to load layout for our fragment, it could be View or int(R)
        if (setLayout() instanceof Integer) {
            rootView = inflater.inflate((Integer) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            rootView = (View) setLayout();
        } else {
            throw new ClassCastException("setLayout() tpe must be int or View!");
        }
        //bind this fragment and the view
        mUnBinder = ButterKnife.bind(this, rootView);
        onBindView(savedInstanceState, rootView);

        return rootView;
    }

    //get the parent activity of this fragment
    public final ProxyActivity getProxyActivity(){
        return (ProxyActivity) _mActivity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //unbind this fragment and the view
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
    }
}
