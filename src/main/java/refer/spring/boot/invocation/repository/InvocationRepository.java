package refer.spring.boot.invocation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import refer.spring.boot.invocation.domain.Invocation;

public interface InvocationRepository extends JpaRepository<Invocation, Long> {
}
