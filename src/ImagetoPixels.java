import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.PrintWriter;
import javax.imageio.ImageIO;

public class ImagetoPixels {
    private static final String IMAGE_EXT_JPG = "jpg";
    private static final String IMAGE_EXT_JPEG = "jpeg";
    private static final String IMAGE_EXT_PNG = "png";
    private static final String IMAGE_EXT_GIF = "gif";
    public static final String IMAGE_ALLOW_TYPES = "Image types allowed - jpgjpegpnggif";

    public ImagetoPixels() {
    }

    public static void main(String[] args) {
        BufferedImage bufferedImage = getImage("/Users/abdurehaman/IdeaProjects/demo_fake/paint.png");
        int[][] pixels = getImageToPixels(bufferedImage);

        for(int i = 0; i < pixels.length; ++i) {
            for(int j = 0; j < pixels[0].length; ++j) {
                if (pixels[i][j] != -1) {
                    int color = pixels[i][j];
                    int blue = color & 255;
                    int green = (color & '\uff00') >> 8;
                    int red = (color & 16711680) >> 16;
                    int grey = (blue + red + green) / 3;
                    pixels[i][j] = red;
                } else {
                    pixels[i][j] = 0;
                }

                System.out.print(pixels[i][j] + " ");
            }

            System.out.println();
        }
        try{
            PrintWriter pw = new PrintWriter(new File("/Users/abdurehaman/IdeaProjects/CNN_Tutorial/trial.csv"));
            StringBuilder sb = new StringBuilder();
            for (int[] pixel : pixels)
                for (int j = 0; j < pixels[0].length; ++j) {
                    sb.append(pixel[j]);
                    sb.append(",");
                }
            String str = sb.toString();
            str = str.substring(0,str.length()-1);
            String a = "999,";
            str = a+str;
            pw.write(str);
            pw.close();
        }catch (Exception e){}

    }

    public static BufferedImage getImage(String imageFullPath) {
        BufferedImage bufferedImage = null;

        try {
            if (imageFullPath == null) {
                throw new NullPointerException("Image full path cannot be null or empty");
            }

            boolean isImage = isFileAnImage(imageFullPath);
            if (!isImage) {
                throw new ImagingOpException("Image types allowed - jpgjpegpnggif");
            }

            bufferedImage = ImageIO.read(new File(imageFullPath));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return bufferedImage;
    }

    public static int[][] getImageToPixels(BufferedImage bufferedImage) {
        if (bufferedImage == null) {
            throw new IllegalArgumentException();
        } else {
            int h = bufferedImage.getHeight();
            int w = bufferedImage.getWidth();
            int[][] pixels = new int[h][w];

            for(int i = 0; i < h; ++i) {
                bufferedImage.getRGB(0, i, w, 1, pixels[i], 0, w);
            }

            return pixels;
        }
    }

    private static boolean isFileAnImage(String imageName) {
        if (imageName == null) {
            throw new NullPointerException("Image full path cannot be null or empty");
        } else {
            File imageFile = new File(imageName);
            String ext = getFileExtension(imageFile);
            return "gif".equalsIgnoreCase(ext) || "jpeg".equalsIgnoreCase(ext) || "jpg".equalsIgnoreCase(ext) || "png".equalsIgnoreCase(ext);
        }
    }

    public static String getFileExtension(File file) {
        if (file == null) {
            throw new NullPointerException("Image file cannot be null");
        } else {
            String name = file.getName();
            int lastDotIndex = name.lastIndexOf(".");
            return lastDotIndex > 0 && lastDotIndex < name.length() - 1 ? name.substring(lastDotIndex + 1).toLowerCase() : "";
        }
    }
}
