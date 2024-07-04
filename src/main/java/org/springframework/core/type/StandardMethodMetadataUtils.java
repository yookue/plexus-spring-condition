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

package org.springframework.core.type;


import java.lang.reflect.Method;
import jakarta.annotation.Nonnull;


/**
 * Utilities for {@link org.springframework.core.type.StandardMethodMetadata}
 *
 * @author David Hsing
 * @see org.springframework.core.type.StandardMethodMetadata
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class StandardMethodMetadataUtils {
    @Nonnull
    public static StandardMethodMetadata createStandardMetadata(@Nonnull Method introspectedMethod, boolean nestedAnnotationsAsMap) {
        return new StandardMethodMetadata(introspectedMethod, nestedAnnotationsAsMap);
    }
}
