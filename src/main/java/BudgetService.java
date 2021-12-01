import java.time.LocalDate;

public class BudgetService {

    private final BudgetRepo repo;

    public BudgetService(BudgetRepo repo) {
        this.repo = repo;
    }

    public double query(LocalDate start, LocalDate end) {
        return 0;
    }
}
