package refer.spring.boot.operation.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import refer.spring.boot.operation.controller.api.response.ResponseOperation;
import refer.spring.boot.operation.domain.Operation;
import refer.spring.boot.operation.service.OperationService;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/operation")
@RestController
public class OperationController {

    private final OperationService operationService;

    @Autowired
    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping("")
    public ResponseEntity<List<ResponseOperation>> index() {
        List<Operation> operations = operationService.findOperations();

        return ResponseEntity.ok(operations.stream()
                .map(ApiMapper.INSTANCE::toResponseOperation)
                .collect(Collectors.toList()));
    }
}
