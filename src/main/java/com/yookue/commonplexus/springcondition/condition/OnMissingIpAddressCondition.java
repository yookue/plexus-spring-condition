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
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.CollectionUtils;
import com.yookue.commonplexus.javaseutil.util.RegexUtilsWraps;
import com.yookue.commonplexus.springcondition.annotation.ConditionalOnMissingIpAddress;
import com.yookue.commonplexus.springcondition.util.ConditionBecauseUtils;
import com.yookue.commonplexus.springcondition.util.ConditionIpAddressUtils;


/**
 * Condition being active when missing the ip address
 *
 * @author David Hsing
 */
@Order(value = Ordered.LOWEST_PRECEDENCE - 1000)
@SuppressWarnings("unused")
public class OnMissingIpAddressCondition extends SpringBootCondition {
    private static final Class<? extends Annotation> annotation = ConditionalOnMissingIpAddress.class;

    @Override
    @SuppressWarnings("DuplicatedCode")
    public ConditionOutcome getMatchOutcome(@Nonnull ConditionContext context, @Nonnull AnnotatedTypeMetadata metadata) {
        ConditionMessage.Builder builder = ConditionMessage.forCondition(annotation);
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(annotation.getName()));
        if (CollectionUtils.isEmpty(attributes)) {
            return ConditionOutcome.noMatch(builder.because(ConditionBecauseUtils.emptyAttributes(annotation)));
        }
        String address = attributes.getString("address");    // $NON-NLS-1$
        if (StringUtils.isBlank(address)) {
            return ConditionOutcome.noMatch(builder.because(ConditionBecauseUtils.missingAttribute("address")));    // $NON-NLS-1$
        }
        boolean regex = attributes.getBoolean("regex");    // $NON-NLS-1$
        if (regex && !RegexUtilsWraps.isCompilable(address)) {
            return ConditionOutcome.noMatch(builder.because(ConditionBecauseUtils.illegalAttribute("address")));    // $NON-NLS-1$
        }
        return ConditionOutcome.inverse(ConditionIpAddressUtils.matchIpAddress(annotation, "address", address, regex));    // $NON-NLS-1$
    }
}
