#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define MAX_LINE 10000
#define MAX_FIELDS 20
#define MAX_ARRAY 100
#define MAX_STR 512

typedef struct {
    int id;
    char name[MAX_STR];
    char releaseDate[MAX_STR];
    int estimateOwners;
    double price;
    char **supportedLanguages;
    int supportedLanguagesCount;
    int metacriticScore;
    double userScore;
    int achieviments;
    char **publishers;
    int publishersCount;
    char **developers;
    int developersCount;
    char **categories;
    int categoriesCount;
    char **genres;
    int genresCount;
    char **tags;
    int tagsCount;
} Game;

// Função trim
char *trim(char *str) {
    while (isspace((unsigned char)*str)) str++;
    if (*str == 0) return str;
    char *end = str + strlen(str) - 1;
    while (end > str && isspace((unsigned char)*end)) end--;
    end[1] = '\0';
    return str;
}

// Remove aspas
void remove_quotes(char *s) {
    char *start = s;
    while (*start == '\'' || *start == '"') start++;
    char *end = s + strlen(s) - 1;
    while (end >= start && (*end == '\'' || *end == '"')) *end-- = '\0';
    if (start != s) memmove(s, start, strlen(start) + 1);
}

// Parse array CSV
char **parseArray(const char *s, int *count) {
    *count = 0;
    char *copy = strdup(s);
    char *str = trim(copy);
    if (str[0] == '[') str++;
    size_t len = strlen(str);
    if (len > 0 && str[len - 1] == ']') str[len - 1] = '\0';
    if (strlen(str) == 0) { free(copy); return NULL; }

    char **arr = malloc(MAX_ARRAY * sizeof(char *));
    char *token = strtok(str, ",");
    while (token && *count < MAX_ARRAY) {
        token = trim(token);
        remove_quotes(token);
        arr[*count] = strdup(token);
        (*count)++;
        token = strtok(NULL, ",");
    }
    free(copy);
    return arr;
}

// Parse CSV linha
int parseCSVLine(const char *line, char fields[MAX_FIELDS][MAX_LINE]) {
    int count = 0, inQuotes = 0, j = 0;
    char buffer[MAX_LINE];
    for (int i = 0; line[i] != '\0'; i++) {
        char c = line[i];
        if (c == '"') { inQuotes = !inQuotes; }
        else if (c == ',' && !inQuotes) {
            buffer[j] = '\0';
            strcpy(fields[count++], trim(buffer));
            j = 0;
        } else { buffer[j++] = c; }
    }
    buffer[j] = '\0';
    strcpy(fields[count++], trim(buffer));
    return count;
}

// Print array
void printArray(char **arr, int count) {
    printf("[");
    for (int i = 0; i < count; i++) {
        printf("%s", arr[i]);
        if (i < count - 1) printf(", ");
    }
    printf("]");
}

// Print game
void printGame(Game *g) {
    char priceStr[20];
    if (g->price == 0.0) strcpy(priceStr, "0.0");
    else sprintf(priceStr, "%.2f", g->price);

    printf("=> %d ## %s ## %s ## %d ## %s ## ",
           g->id, g->name, g->releaseDate, g->estimateOwners, priceStr);

    printArray(g->supportedLanguages, g->supportedLanguagesCount);
    printf(" ## %d ## %.1f ## %d ## ", g->metacriticScore, g->userScore, g->achieviments);

    printArray(g->publishers, g->publishersCount);
    printf(" ## ");
    printArray(g->developers, g->developersCount);
    printf(" ## ");
    printArray(g->categories, g->categoriesCount);
    printf(" ## ");
    printArray(g->genres, g->genresCount);
    printf(" ## ");
    printArray(g->tags, g->tagsCount);
    printf(" ##\n");
}

