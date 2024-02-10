import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RankTeams {
    private static Map<String, TeamStats> teamStatsMap = new HashMap<>();

    public static void main(String[] args) {
        File file = new File("/Users/mt25190/GitHub/csvFormatter/VictiScout_2-8-24.csv");

        try (Scanner scanner = new Scanner(file)) {
            // Skip header
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");

                String team = data[0];
                int teleopNotesMade = data[9].isEmpty() ? 0 : Integer.parseInt(data[9]);
                int teleopNotesInAmp = data[10].isEmpty() ? 0 : Integer.parseInt(data[10]);

                TeamStats teamStats = teamStatsMap.getOrDefault(team, new TeamStats());
                teamStats.addTeleopNotesMade(teleopNotesMade);
                teamStats.addTeleopNotesInAmp(teleopNotesInAmp);
                teamStatsMap.put(team, teamStats);
            }

            System.out.println("Team, Average Teleop Notes Made, Average Teleop Notes In Amp");
            for (Map.Entry<String, TeamStats> entry : teamStatsMap.entrySet()) {
                String team = entry.getKey();
                TeamStats teamStats = entry.getValue();
                System.out.println(team + ", " + teamStats.calculateAverageTeleopNotesMade() + ", " + teamStats.calculateAverageTeleopNotesInAmp());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error! File not found: " + e.getMessage());
        }
    }

    private static class TeamStats {
        private int totalTeleopNotesMade = 0;
        private int totalTeleopNotesInAmp = 0;
        private int count = 0;

        public void addTeleopNotesMade(int teleopNotesMade) {
            totalTeleopNotesMade += teleopNotesMade;
            count++;
        }

        public void addTeleopNotesInAmp(int teleopNotesInAmp) {
            totalTeleopNotesInAmp += teleopNotesInAmp;
        }

        public double calculateAverageTeleopNotesMade() {
            return count > 0 ? (double) totalTeleopNotesMade / count : 0;
        }

        public double calculateAverageTeleopNotesInAmp() {
            return count > 0 ? (double) totalTeleopNotesInAmp / count : 0;
        }
    }
}
