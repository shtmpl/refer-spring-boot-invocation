package refer.spring.boot.invocation.service;

import refer.spring.boot.invocation.domain.Invocation;

import java.util.List;
import java.util.Optional;

public interface InvocationService {

    List<Invocation> findInvocations();

    Optional<Invocation> findInvocation(Long id);

    Invocation saveInvocation(Invocation invocation);

    Invocation updateInvocation(Long id, Invocation invocation);
}
