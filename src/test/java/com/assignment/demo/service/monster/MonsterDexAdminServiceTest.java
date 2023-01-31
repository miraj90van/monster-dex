package com.assignment.demo.service.monster;

import com.assignment.demo.constans.ResponseType;
import com.assignment.demo.constans.RoleType;
import com.assignment.demo.entity.dto.PagingDto;
import com.assignment.demo.entity.model.BaseResponse;
import com.assignment.demo.entity.model.MonsterDex;
import com.assignment.demo.entity.model.MonsterDexStat;
import com.assignment.demo.entity.model.MonsterDexType;
import com.assignment.demo.entity.request.MonsterDexPagingRequest;
import com.assignment.demo.entity.request.MonsterDexRequest;
import com.assignment.demo.entity.response.MonsterDexPagingResponse;
import com.assignment.demo.entity.response.MonsterDexResponse;
import com.assignment.demo.repository.MonsterDexRepository;
import com.assignment.demo.repository.MonsterDexStatRepository;
import com.assignment.demo.repository.MonsterDexTypeRepository;
import com.assignment.demo.service.monster.paging.VwMonsterDexFactory;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith({MockitoExtension.class})
public class MonsterDexAdminServiceTest {

  @InjectMocks
  private MonsterDexAdminService monsterDexAdminService;

  @Mock
  private VwMonsterDexFactory vwMonsterDexFactory;

  @Mock
  private MonsterDexRepository monsterDexRepository;

  @Mock
  private MonsterDexStatRepository monsterDexStatRepository;

  @Mock
  private MonsterDexTypeRepository monsterDexTypeRepository;

  @Mock
  private TransactionalOperator operator;


  @DisplayName("Success Test: getMonsterDex() - When Completed")
  @Test
  void givenTestSuccessGetMonsterDex(){
    int page = 0;
    int size = 10;
    Sort.Direction direction = Direction.DESC;
    String[] properties = {"id"};
    MonsterDexPagingRequest request = getMonsterDexPagingRequest();
    PagingDto<MonsterDexPagingResponse> pagingDto = buildResponsePaging();

    Mockito.when(vwMonsterDexFactory.execute(request, RoleType.ROLE_ADMIN, page, size, direction, properties))
        .thenReturn(Mono.just(pagingDto));

    Mono<BaseResponse<PagingDto<MonsterDexPagingResponse>>> mono = monsterDexAdminService
        .getMonsterDex(request, page, size, direction, properties);

    StepVerifier.create(mono)
        .assertNext(response -> {
          Assertions.assertEquals(ResponseType.SUCCESS.getHttpStatus(), response.getHttpStatus());
          Assertions.assertEquals(ResponseType.SUCCESS.getMessage(), response.getMessage());
        })
        .expectComplete()
        .verify();

    Mockito.verify(vwMonsterDexFactory).execute(request, RoleType.ROLE_ADMIN, page, size, direction, properties);

  }

  @DisplayName("Error Test: getMonsterDex() - When Completed")
  @Test
  void givenTestErrorGetMonsterDex(){
    int page = 0;
    int size = 10;
    Sort.Direction direction = Direction.DESC;
    String[] properties = {"id"};
    MonsterDexPagingRequest request = getMonsterDexPagingRequest();

    Mockito.when(vwMonsterDexFactory.execute(request, RoleType.ROLE_ADMIN, page, size, direction, properties))
        .thenReturn(Mono.error(new RuntimeException()));

    Mono<BaseResponse<PagingDto<MonsterDexPagingResponse>>> mono = monsterDexAdminService
        .getMonsterDex(request, page, size, direction, properties);

    StepVerifier.create(mono)
        .assertNext(response -> {
          Assertions.assertEquals(ResponseType.INVALID.getHttpStatus(), response.getHttpStatus());
          Assertions.assertEquals(ResponseType.INVALID.getMessage(), response.getMessage());
        })
        .expectComplete()
        .verify();

    Mockito.verify(vwMonsterDexFactory).execute(request, RoleType.ROLE_ADMIN, page, size, direction, properties);

  }

