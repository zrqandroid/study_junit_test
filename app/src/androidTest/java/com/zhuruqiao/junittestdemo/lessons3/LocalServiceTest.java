package com.zhuruqiao.junittestdemo.lessons3;


import android.content.Intent;
import android.os.IBinder;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;
import androidx.test.rule.ServiceTestRule;

import com.zhuruqiao.service.LocalService;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeoutException;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;

/**
 * 偷偷的告诉你{@code AndroidJUnit4.class}只能在androidTest文件夹使用。
 *
 * 出现新的注解{@code @MediumTest},官方说明：用于将中等测试大小限定符分配给测试的注释。 此注释可以在方法或类级别使用。
 *
 * 测试大小限定符是构建测试代码的一种很好的方式，用于将测试分配给类似运行时间的测试套件。
 *
 * 执行时间：<1000ms
 *
 *
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class LocalServiceTest {

    @Rule
    public final ServiceTestRule mServiceRule = new ServiceTestRule();

    /**
     * 当前方法做了什么，想必大家都很好奇。我也很好奇
     *
     *
     *
     * @throws TimeoutException
     */
    @Test
    public void testWithBoundService() throws TimeoutException{

        //构建intent用来启动服务
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), LocalService.class);
        //设置相关参数
        intent.putExtra(LocalService.SEED_KEY,42L);
        //绑定服务
        IBinder binder= mServiceRule.bindService(intent);
        //获取服务对象
        LocalService service =((LocalService.LocalBinder) binder).getService();

        //调用服务相关方法，获取结果
        Assert.assertThat(service.getRandomInt(),is(any(Integer.class)));


    }

}
