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

package org.springframework.boot.autoconfigure.condition;


import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.AnnotationMetadataUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import com.yookue.commonplexus.springcondition.annotation.ConditionalOnMultipleCandidates;


/**
 * Condition being active when matching multiple candidates
 *
 * @author David Hsing
 * @see org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate
 */
@Order(value = Ordered.LOWEST_PRECEDENCE - 1000)
public class OnMultipleCandidatesCondition extends SpringBootCondition {
    private static final Class<? extends Annotation> annotation = ConditionalOnMultipleCandidates.class;

    @Override
    public ConditionOutcome getMatchOutcome(@Nonnull ConditionContext context, @Nonnull AnnotatedTypeMetadata metadata) {
        ConditionMessage.Builder builder = ConditionMessage.forCondition(annotation);
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(annotation.getName()));
        if (ObjectUtils.isEmpty(attributes)) {
            return ConditionOutcome.noMatch(builder.because("annotation attributes of metadata is empty"));    // $NON-NLS-1$
        }
        List<ConditionMessage> matched = new ArrayList<>(), unmatched = new ArrayList<>();
        if (metadata instanceof AnnotationMetadata) {
            MergedAnnotation<?> newAnnotation = MergedAnnotation.of(ConditionalOnSingleCandidate.class, attributes);
            AnnotationMetadata newMetadata = AnnotationMetadataUtils.renewMetadataAnnotationQuietly((AnnotationMetadata) metadata, newAnnotation);
            Assert.notNull(newMetadata, "AnnotationMetadata cloned can not be null");
            SpringBootCondition condition = new OnBeanCondition();
            ConditionOutcome outcome = condition.getMatchOutcome(context, newMetadata);
            Assert.notNull(condition, "Condition outcome which be invoked can not be null");
            (outcome.isMatch() ? unmatched : matched).add(outcome.getConditionMessage());
        }
        Assert.isTrue(!CollectionUtils.isEmpty(matched) || !CollectionUtils.isEmpty(unmatched), "Matched and unmatched messages could not both be empty");
        return CollectionUtils.isEmpty(matched) ? ConditionOutcome.noMatch(ConditionMessage.of(unmatched)) : ConditionOutcome.match(ConditionMessage.of(matched));
    }
}
