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


import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Conditional;
import com.yookue.commonplexus.springcondition.condition.OnAnnotationCondition;


/**
 * Annotation being active when matching the given annotation
 *
 * @author David Hsing
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Conditional(value = OnAnnotationCondition.class)
@SuppressWarnings("unused")
public @interface ConditionalOnAnnotation {
    /**
     * The included annotation classes for scanning
     *
     * @return the included annotation classes
     *
     * @see org.springframework.core.type.filter.AnnotationTypeFilter
     */
    Class<? extends Annotation>[] includeFilter();

    /**
     * The excluded annotation classes for scanning
     *
     * @return the excluded annotation classes
     *
     * @see org.springframework.core.type.filter.AnnotationTypeFilter
     */
    Class<? extends Annotation>[] excludeFilter() default {};

    /**
     * The ant path for scanning
     *
     * @return the ant path
     */
    String basePackage() default "*";    // $NON-NLS-1$

    /**
     * The resource pattern for scanning
     *
     * @return the resource pattern
     */
    String resourcePattern() default "**/*.class";    // $NON-NLS-1$

    /**
     * Whether to also match on meta-annotations
     *
     * @return whether to also match on meta-annotations
     */
    boolean considerMetaAnnotations() default true;

    /**
     * Whether to also match interfaces
     *
     * @return whether to also match interfaces
     */
    boolean considerInterfaces() default true;
}
