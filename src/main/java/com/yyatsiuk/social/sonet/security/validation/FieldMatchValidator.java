package com.yyatsiuk.social.sonet.security.validation;


import com.yyatsiuk.social.sonet.exception.FieldMissMachException;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean valid;
        try {
            final Object firstObj = BeanUtils.getProperty(value, firstFieldName);
            final Object secondObj = BeanUtils.getProperty(value, secondFieldName);

            valid = firstObj == null && secondObj == null
                    || firstObj != null && firstObj.equals(secondObj);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignore) {
            throw new FieldMissMachException("Fields needs to match");
        }
        if (!valid) {
            throw new FieldMissMachException("Fields needs to match");
        }
        return true;
    }
}