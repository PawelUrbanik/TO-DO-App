package pl.pawel.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pl.pawel.TaskConfigurationProperties;
import pl.pawel.model.Project;
import pl.pawel.model.ProjectSteps;
import pl.pawel.model.TaskGroups;
import pl.pawel.model.projection.GroupReadModel;
import pl.pawel.repository.ProjectRepository;
import pl.pawel.repository.TaskGroupRepository;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectServiceTest {


    @Nested
    class CreateGroup {

        @Test
        @DisplayName("Should throw illegal state exception when configured to allow just 1 group and other undone group exist")
        void createGroup__throwsIllegalStateException() {
            //GIVEN
            TaskGroupRepository taskGroupRepository = getTaskGroupRepository(true);
            //and
            TaskConfigurationProperties mockConfig = configurationTemplateAndTaskConfig(false);

            //WHEN
            var toTest = new ProjectService(null, taskGroupRepository, null, mockConfig);
            var exception = catchThrowable(() -> toTest.createGroup(1, LocalDateTime.now()));


            // Then
            assertThat(exception)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("Only one undone task from project is allowed");
//        assertThat -> toTest.createGroup(1, LocalDateTime.now())).isInstanceOf(IllegalStateException.class);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when configuration ok and project with given id not exist")
        void createGroup__throwsIllegalArgumentException() {
            //given
            var mockConfig = configurationTemplateAndTaskConfig(true);
            //And
            ProjectRepository projectRepository = getProjectRepository(Optional.empty());


            //when
            var toTest = new ProjectService(projectRepository, null, null, mockConfig);
            var exception = catchThrowable(() -> toTest.createGroup(0, LocalDateTime.now()));

            //Then
            assertThat(exception)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Project with given id not found");
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when not allowed multiple undone tasks and no undone tasks and no project with gived id")
        void createGroup__throwsIllegalArgumentExceptionWhenConfigurationOkAndNoProjectExistWithGivenId() {
            //Given
            var taskGroprepository = getTaskGroupRepository(false);
            var taskGroupConfiguration = configurationTemplateAndTaskConfig(false);
            var projectRepository = getProjectRepository(Optional.empty());

            //when
            var toTest = new ProjectService(projectRepository, taskGroprepository, null, taskGroupConfiguration);
            var exception = catchThrowable(() -> toTest.createGroup(0, LocalDateTime.now()));

            //Then
            assertThat(exception)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Project with given id not found");
        }

        @Test
        @DisplayName("should create new group from project")
        void crateGroup__configurationOkAndExisitingProjectCreatesAndSavesNewGroup() {
            //given
            LocalDateTime today = LocalDate.now().atStartOfDay();
            ProjectRepository projectRepository = mock(ProjectRepository.class);
            final Project project = projectWith("Opis", Set.of(-1, -2));
            when(projectRepository.findById(anyInt()))
                    .thenReturn(
                            Optional.of(
                                    project));
            TaskConfigurationProperties taskConfigurationProperties = configurationTemplateAndTaskConfig(true);
            InMemoryTaskGroupRepository inMemoryGroupRepository = inMemoryTaskGroupRepository();
            TaskGroupService serviceWithInMemoRepo = dummyGroupService(inMemoryGroupRepository);
            var toTest = new ProjectService(projectRepository, inMemoryGroupRepository, serviceWithInMemoRepo, taskConfigurationProperties);
            int countBeforeCall = inMemoryGroupRepository.count();
            //when
            GroupReadModel result = toTest.createGroup(1, today);

            //than
            assertAll(
                    ()-> assertThat(countBeforeCall).isEqualTo(inMemoryGroupRepository.count() - 1),
                    () -> assertEquals(result.getDescription(), "Opis"),
                    () -> assertTrue(result.getTasks().stream().allMatch(task -> task.getDescriiption().equals("foo")))
            );
        }
    }

    private static TaskGroupService dummyGroupService(InMemoryTaskGroupRepository inMemoryGroupRepository) {
        return new TaskGroupService(inMemoryGroupRepository, null);
    }

    private Project projectWith(String description, Set<Integer> daysToDeadline) {
        Project project = mock(Project.class);
        when(project.getDescription()).thenReturn(description);
        final Set<ProjectSteps> projectSteps = daysToDeadline.stream()
                .map(days -> {
                    ProjectSteps step = mock(ProjectSteps.class);
                    when(step.getDescription()).thenReturn("foo");
                    when(step.getDaysToDeadline()).thenReturn(days);
                    return step;
                })
                .collect(Collectors.toSet());
        when(project.getSteps()).thenReturn(
                projectSteps
        );
        return project;
    }

    private TaskConfigurationProperties configurationTemplateAndTaskConfig(final boolean restult) {
        var mockcTemplate = mock(TaskConfigurationProperties.Template.class);
        when(mockcTemplate.isAllowMultipleTasks()).thenReturn(restult);

        var mockConfig = mock(TaskConfigurationProperties.class);
        when(mockConfig.getTemplte()).thenReturn(mockcTemplate);
        return mockConfig;
    }

    private TaskGroupRepository getTaskGroupRepository(boolean result) {
        var taskGroupRepository = mock(TaskGroupRepository.class);
        when(taskGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(result);
        return taskGroupRepository;
    }


    private ProjectRepository getProjectRepository(final Optional optional) {
        var projectRepository = mock(ProjectRepository.class);
        when(projectRepository.findById(anyInt())).thenReturn(optional);
        return projectRepository;
    }

    private InMemoryTaskGroupRepository inMemoryTaskGroupRepository() {
        return new InMemoryTaskGroupRepository();
    }

    private static class InMemoryTaskGroupRepository implements TaskGroupRepository {
        Map<Integer, TaskGroups> taskGroupsMap = new HashMap<>();
        private int index = 0;

        public int count() {
            return taskGroupsMap.values().size();
        }

        @Override
        public List<TaskGroups> findAll() {
            return new ArrayList<>(taskGroupsMap.values());
        }

        @Override
        public Optional<TaskGroups> findById(Long id) {
            return Optional.ofNullable(taskGroupsMap.get(id));
        }

        @Override
        public TaskGroups save(final TaskGroups taskGroups) {
            if (taskGroups.getId() == 0) {
                try {
                    final Field field = TaskGroups.class.getDeclaredField("id");
                    field.setAccessible(true);
                    field.set(taskGroups, ++index);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
            }
            taskGroupsMap.put(index, taskGroups);
            return taskGroups;
        }

        @Override
        public boolean existsByDoneIsFalseAndProject_Id(Integer projectId) {
            return taskGroupsMap.values().stream()
                    .filter(taskGroups -> !taskGroups.isDone())
                    .anyMatch(taskGroup -> taskGroup.getProject() != null && taskGroup.getProject().getId() == projectId);
        }
    }

    ;
}