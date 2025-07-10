import java.io.*;
import java.util.Comparator;

public class Sort{
    static int counter;
    static int[] folders;
    static MaxPQ<Disk> disk_pq = new MaxPQ<>(new Comparator<Disk>() {
        @Override
        public int compare(Disk o1, Disk o2) {
            if(o1.compareTo(o2) > 0){
                return 1;
            }else if(o1.compareTo(o2) < 0){
                return -1;
            }else{
                return 0;
            }
        }
    });
    static String path;
    public static void main(String[] args) {
        path = args[0];
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while(line != null){
                if((Integer.parseInt(line) >= 0) && Integer.parseInt(line) <= 1000000){
                    counter ++;
                    line = reader.readLine();
                }
            }
            folders = new int[counter];
        }
        catch(FileNotFoundException e){
            System.out.println("File " + path + " not found." );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addFolders(folders, counter, path);
        distributeFolders(folders, disk_pq);
        output(path);
    }
    public static void addFolders(int[] folders, int counter, String path){
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader(path));
            String line  = reader.readLine();
            for(int i = 0; i < counter; i ++){
                folders[i] = Integer.parseInt(line);
                line = reader.readLine();
            }
        }
        catch(FileNotFoundException e){
            System.out.println("File" + path + " not found.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mergeSort(folders);
    }
    public static void mergeSort(int[] array){
        if(array.length < 2){
            return;
        }
        int middle = array.length / 2;
        int[] left = new int[middle];
        int[] right = new int[array.length - middle];
        for(int i = 0; i < middle; i ++){
            left[i] = array[i];
        }
        for(int i = middle; i < array.length; i ++){
            right[i - middle] = array[i];
        }
        mergeSort(left);
        mergeSort(right);
        merge(array, left, right);
    }
    private static void merge(int[] array, int[] left, int[] right){
        int leftIterator = 0;
        int rightIterator = 0;
        int arrayIterator = 0;
        while(leftIterator < left.length && rightIterator < right.length){
            if(left[leftIterator] >= right[rightIterator]){
                array[arrayIterator] = left[leftIterator];
                leftIterator ++;
            }else{
                array[arrayIterator] = right[rightIterator];
                rightIterator ++;
            }
            arrayIterator ++;
        }
        while(leftIterator < left.length){
            array[arrayIterator] = left[leftIterator];
            leftIterator ++;
            arrayIterator ++;
        }
        while(rightIterator < right.length){
            array[arrayIterator] = right[rightIterator];
            rightIterator ++;
            arrayIterator ++;
        }
    }
    public static float foldersSum(int[] array){
        float sum = 0;
        for(int i = 0; i < array.length; i ++){
            sum += array[i];
        }
        return sum;
    }
    public static void distributeFolders(int[] array, MaxPQ<Disk> pq){
        for(int i = 0; i < array.length; i ++){
            Greedy.addFolderToDisk(array[i], pq);
        }
    }
    public static void output(String path){
        System.out.println("%java Greedy " + path);
        System.out.println("Sum of all folders = " + foldersSum(folders) / 1000000 + " TB");
        System.out.println("Total number of disks used = " + disk_pq.getSize());
        if(folders.length <= 100) {
            while (!disk_pq.isEmpty()) {
                disk_pq.getMax().diskToString();
            }
        }
    }
    public static int sortTest(String path){
        int[] sort_array;
        MaxPQ<Disk> sort_pq = new MaxPQ<>(new Comparator<Disk>() {
            @Override
            public int compare(Disk o1, Disk o2) {
                if(o1.compareTo(o2) > 0){
                    return 1;
                }else if(o1.compareTo(o2) < 0){
                    return -1;
                }else{
                    return 0;
                }
            }
        });
        int numOfLines = 0;
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while(line != null){
                if((Integer.parseInt(line) >= 0) && Integer.parseInt(line) <= 1000000){
                    numOfLines ++;
                    line = reader.readLine();
                }
            }
        }
        catch(FileNotFoundException e){
            System.out.println("File " + path + " not found." );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sort_array = new int[numOfLines];
        addFolders(sort_array, numOfLines, path);
        distributeFolders(sort_array, sort_pq);
        return sort_pq.getSize();
    }
}