#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define MAX_GAMES 5000
#define MAX_FIELD 3000

typedef struct {
    int app_id;
    char *name;
    char *release_date;
    char *owners;
    float price;
    char *languages;
    int metacritic;
    float user_score;
    int achievements;
    char *publishers;
    char *developers;
    char *categories;
    char *genres;
    char *tags;
} Game;

// ===============================
// Função que divide uma linha CSV corretamente
// (ignora vírgulas dentro de aspas)
// ===============================
char **splitCSVLine(const char *line, int *count) {
    char **fields = malloc(15 * sizeof(char *));
    *count = 0;

    int len = strlen(line);
    char buffer[MAX_FIELD];
    int pos = 0;
    bool insideQuotes = false;

    for (int i = 0; i <= len; i++) {
        char c = line[i];
        if (c == '"') {
            insideQuotes = !insideQuotes;
        } else if ((c == ',' && !insideQuotes) || c == '\0' || c == '\n') {
            buffer[pos] = '\0';
            fields[*count] = strdup(buffer);
            (*count)++;
            pos = 0;
        } else {
            buffer[pos++] = c;
        }
    }

    return fields;
}

// ===============================
// Função para ler um jogo do CSV
// ===============================
Game readGame(char *line) {
    int fieldCount;
    char **fields = splitCSVLine(line, &fieldCount);

    Game g;
    g.app_id = atoi(fields[0]);
    g.name = strdup(fields[1]);
    g.release_date = strdup(fields[2]);
    g.owners = strdup(fields[3]);
    g.price = atof(fields[4]);
    g.languages = strdup(fields[5]);
    g.metacritic = atoi(fields[6]);
    g.user_score = atof(fields[7]);
    g.achievements = atoi(fields[8]);
    g.publishers = strdup(fields[9]);
    g.developers = strdup(fields[10]);
    g.categories = strdup(fields[11]);
    g.genres = strdup(fields[12]);
    g.tags = strdup(fields[13]);

    // libera campos temporários
    for (int i = 0; i < fieldCount; i++) free(fields[i]);
    free(fields);

    return g;
}

// ===============================
// Impressão formatada do jogo
// ===============================
void printGame(Game g) {
    printf("%d ## %s ## %s ## %s ## %.2f ## %s ## %d ## %.1f ## %d ## %s ## %s ## %s ## %s ## %s ##\n",
           g.app_id, g.name, g.release_date, g.owners, g.price,
           g.languages, g.metacritic, g.user_score, g.achievements,
           g.publishers, g.developers, g.categories, g.genres, g.tags);
}

// ===============================
// Selection sort por nome
// ===============================
void selectionSort(Game *games, int n) {
    for (int i = 0; i < n - 1; i++) {
        int min = i;
        for (int j = i + 1; j < n; j++) {
            if (strcmp(games[j].name, games[min].name) < 0) {
                min = j;
            }
        }
        if (min != i) {
            Game temp = games[i];
            games[i] = games[min];
            games[min] = temp;
        }
    }
}

int main() {
    FILE *file = fopen("/tmp/games.csv", "r");
    if (!file) {
        printf("Erro ao abrir o arquivo CSV.\n");
        return 1;
    }

    char line[MAX_FIELD];
    Game allGames[MAX_GAMES];
    int total = 0;

    fgets(line, MAX_FIELD, file); // pula cabeçalho

    while (fgets(line, MAX_FIELD, file) != NULL && total < MAX_GAMES) {
        allGames[total++] = readGame(line);
    }
    fclose(file);

    // ===============================
    // Parte 1 — Leitura dos IDs até "FIM"
    // ===============================
    char input[50];
    Game selected[MAX_GAMES];
    int count = 0;

    while (1) {
        scanf("%s", input);
        if (strcmp(input, "FIM") == 0) break;

        int id = atoi(input);
        for (int i = 0; i < total; i++) {
            if (allGames[i].app_id == id) {
                selected[count++] = allGames[i];
                break;
            }
        }
    }

    // ===============================
    // Ordena os jogos pelo atributo Name
    // ===============================
    selectionSort(selected, count);

    // ===============================
    // Imprime jogos ordenados
    // ===============================
    for (int i = 0; i < count; i++) {
        printGame(selected[i]);
    }

    // ===============================
    // Liberação de memória
    // ===============================
    for (int i = 0; i < total; i++) {
        free(allGames[i].name);
        free(allGames[i].release_date);
        free(allGames[i].owners);
        free(allGames[i].languages);
        free(allGames[i].publishers);
        free(allGames[i].developers);
        free(allGames[i].categories);
        free(allGames[i].genres);
        free(allGames[i].tags);
    }

    return 0;
}
