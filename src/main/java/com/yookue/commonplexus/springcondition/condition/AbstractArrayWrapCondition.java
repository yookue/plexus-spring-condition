/*
 * Copyright (c) 2021 Yookue Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yookue.commonplexus.springcondition.condition;


import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.AnnotationMetadataUtils;
import org.springframework.core.type.classreading.MethodMetadataUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import com.yookue.commonplexus.javaseutil.constant.AssertMessageConst;
import com.yookue.commonplexus.javaseutil.util.TypeUtilsWraps;
import com.yookue.commonplexus.springcondition.util.ConditionBecauseUtils;
import com.yookue.commonplexus.springutil.util.BeanUtilsWraps;


/**
 * Abstract couple wrapper of Spring internal conditions
 *
 * @author David Hsing
 * @see org.springframework.boot.autoconfigure.condition.AnyNestedCondition
 */
@SuppressWarnings("unused")
public abstract class AbstractArrayWrapCondition<W extends Annotation, P extends Annotation> extends SpringBootCondition {
    private static final String WRAPPER_ATTRIBUTE = "value";    // $NON-NLS-1$
    private final Class<W> wrapperType;
    private final Class<P> primitiveType;

    @SuppressWarnings("unchecked")
    public AbstractArrayWrapCondition() {
        wrapperType = (Class<W>) TypeUtilsWraps.getGenericParameterClass(getClass(), 0);
        primitiveType = (Class<P>) TypeUtilsWraps.getGenericParameterClass(getClass(), 1);
        Assert.notNull(wrapperType, AssertMessageConst.NOT_NULL);
        Assert.notNull(primitiveType, AssertMessageConst.NOT_NULL);
    }

    @Override
    public ConditionOutcome getMatchOutcome(@Nonnull ConditionContext context, @Nonnull AnnotatedTypeMetadata metadata) {
        ConditionMessage.Builder builder = ConditionMessage.forCondition(wrapperType);
        AnnotationAttributes anyAttributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(wrapperType.getName()));
        if (ObjectUtils.isEmpty(anyAttributes)) {
            return ConditionOutcome.noMatch(builder.because(ConditionBecauseUtils.emptyAttributes(wrapperType.getName())));
        }
        String subEntry = StringUtils.defaultIfBlank(wrapperAttribute(), WRAPPER_ATTRIBUTE);
        AnnotationAttributes[] subAttributes = anyAttributes.getAnnotationArray(subEntry);
        if (ObjectUtils.isEmpty(subAttributes)) {
            return ConditionOutcome.noMatch(builder.because(ConditionBecauseUtils.missingAttribute(subEntry)));
        }
        List<SpringBootCondition> conditions = primitiveConditions();
        Assert.notNull(conditions, "Primitive condition which be overrode can not be null");
        List<ConditionMessage> matched = new ArrayList<>(), unmatched = new ArrayList<>();
        for (AnnotationAttributes subAttribute : subAttributes) {
            if (!(metadata instanceof AnnotationMetadata) && !(metadata instanceof MethodMetadata)) {
                continue;
            }
            MergedAnnotation<?> subAnnotation = MergedAnnotation.of(primitiveType, subAttribute);
            AnnotatedTypeMetadata subMetadata;
            if (metadata instanceof AnnotationMetadata) {
                subMetadata = AnnotationMetadataUtils.renewMetadataAnnotationQuietly((AnnotationMetadata) metadata, subAnnotation);
            } else {
                subMetadata = MethodMetadataUtils.renewMetadataAnnotationQuietly((MethodMetadata) metadata, subAnnotation);
            }
            Assert.notNull(subMetadata, "AnnotatedTypeMetadata cloned can not be null");
            for (SpringBootCondition condition : conditions) {
                ConditionOutcome outcome = condition.getMatchOutcome(context, subMetadata);
                Assert.notNull(condition, "Condition outcome which be overrode can not be null");
                (outcome.isMatch() ? matched : unmatched).add(outcome.getConditionMessage());
            }
        }
        Assert.isTrue(!CollectionUtils.isEmpty(matched) || !CollectionUtils.isEmpty(unmatched), "Matched and unmatched messages could not both be empty for @" + wrapperType.getCanonicalName());
        return determineOutcome(matched, unmatched);
    }

    @Nonnull
    protected abstract ConditionOutcome determineOutcome(@Nonnull List<ConditionMessage> matched, @Nonnull List<ConditionMessage> unmatched);

    protected List<SpringBootCondition> primitiveConditions() {
        Conditional conditional = AnnotationUtils.getAnnotation(primitiveType, Conditional.class);
        if (conditional == null || ArrayUtils.isEmpty(conditional.value())) {
            return null;
        }
        List<SpringBootCondition> result = new ArrayList<>(ArrayUtils.getLength(conditional.value()));
        Arrays.stream(conditional.value()).forEach(element -> {
            Assert.isAssignable(SpringBootCondition.class, element);
            result.add(BeanUtilsWraps.instantiateClassQuietly(element, SpringBootCondition.class));
        });
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    protected String wrapperAttribute() {
        return WRAPPER_ATTRIBUTE;
    }
}
