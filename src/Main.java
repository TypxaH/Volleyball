import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String playersFile = "Players.csv", teamsFile = "Teams.csv";
        List<VolleyballTeam> volleyballTeams = readVolleyballTeams(teamsFile);
        List<VolleyballPlayer> volleyballPlayers = readVolleyballPlayers(playersFile);
        VolleyballTeam team1, team2;
        try {
            //Добавяне на първите три играчиа към отбор 1
            team1 = volleyballTeams.get(0);
            for (int i = 0; i < 3; i++) {
                team1.players.add(volleyballPlayers.get(i));
            }
            //Добавяне на следващите три играча към отбор 2
            team2 = volleyballTeams.get(1);
            for (int i = 3; i < 6; i++) {
                team2.players.add(volleyballPlayers.get(i));
            }
        } catch (Exception e) {
            return;
        }
        printTeams(volleyballTeams);
        //Симулация със 10 мача между двата отбора
        for (int i = 0; i < 10; i++) {
            playMatch(team1, team2);
        }
        //Три дена пауза
        team1.teamRest();
        team2.teamTraining();
        team1.teamRest();
        team2.teamTraining();
        team1.teamTraining();
        team2.teamRest();
        //Симулация със 10 мача между двата отбора след пауза от три дни
        System.out.println("-----------------NEW SEASON--------------------");
        for (int i = 0; i < 10; i++) {
            playMatch(team1, team2);
        }
        //Записване новото състояние на играчите (с обновени показатели за умения и
        //кондиция)
        try {
            savePlayers(volleyballPlayers, playersFile);
        } catch (Exception e) {
            System.out.println("Can't write to file " + playersFile);
        }
        printTeams(volleyballTeams);
    }

    public static ArrayList<VolleyballTeam> readVolleyballTeams(String fileName) {
        try {
            ArrayList<VolleyballTeam> volleyballTeams = new ArrayList<>();
            File pl = new File(fileName);
            Scanner fileReader = new Scanner(pl, "windows-1251");
            while (fileReader.hasNextLine()) {
                String[] line = fileReader.nextLine().split(",");
                VolleyballTeam team = new VolleyballTeam(line[0], line[1], line[2], new HashSet<>());
                volleyballTeams.add(team);
            }
            fileReader.close();
            return volleyballTeams;
        } catch (Exception e) {
            System.out.println("Teams file not found!");
            return null;
        }
    }

    public static ArrayList<VolleyballPlayer> readVolleyballPlayers(String fileName) {
        try {
            ArrayList<VolleyballPlayer> volleyballPlayers = new ArrayList<>();
            File pl = new File(fileName);
            Scanner fileReader = new Scanner(pl, "windows-1251");
            while (fileReader.hasNextLine()) {
                String[] line = fileReader.nextLine().split(",");
                VolleyballPlayer player = new VolleyballPlayer(line[0], Integer.parseInt(line[1]), line[2], Byte.parseByte(line[3]), Byte.parseByte(line[4]));
                volleyballPlayers.add(player);
            }
            fileReader.close();
            return volleyballPlayers;
        } catch (Exception e) {
            System.out.println("Players file not found!");
            return null;
        }
    }

    private static void printTeams(List<VolleyballTeam> volleyballTeams) {
        for (VolleyballTeam team : volleyballTeams) {
            System.out.println(team);
        }
    }

    //Направете метод playMatch(team1, team2),
    // който определя резултат от мач на база общите умения на отборите
    // и произволно число (от 1 до 25): team1.strength + random1
    // VS team2.strength + random2
    public static void playMatch(VolleyballTeam team1, VolleyballTeam team2) {
        int strength1 = team1.calculateStrength();
        int strength2 = team2.calculateStrength();
        Random rnd = new Random();
        int r1 = rnd.nextInt(1, 26);
        int r2 = rnd.nextInt(1, 26);
        int points1 = strength1 + r1;
        int points2 = strength2 + r2;

        try {
            printResult(team1.name, team2.name, points1, points2);
        } catch (Exception e) {
            System.out.println("File not found!");
        }
    }

    //Печата резултатите на конзолата и във файл едновременно, което мисля не е ОК
    public static void printResult(String team1Name, String team2Name, int p1, int p2) throws FileNotFoundException {
        PrintStream fileWriter = new PrintStream(new FileOutputStream("results.txt", true));
        String result;
        if (p1 > p2) {
            result = String.format("%s beats %s with score %d:%d\n", team1Name, team2Name, p1, p2);
        } else if (p1 < p2) {
            result = String.format("%s beats %s with score %d:%d\n", team2Name, team1Name, p2, p1);
        } else {
            result = String.format("Teams %s and %s draws with score %d:%d\n", team1Name, team2Name, p1, p2);
        }
        System.out.printf(result);
        fileWriter.printf(result);
        fileWriter.close();
    }

    private static void savePlayers(List<VolleyballPlayer> volleyballPlayers, String playersFile) throws FileNotFoundException {
        PrintStream fileWriter = new PrintStream(playersFile);
        for (VolleyballPlayer player : volleyballPlayers) {
            fileWriter.println(player.name + "," + player.age + "," + player.position + "," + player.skills + "," + player.condition);
        }
        fileWriter.close();
    }
}