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
import org.springframework.boot.autoconfigure.condition.OnMultipleCandidatesCondition;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.context.annotation.Conditional;


/**
 * Annotation being active when matching multiple candidates
 *
 * @author David Hsing
 * @see org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Conditional(value = OnMultipleCandidatesCondition.class)
@SuppressWarnings("unused")
public @interface ConditionalOnMultipleCandidates {
    /**
     * The class type of bean that should be checked. The condition matches if a bean of the class specified
     * is contained in the {@link org.springframework.beans.factory.BeanFactory} and a primary candidate exists in case of multiple instances
     * <p>
     * This attribute may <strong>not</strong> be used in conjunction with {@link #type()}, but it may be used instead of {@link #type()}
     * </p>
     *
     * @return the class type of the bean to check
     */
    Class<?> value() default Object.class;

    /**
     * The class type name of bean that should be checked. The condition matches if a bean of the class specified
     * is contained in the {@link org.springframework.beans.factory.BeanFactory} and a primary candidate exists in case of multiple instances
     * <p>
     * This attribute may <strong>not</strong> be used in conjunction with {@link #value()}, but it may be used instead of {@link #value()}
     * </p>
     *
     * @return the class type name of the bean to check
     */
    String type() default StringUtils.EMPTY;

    /**
     * Strategy to decide if the application context hierarchy (parent contexts) should be considered.
     *
     * @return the search strategy
     */
    SearchStrategy search() default SearchStrategy.ALL;
}
