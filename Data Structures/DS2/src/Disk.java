public abstract class Disk implements Comparable<Disk>{
    private int unique_id = 0;
    private List<Integer> folders;
    private int capacity = 1000000;

    public Disk(int id, int space){
        id = unique_id;
        space = capacity;
        unique_id ++;
        folders = new List<Integer>();
    }
    public int getFreeSpace(){
        return capacity;
    }

    public void addFolder(int folder){
        this.folders.insertAtFront(folder);
        this.capacity -= folder;
    }

    public int compareTo(Disk disk){
        if(getFreeSpace() > disk.getFreeSpace()){
            return 1;
        }else if(getFreeSpace() < disk.getFreeSpace()){
            return  -1;
        }else return 0;
    }

    public void diskToString(){
        System.out.print("Disk id: " + this.unique_id + " Free space: " + getFreeSpace());
    }


}