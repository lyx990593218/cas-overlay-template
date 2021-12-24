package club.laiyouxu.captcha.puzzle;

import club.laiyouxu.captcha.puzzle.path.PuzzlePath;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.resource.Resource;
import cn.hutool.core.util.NumberUtil;
import com.jhlabs.image.ImageUtils;
import com.jhlabs.image.InvertAlphaFilter;
import com.jhlabs.image.ShadowFilter;
import lombok.Getter;
import lombok.Setter;

import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Random;

/**
 * @Description: 拼图验证码
 * @Author: Kurt Xu
 * @Date: 2021/12/22 18:58
 * @Version: 1.0
 */
@Setter
@Getter
public class PuzzleCaptcha {
    /** 默认宽度，用来计算阴影基本长度 */
    private static final int DEFAULT_WIDTH = 280;
    /** 随机数 */
    private static final Random RANDOM = new Random();
    /** 蒙版 */
    private static Color color = new Color(255, 255, 255, 204);
    /** alpha通道过滤器 */
    private static InvertAlphaFilter alphaFilter = new InvertAlphaFilter();
    /** 边距 */
    private static int margin = 10;

    /** 生成图片的宽度 */
    private int width = DEFAULT_WIDTH;
    /** 生成图片高度 */
    private int height = 150;
    /** x轴的坐标，由算法决定 */
    private int x;
    /** y轴的坐标，由算法决定 */
    private int y;
    /** 拼图长宽 */
    private int vwh = 10 * 3;
    /** 原图 */
    private Image image;
    /** 大图 */
    private Image artwork;
    /** 小图 */
    private Image vacancy;
    /** 是否注重速度 */
    private boolean isFast = false;
    /** 小图描边颜色 */
    private Color vacancyBorderColor;
    /** 小图描边线条的宽度 */
    private float vacancyBorderWidth = 2.5f;
    /** 主图描边的颜色 */
    private Color artworkBorderColor;
    /** 主图描边线条的宽度 */
    private float artworkBorderWidth = 5f;
    /**
     * 最高放大倍数,合理的放大倍数可以使图像平滑且提高渲染速度
     * 当isFast为false时，此属性生效
     * 放大倍数越高，生成的图像越平滑，受原始图片大小的影响。
     */
    private double maxRatio = 2;
    /**
     * 画质
     *
     * @see Image#SCALE_DEFAULT
     * @see Image#SCALE_FAST
     * @see Image#SCALE_SMOOTH
     * @see Image#SCALE_REPLICATE
     * @see Image#SCALE_AREA_AVERAGING
     */
    private int imageQuality = Image.SCALE_SMOOTH;

    /**
     * 路径
     */
    private PuzzlePath path;

    /**
     * 从文件中读取图片
     *
     * @param file
     */
    public PuzzleCaptcha(File file, PuzzlePath path) {
        image = ImgUtil.read(file);
        this.path = path;
    }

    /**
     * 从文件中读取图片，请使用绝对路径，使用相对路径会相对于ClassPath
     *
     * @param imageFilePath
     */
    public PuzzleCaptcha(String imageFilePath, PuzzlePath path) {
        image = ImgUtil.read(imageFilePath);
        this.path = path;
    }

    /**
     * 从{@link Resource}中读取图片
     *
     * @param resource
     */
    public PuzzleCaptcha(Resource resource, PuzzlePath path) {
        image = ImgUtil.read(resource);
        this.path = path;
    }

    /**
     * 从流中读取图片
     *
     * @param imageStream
     */
    public PuzzleCaptcha(InputStream imageStream, PuzzlePath path) {
        image = ImgUtil.read(imageStream);
        this.path = path;
    }

    /**
     * 从图片流中读取图片
     *
     * @param imageStream
     */
    public PuzzleCaptcha(ImageInputStream imageStream, PuzzlePath path) {
        image = ImgUtil.read(imageStream);
        this.path = path;
    }

    /**
     * 加载图片
     *
     * @param image
     */
    public PuzzleCaptcha(Image image, PuzzlePath path) {
        this.image = image;
        this.path = path;
    }

    /**
     * 加载图片
     *
     * @param bytes
     */
    public PuzzleCaptcha(byte[] bytes, PuzzlePath path) {
        this.image = ImgUtil.read(new ByteArrayInputStream(bytes));
        this.path = path;
    }

    /**
     * 生成随机x、y坐标
     */
    private void init() {
        if (x == 0 || y == 0) {
            this.x = random(vwh, this.width - vwh - margin);
            this.y = random(margin, this.height - vwh - margin);
        }
    }

