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

package com.yookue.commonplexus.springcondition.util;


import java.lang.annotation.Annotation;
import javax.annotation.Nonnull;
import org.springframework.util.ClassUtils;


/**
 * Utilities for condition reasons
 *
 * @author David Hsing
 */
@SuppressWarnings("unused")
public abstract class ConditionBecauseUtils {
    private static final String EMPTY_ATTRIBUTES = "annotation attributes of metadata for '%s' is empty";    // $NON-NLS-1$
    private static final String MISSING_ATTRIBUTE = "annotation attribute '%s' is missing or empty";    // $NON-NLS-1$
    private static final String ILLEGAL_ATTRIBUTE = "annotation attribute '%s' is illegal";    // $NON-NLS-1$

    @Nonnull
    public static String emptyAttributes(@Nonnull Class<? extends Annotation> annotation) {
        return emptyAttributes(ClassUtils.getQualifiedName(annotation));
    }

    @Nonnull
    public static String emptyAttributes(@Nonnull String className) {
        return String.format(EMPTY_ATTRIBUTES, className);
    }

    @Nonnull
    public static String missingAttribute(@Nonnull String attribute) {
        return String.format(MISSING_ATTRIBUTE, attribute);
    }

    @Nonnull
    public static String illegalAttribute(@Nonnull String attribute) {
        return String.format(ILLEGAL_ATTRIBUTE, attribute);
    }
}
