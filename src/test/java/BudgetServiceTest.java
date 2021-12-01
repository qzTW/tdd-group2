import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BudgetServiceTest {

    private BudgetRepo mockRepo;
    private BudgetService budgetService;

    @BeforeEach
    void setUp() {
        mockRepo = Mockito.mock(BudgetRepo.class);
        budgetService = new BudgetService(mockRepo);
    }

    @Test
    void test_start_big_than_end() {
        LocalDate start = LocalDate.of(2020, 3, 2);
        LocalDate end = LocalDate.of(2020, 3, 1);
        assertEquals(0, budgetService.query(start, end));
    }

    @Test
    void test_no_month_budget() {
        when(mockRepo.getAll()).thenReturn(Arrays.asList(
                new Budget("202004", 3000),
                new Budget("202006", 3000)
        ));
        LocalDate start = LocalDate.of(2020, 5, 1);
        LocalDate end = LocalDate.of(2020, 5, 31);
        assertEquals(0, budgetService.query(start, end));
    }

    @Test
    void test_one_month_budget() {
        when(mockRepo.getAll()).thenReturn(Arrays.asList(
                new Budget("202004", 3000)
        ));
        LocalDate start = LocalDate.of(2020, 4, 1);
        LocalDate end = LocalDate.of(2020, 4, 30);
        assertEquals(3000.00, budgetService.query(start, end));
    }

    @Test
    void test_partial_month_budget() {
        when(mockRepo.getAll()).thenReturn(Arrays.asList(
                new Budget("202004", 3000)
        ));
        LocalDate start = LocalDate.of(2020, 4, 10);
        LocalDate end = LocalDate.of(2020, 4, 15);
        assertEquals(600.00, budgetService.query(start, end));
    }

    @Test
    void test_cross_month_budget() {
        when(mockRepo.getAll()).thenReturn(Arrays.asList(
                new Budget("202004", 3000),
                new Budget("202006", 30),
                new Budget("202007", 310),
                new Budget("202008", 62)
        ));
        LocalDate start = LocalDate.of(2020, 4, 29);
        LocalDate end = LocalDate.of(2020, 8, 2);
        assertEquals(544.00, budgetService.query(start, end));
    }

    @Test
    void test_zero_month_budget() {
        when(mockRepo.getAll()).thenReturn(Arrays.asList(
                new Budget("202004", 0)
        ));
        LocalDate start = LocalDate.of(2020, 4, 29);
        LocalDate end = LocalDate.of(2020, 4, 30);
        assertEquals(0, budgetService.query(start, end));
    }
}