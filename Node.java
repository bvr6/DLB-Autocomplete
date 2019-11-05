public class Node {
    public Node next;
    public Node child;
    public char character;
    public int value;
    public String s;
    public Node(Node next, Node child, char character){
        this.next = next;
        this.child = child;
        this.character = character;
        this.value = 0;
        this.s = "";
    }

    public Node(Node next, Node child, char character, int value){
        this.next = next;
        this.child = child;
        this.character = character;
        this.value = value;
        this.s = "";
    }

    public void put(String s, int value){
        this.s = s;
        this.value = value;
    }

}