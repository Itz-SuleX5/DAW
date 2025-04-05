import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Dashboard {

    private String dashboardName;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private BigDecimal totalIncome;
    private BigDecimal totalExpenses;
    private BigDecimal totalInvestment;
    private BigDecimal totalSavings;
    private BigDecimal netBalance;
    private List<CategorySummary> categorySummaries;
    private List<AccountSummary> accountSummaries;

     // Constructor
public DashboardDTO(String dashboardName, LocalDate periodStart, LocalDate periodEnd,
BigDecimal totalIncome, BigDecimal totalExpenses, BigDecimal totalInvestment,
BigDecimal totalSavings, BigDecimal netBalance,
List<CategorySummary> categorySummaries, List<AccountSummary> accountSummaries) {
this.dashboardName = dashboardName;
this.periodStart = periodStart;
this.periodEnd = periodEnd;
this.totalIncome = totalIncome;
this.totalExpenses = totalExpenses;
this.totalInvestment = totalInvestment;
this.totalSavings = totalSavings;
this.netBalance = netBalance;
this.categorySummaries = categorySummaries;
this.accountSummaries = accountSummaries;
}

// Getters y Setters
public String getDashboardName() { return dashboardName; }
public void setDashboardName(String dashboardName) { this.dashboardName = dashboardName; }

public LocalDate getPeriodStart() { return periodStart; }
public void setPeriodStart(LocalDate periodStart) { this.periodStart = periodStart; }

public LocalDate getPeriodEnd() { return periodEnd; }
public void setPeriodEnd(LocalDate periodEnd) { this.periodEnd = periodEnd; }

public BigDecimal getTotalIncome() { return totalIncome; }
public void setTotalIncome(BigDecimal totalIncome) { this.totalIncome = totalIncome; }

public BigDecimal getTotalExpenses() { return totalExpenses; }
public void setTotalExpenses(BigDecimal totalExpenses) { this.totalExpenses = totalExpenses; }

public BigDecimal getTotalInvestment() { return totalInvestment; }
public void setTotalInvestment(BigDecimal totalInvestment) { this.totalInvestment = totalInvestment; }

public BigDecimal getTotalSavings() { return totalSavings; }
public void setTotalSavings(BigDecimal totalSavings) { this.totalSavings = totalSavings; }

public BigDecimal getNetBalance() { return netBalance; }
public void setNetBalance(BigDecimal netBalance) { this.netBalance = netBalance; }

public List<CategorySummary> getCategorySummaries() { return categorySummaries; }
public void setCategorySummaries(List<CategorySummary> categorySummaries) { this.categorySummaries = categorySummaries; }

public List<AccountSummary> getAccountSummaries() { return accountSummaries; }
public void setAccountSummaries(List<AccountSummary> accountSummaries) { this.accountSummaries = accountSummaries; }

}