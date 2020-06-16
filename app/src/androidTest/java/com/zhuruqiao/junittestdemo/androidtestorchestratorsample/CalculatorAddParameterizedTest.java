/*
 * Copyright 2020, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zhuruqiao.junittestdemo.androidtestorchestratorsample;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import androidx.test.filters.SmallTest;

import com.zhuruqiao.junittestdemo.androidjunitrunnersample.Calculator;

import java.lang.Iterable;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.Parameters;


/**
 *
 * 当统一测试方法需要重复测试时，可以用当前方法
 *
 *
 * {@code @RunWith(Parameterized.class)}这是个很神奇的注解，通过该注解，在单元测试时你可以指定多个参数集来为一个方法做重复的测试
 *
 */
@RunWith(Parameterized.class)
@SmallTest
public class CalculatorAddParameterizedTest {

    /**
     * @return {@link Iterable} that contains the values that should be passed to the constructor.
     * In this example we are going to use three parameters: operand one, operand two and the
     * expected result.
     * @return {@link Iterable} 包含了将要传递到构造函数的参数。在这个样例中我们将会用到三个参数，运算数1、运算数2和期望的结果
     * 值得注意的是，该方法为静态方法，你看到了么？
     *
     *
     */
    @Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0, 0, 0},
                {0, -1, -1},
                {2, 2, 4},
                {8, 8, 16},
                {16, 16, 32},
                {32, 0, 32},
                {64, 64, 128}});
    }

    private final double mOperandOne;

    private final double mOperandTwo;

    private final double mExpectedResult;

    private Calculator mCalculator;

    /**
     * Constructor that takes in the values specified in
     * {@link CalculatorAddParameterizedTest#data()}. The values need to be saved to fields in order
     * to reuse them in your tests.
     *
     *
     */
    public CalculatorAddParameterizedTest(double operandOne, double operandTwo,
            double expectedResult) {

        mOperandOne = operandOne;
        mOperandTwo = operandTwo;
        mExpectedResult = expectedResult;
    }

    @Before
    public void setUp() {
        mCalculator = new Calculator();
    }

    @Test
    public void testAdd_TwoNumbers() {
        double resultAdd = mCalculator.add(mOperandOne, mOperandTwo);
        assertThat(resultAdd, is(equalTo(mExpectedResult)));
    }
}