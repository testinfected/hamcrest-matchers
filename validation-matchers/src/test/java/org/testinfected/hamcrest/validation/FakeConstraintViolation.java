package org.testinfected.hamcrest.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;

public class FakeConstraintViolation implements ConstraintViolation<Object> {

    public String description;
    public String messageTemplate;
    public Path path;

    public static FakeConstraintViolation aViolation() {
        return new FakeConstraintViolation();
    }

    public FakeConstraintViolation on(Path path) {
        this.path = path;
        return this;
    }

    public FakeConstraintViolation withMessageTemplate(String template) {
        messageTemplate = template;
        return this;
    }

    public FakeConstraintViolation describedAs(String description) {
        this.description = description;
        return this;
    }

    public String getMessage() {
        return null;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }

    public Object getRootBean() {
        return null;
    }

    public Class<Object> getRootBeanClass() {
        return null;
    }

    public Object getLeafBean() {
        return null;
    }

    public Path getPropertyPath() {
        return path;
    }

    public Object getInvalidValue() {
        return null;
    }

    public ConstraintDescriptor<?> getConstraintDescriptor() {
        return null;
    }

    @Override public String toString() {
        return description;
    }
}

