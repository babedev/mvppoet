package com.babedev.javapoetcodelab.sample2;

import android.app.Activity;
import android.os.Bundle;
import java.lang.Override;

class Sample2Activity extends Activity implements Sample2View {
  private Sample2Presenter mPresenter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    // TODO setContentView()
    mPresenter = new Sample2Presenter();
    mPresenter.attachView(this);
  }
}
