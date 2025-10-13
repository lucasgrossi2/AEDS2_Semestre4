import java.io.*;
import java.util.*;

public class Game {
    int id;
    String name;
    String releaseDate;
    int estimatedOwners;
    float price;
    String[] supportedLanguages;
    int metacriticScore;
    float userScore;
    int achievements;
    String[] publishers;
    String[] devs;
    String[] categories;
    String[] genres;
    String[] tags;

    public Game(int id, String name, String releaseDate, int estimatedOwners, float price,
                String[] supportedLanguages, int metacriticScore, float userScore,
                int achievements, String[] publishers, String[] devs,
                String[] categories, String[] genres, String[] tags) {

        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.estimatedOwners = estimatedOwners;
        this.price = price;
        this.supportedLanguages = supportedLanguages;
        this.metacriticScore = metacriticScore;
        this.userScore = userScore;
        this.achievements = achievements;
        this.publishers = publishers;
        this.devs = devs;
        this.categories = categories;
        this.genres = genres;
        this.tags = tags;
    }

    public static Game fromCSV(String line) {
         String[] parts = splitCSVLine(line);

        parts = Arrays.copyOf(parts, 14);
        for (int i = 0; i < parts.length; i++) {
            if (parts[i] == null) parts[i] = "";
        }

        int id = Integer.parseInt(parts[0]);
        String name = parts[1];

        String date = parts[2].replace("\"", "").trim();
        date = normalizeDate(date);

        int owners = Integer.parseInt(parts[3].replaceAll("[^0-9]", ""));

        float price = parts[4].equalsIgnoreCase("Free to Play") ? 0.0f
                        : Float.parseFloat(parts[4]);

        String[] langs = extractArray(parts[5]);
        int meta = parts[6].isEmpty() ? -1 : Integer.parseInt(parts[6]);
        float user = (parts[7].isEmpty() || parts[7].equalsIgnoreCase("tbd")) ? -1.0f
                       : Float.parseFloat(parts[7]);
        int ach = parts[8].isEmpty() ? 0 : Integer.parseInt(parts[8]);

        String[] publishers = parts[9].split(",");
        String[] devs = parts[10].split(",");
        String[] categories = extractArray(parts[11]);
        String[] genres = extractArray(parts[12]);
        String[] tags = extractArray(parts[13]);

        return new Game(id, name, date, owners, price, langs, meta, user,
                        ach, publishers, devs, categories, genres, tags);
    }

    private static String normalizeDate(String s) {
        s = s.replace(",", "");
        String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        for (int i = 0; i < months.length; i++) {
            if (s.contains(months[i])) {
                s = s.replace(months[i], String.valueOf(i + 1));
                break;
            }
        }
        String[] parts = s.split(" ");
        String day = "01", month = "01", year = "2000";

        if (parts.length == 3) {
            month = parts[0];
            day = parts[1];
            year = parts[2];
        } else if (parts.length == 2) {
            month = parts[0];
            year = parts[1];
        } else if (parts.length == 1) {
            year = parts[0];
        }

        int m = Integer.parseInt(month);
        int d = Integer.parseInt(day);
        return String.format("%02d/%02d/%s", d, m, year);
    }

    private static String[] extractArray(String s) {
        s = s.replaceAll("[\\[\\]'\"]", "").trim();
        if (s.isEmpty()) return new String[0];
        String[] arr = s.split(",");
        for (int i = 0; i < arr.length; i++) arr[i] = arr[i].trim();
        return arr;
    }

    private static String[] splitCSVLine(String line) {
        return line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
    }

    @Override
    public String toString() {
        return "=> " + id + " ## " + name + " ## " + releaseDate + " ## " +
               estimatedOwners + " ## " + price + " ## " + Arrays.toString(supportedLanguages) +
               " ## " + metacriticScore + " ## " + userScore + " ## " + achievements + " ## " +
               Arrays.toString(publishers) + " ## " + Arrays.toString(devs) + " ## " +
               Arrays.toString(categories) + " ## " + Arrays.toString(genres) + " ## " +
               Arrays.toString(tags) + " ##";
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("/tmp/games.csv"));
        List<Game> games = new ArrayList<>();

        String line;
        boolean first = true;

        while ((line = br.readLine()) != null) {
            if (first) {
                first = false; // skip header
                continue;
            }
            games.add(Game.fromCSV(line));
        }
        br.close();

        Scanner sc = new Scanner(System.in);
        String input = sc.next();
        
        while (!input.equals("FIM")) {
            int id = Integer.parseInt(input);
            boolean found = false;

            for (Game g : games) {
                if (g.id == id) {
                    System.out.println(g);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Jogo não encontrado.");
            }

            input = sc.next();
        }
        sc.close();
    }
}

