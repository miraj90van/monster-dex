package com.assignment.demo.service.monster;

import com.assignment.demo.constans.ResponseType;
import com.assignment.demo.constans.RoleType;
import com.assignment.demo.entity.dto.PagingDto;
import com.assignment.demo.entity.model.BaseResponse;
import com.assignment.demo.entity.request.MonsterDexPagingRequest;
import com.assignment.demo.entity.response.MonsterDexPagingResponse;
import com.assignment.demo.service.monster.paging.VwMonsterDexFactory;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith({MockitoExtension.class})
public class MonsterDexPublicServiceTest {

  @InjectMocks
  private MonsterDexPublicService monsterDexPublicService;

  @Mock
  private VwMonsterDexFactory vwMonsterDexFactory;

  @DisplayName("Success Test: findAllPageable() - When Completed")
  @Test
  void givenTestSuccessFindAllPageable(){
    MonsterDexPagingRequest request = getMonsterDexPagingRequest();
    int page = 0;
    int size = 10;
    Sort.Direction direction = Direction.DESC;
    String[] properties = {"id"};

    Mockito.when(vwMonsterDexFactory
            .execute(request, RoleType.ROLE_PUBLIC, page, size, direction, properties))
        .thenReturn(Mono.just(buildResponsePaging()));

    Mono<BaseResponse<PagingDto<MonsterDexPagingResponse>>> mono = monsterDexPublicService
        .findAllPageable(request, page, size, direction, properties);

    StepVerifier.create(mono)
        .assertNext(response -> {
          Assertions.assertEquals(ResponseType.SUCCESS.getHttpStatus(), response.getHttpStatus());
          Assertions.assertEquals(ResponseType.SUCCESS.getMessage(), response.getMessage());
        })
        .expectComplete()
        .verify();

    Mockito.verify(vwMonsterDexFactory)
        .execute(request, RoleType.ROLE_PUBLIC, page, size, direction, properties);
  }

  @DisplayName("Failed Test: findAllPageable() - When Completed")
  @Test
  void givenTestFailedFindAllPageable(){
    MonsterDexPagingRequest request = getMonsterDexPagingRequest();
    int page = 0;
    int size = 10;
    Sort.Direction direction = Direction.DESC;
    String[] properties = {"id"};

    Mockito.when(vwMonsterDexFactory
            .execute(request, RoleType.ROLE_PUBLIC, page, size, direction, properties))
        .thenReturn(Mono.error(new RuntimeException()));

    Mono<BaseResponse<PagingDto<MonsterDexPagingResponse>>> mono = monsterDexPublicService
        .findAllPageable(request, page, size, direction, properties);

    StepVerifier.create(mono)
        .assertNext(response -> {
          Assertions.assertEquals(ResponseType.INVALID.getHttpStatus(), response.getHttpStatus());
          Assertions.assertEquals(ResponseType.INVALID.getMessage(), response.getMessage());
        })
        .expectComplete()
        .verify();

    Mockito.verify(vwMonsterDexFactory)
        .execute(request, RoleType.ROLE_PUBLIC, page, size, direction, properties);
  }

  private MonsterDexPagingRequest getMonsterDexPagingRequest(){
    return MonsterDexPagingRequest.builder().build();
  }

  private PagingDto<MonsterDexPagingResponse> buildResponsePaging(){
    return new PagingDto<>(0, 10, new ArrayList<>());
  }

}