  @SuppressWarnings("unchecked")
  @DisplayName("Success Test: insertMonsterDex() - When Completed")
  @Test
  void givenTestSuccessInsertMonsterDex(){
    final MonsterDexRequest request = getMonsterDexRequest();
    final MonsterDex monsterDex = getMonsterDex(request);

    Mockito.when(monsterDexRepository.save(monsterDex)).thenReturn(Mono.just(monsterDex));

    Mockito.when(monsterDexStatRepository.saveAll(request.getMonsterDexStats()))
        .thenReturn(Flux.fromIterable(request.getMonsterDexStats()));

    Mockito.when(monsterDexTypeRepository.saveAll(request.getMonsterDexTypes()))
        .thenReturn(Flux.fromIterable(request.getMonsterDexTypes()));

    Mockito.when(operator.transactional(ArgumentMatchers.any(Mono.class)))
        .thenAnswer(i -> i.getArgument(0));

    Mono<BaseResponse<MonsterDexResponse>> mono = monsterDexAdminService.insertMonsterDex(request);
    StepVerifier.create(mono)
        .assertNext(response -> {
          Assertions.assertEquals(monsterDex.getName(), response.getData().getName());
          Assertions.assertEquals(monsterDex.getSubName(), response.getData().getSubName());
        })
        .expectComplete()
        .verify();

    Mockito.verify(monsterDexRepository).save(monsterDex);
    Mockito.verify(monsterDexStatRepository).saveAll(request.getMonsterDexStats());
    Mockito.verify(monsterDexTypeRepository).saveAll(request.getMonsterDexTypes());
    Mockito.verify(operator).transactional(ArgumentMatchers.any(Mono.class));
  }

  @SuppressWarnings("unchecked")
  @DisplayName("Failed Test: insertMonsterDex() - When Completed")
  @Test
  void givenTestFailedInsertMonsterDex(){
    final MonsterDexRequest request = getMonsterDexRequest();
    final MonsterDex monsterDex = getMonsterDex(request);

    Mockito.when(monsterDexRepository.save(monsterDex)).thenReturn(Mono.just(monsterDex));

    Mockito.when(monsterDexStatRepository.saveAll(request.getMonsterDexStats()))
        .thenReturn(Flux.fromIterable(request.getMonsterDexStats()));

    Mockito.when(monsterDexTypeRepository.saveAll(request.getMonsterDexTypes()))
        .thenReturn(Flux.error(new RuntimeException()));

    Mockito.when(operator.transactional(ArgumentMatchers.any(Mono.class)))
        .thenAnswer(i -> i.getArgument(0));

    Mono<BaseResponse<MonsterDexResponse>> mono = monsterDexAdminService.insertMonsterDex(request);
    StepVerifier.create(mono)
        .assertNext(response -> {
          Assertions.assertEquals(ResponseType.INVALID.getHttpStatus(), response.getHttpStatus());
          Assertions.assertEquals(ResponseType.INVALID.getMessage(), response.getMessage());
        })
        .expectComplete()
        .verify();

    Mockito.verify(monsterDexRepository).save(monsterDex);
    Mockito.verify(monsterDexStatRepository).saveAll(request.getMonsterDexStats());
    Mockito.verify(monsterDexTypeRepository).saveAll(request.getMonsterDexTypes());
    Mockito.verify(operator).transactional(ArgumentMatchers.any(Mono.class));
  }

