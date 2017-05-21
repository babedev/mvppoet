package com.babedev.kotlinpoetcodelab.sample2

class Sample2Presenter {
  lateinit var mView: Sample2View

  /**
   * Attach View */
  fun attachView(view: Sample2View) {
    mView = view
  }
}
