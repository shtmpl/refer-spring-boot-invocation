package refer.spring.boot.invocation.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import refer.spring.boot.invocation.controller.api.response.ResponseInvocation;
import refer.spring.boot.invocation.domain.Invocation;
import refer.spring.boot.invocation.service.InvocationService;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/invocation")
@RestController
public class InvocationController {

    private final InvocationService invocationService;

    @Autowired
    public InvocationController(InvocationService invocationService) {
        this.invocationService = invocationService;
    }

    @GetMapping("")
    public ResponseEntity<List<ResponseInvocation>> index() {
        List<Invocation> invocations = invocationService.findInvocations();

        return ResponseEntity.ok(invocations.stream()
                .map(ApiMapper.INSTANCE::toResponseInvocation)
                .collect(Collectors.toList()));
    }
}
