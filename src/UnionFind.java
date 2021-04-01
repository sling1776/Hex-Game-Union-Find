import java.util.ArrayList;

public class UnionFind <E extends Comparable>{
    public static class Cell<E>{
        private Cell groupID;
        private E value;
        int groupSize = 1;

        public Cell(E v){
            value = v;
            groupID = this;
        }

        public void updateGroupID(Cell newGroupID){
            if(newGroupID== null){
                groupID = this;
            }else {
                groupID = newGroupID;
            }
        }

        public Cell getGroupID() {
            return groupID;
        }

        public E getValue() {
            return value;
        }

        public int getGroupSize() {
            return groupSize;
        }

        public void setGroupSize(int groupSize) {
            this.groupSize = groupSize;
        }

        public void setValue(E value) {
            this.value = value;
        }


        public String display(){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Value: ");
            stringBuilder.append(getValue());
            stringBuilder.append("\tGroup: ");
            stringBuilder.append(getGroupID().getValue());
            stringBuilder.append("\tGroupSize: ");
            stringBuilder.append(getGroupSize());
            return stringBuilder.toString();
        }
    }

    public static Cell find(Cell cell){
        if(cell.getGroupID() == cell.getGroupID().getGroupID()){
            return cell.getGroupID();
        }
        return find(cell.getGroupID());
    }

    public static boolean union(Cell cell1, Cell cell2){
        Cell c1 = find(cell1);
        Cell c2 = find(cell2);
        if(c1 == c2){
            return false;
        }else{
            if(c1.getGroupSize() > c2.getGroupSize()){
                c1.setGroupSize(c1.getGroupSize() + c2.getGroupSize());
                c2.updateGroupID(c1);
            }else{
                c2.setGroupSize(c1.getGroupSize() + c2.getGroupSize());
                c1.updateGroupID(c2);
            }
            return true;
        }
    }

    public static void main(String[] args) {
        ArrayList<Cell<Integer>> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Cell<Integer> j = new Cell<>(i);
            list.add(j);
        }

        System.out.println("BeginTest:");
        System.out.println("\n\tFind Initial Group:");
        System.out.println("\t\t" + find(list.get(0)).getValue().toString());

        System.out.println("\n\tUnion Elements: ");
        System.out.println("\t\t(3,4) " + union(list.get(3), list.get(4)));
        System.out.println("\t\t\t3: " + list.get(3).getGroupID().getValue() +"  4: "+ list.get(4).getGroupID().getValue());
        System.out.println("\t\t(2,19) " + union(list.get(2), list.get(19)));
        System.out.println("\t\t\t2: " + list.get(2).getGroupID().getValue() +"  19: "+ list.get(19).getGroupID().getValue());
        System.out.println("\t\t(5,7) " + union(list.get(5), list.get(7)));
        System.out.println("\t\t\t5: " + list.get(5).getGroupID().getValue() +"  7: "+ list.get(7).getGroupID().getValue());

        System.out.println("\n\tFind in groups: ");
        System.out.println("\t\t2: " + find(list.get(2)).getGroupID().getValue().toString());
        System.out.println("\t\t19: " + find(list.get(19)).getGroupID().getValue().toString());


        System.out.println("\n\tDuplicate Union:");
        System.out.println("\t\t(2,19) " + union(list.get(2), list.get(19))+ "- Should be False");
        System.out.println("\t\t\t2: " + list.get(2).getGroupID().getValue() +"  19: "+ list.get(19).getGroupID().getValue());


        System.out.println("\n\tUnion Large Groups: (shows the parent pointers not the group)");
        System.out.println("\t\t(2,3) " + union(list.get(2), list.get(3)));
        System.out.println("\t\t\t2: " + list.get(2).getGroupID().getValue() +"  3: "+ list.get(3).getGroupID().getValue());
        System.out.println("\t\t\t4: " + list.get(4).getGroupID().getValue() +"  19: "+ list.get(19).getGroupID().getValue());
        System.out.println("\t\t(3,7) " + union(list.get(3), list.get(7)));
        System.out.println("\t\t\t3: " + list.get(3).getGroupID().getValue() +"  7: "+ list.get(7).getGroupID().getValue());
        System.out.println("\t\t\t4: " + list.get(4).getGroupID().getValue() +"  19: "+ list.get(19).getGroupID().getValue());
        System.out.println("\t\t\t5: " + list.get(5).getGroupID().getValue() +"  2: "+ list.get(2).getGroupID().getValue());


        System.out.println("\n\tFind in Large Groups: (second Row should all be the same)");
        System.out.println("\t\t3: " + find(list.get(3)).getValue().toString());
        System.out.println("\t\t7: " + find(list.get(7)).getValue().toString());
        System.out.println("\t\t2: " + find(list.get(2)).getValue().toString());
        System.out.println("\t\t5: " + find(list.get(5)).getValue().toString());


        System.out.println("\n\n");
        for (Cell cell: list
             ) {
            System.out.println((cell.display()));
        }
    }


}

