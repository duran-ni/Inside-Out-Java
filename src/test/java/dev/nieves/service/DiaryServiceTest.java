package dev.nieves.service;

import dev.nieves.model.Emotion;
import dev.nieves.model.Moment;
import dev.nieves.repository.InMemoryDiaryRepository;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests unitarios de la lógica de negocio de {@link DiaryService}.
 */
class DiaryServiceTest {

    private static final LocalDate SAMPLE_DATE = LocalDate.of(2026, Month.JANUARY, 15);
    private static final int NON_EXISTING_ID = 999;

    private InterfaceDiaryService service;

    @BeforeEach
    void setUp() {
        InMemoryDiaryRepository repository = new InMemoryDiaryRepository();
        service = new DiaryService(repository, repository);
    }

    @Test
    void addMoment_withValidData_shouldReturnMomentWithId() {
        Moment result = service.addMoment("First day", "It was a great day", Emotion.JOY, SAMPLE_DATE);

        assertThat(result.getId(), is(notNullValue()));
        assertThat(result.getTitle(), is("First day"));
        assertThat(result.getEmotion(), is(Emotion.JOY));
    }

    @Test
    void addMoment_withBlankTitle_shouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> service.addMoment("   ", "description", Emotion.JOY, SAMPLE_DATE));
    }

    @Test
    void addMoment_withNullEmotion_shouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> service.addMoment("Title", "description", null, SAMPLE_DATE));
    }

    @Test
    void listMoments_withNoMomentsAdded_shouldReturnEmptyList() {
        List<Moment> result = service.listMoments();

        assertThat(result, is(empty()));
    }

    @Test
    void listMoments_withMomentsAdded_shouldReturnAllOfThem() {
        service.addMoment("First day", "It was a great day", Emotion.JOY, SAMPLE_DATE);
        service.addMoment("Hard day", "Struggled a lot today", Emotion.SADNESS, SAMPLE_DATE);

        List<Moment> result = service.listMoments();

        assertThat(result, hasSize(2));
    }

    @Test
    void deleteMoment_withExistingId_shouldRemoveIt() {
        Moment added = service.addMoment("First day", "It was a great day", Emotion.JOY, SAMPLE_DATE);

        service.deleteMoment(added.getId());

        assertThat(service.listMoments(), is(empty()));
    }

    @Test
    void deleteMoment_withNonExistingId_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> service.deleteMoment(NON_EXISTING_ID));
    }

    @Test
    void deleteMoment_withNullId_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> service.deleteMoment(null));
    }

    @Test
    void updateMoment_withExistingId_shouldUpdateFields() {
        Moment added = service.addMoment("First day", "It was a great day", Emotion.JOY, SAMPLE_DATE);

        Moment updated = service.updateMoment(added.getId(), "Updated title", "Updated description",
                Emotion.NOSTALGIA, SAMPLE_DATE);

        assertThat(updated.getId(), is(added.getId()));
        assertThat(updated.getTitle(), is("Updated title"));
        assertThat(updated.getEmotion(), is(Emotion.NOSTALGIA));
        assertThat(updated.getCreatedAt(), is(added.getCreatedAt()));
    }

    @Test
    void updateMoment_withNonExistingId_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                service.updateMoment(NON_EXISTING_ID, "Title", "Description", Emotion.JOY, SAMPLE_DATE));
    }

    @Test
    void updateMoment_withNullId_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                service.updateMoment(null, "Title", "Description", Emotion.JOY, SAMPLE_DATE));
    }

    @Test
    void updateMoment_withBlankTitle_shouldThrowException() {
        Moment added = service.addMoment("First day", "It was a great day", Emotion.JOY, SAMPLE_DATE);

        assertThrows(IllegalArgumentException.class, () ->
                service.updateMoment(added.getId(), "   ", "Description", Emotion.JOY, SAMPLE_DATE));
    }

    @Test
    void getMomentsByEmotion_withMatchingMoments_shouldReturnOnlyThose() {
        service.addMoment("First day", "It was a great day", Emotion.JOY, SAMPLE_DATE);
        service.addMoment("Hard day", "Struggled a lot today", Emotion.SADNESS, SAMPLE_DATE);
        service.addMoment("Another good day", "Nice weather", Emotion.JOY, SAMPLE_DATE);

        List<Moment> result = service.getMomentsByEmotion(Emotion.JOY);

        assertThat(result, hasSize(2));
    }

    @Test
    void getMomentsByEmotion_withNoMatchingMoments_shouldReturnEmptyList() {
        service.addMoment("Hard day", "Struggled a lot today", Emotion.SADNESS, SAMPLE_DATE);

        List<Moment> result = service.getMomentsByEmotion(Emotion.JOY);

        assertThat(result, is(empty()));
    }

    @Test
    void getMomentsByEmotion_withNullEmotion_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> service.getMomentsByEmotion(null));
    }
}
