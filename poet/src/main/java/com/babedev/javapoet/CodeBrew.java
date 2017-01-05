package com.babedev.javapoet;

import java.io.IOException;

/**
 * @author BabeDev
 */
public class CodeBrew {

    public static void main(String[] args) throws IOException {
        MvpPoet poet = new MvpPoet("com.babedev.javapoetcodelab", "Sample2");
        poet.generateMvp();
    }
}
