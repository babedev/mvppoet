package com.babedev.javapoet;

import android.app.Activity;
import android.os.Bundle;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.IOException;

import javax.lang.model.element.Modifier;

class MvpPoet {

    private String packageName;
    private String featureName;

    MvpPoet(String packageName, String featureName) {
        this.packageName = packageName;
        this.featureName = featureName;
    }

    void generateMvp() throws IOException {
        generateView();
        generateActivity();
        generatePresenter();
    }

    private void generateActivity() throws IOException {
        MethodSpec onCreate = MethodSpec.methodBuilder("onCreate")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(Bundle.class, "savedInstanceState")
                .addStatement("super.onCreate(savedInstanceState)")
                .addComment("TODO setContentView()")
                .addStatement("mPresenter = new $T()", getPresenterType())
                .addStatement("mPresenter.attachView(this)")
                .build();

        TypeSpec mvpActivity = TypeSpec.classBuilder(featureName + "Activity")
                .superclass(Activity.class)
                .addSuperinterface(getViewType())
                .addField(getPresenterType(), "mPresenter", Modifier.PRIVATE)
                .addMethod(onCreate)
                .build();

        JavaFile javaFile = JavaFile.builder(packageName + "." + featureName.toLowerCase(), mvpActivity)
                .build();

        File file = new File("app/src/main/java");
        javaFile.writeTo(file);
    }

    private void generateView() throws IOException {
        TypeSpec mvpView = TypeSpec.interfaceBuilder(featureName + "View")
                .build();

        JavaFile javaFile = JavaFile.builder(packageName + "." + featureName.toLowerCase(), mvpView)
                .build();

        File file = new File("app/src/main/java");
        javaFile.writeTo(file);
    }

    private void generatePresenter() throws IOException {
        MethodSpec attachView = MethodSpec.methodBuilder("attachView")
                .addJavadoc(CodeBlock.of("Attach View"))
                .addParameter(getViewType(), "view")
                .addStatement("mView = view")
                .build();

        TypeSpec mvpPresenter = TypeSpec.classBuilder(featureName + "Presenter")
                .addMethod(attachView)
                .addField(getViewType(), "mView", Modifier.PRIVATE)
                .build();

        JavaFile javaFile = JavaFile.builder(packageName + "." + featureName.toLowerCase(), mvpPresenter)
                .build();

        File file = new File("app/src/main/java");
        javaFile.writeTo(file);
    }

    private TypeName getViewType() {
        return ClassName.bestGuess(packageName + "." + featureName.toLowerCase() + "." + featureName + "View");
    }

    private TypeName getPresenterType() {
        return ClassName.bestGuess(packageName + "." + featureName.toLowerCase() + "." + featureName + "Presenter");
    }
}
