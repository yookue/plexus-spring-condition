/*
 * Copyright (c) 2021 Yookue Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yookue.commonplexus.springcondition;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.yookue.commonplexus.javaseutil.constant.AssertMessageConst;
import com.yookue.commonplexus.springutil.util.BeanFactoryWraps;


@SpringBootTest(classes = {TestApplicationRunner.class, TestApplicationConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AnnotationConditionTest {
    @Autowired
    private BeanFactory beanFactory;

    @Test
    void conditionalOnAllClasses() {
        Assertions.assertNotNull(BeanFactoryWraps.getBean(beanFactory, TestApplicationConfig.CONDITIONAL_ON_ALL_CLASSES_BEAN), AssertMessageConst.NOT_NULL);
    }
}
