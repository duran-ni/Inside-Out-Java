package dev.nieves.controller;

import dev.nieves.export.CsvMomentExporter;
import dev.nieves.model.Emotion;
import dev.nieves.model.Moment;
import dev.nieves.model.MonthYear;
import dev.nieves.repository.InMemoryDiaryRepository;
import dev.nieves.service.DiaryService;
import dev.nieves.service.InterfaceDiaryService;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Tests unitarios de {@link MomentController}.
 */
class MomentControllerTest {

    private static final LocalDate SAMPLE_DATE = LocalDate.of(2026, Month.JANUARY, 15);

    private MomentController controller;

    @BeforeEach
    void setUp(@TempDir Path tempDir) {
        InMemoryDiaryRepository repository = new InMemoryDiaryRepository();
        CsvMomentExporter exporter = new CsvMomentExporter(tempDir);
        InterfaceDiaryService diaryService = new DiaryService(repository, repository, exporter);
        controller = new MomentController(diaryService);
    }

    @Test
    void addMoment_shouldDelegateToService() {
        Moment result = controller.addMoment("Title", "Description", Emotion.JOY, SAMPLE_DATE);

        assertThat(result.getId(), is(notNullValue()));
    }

    @Test
    void listMoments_shouldDelegateToService() {
        controller.addMoment("Title", "Description", Emotion.JOY, SAMPLE_DATE);

        List<Moment> result = controller.listMoments();

        assertThat(result, hasSize(1));
    }

    @Test
    void getMomentById_shouldDelegateToService() {
        Moment added = controller.addMoment("Title", "Description", Emotion.JOY, SAMPLE_DATE);

        Moment result = controller.getMomentById(added.getId());

        assertThat(result.getId(), is(added.getId()));
    }

    @Test
    void deleteMoment_shouldDelegateToService() {
        Moment added = controller.addMoment("Title", "Description", Emotion.JOY, SAMPLE_DATE);

        controller.deleteMoment(added.getId());

        assertThat(controller.listMoments(), hasSize(0));
    }

    @Test
    void updateMoment_shouldDelegateToService() {
        Moment added = controller.addMoment("Title", "Description", Emotion.JOY, SAMPLE_DATE);

        Moment updated = controller.updateMoment(added.getId(), "New title", "New description",
                Emotion.SADNESS, SAMPLE_DATE);

        assertThat(updated.getTitle(), is("New title"));
    }

    @Test
    void getMomentsByEmotion_shouldDelegateToService() {
        controller.addMoment("Title", "Description", Emotion.JOY, SAMPLE_DATE);

        List<Moment> result = controller.getMomentsByEmotion(Emotion.JOY);

        assertThat(result, hasSize(1));
    }

    @Test
    void getMomentsByMonth_shouldDelegateToService() {
        controller.addMoment("Title", "Description", Emotion.JOY, SAMPLE_DATE);
        MonthYear monthYear = new MonthYear(SAMPLE_DATE.getMonthValue(), SAMPLE_DATE.getYear());

        List<Moment> result = controller.getMomentsByMonth(monthYear);

        assertThat(result, hasSize(1));
    }

    @Test
    void exportToCsv_shouldDelegateToService() {
        controller.addMoment("Title", "Description", Emotion.JOY, SAMPLE_DATE);

        String filePath = controller.exportToCsv();

        assertThat(filePath, is(notNullValue()));
    }
}
