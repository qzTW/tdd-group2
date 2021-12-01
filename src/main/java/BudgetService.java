import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BudgetService {

    private final BudgetRepo repo;

    DateTimeFormatter yyyyMM = DateTimeFormatter.ofPattern("yyyyMM");

    public BudgetService(BudgetRepo repo) {
        this.repo = repo;
    }

    public double query(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            return 0.0;
        }

        double budget = 0;

        List<Budget> allBudget = repo.getAll();
        for (LocalDate date = start; date.isBefore(end.plusDays(1)); date = date.plusDays(1)) {
            String key = date.format(yyyyMM);
            int monthBudget = getMonthBudget(key, allBudget);
            if (monthBudget == 0) {
                continue;
            }

            int lengthOfMonth = YearMonth.of(date.getYear(), date.getMonth()).lengthOfMonth();
            budget += (double) monthBudget / lengthOfMonth;
        }

        return budget;
    }

    private int getMonthBudget(String key, List<Budget> allBudget) {
        for (Budget budget : allBudget) {
            if (budget.getYearMonth().equals(key)) {
                return budget.getAmount();
            }
        }
        return 0;
    }
}
