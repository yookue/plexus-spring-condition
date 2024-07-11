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
import java.util.Arrays;
import java.util.regex.Pattern;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import com.yookue.commonplexus.javaseutil.util.ClassLoaderWraps;
import com.yookue.commonplexus.javaseutil.util.RegexUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.StringUtilsWraps;
import jakarta.annotation.Nonnull;


/**
 * Utilities for condition library names
 *
 * @author David Hsing
 */
@SuppressWarnings("unused")
public abstract class ConditionLibraryUtils {
    @Nonnull
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static ConditionOutcome matchLibraryName(@Nonnull Class<? extends Annotation> conditionClass, @Nonnull String attributeName, @Nonnull ClassLoader classLoader, @Nonnull String libraryName, boolean caseSensitive, boolean regExp) {
        ConditionMessage.Builder builder = ConditionMessage.forCondition(conditionClass);
        String[] loadedLibraries = ClassLoaderWraps.getLoadedLibraryNames(classLoader, false);
        if (ArrayUtils.isEmpty(loadedLibraries)) {
            return ConditionOutcome.noMatch(builder.because("property 'loadedLibraryNames' of ClassLoader is empty"));    // $NON-NLS-1$
        }
        String quotation = StringUtilsWraps.quoteDouble(libraryName);
        boolean matched;
        if (regExp) {
            Pattern pattern = RegexUtilsWraps.compilePattern(libraryName, caseSensitive ? 0 : Pattern.CASE_INSENSITIVE);
            if (pattern == null) {
                return ConditionOutcome.noMatch(builder.because(ConditionBecauseUtils.illegalAttribute(attributeName)));
            }
            matched = Arrays.stream(loadedLibraries).anyMatch(element -> pattern.matcher(element).matches());
        } else {
            matched = Arrays.stream(loadedLibraries).anyMatch(element -> caseSensitive ? StringUtils.equals(element, libraryName) : StringUtils.equalsIgnoreCase(element, libraryName));
        }
        return matched ? ConditionOutcome.match(builder.available(quotation)) : ConditionOutcome.noMatch(builder.notAvailable(quotation));
    }
}
