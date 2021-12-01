import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.Mockito.when;

class BudgetServiceTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void query() {
        BudgetRepo mockRepo = Mockito.mock(BudgetRepo.class);
        when(mockRepo.getAll()).thenReturn(Arrays.asList(
                new Budget("202003", 3100)
        ));

        BudgetService budgetService = new BudgetService(mockRepo);
        Assertions.assertEquals(200, budgetService.query(LocalDate.of(2020, 03, 01), LocalDate.of(2020, 03, 02)));
    }
}