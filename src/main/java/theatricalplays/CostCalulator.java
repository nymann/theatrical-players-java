package theatricalplays;

import theatricalplays.Invoice;
import theatricalplays.Performance;
import theatricalplays.Play;

import java.util.Map;

public class CostCalulator {
    public static int getTotalAmount(Invoice invoice, Map<String, Play> plays) {
        int totalAmount = 0;
        for (var perf : invoice.performances) {
            var play = plays.get(perf.playID);
            var cost = costOfPerformance(perf, play);
            totalAmount += cost;
        }
        return totalAmount;
    }

    public static int getVolumeCredits(Invoice invoice, Map<String, Play> plays) {
        int volumeCredits = 0;
        for (var perf : invoice.performances) {
            var play = plays.get(perf.playID);
            volumeCredits += Math.max(perf.audience - 30, 0);
            if ("comedy".equals(play.type)) volumeCredits += (int) (double) (perf.audience / 5);
        }
        return volumeCredits;
    }

    public static int costOfPerformance(Performance perf, Play play) {
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
