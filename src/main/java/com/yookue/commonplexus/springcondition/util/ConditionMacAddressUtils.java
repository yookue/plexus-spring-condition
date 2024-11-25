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
import java.util.List;
import jakarta.annotation.Nonnull;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.util.CollectionUtils;
import com.yookue.commonplexus.javaseutil.util.InetAddressWraps;
import com.yookue.commonplexus.javaseutil.util.RegexUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.StringUtilsWraps;


/**
 * Utilities for condition mac address
 *
 * @author David Hsing
 */
@SuppressWarnings("unused")
public abstract class ConditionMacAddressUtils {
    @Nonnull
    public static ConditionOutcome matchMacAddress(@Nonnull Class<? extends Annotation> conditionClass, @Nonnull String attributeName, @Nonnull String macAddress, boolean regex) {
        ConditionMessage.Builder builder = ConditionMessage.forCondition(conditionClass);
        List<String> localAddresses = InetAddressWraps.getLocalMacAddressQuietly();
        if (CollectionUtils.isEmpty(localAddresses)) {
            return ConditionOutcome.noMatch(builder.because("the localhost mac address is empty"));
        }
        String quotation = StringUtilsWraps.quoteDouble(macAddress);
        boolean matched;
        if (regex) {
            if (!RegexUtilsWraps.isCompilable(macAddress)) {
                return ConditionOutcome.noMatch(builder.because(ConditionBecauseUtils.illegalAttribute(attributeName)));
            }
            matched = RegexUtilsWraps.matchAnySequencesIgnoreCase(macAddress, localAddresses);
        } else {
            matched = StringUtilsWraps.equalsAnyIgnoreCase(macAddress, localAddresses);
        }
        return matched ? ConditionOutcome.match(builder.available(quotation)) : ConditionOutcome.noMatch(builder.notAvailable(quotation));
    }
}
