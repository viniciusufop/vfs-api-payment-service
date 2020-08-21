package br.com.vfs.api.payment.service.shared.validators;

import br.com.vfs.api.payment.service.shared.annotations.ExistElement;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.stream.Stream;

public class ExistElementValidator implements ConstraintValidator<ExistElement, Object> {
    private static final String QUERY = "select 1 from %s where %s=:value";
    private Class<?> clazz;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(final ExistElement constraintAnnotation) {
        clazz = constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        if(Objects.isNull(value)) return true;
        final var fieldName = getIdField();
        final var query = entityManager.createQuery(String.format(QUERY, clazz.getName(), fieldName));
        query.setParameter("value", value);
        final var resultList = query.getResultList();
        Assert.isTrue(resultList.size() <= 1, String.format("Duplicate value (%s) in attribute %s and class %s", value, fieldName, clazz.getSimpleName()));
        return !resultList.isEmpty();

    }
    private String getIdField() {
        final var id = Stream.of(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Class (%s) not id element", clazz.getSimpleName())));
        return id.getName();
    }
}
