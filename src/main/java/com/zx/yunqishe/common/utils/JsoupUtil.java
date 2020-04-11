package com.zx.yunqishe.common.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * xss非法标签过滤
 * @link{http://www.jianshu.com/p/32abc12a175a?nomobile=yes}
 */
public class JsoupUtil {

    /**
     * 有5类白名单，这里用开放能力最强的那个
     */
    private static final Whitelist whitelist = Whitelist.relaxed();
    /** 配置过滤化参数,不对代码进行格式化 */
    private static final Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);
    static {
        // 富文本编辑时一些样式是使用style来进行实现的
        // 比如红色字体 style="color:red;"
        // 所以需要给所有标签添加style属性
        whitelist.addAttributes(":all", "style");
    }

    public static String clean(String content) {
        return Jsoup.clean(content, "", whitelist, outputSettings);
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String text = "<a href=\"http://www.baidu.com/a\" onclick=\"alert(1);\">sss</a><script>alert(0);</script>sss";
        System.out.println(clean(text));
    }

}