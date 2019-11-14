package refer.spring.boot.invocation.service;

import refer.spring.boot.invocation.domain.Invocation;

public interface EvaluationService {

    Long evaluate(Long first, Long other);

    Invocation evaluateAsync(Long first, Long other);
}
