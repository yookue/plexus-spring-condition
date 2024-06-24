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
import javax.annotation.Nonnull;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import com.yookue.commonplexus.springcondition.annotation.ConditionalOnLibrary;
import com.yookue.commonplexus.springcondition.util.ConditionBecauseUtils;
import com.yookue.commonplexus.springcondition.util.ConditionLibraryUtils;
import lombok.extern.slf4j.Slf4j;


/**
 * Condition being active when matching the loaded library name in the classpath
 *
 * @author David Hsing
 * @reference "https://stackoverflow.com/questions/1007861/how-do-i-get-a-list-of-jni-libraries-which-are-loaded"
 */
@Order(value = Ordered.LOWEST_PRECEDENCE - 1000)
@Slf4j
@SuppressWarnings({"JavadocDeclaration", "JavadocLinkAsPlainText"})
public class OnLibraryCondition extends SpringBootCondition {
    private static final Class<? extends Annotation> annotation = ConditionalOnLibrary.class;

    @Override
    @SuppressWarnings("DuplicatedCode")
    public ConditionOutcome getMatchOutcome(@Nonnull ConditionContext context, @Nonnull AnnotatedTypeMetadata metadata) {
        ConditionMessage.Builder builder = ConditionMessage.forCondition(annotation);
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(annotation.getName()));
        if (CollectionUtils.isEmpty(attributes)) {
            return ConditionOutcome.noMatch(builder.because(ConditionBecauseUtils.emptyAttributes(annotation)));
        }
        String libraryName = attributes.getString("name");    // $NON-NLS-1$
        if (StringUtils.isBlank(libraryName)) {
            return ConditionOutcome.noMatch(builder.because(ConditionBecauseUtils.missingAttribute("name")));    // $NON-NLS-1$
        }
        boolean caseSensitive = attributes.getBoolean("caseSensitive");    // $NON-NLS-1$
        boolean regExp = attributes.getBoolean("regExp");    // $NON-NLS-1$
        ClassLoader classLoader = ObjectUtils.defaultIfNull(context.getClassLoader(), ClassUtils.getDefaultClassLoader());
        return ConditionLibraryUtils.matchLibraryName(annotation, "name", classLoader, libraryName, caseSensitive, regExp);    // $NON-NLS-1$
    }
}
