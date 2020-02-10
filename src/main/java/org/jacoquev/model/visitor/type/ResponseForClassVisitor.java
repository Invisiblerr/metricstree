package org.jacoquev.model.visitor.type;

import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiCallExpression;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import org.jacoquev.model.metric.util.ClassUtils;
import org.jacoquev.model.metric.value.Value;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ResponseForClassVisitor extends JavaClassVisitor {
    @Override
    public void visitClass(PsiClass psiClass) {
        metric.setName("RFC");
        metric.setDescription("Response For Class");
        metric.setDescriptionUrl("/html/ResponseForClass.html");
        if (ClassUtils.isConcrete(psiClass)) {
            Set<PsiMethod> methodsCalled = new HashSet<>();
            super.visitClass(psiClass);
            Collections.addAll(methodsCalled, psiClass.getMethods());
            psiClass.acceptChildren(new JavaRecursiveElementVisitor() {

                @Override
                public void visitClass(PsiClass psiClass) {}

                @Override
                public void visitCallExpression(PsiCallExpression callExpression) {
                    super.visitCallExpression(callExpression);
                    final PsiMethod target = callExpression.resolveMethod();
                    if (target != null) {
                        methodsCalled.add(target);
                    }
                }
            });
            metric.setValue(Value.of(methodsCalled.size()));
        }
    }
}