// Load games
int loadGames(Game **gamesOut) {
    FILE *fp = fopen("/tmp/games.csv", "r");
    if (!fp) { perror("Erro abrindo /tmp/games.csv"); exit(1); }
    char line[MAX_LINE];
    fgets(line, sizeof(line), fp); // cabeçalho
    Game *games = malloc(5000 * sizeof(Game));
    int gameCount = 0;
    while (fgets(line, sizeof(line), fp)) {
        line[strcspn(line, "\n")] = '\0';
        if (strlen(line) == 0) continue;
        char fields[MAX_FIELDS][MAX_LINE];
        int fieldCount = parseCSVLine(line, fields);
        if (fieldCount < 14) continue;

        Game g;
        g.id = atoi(fields[0]);
        strcpy(g.name, fields[1]);
        strcpy(g.releaseDate, fields[2]);
        g.estimateOwners = atoi(fields[3]);
        g.price = atof(fields[4]);
        g.supportedLanguages = parseArray(fields[5], &g.supportedLanguagesCount);
        g.metacriticScore = atoi(fields[6]);
        g.userScore = atof(fields[7]);
        g.achieviments = atoi(fields[8]);
        g.publishers = parseArray(fields[9], &g.publishersCount);
        g.developers = parseArray(fields[10], &g.developersCount);
        g.categories = parseArray(fields[11], &g.categoriesCount);
        g.genres = parseArray(fields[12], &g.genresCount);
        g.tags = parseArray(fields[13], &g.tagsCount);

        games[gameCount++] = g;
    }
    fclose(fp);
    *gamesOut = games;
    return gameCount;
}

// Função de comparação para Quicksort (Release_date -> AppID)
int compareGames(const Game *a, const Game *b) {
    int dayA=0, monthA=0, yearA=0;
    int dayB=0, monthB=0, yearB=0;

    if (sscanf(a->releaseDate, "%d/%d/%d", &dayA, &monthA, &yearA) != 3) dayA=monthA=yearA=0;
    if (sscanf(b->releaseDate, "%d/%d/%d", &dayB, &monthB, &yearB) != 3) dayB=monthB=yearB=0;

    int dateA = yearA*10000 + monthA*100 + dayA;
    int dateB = yearB*10000 + monthB*100 + dayB;

    if (dateA != dateB) return dateA - dateB;
    return a->id - b->id;
}

// Quicksort
void quicksort(Game arr[], int left, int right) {
    if (left >= right) return;
    Game pivot = arr[right];
    int i = left;
    for (int j = left; j < right; j++) {
        if (compareGames(&arr[j], &pivot) <= 0) {
            Game tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
            i++;
        }
    }
    Game tmp = arr[i];
    arr[i] = arr[right];
    arr[right] = tmp;

    quicksort(arr, left, i - 1);
    quicksort(arr, i + 1, right);
}

int main() {
    Game *games;
    int gameCount = loadGames(&games);

    Game selected[5000];
    int selectedCount = 0;
    char input[50];
    while (1) {
        if (!fgets(input, sizeof(input), stdin)) break;
        input[strcspn(input, "\n")] = '\0';
        if (strcmp(input, "FIM") == 0) break;
        int id = atoi(input);
        for (int i = 0; i < gameCount; i++) {
            if (games[i].id == id) {
                selected[selectedCount++] = games[i];
                break;
            }
        }
    }

    quicksort(selected, 0, selectedCount - 1);

    for (int i = 0; i < selectedCount; i++) printGame(&selected[i]);

    // Liberação
    for (int i = 0; i < gameCount; i++) {
        for (int j = 0; j < games[i].supportedLanguagesCount; j++) free(games[i].supportedLanguages[j]);
        for (int j = 0; j < games[i].publishersCount; j++) free(games[i].publishers[j]);
        for (int j = 0; j < games[i].developersCount; j++) free(games[i].developers[j]);
        for (int j = 0; j < games[i].categoriesCount; j++) free(games[i].categories[j]);
        for (int j = 0; j < games[i].genresCount; j++) free(games[i].genres[j]);
        for (int j = 0; j < games[i].tagsCount; j++) free(games[i].tags[j]);
        free(games[i].supportedLanguages);
        free(games[i].publishers);
        free(games[i].developers);
        free(games[i].categories);
        free(games[i].genres);
        free(games[i].tags);
    }
    free(games);

    return 0;
}
