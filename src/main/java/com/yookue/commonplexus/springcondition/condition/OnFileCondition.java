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


import java.io.File;
import java.lang.annotation.Annotation;
import javax.annotation.Nonnull;
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
import com.yookue.commonplexus.javaseutil.util.StringUtilsWraps;
import com.yookue.commonplexus.springcondition.annotation.ConditionalOnFile;
import com.yookue.commonplexus.springcondition.util.ConditionBecauseUtils;


/**
 * Condition being active when matching the file or directory
 *
 * @author David Hsing
 */
@Order(value = Ordered.LOWEST_PRECEDENCE - 1000)
public class OnFileCondition extends SpringBootCondition {
    private static final Class<? extends Annotation> annotation = ConditionalOnFile.class;

    @Override
    @SuppressWarnings("DuplicatedCode")
    public ConditionOutcome getMatchOutcome(@Nonnull ConditionContext context, @Nonnull AnnotatedTypeMetadata metadata) {
        ConditionMessage.Builder builder = ConditionMessage.forCondition(annotation);
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(annotation.getName()));
        if (CollectionUtils.isEmpty(attributes)) {
            return ConditionOutcome.noMatch(builder.because(ConditionBecauseUtils.emptyAttributes(annotation)));
        }
        String pathname = context.getEnvironment().resolvePlaceholders(attributes.getString("pathname"));    // $NON-NLS-1$
        if (StringUtils.isBlank(pathname)) {
            return ConditionOutcome.noMatch(builder.because(ConditionBecauseUtils.missingAttribute("pathname")));    // $NON-NLS-1$
        }
        boolean directory = attributes.getBoolean("directory");    // $NON-NLS-1$
        File target = new File(pathname);
        boolean matched = target.exists() && (directory ? target.isDirectory() : target.isFile());
        String quotation = StringUtilsWraps.quoteDouble(pathname);
        return matched ? ConditionOutcome.match(builder.available(quotation)) : ConditionOutcome.noMatch(builder.notAvailable(quotation));
    }
}
