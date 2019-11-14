package refer.spring.boot.invocation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refer.spring.boot.invocation.domain.Invocation;
import refer.spring.boot.invocation.repository.InvocationRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class InvocationServiceImpl implements InvocationService {

    private final InvocationRepository invocationRepository;

    @Autowired
    public InvocationServiceImpl(InvocationRepository invocationRepository) {
        this.invocationRepository = invocationRepository;
    }

    @Override
    public List<Invocation> findInvocations() {
        return invocationRepository.findAll();
    }

    @Override
    public Optional<Invocation> findInvocation(Long id) {
        return invocationRepository.findById(id);
    }

    @Override
    public Invocation saveInvocation(Invocation invocation) {
        Invocation result = new Invocation();
        result.setCreatedAt(OffsetDateTime.now());
        result.setInitiatedAt(invocation.getInitiatedAt());
        result.setCompletedAt(invocation.getCompletedAt());
        result.setOutcome(invocation.getOutcome());
        result.setReason(invocation.getReason());

        return invocationRepository.save(result);
    }

    @Override
    public Invocation updateInvocation(Long id, Invocation invocation) {
        Invocation found = invocationRepository.findById(id)
                .orElse(null);
        if (found == null) {
            return saveInvocation(invocation);
        }

        OffsetDateTime initiatedAt = invocation.getInitiatedAt();
        if (initiatedAt != null && !initiatedAt.equals(found.getInitiatedAt())) {
            found.setInitiatedAt(initiatedAt);
        }

        OffsetDateTime completedAt = invocation.getCompletedAt();
        if (completedAt != null && !completedAt.equals(found.getCompletedAt())) {
            found.setCompletedAt(completedAt);
        }

        Invocation.Outcome outcome = invocation.getOutcome();
        if (outcome != null && !outcome.equals(found.getOutcome())) {
            found.setOutcome(outcome);
        }

        String reason = invocation.getReason();
        if (outcome == Invocation.Outcome.SUCCESS) {
            found.setReason(null);
        } else {
            if (reason != null && !reason.equals(found.getReason())) {
                found.setReason(reason);
            }
        }

        return invocationRepository.save(found);
    }
}
