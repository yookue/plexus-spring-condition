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

package org.springframework.core.type.classreading;


import java.lang.reflect.Method;
import java.util.Arrays;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.StandardMethodMetadata;


/**
 * Utilities for {@link org.springframework.core.type.ClassMetadata}
 *
 * @author David Hsing
 * @see org.springframework.core.type.classreading.SimpleMethodMetadata
 * @see org.springframework.core.type.classreading.SimpleMethodMetadataReadingVisitor
 * @see org.springframework.core.type.StandardMethodMetadata
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class MethodMetadataUtils {
    @Nonnull
    public static MethodMetadata createSimpleMetadata(@Nonnull String methodName, int access, @Nonnull String declaringClassName,
        @Nonnull String returnTypeName, @Nonnull Object source, @Nonnull MergedAnnotations annotations) {
        return new SimpleMethodMetadata(methodName, access, declaringClassName, returnTypeName, source, annotations);
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public static StandardMethodMetadata createStandardMetadata(@Nonnull Method introspectedMethod, boolean nestedAnnotationsAsMap) {
        return new StandardMethodMetadata(introspectedMethod, nestedAnnotationsAsMap);
    }

    @Nullable
    public static MethodMetadata renewMetadataAnnotation(@Nonnull MethodMetadata metadata, @Nonnull MergedAnnotation<?>... annotations) throws IllegalAccessException {
        return renewMetadataAnnotation(metadata, MergedAnnotations.of(Arrays.asList(annotations)));
    }

    @Nullable
    public static MethodMetadata renewMetadataAnnotation(@Nonnull MethodMetadata metadata, @Nonnull MergedAnnotations annotations) throws IllegalAccessException {
        if (metadata instanceof SimpleMethodMetadata) {
            int access = (int) FieldUtils.readDeclaredField(metadata, "access", true);    // $NON-NLS-1$
            Object source = FieldUtils.readDeclaredField(metadata, "source", true);    // $NON-NLS-1$
            return createSimpleMetadata(metadata.getMethodName(), access, metadata.getDeclaringClassName(), metadata.getReturnTypeName(), source, annotations);
        } else if (metadata instanceof StandardMethodMetadata) {
            Method introspectedMethod = ((StandardMethodMetadata) metadata).getIntrospectedMethod();
            boolean nestedAnnotationsAsMap = (boolean) FieldUtils.readDeclaredField(metadata, "nestedAnnotationsAsMap", true);    // $NON-NLS-1$
            StandardMethodMetadata result = createStandardMetadata(introspectedMethod, nestedAnnotationsAsMap);
            FieldUtils.writeDeclaredField(result, "mergedAnnotations", annotations, true);    // $NON-NLS-1$
            return result;
        }
        return null;
    }

    @Nullable
    public static MethodMetadata renewMetadataAnnotationQuietly(@Nonnull MethodMetadata metadata, @Nonnull MergedAnnotation<?>... annotations) {
        return renewMetadataAnnotationQuietly(metadata, MergedAnnotations.of(Arrays.asList(annotations)));
    }

    @Nullable
    public static MethodMetadata renewMetadataAnnotationQuietly(@Nonnull MethodMetadata metadata, @Nonnull MergedAnnotations annotations) {
        try {
            return renewMetadataAnnotation(metadata, annotations);
        } catch (Exception ignored) {
        }
        return null;
    }
}
