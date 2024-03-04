package theatricalplays;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class HTMLStatementPrinter {
    private static String printInvoice(Invoice invoice, Map<String, Play> plays, int totalAmount, int volumeCredits) {
        StringBuilder result = new StringBuilder("<html>");
        result.append("<body>");
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        result.append("<ul>");
        for (var perf : invoice.performances) {
            var play = plays.get(perf.playID);
            var cost = CostCalulator.costOfPerformance(perf, play);
            result.append(String.format("<li>%s: %s (%s seats)</li>", play.name, format.format(cost / 100), perf.audience));
        }
        result.append("</ul>");
        result.append(String.format("<p>Amount owed is %s</p>", format.format(totalAmount / 100)));
        result.append(String.format("<p>You earned %s credits</p>", volumeCredits));
        result.append("</body>");
        result.append("</html>");
        return result.toString();
    }

    public String print(Invoice invoice, Map<String, Play> plays) {
        var totalAmount = CostCalulator.getTotalAmount(invoice, plays);
        var volumeCredits = CostCalulator.getVolumeCredits(invoice, plays);

        return printInvoice(invoice, plays, totalAmount, volumeCredits);
    }
}