  @SuppressWarnings("unchecked")
  @DisplayName("Success Test: updateMonsterDex() - When Completed")
  @Test
  void givenTestSuccessUpdateMonsterDex(){
    final long id = 0L;
    final MonsterDexRequest request = getMonsterDexRequest();
    final MonsterDex monsterDex = getMonsterDex(request);

    Mockito.when(monsterDexRepository.findById(id)).thenReturn(Mono.just(monsterDex));
    Mockito.when(monsterDexRepository.save(monsterDex)).thenReturn(Mono.just(monsterDex));

    Mockito.when(monsterDexStatRepository.deleteByMonsterDexId(id)).thenReturn(Mono.empty());
    Mockito.when(monsterDexStatRepository.saveAll(request.getMonsterDexStats()))
        .thenReturn(Flux.fromIterable(request.getMonsterDexStats()));

    Mockito.when(monsterDexTypeRepository.deleteByMonsterDexId(id)).thenReturn(Mono.empty());
    Mockito.when(monsterDexTypeRepository.saveAll(request.getMonsterDexTypes()))
        .thenReturn(Flux.fromIterable(request.getMonsterDexTypes()));

    Mockito.when(operator.transactional(ArgumentMatchers.any(Mono.class)))
        .thenAnswer(i -> i.getArgument(0));

    Mono<BaseResponse<MonsterDexResponse>> mono = monsterDexAdminService.updateMonsterDex(request, id);

    StepVerifier.create(mono)
        .assertNext(response -> {
          Assertions.assertEquals(monsterDex.getName(), response.getData().getName());
          Assertions.assertEquals(monsterDex.getSubName(), response.getData().getSubName());
        })
        .expectComplete()
        .verify();

    Mockito.verify(monsterDexRepository).findById(id);
    Mockito.verify(monsterDexRepository).save(monsterDex);

    Mockito.verify(monsterDexStatRepository).deleteByMonsterDexId(id);
    Mockito.verify(monsterDexStatRepository).saveAll(request.getMonsterDexStats());

    Mockito.verify(monsterDexTypeRepository).deleteByMonsterDexId(id);
    Mockito.verify(monsterDexTypeRepository).saveAll(request.getMonsterDexTypes());

    Mockito.verify(operator).transactional(ArgumentMatchers.any(Mono.class));
  }

  @SuppressWarnings("unchecked")
  @DisplayName("Failed Test: updateMonsterDex() - When Completed")
  @Test
  void givenTestFailedUpdateMonsterDex(){
    final long id = 0L;
    final MonsterDexRequest request = getMonsterDexRequest();
    final MonsterDex monsterDex = getMonsterDex(request);

    Mockito.when(monsterDexRepository.findById(id)).thenReturn(Mono.just(monsterDex));
    Mockito.when(monsterDexRepository.save(monsterDex)).thenReturn(Mono.just(monsterDex));

    Mockito.when(monsterDexStatRepository.deleteByMonsterDexId(id)).thenReturn(Mono.empty());
    Mockito.when(monsterDexStatRepository.saveAll(request.getMonsterDexStats()))
        .thenReturn(Flux.fromIterable(request.getMonsterDexStats()));

    Mockito.when(monsterDexTypeRepository.deleteByMonsterDexId(id)).thenReturn(Mono.empty());
    Mockito.when(monsterDexTypeRepository.saveAll(request.getMonsterDexTypes()))
        .thenReturn(Flux.error(new RuntimeException()));

    Mockito.when(operator.transactional(ArgumentMatchers.any(Mono.class)))
        .thenAnswer(i -> i.getArgument(0));

    Mono<BaseResponse<MonsterDexResponse>> mono = monsterDexAdminService.updateMonsterDex(request, id);

    StepVerifier.create(mono)
        .assertNext(response -> {
          Assertions.assertEquals(ResponseType.INVALID.getHttpStatus(), response.getHttpStatus());
          Assertions.assertEquals(ResponseType.INVALID.getMessage(), response.getMessage());
        })
        .expectComplete()
        .verify();

    Mockito.verify(monsterDexRepository).findById(id);
    Mockito.verify(monsterDexRepository).save(monsterDex);

    Mockito.verify(monsterDexStatRepository).deleteByMonsterDexId(id);
    Mockito.verify(monsterDexStatRepository).saveAll(request.getMonsterDexStats());

    Mockito.verify(monsterDexTypeRepository).deleteByMonsterDexId(id);
    Mockito.verify(monsterDexTypeRepository).saveAll(request.getMonsterDexTypes());

    Mockito.verify(operator).transactional(ArgumentMatchers.any(Mono.class));
  }

