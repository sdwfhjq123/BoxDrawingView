package com.yinhao.boxdrawingview;

import android.support.v4.app.Fragment;
import android.os.Bundle;

public class DragAndDrawActivity extends SingleFragmentActivtiy {

    @Override
    public Fragment createFragment() {
        return DragFragment.newInstance();
    }


}
