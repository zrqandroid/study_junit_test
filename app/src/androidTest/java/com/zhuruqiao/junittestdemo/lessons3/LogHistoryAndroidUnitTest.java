/*
 * Copyright 2015 Google Inc. All rights reserved.
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

package com.zhuruqiao.junittestdemo.lessons3;

import android.os.Parcel;
import android.util.Pair;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.zhuruqiao.parcelable.LogHistory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/**
 * 用来验证parcelable实现是正确的
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class LogHistoryAndroidUnitTest {

    public static final String TEST_STRING = "This is a string";

    public static final long TEST_LONG = 12345678L;
    private LogHistory mLogHistory;


    @Before
    public void createLogHistory() {
        //先创建一个History对象
        mLogHistory = new LogHistory();
    }

    @Test
    public void logHistory_ParcelableWriteRead() {
        // 像LogHistory中的data中添加一个pair
        mLogHistory.addEntry(TEST_STRING, TEST_LONG);

        // 构建parcel  吧history中的数据写入到parcel中
        Parcel parcel = Parcel.obtain();
        mLogHistory.writeToParcel(parcel, mLogHistory.describeContents());

        // 将游标至0
        parcel.setDataPosition(0);

        // 从parcel中读出原有数据
        LogHistory createdFromParcel = LogHistory.CREATOR.createFromParcel(parcel);
        List<Pair<String, Long>> createdFromParcelData = createdFromParcel.getData();

        // 校验数据是否正确
        assertThat(createdFromParcelData.size()).isEqualTo(1);
        assertThat(createdFromParcelData.get(0).first).isEqualTo(TEST_STRING);
        assertThat(createdFromParcelData.get(0).second).isEqualTo(TEST_LONG);
    }
}
