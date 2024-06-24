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


import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import com.yookue.commonplexus.springcondition.annotation.ConditionalOnAllClasses;


@TestConfiguration
class TestApplicationConfig {
    static final String CONDITIONAL_ON_ALL_CLASSES_BEAN = "conditionalOnAllClassesBean";    // $NON-NLS-1$

    @Bean(name = CONDITIONAL_ON_ALL_CLASSES_BEAN)
    @ConditionalOnAllClasses(value = {
        @ConditionalOnClass(name = "org.springframework.boot.test.context.SpringBootTest"),
        @ConditionalOnClass(value = TestApplicationRunner.class)
    })
    public TestingStruct conditionalOnAllClasses() {
        return new TestingStruct();
    }

    static class TestingStruct {
    }
}
