package com.babedev.kotlinpoet

import android.app.Activity
import android.os.Bundle
import com.squareup.kotlinpoet.*

import java.io.File

class MvpPoet(private val packageName: String, private val featureName: String) {

    val rootPath = "app/src/main/kotlin"

    fun generateMvp() {
        generateView()
        generateActivity()
        generatePresenter()
    }

    private fun generateActivity() {
        val onCreate = FunSpec.builder("onCreate")
                .addModifiers(KModifier.OVERRIDE, KModifier.PUBLIC)
                .addParameter("savedInstanceState", Bundle::class)
                .addStatement("super.onCreate(savedInstanceState)")
                .addComment("TODO setContentView()")
                .addStatement("mPresenter = %T()", presenterType)
                .addStatement("mPresenter.attachView(this)")
                .build()

        val mvpActivity = TypeSpec.classBuilder(featureName + "Activity")
                .superclass(TypeName.get(Activity::class.java))
                .addSuperinterface(viewType)
                .addProperty(PropertySpec
                        .varBuilder("mPresenter", presenterType, KModifier.LATEINIT)
                        .build())
                .addFun(onCreate)
                .build()

        KotlinFile.builder(packageName + "." + featureName.toLowerCase(), featureName + "Activity")
                .addType(mvpActivity)
                .build()
                .writeTo(File(rootPath))
    }

    private fun generateView() {
        val mvpView = TypeSpec
                .interfaceBuilder(featureName + "View")
                .build()

        KotlinFile.builder(packageName + "." + featureName.toLowerCase(), featureName + "View")
                .addType(mvpView)
                .build()
                .writeTo(File(rootPath))
    }

    private fun generatePresenter() {
        val attachView = FunSpec.builder("attachView")
                .addKdoc(CodeBlock.of("Attach View"))
                .addParameter("view", viewType)
                .addStatement("mView = view")
                .build()

        val mvpPresenter = TypeSpec.classBuilder(featureName + "Presenter")
                .addFun(attachView)
                .addProperty(PropertySpec
                        .varBuilder("mView", viewType, KModifier.LATEINIT)
                        .build())
                .build()

        KotlinFile.builder(packageName + "." + featureName.toLowerCase(), featureName + "Presenter")
                .addType(mvpPresenter)
                .build()
                .writeTo(File(rootPath))
    }

    private val viewType: TypeName
        get() = ClassName.bestGuess(packageName + "." + featureName.toLowerCase() + "." + featureName + "View")

    private val presenterType: TypeName
        get() = ClassName.bestGuess(packageName + "." + featureName.toLowerCase() + "." + featureName + "Presenter")
}
