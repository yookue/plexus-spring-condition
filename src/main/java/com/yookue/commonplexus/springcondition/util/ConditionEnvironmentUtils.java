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

package com.yookue.commonplexus.springcondition.util;


import java.lang.annotation.Annotation;
import java.util.regex.Pattern;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import com.yookue.commonplexus.javaseutil.util.RegexUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.StringUtilsWraps;


/**
 * Utilities for condition system environment
 *
 * @author David Hsing
 */
@SuppressWarnings("unused")
public abstract class ConditionEnvironmentUtils {
    @Nonnull
    @SuppressWarnings("DuplicatedCode")
    public static ConditionOutcome matchEnvironment(@Nonnull Class<? extends Annotation> conditionClass, @Nonnull String attributeName, @Nonnull String environmentName, @Nullable String havingValue, boolean caseSensitive, boolean regex) {
        ConditionMessage.Builder builder = ConditionMessage.forCondition(conditionClass);
        String quotation = StringUtilsWraps.quoteDouble(environmentName);
        String environmentValue = SystemUtils.getEnvironmentVariable(environmentName, null);
        boolean matched;
        if (regex) {
            Pattern pattern = RegexUtilsWraps.compilePattern(havingValue, caseSensitive ? 0 : Pattern.CASE_INSENSITIVE);
            if (pattern == null) {
                return ConditionOutcome.noMatch(builder.because(ConditionBecauseUtils.illegalAttribute(attributeName)));
            }
            matched = pattern.matcher(environmentValue).matches();
        } else {
            matched = caseSensitive ? StringUtils.equals(havingValue, environmentValue) : StringUtils.equalsIgnoreCase(havingValue, environmentValue);
        }
        return matched ? ConditionOutcome.match(builder.available(quotation)) : ConditionOutcome.noMatch(builder.notAvailable(quotation));
    }
}
