import java.io.*;
import java.util.*;

class Game {
    int appID;
    String name;
    String releaseDate;
    long estimatedOwners; // Alterado de int para long
    double price;
    String[] supportedLanguages;
    int metacriticScore;
    double userScore;
    int achievements;
    String[] publishers;
    String[] developers;
    String[] categories;
    String[] genres;
    String[] tags;

    public Game() {}

    private String arrayToString(String[] arr) {
        if (arr == null || arr.length == 0) return "[]";
        return "[" + String.join(", ", arr) + "]";
    }

    @Override
    public String toString() {
        return "=> " + appID + " ## " + name + " ## " + releaseDate + " ## " + estimatedOwners + " ## "
                + String.format("%.2f", price) + " ## "
                + arrayToString(supportedLanguages) + " ## "
                + metacriticScore + " ## "
                + String.format("%.1f", userScore) + " ## "
                + achievements + " ## "
                + arrayToString(publishers) + " ## "
                + arrayToString(developers) + " ## "
                + arrayToString(categories) + " ## "
                + arrayToString(genres) + " ## "
                + arrayToString(tags) + " ##";
    }
}

public class Aplicacao {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        List<Game> jogos = carregarJogos("/tmp/games.csv");
        if (jogos.isEmpty()) return;

        Scanner sc = new Scanner(System.in);
        List<Game> selecionados = new ArrayList<>();

        // Entrada de IDs até FIM
        while (true) {
            String entrada = sc.next();
            if (entrada.equalsIgnoreCase("FIM")) break;
            try {
                int id = Integer.parseInt(entrada);
                for (Game g : jogos) {
                    if (g.appID == id) {
                        selecionados.add(g);
                        break;
                    }
                }
            } catch (NumberFormatException ignored) {}
        }
        sc.close();

        if (selecionados.isEmpty()) return;

        // MergeSort por preço (desempate AppID)
        Game[] vetor = selecionados.toArray(new Game[0]);
        mergeSort(vetor, 0, vetor.length - 1);

        // Imprimir 5 mais caros
        System.out.println("| 5 preços mais caros |");
        for (int i = vetor.length - 1; i >= Math.max(vetor.length - 5, 0); i--) {
            System.out.println(vetor[i]);
        }

        // Imprimir 5 mais baratos
        System.out.println();
        System.out.println("| 5 preços mais baratos |");
        for (int i = 0; i < Math.min(5, vetor.length); i++) {
            System.out.println(vetor[i]);
        }
    }

    // ---------------- MergeSort ----------------
    public static void mergeSort(Game[] vetor, int inicio, int fim) {
        if (inicio < fim) {
            int meio = (inicio + fim) / 2;
            mergeSort(vetor, inicio, meio);
            mergeSort(vetor, meio + 1, fim);
            merge(vetor, inicio, meio, fim);
        }
    }

    private static void merge(Game[] vetor, int inicio, int meio, int fim) {
        int n1 = meio - inicio + 1;
        int n2 = fim - meio;

        Game[] L = new Game[n1];
        Game[] R = new Game[n2];

        System.arraycopy(vetor, inicio, L, 0, n1);
        System.arraycopy(vetor, meio + 1, R, 0, n2);

        int i = 0, j = 0, k = inicio;
        while (i < n1 && j < n2) {
            if (L[i].price < R[j].price || (L[i].price == R[j].price && L[i].appID < R[j].appID)) {
                vetor[k++] = L[i++];
            } else {
                vetor[k++] = R[j++];
            }
        }

        while (i < n1) vetor[k++] = L[i++];
        while (j < n2) vetor[k++] = R[j++];
    }

    // ---------------- Leitura do CSV ----------------
    private static List<Game> carregarJogos(String caminho) {
        List<Game> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha = br.readLine(); // ignorar cabeçalho
            while ((linha = br.readLine()) != null) {
                String[] campos = splitCSV(linha);
                if (campos.length < 14) continue;

                Game g = new Game();
                try {
                    g.appID = Integer.parseInt(campos[0]);
                    g.name = campos[1];
                    g.releaseDate = normalizeDate(campos[2]);
                    g.estimatedOwners = parseLongSafe(campos[3]);
                    g.price = parsePrice(campos[4]);
                    g.supportedLanguages = parseArray(campos[5]);
                    g.metacriticScore = campos[6].isEmpty() ? 0 : Integer.parseInt(campos[6]);
                    g.userScore = campos[7].isEmpty() || campos[7].equalsIgnoreCase("tbd") ? 0.0 : Double.parseDouble(campos[7]);
                    g.achievements = campos[8].isEmpty() ? 0 : Integer.parseInt(campos[8]);
                    g.publishers = parseArray(campos[9]);
                    g.developers = parseArray(campos[10]);
                    g.categories = parseArray(campos[11]);
                    g.genres = parseArray(campos[12]);
                    g.tags = parseArray(campos[13]);
                    lista.add(g);
                } catch (Exception ignored) {}
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler CSV: " + e.getMessage());
        }
        return lista;
    }

    // ---------------- Utilitários ----------------
    private static String[] parseArray(String s) {
        s = s.replaceAll("[\\[\\]'\"\\]]", "").trim();
        if (s.isEmpty()) return new String[0];
        String[] arr = s.split(",");
        for (int i = 0; i < arr.length; i++) arr[i] = arr[i].trim();
        return arr;
    }

    private static double parsePrice(String s) {
        if (s.equalsIgnoreCase("Free to Play")) return 0.0;
        try { return Double.parseDouble(s); } catch (Exception e) { return 0.0; }
    }

    private static long parseLongSafe(String s) {
        try {
            s = s.replaceAll("[^0-9]", "");
            if (s.isEmpty()) return 0;
            return Long.parseLong(s);
        } catch (Exception e) { return 0; }
    }

    private static String normalizeDate(String s) {
        s = s.replace(",", "").trim();
        String[] meses = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        for (int i = 0; i < meses.length; i++) {
            if (s.contains(meses[i])) {
                s = s.replace(meses[i], String.format("%02d", i + 1));
                break;
            }
        }

        String[] partes = s.split(" ");
        String dia = "01", mes = "01", ano = "2000";

        if (partes.length == 3) {
            mes = partes[0];
            dia = partes[1];
            ano = partes[2];
        } else if (partes.length == 2) {
            mes = partes[0];
            ano = partes[1];
        } else if (partes.length == 1) {
            ano = partes[0];
        }

        try {
            int m = Integer.parseInt(mes);
            int d = Integer.parseInt(dia);
            return String.format("%02d/%02d/%s", d, m, ano);
        } catch (Exception e) {
            return s;
        }
    }

    private static String[] splitCSV(String line) {
        List<String> campos = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();
        for (char c : line.toCharArray()) {
            if (c == '"') inQuotes = !inQuotes;
            else if (c == ',' && !inQuotes) {
                campos.add(sb.toString().trim());
                sb.setLength(0);
            } else sb.append(c);
        }
        campos.add(sb.toString().trim());
        return campos.toArray(new String[0]);
    }
}
