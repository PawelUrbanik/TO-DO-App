package pl.pawel.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pawel.model.TaskGroups;
import pl.pawel.repository.TaskGroupRepository;
import pl.pawel.repository.TaskRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskGroupServiceTest {


    @Mock
    private TaskGroupRepository taskGroupRepository;
    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private TaskGroupService taskGroupService;

    @Nested
    class ToogleGroup {

        @Test
        @DisplayName("Should return illegalstateexception when group contains udnode tasks")
        void toogleGroup_shouldReturnIllegalStateException() {
            //given
            when(taskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(true);

            //when
            Throwable exception = catchThrowable(() -> taskGroupService.toogleGroup(0));
            //then
            assertThat(exception)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("Grupa ma niezakończone taski");
        }

        @Test
        @DisplayName("Should return IllegalArgumentException when task not exist")
        void toogleGroup_shouldReturnIllegalArgumentExceptionn() {
            //when
            Throwable exception = catchThrowable(() -> taskGroupService.toogleGroup(0));
            //then
            assertThat(exception)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Nie znaleziono taska z takim id");
        }

        @Test
        @DisplayName("Should toggle group correctly")
        void toogleGroup_shouldToggleGroup() {
            //given
            TaskGroups taskGroup = new TaskGroups();
            when(taskGroupRepository.findById(anyInt())).thenReturn(Optional.of(taskGroup));
            //then
            assertAll(
                    () -> assertFalse(taskGroup.isDone()),
                    () -> {
                        taskGroupService.toogleGroup(0);
                        assertTrue(taskGroup.isDone());
                    }
            );
        }
    }
}