  @SuppressWarnings("unchecked")
  @DisplayName("Success Test: deleteMonsterDex() - When Completed")
  @Test
  void givenTestSuccessDeleteMonsterDex(){
    final long id = 1L;

    Mockito.when(monsterDexRepository.deleteById(id)).thenReturn(Mono.empty());
    Mockito.when(monsterDexStatRepository.deleteByMonsterDexId(id)).thenReturn(Mono.empty());
    Mockito.when(monsterDexTypeRepository.deleteByMonsterDexId(id)).thenReturn(Mono.empty());

    Mockito.when(operator.transactional(ArgumentMatchers.any(Mono.class)))
        .thenAnswer(i -> i.getArgument(0));

    Mono<BaseResponse<Boolean>> mono = monsterDexAdminService.deleteMonsterDex(id);
    StepVerifier.create(mono)
        .assertNext(response -> {
          Assertions.assertEquals(ResponseType.SUCCESS.getHttpStatus(), response.getHttpStatus());
          Assertions.assertEquals(ResponseType.SUCCESS.getMessage(), response.getMessage());
        })
        .expectComplete()
        .verify();

    Mockito.verify(monsterDexRepository).deleteById(id);
    Mockito.verify(monsterDexStatRepository).deleteByMonsterDexId(id);
    Mockito.verify(monsterDexTypeRepository).deleteByMonsterDexId(id);
    Mockito.verify(operator).transactional(ArgumentMatchers.any(Mono.class));
  }

  @SuppressWarnings("unchecked")
  @DisplayName("Failed Test: deleteMonsterDex() - When Completed")
  @Test
  void givenTestFailedDeleteMonsterDex(){
    final long id = 1L;

    Mockito.when(monsterDexRepository.deleteById(id)).thenReturn(Mono.empty());
    Mockito.when(monsterDexStatRepository.deleteByMonsterDexId(id)).thenReturn(Mono.empty());
    Mockito.when(monsterDexTypeRepository.deleteByMonsterDexId(id))
        .thenThrow(new RuntimeException());

    Mockito.when(operator.transactional(ArgumentMatchers.any(Mono.class)))
        .thenAnswer(i -> i.getArgument(0));

    Mono<BaseResponse<Boolean>> mono = monsterDexAdminService.deleteMonsterDex(id);
    StepVerifier.create(mono)
        .assertNext(response -> {
          Assertions.assertEquals(ResponseType.INVALID.getHttpStatus(), response.getHttpStatus());
          Assertions.assertEquals(ResponseType.INVALID.getMessage(), response.getMessage());
        })
        .expectComplete()
        .verify();

    Mockito.verify(monsterDexRepository).deleteById(id);
    Mockito.verify(monsterDexStatRepository).deleteByMonsterDexId(id);
    Mockito.verify(monsterDexTypeRepository).deleteByMonsterDexId(id);
    Mockito.verify(operator).transactional(ArgumentMatchers.any(Mono.class));
  }

  private MonsterDexPagingRequest getMonsterDexPagingRequest(){
    return MonsterDexPagingRequest.builder().build();
  }

  private PagingDto<MonsterDexPagingResponse> buildResponsePaging(){
    return new PagingDto<>(0, 10, new ArrayList<>());
  }

  private MonsterDexRequest getMonsterDexRequest(){
    return MonsterDexRequest.builder()
        .name("Chikorita")
        .subName("Leaf Monster")
        .height(5.1)
        .weight(216)
        .image("http://localhost/iamge.png")
        .description("description for monster dex")
        .monsterDexStats(Collections.singletonList(getMonsterDexStat()))
        .monsterDexTypes(Collections.singletonList(getMonsterDexType()))
        .build();
  }

  private MonsterDex getMonsterDex(MonsterDexRequest request){
    return MonsterDex.builder()
        .name(request.getName())
        .subName(request.getSubName())
        .height(request.getHeight())
        .weight(request.getWeight())
        .image(request.getImage())
        .description(request.getDescription())
        .enabled(true)
        .createdAt(0)
        .updatedAt(0)
        .deletedAt(0)
        .build();
  }

  private MonsterDexStat getMonsterDexStat(){
    return MonsterDexStat.builder()
        .monsterStatId(1L)
        .amount(100)
        .build();
  }

  private MonsterDexType getMonsterDexType(){
    return MonsterDexType.builder()
        .monsterTypeId(3)
        .build();
  }


}