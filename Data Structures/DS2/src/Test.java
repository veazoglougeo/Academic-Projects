import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        Random rand = new Random();
        FileWriter writer;
        int randNum;
        try{
            for(int i = 0; i < 10; i ++){
                writer = new FileWriter(new File("folder" + i + "_100.txt"));
                for(int j = 1; j <=  100; j ++){
                    randNum = rand.nextInt(1000001);
                    writer.write(randNum+"\n");
                }
                writer.close();
            }
            for(int i = 0; i < 10; i ++) {
                writer = new FileWriter(new File("folder" + i + "_500.txt"));
                for (int j = 1; j <= 500; j++) {
                    randNum = rand.nextInt(1000001);
                    writer.write(randNum + "\n");
                }
                writer.close();
            }
            for(int i = 0; i < 10; i ++){
                writer = new FileWriter(new File("folder" + i + "_1000.txt"));
                for(int j = 1; j <=  1000; j ++){
                    randNum = rand.nextInt(1000001);
                    writer.write(randNum+"\n");
                }
                writer.close();
            }
            int greedyDisksUsed100 = 0;
            int sortDisksUsed100 = 0;
            int greedyDisksUsed500 = 0;
            int sortDisksUsed500 = 0;
            int greedyDisksUsed1000 = 0;
            int sortDisksUsed1000 = 0;
            for(int i = 0; i < 10; i ++){
                greedyDisksUsed100 += Greedy.greedyTest("folder" + i +"_100.txt");

                sortDisksUsed100 += Sort.sortTest("folder" + i + "_100.txt");
            }
            for(int i = 0; i < 10; i ++){
                greedyDisksUsed500 += Greedy.greedyTest("folder" + i +"_500.txt");

                sortDisksUsed500 += Sort.sortTest("folder" + i + "_500.txt");
            }
            for(int i = 0; i < 10; i ++){
                greedyDisksUsed1000 += Greedy.greedyTest("folder" + i +"_1000.txt");

                sortDisksUsed1000 += Sort.sortTest("folder" + i + "_1000.txt");
            }
            System.out.println("For N = 100:");
            System.out.println("Greedy class used an average of " + greedyDisksUsed100 / 10.0 + " disks.");
            System.out.println("Sort class used an average of " + sortDisksUsed100 / 10.0 + " disks.");
            System.out.println();
            System.out.println("For N = 500:");
            System.out.println("Greedy class used an average of " + greedyDisksUsed500 / 10.0 + " disks.");
            System.out.println("Sort class used an average of " + sortDisksUsed500 / 10.0 + " disks.");
            System.out.println();
            System.out.println("For N = 1000:");
            System.out.println("Greedy class used an average of " + greedyDisksUsed1000 / 10.0 + " disks.");
            System.out.println("Sort class used an average of " + sortDisksUsed1000 / 10.0 + " disks.");
        }

        catch (IOException e) {
            System.out.println("File writing failed.");
        }
    }
}
