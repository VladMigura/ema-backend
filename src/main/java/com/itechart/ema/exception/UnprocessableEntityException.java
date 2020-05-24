package com.itechart.ema.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class UnprocessableEntityException extends AbstractThrowableProblem {

    public UnprocessableEntityException(final String message) {
        super(ErrorType.UNPROCESSABLE_ENTITY, "Unprocessable Entity", Status.UNPROCESSABLE_ENTITY, message);
    }

}
