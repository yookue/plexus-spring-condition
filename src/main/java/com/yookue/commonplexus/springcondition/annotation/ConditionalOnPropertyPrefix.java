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
import com.yookue.commonplexus.springcondition.condition.OnPropertyPrefixCondition;


/**
 * Annotation being active when matching all the property prefixes
 *
 * @author David Hsing
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Conditional(value = OnPropertyPrefixCondition.class)
@SuppressWarnings("unused")
public @interface ConditionalOnPropertyPrefix {
    /**
     * Returns the prefixes of properties
     * <p>
     * Use the dashed notation to specify each property, that is all lower case with a "-" to separate words (e.g. {@code my-long-property})
     * <p>
     * Matched when all the package names are matching
     *
     * @return the prefixes of properties
     */
    String[] prefix();
}
