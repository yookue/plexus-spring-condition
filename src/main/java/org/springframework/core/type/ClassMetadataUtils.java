/*
 * Copyright (c) 2024 Yookue Ltd. All rights reserved.
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


import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;


/**
 * Utilities for {@link org.springframework.core.type.ClassMetadata}
 *
 * @author David Hsing
 * @see org.springframework.core.type.ClassMetadata
 * @see org.springframework.core.type.StandardClassMetadata
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class ClassMetadataUtils {
    @Nullable
    public static StandardAnnotationMetadata createStandardAnnotationMetadata(@Nonnull Class<?> introspectedClass) {
        AnnotationMetadata metadata = AnnotationMetadata.introspect(introspectedClass);
        return (metadata instanceof StandardAnnotationMetadata instance) ? instance : null;
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public static StandardClassMetadata createStandardMetadata(@Nonnull Class<?> introspectedClass) {
        return new StandardClassMetadata(introspectedClass);
    }

    @Nullable
    public static ClassMetadata cloneMetadata(@Nonnull ClassMetadata metadata) {
        if (metadata instanceof StandardAnnotationMetadata instance) {
            return AnnotationMetadata.introspect(instance.getIntrospectedClass());
        } else if (metadata instanceof StandardClassMetadata instance) {
            return createStandardMetadata(instance.getIntrospectedClass());
        }
        return null;
    }
}
