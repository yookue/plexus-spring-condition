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
import javax.annotation.Nonnull;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.yookue.commonplexus.springcondition.annotation.ConditionalOnMissingResource;
import com.yookue.commonplexus.springcondition.util.ConditionBecauseUtils;


/**
 * Condition being active when missing all the resources
 *
 * @author David Hsing
 */
@Order(value = Ordered.LOWEST_PRECEDENCE - 1000)
public class OnMissingResourceCondition extends SpringBootCondition {
    private static final Class<? extends Annotation> annotation = ConditionalOnMissingResource.class;

    @Override
    public ConditionOutcome getMatchOutcome(@Nonnull ConditionContext context, @Nonnull AnnotatedTypeMetadata metadata) {
        ConditionMessage.Builder builder = ConditionMessage.forCondition(annotation);
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(annotation.getName()));
        if (CollectionUtils.isEmpty(attributes)) {
            return ConditionOutcome.noMatch(builder.because(ConditionBecauseUtils.emptyAttributes(annotation)));
        }
        String[] resources = attributes.getStringArray("resources");    // $NON-NLS-1$
        if (ArrayUtils.isEmpty(resources)) {
            return ConditionOutcome.noMatch(builder.because(ConditionBecauseUtils.missingAttribute("resources")));    // $NON-NLS-1$
        }
        ResourceLoader loader = context.getResourceLoader();
        boolean unmatched = Arrays.stream(resources).filter(StringUtils::hasText).noneMatch(element -> loader.getResource(element).exists());
        String delimited = String.format("[%s]", StringUtils.arrayToCommaDelimitedString(resources));    // $NON-NLS-1$
        return unmatched ? ConditionOutcome.match(builder.notAvailable(delimited)) : ConditionOutcome.noMatch(builder.available(delimited));
    }
}
