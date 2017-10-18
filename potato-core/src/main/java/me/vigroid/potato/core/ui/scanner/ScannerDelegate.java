package me.vigroid.potato.core.ui.scanner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;
import me.vigroid.potato.core.delegates.PotatoDelegate;
import me.vigroid.potato.core.util.callback.CallbackManager;
import me.vigroid.potato.core.util.callback.CallbackType;
import me.vigroid.potato.core.util.callback.IGlobalCallback;

/**
 * Created by vigroid on 10/17/17.
 * fragment for qr scanner
 */

public class ScannerDelegate extends PotatoDelegate implements ZBarScannerView.ResultHandler{

    private ScanView mScanView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mScanView==null){
            mScanView = new ScanView(getContext());
        }
        mScanView.setAutoFocus(true);
        mScanView.setResultHandler(this);
    }

    @Override
    public Object setLayout() {
        return mScanView;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mScanView!=null){
            mScanView.startCamera();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mScanView!=null){
            mScanView.stopCameraPreview();
            mScanView.stopCamera();
        }
    }

    @Override
    public void handleResult(Result result) {
        @SuppressWarnings("unchecked")
        final IGlobalCallback<String> callback = CallbackManager
                .getInstance()
                .getCallback(CallbackType.ON_SCAN);
        if (callback != null) {
            callback.executeCallback(result.getContents());
        }
        getSupportDelegate().pop();
    }
}
