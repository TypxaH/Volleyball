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
        VolleyballTeam team1,team2;
        //����� 2 ������ � �� 3-�� ������
        if (volleyballPlayers.size() >= 6 && volleyballTeams.size() >= 2) {
            //�������� �� ������� ��� ������� ��� ����� 1
            team1 = getVolleyballTeamPlayers(volleyballTeams, volleyballPlayers,1,3);
            //�������� �� ���������� ��� ������ ��� ����� 2
            team2 = getVolleyballTeamPlayers(volleyballTeams, volleyballPlayers,2,3);
        } else {
            System.out.println("There are less then two teams or less then six players in the input files!");
            return;
        }
        //���������� �� ������� �� ��������
        printTeams(volleyballTeams);
        //��������� � 10 ���� ����� ����� ������
        playMatches(team1, team2,10);
        //��� ���� �����
        restAndTrainThreeDays(team1, team2);
        //���������� �� ���������� ����� �� ��������
        printTeams(volleyballTeams);
        //��������� � 10 ���� ����� ����� ������ ���� ����� �� ��� ���
        System.out.println("-----------------NEW SEASON--------------------");
        playMatches(team1, team2,10);
        //��������� ������ ��������� �� �������� ��� ����� (� �������� ���������� �� ������ � ��������)
        try {
            savePlayers(volleyballPlayers, playersFile);
        } catch (Exception e) {
            System.out.println("Can't write to file " + playersFile);
        }
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
            System.out.println("Teams file " + fileName + " not found!");
            return new ArrayList<>();
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
            System.out.println("Players file " + fileName + " not found!");
            return new ArrayList<>();
        }
    }
    private static VolleyballTeam getVolleyballTeamPlayers(List<VolleyballTeam> volleyballTeams, List<VolleyballPlayer> volleyballPlayers, int teamNumber,int playersCount) {
        VolleyballTeam team;
        team = volleyballTeams.get(teamNumber-1);
        for (int i = (teamNumber-1)*playersCount; i < teamNumber*playersCount; i++) {
            team.players.add(volleyballPlayers.get(i));
        }
        return team;
    }
    private static void printTeams(List<VolleyballTeam> volleyballTeams) {
        for (VolleyballTeam team : volleyballTeams) {
            System.out.println(team);
        }
    }
    public static void playMatches(VolleyballTeam team1, VolleyballTeam team2, int n) {
        for (int i = 0; i < n; i++) {
            playMatch(team1, team2);
        }
    }
    //��������� ����� playMatch(team1, team2),
    // ����� �������� �������� �� ��� �� ���� ������ ������ �� ��������
    // � ���������� ����� (�� 1 �� 25): team1.strength + random1
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

    //������ ����������� �� ��������� � ��� ���� ������������, ����� ����� �� � ��
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
    private static void restAndTrainThreeDays(VolleyballTeam team1, VolleyballTeam team2) {
        team1.teamRest();
        team2.teamTraining();
        team1.teamRest();
        team2.teamTraining();
        team1.teamTraining();
        team2.teamRest();
    }
    private static void savePlayers(List<VolleyballPlayer> volleyballPlayers, String playersFile) throws FileNotFoundException {
        PrintStream fileWriter = new PrintStream(playersFile);
        for (VolleyballPlayer player : volleyballPlayers) {
            fileWriter.println(player.name + "," + player.age + "," + player.position + "," + player.skills + "," + player.condition);
        }
        fileWriter.close();
    }
}