    /**
     * 执行
     */
    public void run() {
        init();
        // 缩略图
        Image thumbnail;
        GeneralPath generalPath;
        int realW = image.getWidth(null);
        int realH = image.getHeight(null);
        int w = realW, h = realH;
        double wScale = 1, hScale = 1;
        // 如果原始图片比执行的图片还小，则先拉伸再裁剪
        boolean isFast = this.isFast || w < this.width || h < this.height;
        if (isFast) {
            // 缩放，使用平滑模式
            thumbnail = image.getScaledInstance(width, height, imageQuality);
            generalPath = this.path.plan(x,y,1,1,vwh);
            w = this.width;
            h = this.height;
        } else {
            // 缩小到一定的宽高，保证裁剪的圆润
            boolean flag = false;
            if (realW > width * maxRatio) {
                // 不超过最大倍数且不超过原始图片的宽
                w = Math.min((int) (width * maxRatio), realW);
                flag = true;
            }
            if (realH > height * maxRatio) {
                h = Math.min((int) (height * maxRatio), realH);
                flag = true;
            }
            if (flag) {
                // 若放大倍数生效，则缩小图片至最高放大倍数，再进行裁剪
                thumbnail = image.getScaledInstance(w, h, imageQuality);
            } else {
                thumbnail = image;
            }
            hScale = NumberUtil.div(h, height);
            wScale = NumberUtil.div(w, width);
            generalPath = this.path.plan(x,y,wScale,hScale,vwh);
        }

        // 创建阴影过滤器
        float radius = 5 * ((float) w / DEFAULT_WIDTH) * (float) wScale;
        int left = 1;
        ShadowFilter shadowFilter = new ShadowFilter(radius, 2 * (float) wScale, -1 * (float) hScale, 0.8f);

        // 创建空白的图片
        BufferedImage artwork = translucent(new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB));
        BufferedImage localVacancy = translucent(new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB));
        // 画小图
        Graphics2D vg = localVacancy.createGraphics();
        // 抗锯齿
        vg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 设置画图路径范围
        vg.setClip(generalPath);
        // 将区域中的图像画到小图中
        vg.drawImage(thumbnail, null, null);
        //描边
        if (vacancyBorderColor != null) {
            vg.setColor(vacancyBorderColor);
            vg.setStroke(new BasicStroke(vacancyBorderWidth));
            vg.draw(generalPath);
        }
        // 释放图像
        vg.dispose();

        // 画大图
        // 创建画笔
        Graphics2D g = artwork.createGraphics();
        // 抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 画上图片
        g.drawImage(thumbnail, null, null);
        // 设置画图路径范围
        g.setClip(generalPath);
        // 填充缺口透明度 颜色混合,不透明在上
        g.setComposite(AlphaComposite.SrcAtop);
        // 填充一层白色的透明蒙版，透明度越高，白色越深 alpha：0-255
        g.setColor(color);
        g.fill(generalPath);
        //描边
        if (artworkBorderColor != null) {
            g.setColor(artworkBorderColor);
            g.setStroke(new BasicStroke(artworkBorderWidth));
            g.draw(generalPath);
        }
        // 画上基于小图的内阴影，先反转alpha通道，然后创建阴影
        g.drawImage(shadowFilter.filter(alphaFilter.filter(localVacancy, null), null), null, null);
        // 释放图像
        g.dispose();

        // 裁剪掉多余的透明背景
        localVacancy = ImageUtils.getSubimage(localVacancy, (int) (generalPath.getBounds().getX() -(int)Math.ceil(left+radius)/2), 0, (int) Math.ceil(generalPath.getBounds().getWidth() + radius) + left, h);
        if (isFast) {
            // 添加阴影
            this.vacancy = shadowFilter.filter(localVacancy, null);
            this.artwork = artwork;
        } else {
            // 小图添加阴影
            localVacancy = shadowFilter.filter(localVacancy, null);
            // 大图缩放
            this.artwork = artwork.getScaledInstance(width, height, imageQuality);
            // 缩放时，需要加上阴影的宽度，再除以放大比例
            this.vacancy = localVacancy.getScaledInstance((int) ((generalPath.getBounds().getWidth() + radius) / wScale), height, imageQuality);
        }
    }

    /**
     * 透明背景
     *
     * @param bufferedImage
     * @return
     */
    private BufferedImage translucent(BufferedImage bufferedImage) {
        Graphics2D g = bufferedImage.createGraphics();
        bufferedImage = g.getDeviceConfiguration().createCompatibleImage(bufferedImage.getWidth(), bufferedImage.getHeight(), Transparency.TRANSLUCENT);
        g.dispose();
        return bufferedImage;
    }

    /**
     * 随机数
     *
     * @param min
     * @param max
     * @return
     */
    private static int random(int min, int max) {
        return RANDOM.ints(min, max + 1).findFirst().getAsInt();
    }
}
