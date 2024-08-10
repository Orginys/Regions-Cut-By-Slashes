class Solution {
    public int regionsBySlashes(String[] grid) {
        int n = grid.length;
        int size = n * n * 4;
        UnionFind uf = new UnionFind(size);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int root = 4 * (i * n + j);
                char c = grid[i].charAt(j);

                // Объединяем треугольники внутри одной ячейки
                if (c == '/') {
                    uf.union(root + 0, root + 3);
                    uf.union(root + 1, root + 2);
                } else if (c == '\\') {
                    uf.union(root + 0, root + 1);
                    uf.union(root + 2, root + 3);
                } else { // ' '
                    uf.union(root + 0, root + 1);
                    uf.union(root + 1, root + 2);
                    uf.union(root + 2, root + 3);
                }

                // Объединяем треугольники с соседними ячейками
                // Верхняя ячейка
                if (i > 0) {
                    uf.union(root + 0, root - 4 * n + 2);
                }
                // Левая ячейка
                if (j > 0) {
                    uf.union(root + 3, root - 4 + 1);
                }
            }
        }

        // Подсчёт регионов
        int regions = 0;
        for (int i = 0; i < size; i++) {
            if (uf.find(i) == i) {
                regions++;
            }
        }
        return regions;
    }

    class UnionFind {
        int[] parent;
        int[] rank;

        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }
        }
    }
}
