import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {

    private Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException();
        this.picture = new Picture(picture);
    }

    //  unit testing (optional)
    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);
        // picture.show();
        SeamCarver seamCarver = new SeamCarver(picture);

        for (int i = 0; i < seamCarver.height(); i++) {
            for (int i2 = 0; i2 < seamCarver.width(); i2++) {
                System.out.print(seamCarver.picture.getRGB(i2, i) + " ");
            }
            System.out.println(" ");
        }

    }

    // current picture
    public Picture picture() {
        return new Picture(picture);
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x >= picture.width() || x < 0 || y >= picture.height() || y < 0) throw new IllegalArgumentException();
        if (x == picture.width()-1 || x == 0 || y == picture.height()-1 || y == 0) return 1000;

        int rgb1 = picture.getRGB(x+1, y);
        Color color1 = new Color(rgb1);
        int red1 = color1.getRed();
        int green1 = color1.getGreen();
        int blue1 = color1.getBlue();
        int rgb2 = picture.getRGB(x-1, y);
        Color color2 = new Color(rgb2);
        int red2 = color2.getRed();
        int green2 = color2.getGreen();
        int blue2 = color2.getBlue();

        int dx = (red2-red1) * (red2-red1) + (green2-green1) * (green2-green1) + (blue2-blue1) * (blue2-blue1);

        rgb1 = picture.getRGB(x, y+1);
        color1 = new Color(rgb1);
        red1 = color1.getRed();
        green1 = color1.getGreen();
        blue1 = color1.getBlue();
        rgb2 = picture.getRGB(x, y-1);
        color2 = new Color(rgb2);
        red2 = color2.getRed();
        green2 = color2.getGreen();
        blue2 = color2.getBlue();

        int dy = (red2-red1) * (red2-red1) + (green2-green1) * (green2-green1) + (blue2-blue1) * (blue2-blue1);

        return Math.sqrt(dx + dy);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int[] result = new int[picture.width()];
        double[][] memo = new double[picture.height()][picture.width()];

        for (int i = 0; i < picture.width(); i++) {
            double minValue = -1;
            int minIndex = -1;
            for (int i2 = 0; i2 < picture.height(); i2++) {
                double energy = energy(i, i2);

                if (i > 0) {
                    double select = memo[i2][i-1];
                    if (i2 > 0) select = memo[i2-1][i-1] < select ? memo[i2-1][i-1] : select;
                    if (i2 < picture.height()-1) select = memo[i2+1][i-1] < select ? memo[i2+1][i-1] : select;
                    energy += select;
                }

                memo[i2][i] += energy;

                if (minValue == -1 || minValue > memo[i2][i]) {
                    minIndex = i2;
                    minValue = memo[i2][i];
                }
            }
            if (i > 0 && i < picture.width()-1) result[i] = minIndex;
        }

        // back-tracking for minimum energy
        for (int i = result.length-2; i > 0; i--) {
            int minIndex = result[i];
            if (i-1 > 0) {
                double minValue = memo[result[i]][i-1];
                if (result[i]-1 > -1 && minValue > memo[result[i]-1][i-1]) {
                    minValue = memo[result[i]-1][i-1];
                    minIndex = result[i]-1;
                }
                if (result[i-1]+1 < picture.height() && minValue > memo[result[i]+1][i-1]) {
                    minValue = memo[result[i]+1][i-1];
                    minIndex = result[i]+1;
                }
            } else if (i == 1) {
                double minValue = memo[result[i]][i];
                if (result[i]-1 > -1 && minValue > memo[result[i]-1][i]) {
                    minValue = memo[result[i]-1][i];
                    minIndex = result[i]-1;
                }
                if (result[i]+1 < picture.height() && minValue > memo[result[i]-1][i]) {
                    minValue = memo[result[i]-1][i];
                    minIndex = result[i]+1;
                }
            }
            result[i-1] = minIndex;
        }

        if (picture.width()-1 > 0) {
            result[0] = result[1]-1 > -1 ? result[1]-1 : 0;
            result[picture.width()-1] = result[picture.width()-2]-1 > -1 ? result[picture.width()-2]-1 : 0;
        }

        return result;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] result = new int[picture.height()];
        double[][] memo = new double[picture.height()][picture.width()];

        for (int i = 0; i < picture.height(); i++) {
            double minValue = -1;
            int minIndex = -1;
            for (int i2 = 0; i2 < picture.width(); i2++) {
                double energy = energy(i2, i);

                if (i > 0) {
                    double select = memo[i-1][i2];
                    if (i2 > 0) select = memo[i-1][i2-1] < select ? memo[i-1][i2-1] : select;
                    if (i2 < picture.width()-1) select = memo[i-1][i2+1] < select ? memo[i-1][i2+1] : select;
                    energy += select;
                }

                memo[i][i2] += energy;

                if (minValue == -1 || minValue > memo[i][i2]) {
                    minIndex = i2;
                    minValue = memo[i][i2];
                }
            }
            if (i > 0 && i < picture.height()-1) result[i] = minIndex;
        }

        // back-tracking for minimum energy
        for (int i = result.length-2; i > 0; i--) {
            int minIndex = result[i];
            if (i-1 > 0) {
                double minValue = memo[i-1][result[i]];
                if (result[i]-1 > -1 && minValue > memo[i-1][result[i]-1]) {
                    minValue = memo[i-1][result[i]-1];
                    minIndex = result[i]-1;
                }
                if (result[i-1]+1 < picture.width() && minValue > memo[i-1][result[i]+1]) {
                    minValue = memo[i-1][result[i]+1];
                    minIndex = result[i]+1;
                }
            } else if (i == 1) {
                double minValue = memo[i][result[i]];
                if (result[i]-1 > -1 && minValue > memo[i][result[i]-1]) {
                    minValue = memo[i][result[i]-1];
                    minIndex = result[i]-1;
                }
                if (result[i]+1 < picture.width() && minValue > memo[i][result[i]-1]) {
                    minValue = memo[i][result[i]-1];
                    minIndex = result[i]+1;
                }
            }
            result[i-1] = minIndex;
        }

        if (picture.height()-1 > 0) {
            result[0] = result[1]-1 > -1 ? result[1]-1 : 0;
            result[picture.height()-1] = result[picture.height()-2]-1 > -1 ? result[picture.height()-2]-1 : 0;
        }

        return result;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (picture.height() == 1 || seam == null || seam.length != picture.width()) throw new IllegalArgumentException();

        Picture newPicture = new Picture(picture.width(), picture.height()-1);
        for (int i = 0; i < picture.width(); i++) {
            if (i > 0 && Math.abs(seam[i-1]-seam[i]) > 1) throw new IllegalArgumentException();
            for (int i2 = 0; i2 < picture.height()-1; i2++) {
                int y = i2;
                if (y >= seam[i]) y += 1;
                newPicture.setRGB(i, i2, picture.getRGB(i, y));
            }
        }
        picture = newPicture;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (picture.width() == 1 || seam == null || seam.length != picture.height()) throw new IllegalArgumentException();

        Picture newPicture = new Picture(picture.width()-1, picture.height());
        for (int i = 0; i < picture.height(); i++) {
            if (i > 0 && Math.abs(seam[i-1]-seam[i]) > 1) throw new IllegalArgumentException();
            for (int i2 = 0; i2 < picture.width()-1; i2++) {
                int y = i2;
                if (y >= seam[i]) y += 1;
                newPicture.setRGB(i2, i, picture.getRGB(y, i));
            }
        }
        picture = newPicture;
    }

}
