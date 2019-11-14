package refer.spring.boot.invocation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refer.spring.boot.invocation.domain.EvaluationException;
import refer.spring.boot.invocation.domain.Invocation;

import java.time.OffsetDateTime;
import java.util.concurrent.CompletableFuture;

@Transactional
@Service
public class EvaluationServiceImpl implements EvaluationService {

    private final InvocationService invocationService;

    @Autowired
    public EvaluationServiceImpl(InvocationService invocationService) {
        this.invocationService = invocationService;
    }

    @Override
    public Long evaluate(Long first, Long other) {
        Invocation invocation = new Invocation();
        invocation.setInitiatedAt(OffsetDateTime.now());

        Invocation initiated = invocationService.saveInvocation(new Invocation());

        Long result;
        try {
            result = calculate(first, other);

            initiated.setOutcome(Invocation.Outcome.SUCCESS);
        } catch (Throwable throwable) {
            initiated.setOutcome(Invocation.Outcome.FAILURE);
            invocation.setReason(throwable.getMessage());

            throw new EvaluationException(throwable);
        } finally {
            initiated.setCompletedAt(OffsetDateTime.now());
        }

        invocationService.updateInvocation(initiated.getId(), initiated);

        return result;
    }

    @Override
    public Invocation evaluateAsync(Long first, Long other) {
        Invocation invocation = new Invocation();
        invocation.setInitiatedAt(OffsetDateTime.now());

        Invocation initiated = invocationService.saveInvocation(invocation);

        CompletableFuture.supplyAsync(() -> calculate(first, other))
                .handle((Long value, Throwable throwable) -> {
                    if (value != null) {
                        initiated.setOutcome(Invocation.Outcome.SUCCESS);
                    }

                    if (throwable != null) {
                        initiated.setOutcome(Invocation.Outcome.FAILURE);
                        initiated.setReason(throwable.getMessage());
                    }

                    initiated.setCompletedAt(OffsetDateTime.now());

                    return invocationService.updateInvocation(initiated.getId(), initiated);
                });

        return initiated;
    }

    private Long calculate(Long first, Long other) {
        Long result = first + other;
        if (first + other == 42) {
            throw new RuntimeException("42!");
        }

        return result;
    }
}
