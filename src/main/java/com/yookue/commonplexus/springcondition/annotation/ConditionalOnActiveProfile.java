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
import org.springframework.context.annotation.Conditional;
import org.springframework.core.annotation.AliasFor;
import com.yookue.commonplexus.springcondition.condition.OnActiveProfileCondition;


/**
 * Annotation being active when matching the profile name
 *
 * @author David Hsing
 * @see org.springframework.context.annotation.Profile
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Conditional(value = OnActiveProfileCondition.class)
@SuppressWarnings("unused")
public @interface ConditionalOnActiveProfile {
    /**
     * The string representation of profile name
     *
     * @return the expected profile name
     */
    @AliasFor(value = "profile")
    String value();

    /**
     * The string representation of profile name
     *
     * @return the expected profile name
     */
    @AliasFor(value = "value")
    String profile();

    /**
     * Specify if the profile name is case-sensitive
     *
     * @return whether the profile name is case-sensitive or not
     */
    boolean caseSensitive() default false;
}
