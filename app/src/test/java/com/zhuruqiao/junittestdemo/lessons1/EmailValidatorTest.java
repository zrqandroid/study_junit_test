package com.zhuruqiao.junittestdemo.lessons1;
import com.zhuruqiao.junittestdemo.EmailValidator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * 邮箱格式验证逻辑校验器
 */
public class EmailValidatorTest {

    /**
     * 利用｛@code @Test｝来标注一个方法，让其变成单元测试的方法。
     * 通过assertTrue来判断当前值是否与预期相符合。
     */
    @Test
    public void emailValidatorCorrectEmailSampleReturnsTrue(){
        assertTrue(EmailValidator.isValidEmail("name@email.com"));
    }

    /**
     * 断言结果为false
     */
    @Test
    public void emailValidatorCorrectEmailSampleReturnsFalse(){
        assertFalse(EmailValidator.isValidEmail("name@email..com"));
    }

    /**
     * 加入message来描述当前结果，可以让测试结果更清晰，当然你也可以不加。
     */
    @Test
    public void emailValidatorCorrectEmailSampleReturnsFalseWithMsg(){
        assertTrue("当前邮箱不符合预期：",EmailValidator.isValidEmail("name@email..com"));
    }


    /**
     *当我们需要在测试某方法之前做一些准备工作时，我们可以用{@code @Before}来标记一个方法，该方法在每次单元测之前都会执行。
     */
    @Before
    public void prepare(){
        System.out.println("做一些准备工作");

    }
}


