package com.babedev.kotlinpoetcodelab.sample2

import android.app.Activity
import android.os.Bundle

class Sample2Activity : Activity, Sample2View {
  lateinit var mPresenter: Sample2Presenter

  override fun onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    // TODO setContentView()
    mPresenter = Sample2Presenter()
    mPresenter.attachView(this)
  }
}
