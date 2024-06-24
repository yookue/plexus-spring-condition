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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.PropertyAccessor;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.CollectionUtils;


/**
 * Utilities for condition properties
 *
 * @author David Hsing
 * @reference "https://stackoverflow.com/questions/46118782/spel-conditionalonproperty-string-property-empty-or-nulll"
 */
@SuppressWarnings({"unused", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public abstract class ConditionPropertyUtils {
    @Nullable
    @SuppressWarnings({"ConstantConditions", "RedundantSuppression"})
    public static List<String> getCompositePrefixNames(@Nullable AnnotatedTypeMetadata metadata, @Nullable Class<? extends Annotation> condition) {
        return ObjectUtils.anyNull(metadata, condition) ? null : getCompositePrefixNames(metadata, condition.getName());
    }

    /**
     * Return the extracted string list of condition annotation with 'prefix' and 'name' attributes, join with '.'
     *
     * @param metadata the metadata of the {@link org.springframework.core.type.AnnotationMetadata class} or {@link org.springframework.core.type.MethodMetadata method} being checked
     * @param condition annotation which has 'prefix' and 'name' attributes
     * @return the extracted string list of condition annotation with 'prefix' and 'name' attributes, join with '.'
     */
    @Nullable
    public static List<String> getCompositePrefixNames(@Nullable AnnotatedTypeMetadata metadata, @Nullable String condition) {
        if (metadata == null || StringUtils.isBlank(condition)) {
            return null;
        }
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(condition));
        if (CollectionUtils.isEmpty(attributes)) {
            return null;
        }
        // Annotation attributes
        String prefix = attributes.getString("prefix");    // $NON-NLS-1$
        String[] name = attributes.getStringArray("name");    // $NON-NLS-1$
        // Determine outcome
        if (StringUtils.isBlank(prefix) && (ArrayUtils.isEmpty(name) || Arrays.stream(name).noneMatch(StringUtils::isNotBlank))) {
            return null;
        }
        List<String> result = new ArrayList<>();
        if (ArrayUtils.isEmpty(name)) {
            result.add(prefix);
        } else {
            Arrays.stream(name).filter(StringUtils::isNotBlank).forEach(element -> {
                element = StringUtils.trimToNull(element);
                if (StringUtils.isBlank(prefix)) {
                    result.add(element);
                } else {
                    result.add(StringUtils.join(StringUtils.appendIfMissing(prefix, PropertyAccessor.NESTED_PROPERTY_SEPARATOR), element));
                }
            });
        }
        return CollectionUtils.isEmpty(result) ? null : result;
    }
}
