package com.babedev.javapoetcodelab.sample;

import android.app.Activity;
import android.os.Bundle;

import java.lang.Override;

class SampleActivity extends Activity implements SampleView {
    private SamplePresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO setContentView()
        mPresenter = new SamplePresenter();
        mPresenter.attachView(this);
    }
}
