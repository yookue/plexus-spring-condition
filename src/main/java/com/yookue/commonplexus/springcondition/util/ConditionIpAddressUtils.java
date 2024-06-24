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
import javax.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import com.yookue.commonplexus.javaseutil.util.InetAddressWraps;
import com.yookue.commonplexus.javaseutil.util.RegexUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.StringUtilsWraps;


/**
 * Utilities for condition ip address
 *
 * @author David Hsing
 */
@SuppressWarnings("unused")
public abstract class ConditionIpAddressUtils {
    @Nonnull
    public static ConditionOutcome matchIpAddress(@Nonnull Class<? extends Annotation> conditionClass, @Nonnull String attributeName, @Nonnull String ipAddress, boolean regExp) {
        ConditionMessage.Builder builder = ConditionMessage.forCondition(conditionClass);
        String localAddress = InetAddressWraps.getLocalIpAddressQuietly();
        if (StringUtils.isBlank(localAddress)) {
            return ConditionOutcome.noMatch(builder.because("the localhost ip address is empty"));
        }
        String quotation = StringUtilsWraps.quoteDouble(ipAddress);
        boolean matched;
        if (regExp) {
            Pattern pattern = RegexUtilsWraps.compilePattern(ipAddress);
            if (pattern == null) {
                return ConditionOutcome.noMatch(builder.because(ConditionBecauseUtils.illegalAttribute(attributeName)));
            }
            matched = pattern.matcher(localAddress).matches();
        } else {
            matched = StringUtils.equals(ipAddress, localAddress);
        }
        return matched ? ConditionOutcome.match(builder.available(quotation)) : ConditionOutcome.noMatch(builder.notAvailable(quotation));
    }
}
