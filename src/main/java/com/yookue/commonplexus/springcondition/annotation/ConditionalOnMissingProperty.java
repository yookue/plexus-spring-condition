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
import com.yookue.commonplexus.springcondition.condition.OnMissingPropertyCondition;


/**
 * Annotation being active when missing the property
 *
 * @author David Hsing
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Conditional(value = OnMissingPropertyCondition.class)
@SuppressWarnings("unused")
public @interface ConditionalOnMissingProperty {
    /**
     * A prefix that should be applied to each property
     * <p>
     * The prefix automatically ends with a dot if not specified
     * A valid prefix is defined by one or more words separated with dots (e.g. {@code "acme.system.feature"})
     *
     * @return the prefix
     */
    String prefix() default StringUtils.EMPTY;

    /**
     * Returns the names of properties
     * <p>
     * If a prefix has been defined, it is applied to compute the full key of each property
     * For instance if the prefix is {@code app.config} and one value is {@code my-value}, the full key would be {@code app.config.my-value}
     * <p>
     * Use the dashed notation to specify each property, that is all lower case with a "-" to separate words (e.g. {@code my-long-property})
     * <p>
     * Matched when all the names are missing
     *
     * @return the names of properties
     */
    String[] name() default {};
}
