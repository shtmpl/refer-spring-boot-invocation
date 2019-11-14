package refer.spring.boot.invocation.controller.api;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refer.spring.boot.invocation.controller.api.response.ResponseInvocation;
import refer.spring.boot.invocation.domain.Invocation;

@Mapper
public interface ApiMapper {

    ApiMapper INSTANCE = Mappers.getMapper(ApiMapper.class);

    ResponseInvocation toResponseInvocation(Invocation invocation);
}
