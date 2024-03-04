package theatricalplays;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class StatementPrinter {

    public String print(Invoice invoice, Map<String, Play> plays) {
        var totalAmount = 0;
        var volumeCredits = 0;
        StringBuilder result = new StringBuilder(String.format("Statement for %s\n", invoice.customer));

        NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);

        for (var perf : invoice.performances) {
            var play = plays.get(perf.playID);
            var cost = costOfPerformance(perf, play);
            volumeCredits = getVolumeCredits(perf, volumeCredits, play);

            totalAmount += cost;
        }
        for (var perf : invoice.performances) {
            var play = plays.get(perf.playID);
            var cost = costOfPerformance(perf, play);
            result.append(String.format("  %s: %s (%s seats)\n", play.name, frmt.format(cost / 100), perf.audience));
        }

        result.append(String.format("Amount owed is %s\n", frmt.format(totalAmount / 100)));
        result.append(String.format("You earned %s credits\n", volumeCredits));
        return result.toString();
    }

    private static int getVolumeCredits(Performance perf, int volumeCredits, Play play) {
        volumeCredits += Math.max(perf.audience - 30, 0);
        if ("comedy".equals(play.type)) volumeCredits += (int) (double) (perf.audience / 5);
        return volumeCredits;
    }

    private static int costOfPerformance(Performance perf, Play play) {
        var thisAmount = 0;

        switch (play.type) {
            case "tragedy":
                thisAmount = 40000;
                if (perf.audience > 30) {
                    thisAmount += 1000 * (perf.audience - 30);
                }
                break;
            case "comedy":
                thisAmount = 30000;
                if (perf.audience > 20) {
                    thisAmount += 10000 + 500 * (perf.audience - 20);
                }
                thisAmount += 300 * perf.audience;
                break;
            default:
                throw new Error("unknown type: ${play.type}");
        }
        return thisAmount;
    }

}
