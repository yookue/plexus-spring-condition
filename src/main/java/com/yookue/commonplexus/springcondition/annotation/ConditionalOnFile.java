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
import com.yookue.commonplexus.springcondition.condition.OnFileCondition;


/**
 * Annotation being active when matching the file or directory
 *
 * @author David Hsing
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Conditional(value = OnFileCondition.class)
@SuppressWarnings("unused")
public @interface ConditionalOnFile {
    /**
     * The string representation of pathname
     *
     * @return the expected pathname
     */
    String pathname();

    /**
     * Specify if the condition should match if the pathname is a folder (true) or a file (false)
     * This parameter is available only when {@code exists} is true
     *
     * @return should match if the pathname is a folder or a file
     */
    boolean directory() default false;
}
