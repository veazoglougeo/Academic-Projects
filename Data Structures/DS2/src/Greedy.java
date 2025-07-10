import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Comparator;

public class Greedy {
    private static List<Integer> folders = new List<>();

    static MaxPQ<Disk> disk_pq = new MaxPQ<>(new Comparator<Disk>() {
        @Override
        public int compare(Disk o1, Disk o2) {
            if (o1.compareTo(o2) > 0) {
                return 1;
            } else if (o1.compareTo(o2) < 0) {
                return -1;
            } else {
                return 0;
            }
        }
    });
    static int counter = 0;

    public static void main(String[] args) {
        input(args[0]);
        distributeFolders(folders);
        output(args[0]);
    }

    public static void addFolderToDisk(int folder, MaxPQ<Disk> pq) {
        if (pq.isEmpty()) {
            Disk disk = new Disk();
            disk.addFolder(folder);
            pq.add(disk);
        } else if (pq.peek().getFreeSpace() >= folder) {
            pq.peek().addFolder(folder);
            pq.sink(1);
        } else {
            Disk disk = new Disk();
            disk.addFolder(folder);
            pq.add(disk);
        }
    }

    public static int distributeFolders(List list) {
        for (int i = 0; i < counter; i++) {
            addFolderToDisk(list.getNodeData(i), disk_pq);
        }
        return disk_pq.getSize();
    }

    public static void output(String path) {
        System.out.println("%java Greedy " + path);
        System.out.println("Sum of all folders = " + folders.sumNodes() / 1000000 + " TB");
        System.out.println("Total number of disks used = " + disk_pq.getSize());
        if (folders.getSize() <= 100) {
            while (!disk_pq.isEmpty()) {
                disk_pq.getMax().diskToString();
            }
        }
    }

    public static List<Integer> input(String path) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while (line != null) {
                if ((Integer.parseInt(line) >= 0 && Integer.parseInt(line) <= 1000000)) {
                    folders.insertAtFront(Integer.parseInt(line));
                    line = reader.readLine();
                    counter++;
                } else if (Integer.parseInt(line) < 0 || Integer.parseInt(line) > 1000000) {
                    throw new Exception();
                }
            }
        } catch (Exception e) {
            System.out.println("File size must be 0 - 1.000.000 MBs.");
        }
        return folders;
    }

    public static int greedyTest(String path) {
        MaxPQ<Disk> greedy_pq = new MaxPQ<>(new Comparator<Disk>() {
            @Override
            public int compare(Disk o1, Disk o2) {
                if (o1.compareTo(o2) > 0) {
                    return 1;
                } else if (o1.compareTo(o2) < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        List<Integer> greedy_folders = new List<>();
        BufferedReader reader;
        int numOfLines = 0;
        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while (line != null) {
                if ((Integer.parseInt(line) >= 0 && Integer.parseInt(line) <= 1000000)) {
                    greedy_folders.insertAtFront(Integer.parseInt(line));
                    line = reader.readLine();
                    numOfLines++;
                } else if (Integer.parseInt(line) < 0 || Integer.parseInt(line) > 1000000) {
                    throw new Exception();
                }
            }
            for (int i = 0; i < numOfLines; i++) {
                addFolderToDisk(greedy_folders.getNodeData(i), greedy_pq);
            }
        } catch (Exception e) {
            System.out.println("File size must be 0 - 1.000.000 MBs.");
        }
        return greedy_pq.getSize();
    }
}