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
import java.util.List;
import javax.annotation.Nonnull;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.yookue.commonplexus.springcondition.annotation.ConditionalOnMissingProperty;
import com.yookue.commonplexus.springcondition.util.ConditionBecauseUtils;
import com.yookue.commonplexus.springcondition.util.ConditionPropertyUtils;


/**
 * Condition being active when missing the property
 *
 * @author David Hsing
 */
@Order(value = Ordered.LOWEST_PRECEDENCE - 1000)
public class OnMissingPropertyCondition extends SpringBootCondition {
    private static final Class<? extends Annotation> annotation = ConditionalOnMissingProperty.class;

    @Override
    public ConditionOutcome getMatchOutcome(@Nonnull ConditionContext context, @Nonnull AnnotatedTypeMetadata metadata) {
        ConditionMessage.Builder builder = ConditionMessage.forCondition(annotation);
        List<String> properties = ConditionPropertyUtils.getCompositePrefixNames(metadata, annotation);
        if (CollectionUtils.isEmpty(properties)) {
            return ConditionOutcome.noMatch(builder.because(ConditionBecauseUtils.missingAttribute("prefix/name")));    // $NON-NLS-1$
        }
        boolean unmatched = properties.stream().noneMatch(property -> context.getEnvironment().containsProperty(property));
        String delimited = String.format("[%s]", StringUtils.collectionToCommaDelimitedString(properties));    // $NON-NLS-1$
        return unmatched ? ConditionOutcome.match(builder.notAvailable(delimited)) : ConditionOutcome.noMatch(builder.available(delimited));
    }
}
