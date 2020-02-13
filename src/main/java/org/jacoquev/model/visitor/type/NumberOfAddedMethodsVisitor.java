package org.jacoquev.model.visitor.type;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifier;
import org.jacoquev.model.metric.Metric;
import org.jacoquev.model.metric.util.ClassUtils;
import org.jacoquev.model.metric.util.MethodUtils;
import org.jacoquev.model.metric.value.Value;

public class NumberOfAddedMethodsVisitor extends JavaClassVisitor {
    @Override
    public void visitClass(PsiClass psiClass) {
        super.visitClass(psiClass);
        metric = Metric.of("NOAM", "Number Of Added Methods",
                "/html/NumberOfAddedMethods.html", Value.UNDEFINED);
        if (ClassUtils.isConcrete(psiClass)) {
            long numAddedMethods = 0;
            PsiMethod[] methods = psiClass.getMethods();
            for (PsiMethod method : methods) {
                if (method.isConstructor() || method.hasModifierProperty(PsiModifier.ABSTRACT)) {
                    continue;
                }
                if (method.hasModifierProperty(PsiModifier.PRIVATE) || method.hasModifierProperty(PsiModifier.STATIC)) {
                    numAddedMethods++;
                    continue;
                }
                if (!MethodUtils.hasConcreteSuperMethod(method)) {
                    numAddedMethods++;
                }
            }
            metric = Metric.of("NOAM", "Number Of Added Methods",
                    "/html/NumberOfAddedMethods.html", numAddedMethods);
        }
    }
}