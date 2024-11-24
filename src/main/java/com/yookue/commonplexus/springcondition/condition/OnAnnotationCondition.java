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

package com.yookue.commonplexus.springcondition.condition;


import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.yookue.commonplexus.springcondition.annotation.ConditionalOnAnnotation;
import com.yookue.commonplexus.springcondition.util.ConditionBecauseUtils;
import com.yookue.commonplexus.springutil.support.ClassPathScanningSimpleComponentProvider;


/**
 * Condition being active when matching the given annotation
 *
 * @author David Hsing
 */
@Order(value = Ordered.LOWEST_PRECEDENCE - 1000)
@SuppressWarnings("unused")
public class OnAnnotationCondition extends SpringBootCondition {
    private static final Class<? extends Annotation> annotation = ConditionalOnAnnotation.class;

    @Override
    @SuppressWarnings("unchecked")
    public ConditionOutcome getMatchOutcome(@Nonnull ConditionContext context, @Nonnull AnnotatedTypeMetadata metadata) {
        ConditionMessage.Builder builder = ConditionMessage.forCondition(annotation);
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(annotation.getName()));
        if (CollectionUtils.isEmpty(attributes)) {
            return ConditionOutcome.noMatch(builder.because(ConditionBecauseUtils.emptyAttributes(annotation)));
        }
        Class<? extends Annotation>[] includeFilters = (Class<? extends Annotation>[]) attributes.getClassArray("includeFilter");    // $NON-NLS-1$
        if (ArrayUtils.isEmpty(includeFilters)) {
            return ConditionOutcome.noMatch(builder.because(ConditionBecauseUtils.missingAttribute("includeFilter")));    // $NON-NLS-1$
        }
        Class<? extends Annotation>[] excludeFilters = (Class<? extends Annotation>[]) attributes.getClassArray("excludeFilter");    // $NON-NLS-1$
        String basePackage = attributes.getString("basePackage");    // $NON-NLS-1$
        if (!StringUtils.hasText(basePackage)) {
            return ConditionOutcome.noMatch(builder.because(ConditionBecauseUtils.missingAttribute("basePackage")));    // $NON-NLS-1$
        }
        String resourcePattern = attributes.getString("resourcePattern");    // $NON-NLS-1$
        boolean considerMetaAnnotations = attributes.getBoolean("considerMetaAnnotations");    // $NON-NLS-1$
        boolean considerInterfaces = attributes.getBoolean("considerInterfaces");    // $NON-NLS-1$
        ClassPathScanningSimpleComponentProvider provider = new ClassPathScanningSimpleComponentProvider(false);
        Arrays.stream(includeFilters).filter(Objects::nonNull).forEach(clazz -> provider.addIncludeFilter(new AnnotationTypeFilter(clazz, considerMetaAnnotations, considerInterfaces)));
        if (ArrayUtils.isNotEmpty(excludeFilters)) {
            Arrays.stream(excludeFilters).filter(Objects::nonNull).forEach(clazz -> provider.addExcludeFilter(new AnnotationTypeFilter(clazz, considerMetaAnnotations, considerInterfaces)));
        }
        if (StringUtils.hasText(resourcePattern)) {
            provider.setResourcePattern(resourcePattern);
        }
        Set<BeanDefinition> components = provider.findCandidateComponents(basePackage);
        return CollectionUtils.isEmpty(components) ? ConditionOutcome.noMatch(builder.notAvailable(basePackage)) : ConditionOutcome.match(builder.available(basePackage));
    }
}
