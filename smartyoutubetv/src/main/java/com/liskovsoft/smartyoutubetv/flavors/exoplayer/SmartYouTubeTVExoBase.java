package com.liskovsoft.smartyoutubetv.flavors.exoplayer;

import android.app.Activity;
import android.content.Intent;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.SmartYouTubeTVActivityBase;

// marker class
public abstract class SmartYouTubeTVExoBase extends SmartYouTubeTVActivityBase {
    private OnActivityResultListener mOnActivityResultListener;
    private MyResult mResult;

    public interface OnActivityResultListener {
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mOnActivityResultListener != null) {
            mOnActivityResultListener.onActivityResult(requestCode, resultCode, data);
        }

        // seems that this activity has been restored
        if (mOnActivityResultListener == null && resultCode == Activity.RESULT_OK) {
            mResult = new MyResult(requestCode, resultCode, data);
        } else {
            mResult = null;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean setOnActivityResultListener(OnActivityResultListener onActivityResultListener) {
        mOnActivityResultListener = onActivityResultListener;

        if (mResult != null) {
            onActivityResult(mResult.requestCode, mResult.resultCode, mResult.data);
            return false;
        }

        return true;
    }

    private class MyResult {
        public final int requestCode;
        public final int resultCode;
        public final Intent data;

        public MyResult(int requestCode, int resultCode, Intent data) {
            this.requestCode = requestCode;
            this.resultCode = resultCode;
            this.data = data;
        }
    }
}
