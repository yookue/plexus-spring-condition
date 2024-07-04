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
import org.apache.commons.lang3.SystemUtils;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;
import com.yookue.commonplexus.springcondition.annotation.ConditionalOnWindowsOs;


/**
 * Condition being active when matching windowsOS
 *
 * @author David Hsing
 */
@Order(value = Ordered.LOWEST_PRECEDENCE - 1000)
@SuppressWarnings("unused")
public class OnWindowsOsCondition extends SpringBootCondition {
    private static final Class<? extends Annotation> annotation = ConditionalOnWindowsOs.class;

    @Override
    public ConditionOutcome getMatchOutcome(@Nonnull ConditionContext context, @Nonnull AnnotatedTypeMetadata metadata) {
        ConditionMessage.Builder builder = ConditionMessage.forCondition(annotation);
        String equation = String.format("os.name='%s'", SystemUtils.OS_NAME);    // $NON-NLS-1$
        return SystemUtils.IS_OS_WINDOWS ? ConditionOutcome.match(builder.available(equation)) : ConditionOutcome.noMatch(builder.notAvailable(equation));
    }
}
