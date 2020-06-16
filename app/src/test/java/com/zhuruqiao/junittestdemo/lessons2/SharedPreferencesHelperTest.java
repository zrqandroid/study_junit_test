package com.zhuruqiao.junittestdemo.lessons2;

import android.content.SharedPreferences;

import com.zhuruqiao.junittestdemo.SharedPreferenceEntry;
import com.zhuruqiao.junittestdemo.SharedPreferencesHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Calendar;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * 给{@link SharedPreferencesHelper}用的单元测试,利用mocks来模拟{@link SharedPreferences}。
 *
 * 利用{@code @RunWith}来指定单元测试的容器为{@link MockitoJUnitRunner}
 */

@RunWith(MockitoJUnitRunner.class)
public class SharedPreferencesHelperTest {

    private static final String TEST_NAME = "Test name";

    private static final String TEST_EMAIL = "test@email.com";

    private static final Calendar TEST_DATE_OF_BIRTH = Calendar.getInstance();

    static {
        TEST_DATE_OF_BIRTH.set(1980, 1, 1);
    }

    private SharedPreferenceEntry mSharedPreferenceEntry;

    private SharedPreferencesHelper mMockSharedPreferencesHelper;

    private SharedPreferencesHelper mMockBrokenSharedPreferencesHelper;

    /**
     * 没错，你应该发现了{@code @Mock} 这个新的注解，我们通过它来模拟一个对象。该对象在测试时会自动创建。
     */
    @Mock
    SharedPreferences mMockSharedPreferences;

    @Mock
    SharedPreferences mMockBrokenSharedPreferences;

    @Mock
    SharedPreferences.Editor mMockEditor;

    @Mock
    SharedPreferences.Editor mMockBrokenEditor;

    /**
     * 第一课中我们有了解{@code @Before}用来做准备工作
     */
    @Before
    public void initMocks() {
        // 创建一个个人信息的模型类
        mSharedPreferenceEntry = new SharedPreferenceEntry(TEST_NAME, TEST_DATE_OF_BIRTH,
                TEST_EMAIL);

        // 伪造一个保持成功的SpHelper
        mMockSharedPreferencesHelper = createMockSharedPreference();

        // 伪造一个保存失败的SpHelper
        mMockBrokenSharedPreferencesHelper = createBrokenMockSharedPreference();
    }


    @Test
    public void sharedPreferencesHelper_SaveAndReadPersonalInformation() {
        // 利用伪造的Sphelper去保存数据，实际上并没有磁盘操作。
        boolean success = mMockSharedPreferencesHelper.savePersonalInfo(mSharedPreferenceEntry);

        //断言是否保存成功
        assertThat("Checking that SharedPreferenceEntry.save... returns true",
                success, is(true));

        // 从伪造的Sp中读取用户属性
        SharedPreferenceEntry savedSharedPreferenceEntry =
                mMockSharedPreferencesHelper.getPersonalInfo();

        // 断言获取的name是否与保存相同
        assertThat("Checking that SharedPreferenceEntry.name has been persisted and read correctly",
                mSharedPreferenceEntry.getName(),
                is(equalTo(savedSharedPreferenceEntry.getName())));
        assertThat("Checking that SharedPreferenceEntry.dateOfBirth has been persisted and read "
                        + "correctly",
                mSharedPreferenceEntry.getDateOfBirth(),
                is(equalTo(savedSharedPreferenceEntry.getDateOfBirth())));
        assertThat("Checking that SharedPreferenceEntry.email has been persisted and read "
                        + "correctly",
                mSharedPreferenceEntry.getEmail(),
                is(equalTo(savedSharedPreferenceEntry.getEmail())));
    }

    @Test
    public void sharedPreferencesHelper_SavePersonalInformationFailed_ReturnsFalse() {
        // Read personal information from a broken SharedPreferencesHelper
        boolean success =
                mMockBrokenSharedPreferencesHelper.savePersonalInfo(mSharedPreferenceEntry);
        assertThat("Makes sure writing to a broken SharedPreferencesHelper returns false", success,
                is(false));
    }

    /**
     * 创建一个‘欺骗’的sp对象，翻译mocked为欺骗更为合理。
     * 从当前方法代码可知，我们 mock了一个mMockSharedPreferences，同时我们还需要按照伪造相关方法，
     * 通过伪造部分相关输入和输出，来正常运行相关测试。
     */
    private SharedPreferencesHelper createMockSharedPreference() {
        //当mMockSharedPreferences.getString 方法的输入参数为(SharedPreferencesHelper.KEY_NAME ,任意字符串）时返回mSharedPreferenceEntry.getName()
        //
        when(mMockSharedPreferences.getString(eq(SharedPreferencesHelper.KEY_NAME), anyString()))
                .thenReturn(mSharedPreferenceEntry.getName());
        when(mMockSharedPreferences.getString(eq(SharedPreferencesHelper.KEY_EMAIL), anyString()))
                .thenReturn(mSharedPreferenceEntry.getEmail());
        when(mMockSharedPreferences.getLong(eq(SharedPreferencesHelper.KEY_DOB), anyLong()))
                .thenReturn(mSharedPreferenceEntry.getDateOfBirth().getTimeInMillis());

        // 伪造提交成功的方法并返回true
        when(mMockEditor.commit()).thenReturn(true);

        // 当调用mMockSharedPreferences.edit()方法时 我们返回mMockEditor给它。
        when(mMockSharedPreferences.edit()).thenReturn(mMockEditor);
        return new SharedPreferencesHelper(mMockSharedPreferences);
    }

    /**
     * Creates a mocked SharedPreferences that fails when writing.
     */
    private SharedPreferencesHelper createBrokenMockSharedPreference() {
        // Mocking a commit that fails.
        when(mMockBrokenEditor.commit()).thenReturn(false);

        // Return the broken MockEditor when requesting it.
        when(mMockBrokenSharedPreferences.edit()).thenReturn(mMockBrokenEditor);
        return new SharedPreferencesHelper(mMockBrokenSharedPreferences);
    }
}
