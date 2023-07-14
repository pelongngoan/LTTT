import java.util.*;

public class Main {
    // Hàm xây dựng cây Huffman
    public static void createHuffmanTree(String text)
    {
        // Nếu không cung cấp văn bản
        if (text == null || text.length() == 0)
        {
            return;
        }
        //Đếm tần số xuất hiện từng ký tự trong văn bản và lưu nó trong Map
        //Tạo instance của Map
        Map<Character, Integer> freq = new HashMap<>();

        for (char c: text.toCharArray())
        {
            // Lưu ký tự và tần số xuất hiện vào trong Map sử dụng phương thức put()
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        //Tạo Priority Queue lưu node hiện tại của cây Huffman
        //Đây là điểm của note có ưu tiên lớn nhất nghĩa là tần số nhỏ nhất
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(even -> even.freq));
        for (var entry: freq.entrySet())
        {
            // Tạo node lá và thêm vào hàng chờ
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }
        // Lặp lại tới khi không còn nhiều hơn 1 node trong hàng chờ
        while (pq.size() != 1)
        {
            //Loại bỏ node có ưu tiên lớn nhất ( tần số nhỏ nhất ) ra khỏi hàng chờ
            Node left = pq.poll();
            Node right = pq.poll();
            // Tạo node tổng mới có 2 node trên làm node con có tần số bằng tổng tần số 2 node. Thêm node mới vào hàng chờ
            // Cộng tổng 2 tần số của node left và right ( trái và phải ) mà ta vừa xóa
            int sum = left.freq + right.freq;
            //Thêm node tổng (xóa node trái và phải) vào hàng chờ có tần số là tổng tần số của 2 node
            pq.add(new Node(null, sum, left, right));
        }
        // root chỏ tới root của cây Huffman
        Node root = pq.peek();
        // Lần theo cây Huffman và lưu mã hóa Huffman rong map
        Map<Character, String> huffmanCode = new HashMap<>();
        encodeHuffman(root, "", huffmanCode);
        //In mã hóa Huffman cho từng ký tự
        System.out.println("Mã hóa Huffman của từng ký tự: " + huffmanCode);
        //In văn bản nhập vào
        System.out.println("Chuỗi ban đầu: " + text);
        StringBuilder sb = new StringBuilder();
        for (char c: text.toCharArray())
        {
            sb.append(huffmanCode.get(c));
        }
        System.out.println("Mã hóa Huffman: " + sb);
    }
    //Hàm mã hóa Huffman
    public static void encodeHuffman(Node root, String str, Map<Character,String> huffmanCode)
    {
        if (root == null)
        {
            return;
        }
        //Kiểm tra xem là node lá hay không
        if (isLeaf(root))
        {
            huffmanCode.put(root.ch, str.length() > 0 ? str : "1");
        }
        encodeHuffman(root.left, str + '0', huffmanCode);
        encodeHuffman(root.right, str + '1', huffmanCode);
    }
    public static void buildShannonTree(String text) {
        //Đếm tần số xuất hiện từng ký tự trong văn bản và lưu nó trong Map
        //Tạo instance của Map
        Map<Character, Integer> freq = new HashMap<>();
        for (char c: text.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        //Tạo List lưu node hiện tại của cây Shanno
        List<Node> nodes = new ArrayList<>();
        for (var entry: freq.entrySet()) {
            nodes.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (nodes.size() > 1) {
            Collections.sort(nodes,
                    (l, r) -> l.freq - r.freq);
            Node left = nodes.get(0);
            Node right = nodes.get(1);
            nodes.remove(left);
            nodes.remove(right);
            Node node = new Node('\0', left.freq + right.freq);
            node.left = left;
            node.right = right;
            nodes.add(node);
        }
        Node root =nodes.get(0);

        Map<Character, String> shannoCode = new HashMap<>();
        encodeShannon(root, "", shannoCode);

        System.out.println("Ký tự\tTần số\tMã hóa Shannon-Fano");
        for (var entry: freq.entrySet()) {
            System.out.println("  "+entry.getKey() + "\t\t   " + entry.getValue() +"\t\t\t" + shannoCode.get(entry.getKey()));
        }
        // Chiều dài văn bản nhập vào
        double originalSize = text.length();
        // Lượng tin riêng H(x)
        double entropy = 0;
        // Chiều dài mã TB (L)
        double length = 0;
        //Tính lượng tin riêng và chiều dài mã TB
        for (var key:freq.keySet()){
            // Lượng tin riêng H(x)
            entropy += (freq.get(key)/originalSize)* (Math.log(freq.get(key)/originalSize)/Math.log(2));
            // Chiều dài mã TB (L)
            length +=(freq.get(key)/originalSize)*shannoCode.get(key).length();
        }
        //Hiệu suất mã hóa n = H(x)/L
        double compressionRatio = -entropy/length;
        //Tính dư thừa R = 1 - n
        double redundancy = 1 - compressionRatio;
        System.out.println("Hiệu suất mã hóa : " + compressionRatio);
        System.out.println("Tính dư thừa: " + redundancy + "%");

    }
    //Hàm mã hóa Shannon-Fano
    public static void encodeShannon(Node root, String str,Map<Character, String> shannoCode) {
        if (root == null) {
            return;
        }
        //Kiểm tra xem phải node lá hay không
        if (isLeaf(root)) {
            shannoCode.put(root.ch, str.length() > 0 ? str : "1");
        }
        encodeShannon(root.left, str + '0', shannoCode);
        encodeShannon(root.right, str + '1', shannoCode);
    }

    public static boolean isLeaf(Node root)
    {
        // Trả về true nếu 2 điều kiện là true
        return root.left == null && root.right == null;
    }
    public static void main(String args[])
    {
        System.out.print("Mời nhập chuỗi để mã hóa: ");
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine().toLowerCase();
        createHuffmanTree(text);
        buildShannonTree(text);

    }
}