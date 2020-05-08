package com.itechart.ema.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.ThrowableProblem;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.violations.ConstraintViolationProblem;
import org.zalando.problem.violations.Violation;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@ControllerAdvice
@Slf4j
public class ExceptionHandling implements ProblemHandling {

    private static final Map<String, Function<ServerErrorMessage, Violation>> PSQL_ERRORS
            = new ImmutableMap.Builder<String, Function<ServerErrorMessage, Violation>>()
            .put("22P02", // invalid_text_representation
                    (msg) -> new Violation(msg.getDatatype(), "invalid enum value"))
            .put("22003", // numeric_value_out_of_range
                    (msg) -> new Violation(msg.getColumn(), "numeric field overflow - " + msg.getDetail()))
            .put("23502", // not_null_violation
                    (msg) -> new Violation(msg.getColumn(), "must not be null - " + msg.getConstraint()))
            .put("23503", // foreign_key_violation
                    (msg) -> new Violation(msg.getColumn(), "referenced item is not present - " + msg.getDetail()))
            .put("23505", // unique_violation
                    (msg) -> new Violation(msg.getConstraint(), "must be unique - " + msg.getDetail()))
            .put("23514", // check_violation
                    (msg) -> new Violation(msg.getConstraint(), "constraint check failed"))
            .build();

    @Override
    public ResponseEntity<Problem> process(final ResponseEntity<Problem> entity, final NativeWebRequest request) {

        var problem = entity.getBody();
        log.error(Arrays.toString(problem != null ? ((ThrowableProblem) problem).getStackTrace() : ArrayUtils.toArray()));
        if (!(problem instanceof ConstraintViolationProblem || problem instanceof DefaultProblem)) {
            return entity;
        }
        var problemBuilder = Problem.builder()
                .withType(Problem.DEFAULT_TYPE.equals(problem.getType()) ? ErrorType.DEFAULT : problem.getType())
                .withTitle(problem.getTitle())
                .withStatus(problem.getStatus());

        if (problem instanceof ConstraintViolationProblem) {
            problemBuilder.withType(ErrorType.CONSTRAINT_VIOLATION);
            problemBuilder.with("violations", ((ConstraintViolationProblem) problem).getViolations());
        } else {
            problemBuilder
                    .withDetail(problem.getDetail())
                    .withInstance(problem.getInstance())
                    .withCause(((DefaultProblem) problem).getCause());
            problem.getParameters().forEach(problemBuilder::with);
        }

        return new ResponseEntity<>(problemBuilder.build(), entity.getHeaders(), entity.getStatusCode());
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handle(final InvalidFormatException ex, final NativeWebRequest request) {
        return create(Status.UNPROCESSABLE_ENTITY, ex, request, ErrorType.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handle(final DataIntegrityViolationException ex, final NativeWebRequest request) {
        Violation violation = null;
        var rootCause = ExceptionUtils.getRootCause(ex);
        if (rootCause instanceof PSQLException) {
            var psqlException = (PSQLException) rootCause;
            if (PSQL_ERRORS.containsKey(psqlException.getSQLState())) {
                violation = PSQL_ERRORS.get(psqlException.getSQLState()).apply(psqlException.getServerErrorMessage());
            }
        }
        if (violation == null) {
            var message = ExceptionUtils.getRootCauseMessage(ex);
            violation = new Violation("unknown", message);
        }
        var violations = List.of(violation);
        return newConstraintViolationProblem(ex, violations, request);
    }

}
