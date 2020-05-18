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
        // 如果有class,则加上class属性
        whitelist.addAttributes(":all", "style", "class", "cover", "avator");
    }

    public static String clean(String content) {
        if (null == content) return null;
        return Jsoup.clean(content, "", whitelist, outputSettings);
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String text = "<a style=\"color:red;\" href=\"http://www.baidu.com/a\" onclick=\"alert(1);\">sss</a><script>alert(0);</script>sss";
        String text2 = "[{\"id\":2,\"pid\":26,\"sid\":1,\"name\":\"测试\",\"visible\":1,\"charge\":0,\"createTime\":\"2020-04-09\",\"cover\":\"\",\"view_count\":0,\"updateTime\":\"2020-04-09\",\"introduce\":null,\"text\":\"<pre><code><p style=\\\"color: rgb(73, 149, 4); background-color: rgb(199, 237, 204); font-family: Consolas, &quot;Courier New&quot;, monospace; line-height: 19px;\\\"><span style=\\\"color: #2aa198;\\\">&nbsp;&amp;quot;</span></p></code></pre>\"}]";
        System.out.println(clean(text2));
    }

}