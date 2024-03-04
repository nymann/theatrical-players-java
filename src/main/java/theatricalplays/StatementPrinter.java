package theatricalplays;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class StatementPrinter {

    public String print(Invoice invoice, Map<String, Play> plays) {
        var totalAmount = CostCalulator.getTotalAmount(invoice, plays);
        var volumeCredits = CostCalulator.getVolumeCredits(invoice, plays);

        return printInvoice(invoice, plays, totalAmount, volumeCredits);
    }

    private static String printInvoice(Invoice invoice, Map<String, Play> plays, int totalAmount, int volumeCredits) {
        StringBuilder result = new StringBuilder(String.format("Statement for %s\n", invoice.customer));
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        for (var perf : invoice.performances) {
            var play = plays.get(perf.playID);
            var cost = CostCalulator.costOfPerformance(perf, play);
            result.append(String.format("  %s: %s (%s seats)\n", play.name, format.format(cost / 100), perf.audience));
        }
        result.append(String.format("Amount owed is %s\n", format.format(totalAmount / 100)));
        result.append(String.format("You earned %s credits\n", volumeCredits));
        return result.toString();
    }

}