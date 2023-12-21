package pl.pawel.model.projection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.pawel.model.Task;
import pl.pawel.model.TaskGroups;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GroupReadModelTest {

    @Test
    @DisplayName("should create null deadline for group where no task dedlines")
    void should_create_null_deadline() {

        //given
        var source = new TaskGroups();

        source.setId(1L);
        source.setDescription("Foo");
        source.setTasks(Set.of(new Task("desc", null)));

        //when
        var result = new GroupReadModel(source);

        //then
        assertThat(result).hasFieldOrPropertyWithValue("deadline", null);
    }

}