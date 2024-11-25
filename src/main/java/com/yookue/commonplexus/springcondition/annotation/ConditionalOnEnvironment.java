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

package com.yookue.commonplexus.springcondition.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Conditional;
import com.yookue.commonplexus.springcondition.condition.OnEnvironmentCondition;


/**
 * Annotation being active when matching the system environment
 *
 * @author David Hsing
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Conditional(value = OnEnvironmentCondition.class)
@SuppressWarnings("unused")
public @interface ConditionalOnEnvironment {
    /**
     * Returns the name of the system environment
     *
     * @return the name of the system environment
     */
    String environment();

    /**
     * The string representation of the expected value for the environment
     *
     * @return the expected value
     */
    String havingValue() default StringUtils.EMPTY;

    /**
     * Specify should match if the value is case-sensitive
     *
     * @return should match if the value is case-sensitive
     */
    boolean caseSensitive() default false;

    /**
     * Specify if the environment value is a regular expression
     *
     * @return whether the environment value is a regular expression or not
     */
    boolean regExp() default false;
}
