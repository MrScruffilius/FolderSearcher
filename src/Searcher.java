import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Searcher {
    public static void main(String[] args) {
        Scanner si = new Scanner(System.in);

        String path = "....";

        while (!Files.isReadable(Path.of(path))) {
            System.out.println("Input path:");
            path = si.next();
        }

        int temp = -1;

        while (temp <= 0) {
            System.out.println("Input maximum search depth:");
            temp = si.nextInt();
        }


        int temp2 = -1;

        while (temp2 != 0 && temp2 != 1) {
            System.out.println("Input option:");
            System.out.println("\t (0) look for empty folders");
            System.out.println("\t (1) look for file sizes");

            temp2 = si.nextInt();
        }
        if (temp2 == 0) {
            look(Path.of(path), 0, temp);
        } else {
            filelook(Path.of(path), 0, temp);
        }



    }


    /**
     * The File system will be printed in a tree-like-structure
     * non-empty folders are surrounded by [ and ]
     * empty ones are just written without
     **/
    static void look(Path path, int currDepth, int maxDepth) {

        for (int i = 0; i < currDepth; i++) {
            System.out.print("\t");
        }
        System.out.print("[" + path + "]" + "\n");

        if (currDepth == maxDepth) return;
        if (Files.isDirectory(path)) {
            try {
                Files.list(path).forEach(p -> {
                            try {
                                if (Files.isDirectory(p)) {

                                } else if (Files.list(p).count() == 0) {
                                    for (int i = 0; i < currDepth; i++) {
                                        System.out.print("\t");
                                    }
                                    System.out.print(p + "\n");
                                } else {
                                    look(p, currDepth + 1, maxDepth);
                                }
                            } catch (Exception ignored) {
                            }
                        }
                );
            } catch (Exception ignored) {
            }

        } else return;
    }

    /**
     * The File system will be printed in a tree-like-structure
     * non-empty folders are surrounded by [ and ]
     * empty folders aren't printed at all
     * files are printed wit %- in front of them and their size a little after their name
     **/
    static void filelook(Path path, int currDepth, int maxDepth) {

        for (int i = 0; i < currDepth; i++) {
            System.out.print("\t");
        }
        System.out.print("[" + path + "]" + "\n");

        if (currDepth == maxDepth) return;
        if (Files.isDirectory(path)) {
            try {
                Files.list(path).sorted((p1, p2) -> {
                    try {
                        return (int) ((Files.size(p1) - Files.size(p2)) / Math.abs((Files.size(p1) - Files.size(p2))));
                    } catch (Exception e) {
                        return 0;
                    }
                }).forEach(p -> {
                            try {
                                if (!Files.isDirectory(p)) {
                                    for (int i = 0; i < currDepth; i++) {
                                        System.out.print("\t");
                                    }
                                    System.out.print("%- " + p + "\t\t\t" + Files.size(p) + "\n");
                                } else if (Files.list(p).count() != 0) {
                                    filelook(p, currDepth + 1, maxDepth);
                                }
                            } catch (Exception ignored) {
                            }
                        }
                );
            } catch (Exception ignored) {
            }

        } else return;
    }
}
