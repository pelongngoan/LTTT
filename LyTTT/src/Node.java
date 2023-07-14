public class Node{
    //Lưu ký tự vào biến ch kiểu Character
    Character ch;
    //Lưu tần số vào biến freq kiểu int
    Integer freq;
    Node left = null;
    Node right = null;
    //Tạo Constructor cho class Node
    Node(Character ch, Integer freq)
    {
        this.ch = ch;
        this.freq = freq;
    }
    //Tạo Constructor cho class Node
    public Node(Character ch, Integer freq, Node left, Node right)
    {
        this.ch = ch;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }
}