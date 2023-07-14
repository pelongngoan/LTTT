import java.util.Scanner;
import java.util.regex.Pattern;

public class Bai_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int hang;
        int cot;
        double[][] p;
        System.out.println("\t======== Nhập ma trận xác suất kết hợp P(x,y) cỡ MxN ========");
        //Nhập số hàng và cột của ma trận
        do {

            // Nhập số hàng và kiểm tra > 0 hay không
            System.out.print("Nhập số hàng:");
            do {

                hang = scanner.nextInt();
                if (hang <= 0) System.out.print("\t Nhập sai ==> Nhập lại số hàng phải > 0 :");
            } while (hang < 0);

            // Nhập số cột và kiểm tra > 0 hay không
            System.out.print("Nhập số cột:");
            do {
                cot = scanner.nextInt();
                if (cot <= 0) System.out.print("\t Nhập sai ==> Nhập lại số cột phải > 0 :");
            } while (cot < 0);
            if (hang != cot) System.out.println("Ma trận xác suất kết hợp là một ma trận vuông, mời nhập lại:");
        } while (hang != cot);

        p = new double[hang + 1][cot + 1];

        //Nhập xác suất P(x,y)
        for (int x = 0; x < hang; x++) {
            for (int y = 0; y < cot; y++) {
                boolean thoaMan = false;
                while (!thoaMan) {
                    System.out.print("\tP[" + x + "][" + y + "] = ");
                    String input = scanner.nextLine();
                    //Kiểm tra giá trị nhập vào là phân số hay số thực > 0 hay không
                    if (isFraction(input) && (toDemical(input) >= 0 && toDemical(input) <= 1)) {
                        p[x][y] = toDemical(input);
                        thoaMan = true;
                    } else if (isNumeric(input) && (Double.valueOf(input) >= 0 && Double.valueOf(input) < 1)) {
                        p[x][y] = Double.valueOf(input);
                        thoaMan = true;
                    } else {
                        System.out.println("Giá trị nhập vào phải trong khoảng từ 0 đến 1");
                    }
                }
            }
        }

        // Tính P(x)
        for (int i = 0; i < cot; i++) {
            double px = 0;
            for (int j = 0; j < hang; j++) {
                px += p[j][i];
            }
            p[hang][i] = px;
        }
        // Tính P(y)
        for (int i = 0; i < hang; i++) {
            double py = 0;
            for (int j = 0; j < cot; j++) {
                py += p[i][j];
            }
            p[i][cot] = py;
        }

        // In ra phần b)
        System.out.println("H(x) = " + getHx(p, hang, cot) + " bits");
        System.out.println("H(y) = " + getHy(p, hang, cot) + " bits");
        System.out.println("H(x|y) = " + getHxDy(p, hang, cot) + " bits");
        System.out.println("H(y|x) = " + getHyDx(p, hang, cot) + " bits");
        System.out.println("H(x,y) = " + getHxy(p, hang, cot) + " bits");
        System.out.println("H(y)-H(y|x) = " + getHyMyDx(p, hang, cot) + " bits");
        System.out.println("I(X;Y) = " + getIxy(p, hang, cot) + " bits");

        // In ra phần c)
        System.out.println("D(P(x)||P(y)) = " + getDpxpy(p, hang, cot) + " bits");
        System.out.println("D(P(y)||P(x) = " + getDpypx(p, hang, cot) + " bits");


    }

    //Kiểm tra xác suất nhập vào là phân số hay không
    public static boolean isFraction(String text) {
        String[] numeratorAndDenominator = text.split("/");
        // Kiểm tra dấu " / " và tách tử và mẫu
        if (numeratorAndDenominator.length != 2 || Integer.parseInt(numeratorAndDenominator[1].trim()) == 0) {
            return false;
        }
        // Kiểm tra tử số là số thực hay không
        try {
            Double.parseDouble(numeratorAndDenominator[0].trim());
        } catch (NumberFormatException e) {
            return false;
        }
        // Kiểm tra mẫu số là số thực hay không
        try {
            Double.parseDouble(numeratorAndDenominator[1].trim());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    // Chuyển phân số nhập vào sang số thập phân
    public static double toDemical(String text) {
        String[] numeratorAndDenominator = text.split("/");
        return Double.valueOf(numeratorAndDenominator[0]) / Double.valueOf(numeratorAndDenominator[1]);
    }

    public static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    //Kiểm tra xác suất nhập vào là số thực hay không
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    // Tính H(x)
    public static double getHx(double p[][], int hang, int cot) {
        double results = 0;
        for (int i = 0; i < cot; i++) {
            if (p[hang][i] != 0) {
                results += p[hang][i] * (Math.log(p[hang][i]) / Math.log(2));
            }
        }
        return -results;
    }

    // Tính H(y)
    public static double getHy(double p[][], int hang, int cot) {
        double results = 0;
        for (int i = 0; i < hang; i++) {
            if (p[i][cot] != 0) {
                results += p[i][cot] * (Math.log(p[i][cot]) / Math.log(2));
            }
        }
        return -results;
    }

    // Tính H(x|y)
    public static double getHxDy(double p[][], int hang, int cot) {
        double results = 0;
        for (int i = 0; i < hang; i++) {
            for (int j = 0; j < cot; j++) {
                if (p[i][j] != 0) {
                    results += p[i][j] * (Math.log(p[i][j] / p[i][cot]) / Math.log(2));
                }
            }
        }
        return -results;
    }

    // Tính H(y|x)
    public static double getHyDx(double p[][], int hang, int cot) {
        double results = 0;
        for (int i = 0; i < hang; i++) {
            for (int j = 0; j < cot; j++) {
                if (p[i][j] != 0) {
                    results += p[i][j] * (Math.log(p[i][j] / p[hang][j]) / Math.log(2));
                }
            }
        }
        return -results;
    }

    // Tính H(x,y)
    public static double getHxy(double p[][], int hang, int cot) {
        return getHx(p, hang, cot) + getHyDx(p, hang, cot);
    }

    // Tính H(y)-H(y|x)
    public static double getHyMyDx(double p[][], int hang, int cot) {
        return getHy(p, hang, cot) - getHyDx(p, hang, cot);
    }

    // Tính I(x;y)
    public static double getIxy(double p[][], int hang, int cot) {
        return getHy(p, hang, cot) - getHyDx(p, hang, cot);
    }

    //Tính D(P(x)||P(y))
    public static double getDpxpy(double p[][], int hang, int cot) {
        double results = 0;
        for (int i = 0; i < cot; i++) {
            if (p[hang][i] != 0 && p[i][cot] != 0) {
                results += p[hang][i] * (Math.log(p[hang][i] / p[i][cot])) / Math.log(2);
                System.out.println();
            }

        }
        return results;
    }

    //Tính D(P(y)||P(x))
    public static double getDpypx(double p[][], int hang, int cot) {
        double results = 0;
        for (int i = 0; i < hang; i++) {
            results += p[i][cot] * (Math.log(p[i][cot] / p[hang][i])) / Math.log(2);
        }
        return results;
    }
}
