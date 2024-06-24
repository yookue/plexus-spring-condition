/*
 * Copyright (c) 2021 Yookue Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
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
import org.springframework.util.CollectionUtils;


/**
 * Condition being active when matching all the conditions
 *
 * @author David Hsing
 */
@SuppressWarnings("unused")
public class AbstractMatchAllCondition<W extends Annotation, P extends Annotation> extends AbstractArrayWrapCondition<W, P> {
    @Nonnull
    @Override
    protected ConditionOutcome determineOutcome(@Nonnull List<ConditionMessage> matched, @Nonnull List<ConditionMessage> unmatched) {
        return CollectionUtils.isEmpty(unmatched) ? ConditionOutcome.match(ConditionMessage.of(matched)) : ConditionOutcome.noMatch(ConditionMessage.of(unmatched));
    }
}
