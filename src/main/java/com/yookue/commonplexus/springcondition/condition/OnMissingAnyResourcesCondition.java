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


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import com.yookue.commonplexus.springcondition.annotation.ConditionalOnMissingAnyResources;
import com.yookue.commonplexus.springcondition.annotation.ConditionalOnMissingResource;


/**
 * Condition being active when matching any of the {@link com.yookue.commonplexus.springcondition.annotation.ConditionalOnMissingResource}
 *
 * @author David Hsing
 */
@Order(value = Ordered.LOWEST_PRECEDENCE - 1000)
public class OnMissingAnyResourcesCondition extends AbstractMatchAnyCondition<ConditionalOnMissingAnyResources, ConditionalOnMissingResource> {
}
