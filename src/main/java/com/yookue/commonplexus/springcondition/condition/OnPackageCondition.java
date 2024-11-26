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
import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.yookue.commonplexus.javaseutil.util.PackagePlainWraps;
import com.yookue.commonplexus.springcondition.annotation.ConditionalOnPackage;
import com.yookue.commonplexus.springcondition.util.ConditionBecauseUtils;


/**
 * Condition being active when matching all the package names
 *
 * @author David Hsing
 */
@Order(value = Ordered.LOWEST_PRECEDENCE - 1000)
@SuppressWarnings("unused")
public class OnPackageCondition extends SpringBootCondition {
    private static final Class<? extends Annotation> annotation = ConditionalOnPackage.class;

    @Override
    @SuppressWarnings("DuplicatedCode")
    public ConditionOutcome getMatchOutcome(@Nonnull ConditionContext context, @Nonnull AnnotatedTypeMetadata metadata) {
        ConditionMessage.Builder builder = ConditionMessage.forCondition(annotation);
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(annotation.getName()));
        if (CollectionUtils.isEmpty(attributes)) {
            return ConditionOutcome.noMatch(builder.because(ConditionBecauseUtils.emptyAttributes(annotation)));
        }
        String[] packet = attributes.getStringArray("packet");    // $NON-NLS-1$
        if (ArrayUtils.isEmpty(packet)) {
            return ConditionOutcome.noMatch(builder.because(ConditionBecauseUtils.missingAttribute("packet")));    // $NON-NLS-1$
        }
        boolean matched = PackagePlainWraps.existsAllPackages(packet);
        String delimited = String.format("[%s]", StringUtils.arrayToCommaDelimitedString(packet));    // $NON-NLS-1$
        return matched ? ConditionOutcome.match(builder.available(delimited)) : ConditionOutcome.noMatch(builder.notAvailable(delimited));
    }
}
