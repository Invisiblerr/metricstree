/*
 * Copyright 2020 b333vv
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.b333vv.metric.visitor.type;

import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiClass;
import org.b333vv.metric.model.code.JavaClass;
import org.b333vv.metric.model.metric.Metric;

public abstract class JavaClassVisitor extends JavaRecursiveElementVisitor {

    protected Metric metric;

    public void visitJavaClass(JavaClass javaClass) {
        PsiClass psiClass = javaClass.getPsiClass();
        visitClass(psiClass);
        javaClass.addMetric(metric);
    }
}