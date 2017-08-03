package com.roo.utils;

import com.blade.kit.Hashids;
import com.blade.kit.StringKit;
import com.vdurmont.emoji.EmojiParser;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.Arrays;
import java.util.List;

/**
 * Roo工具类
 *
 * @author biezhi
 * @date 2017/8/2
 */
public class RooUtils {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz1234567890";

    private static final Hashids hashids = new Hashids("blade-roo", 12, ALPHABET);

    public static String genTid() {
        return hashids.encode(1002, System.currentTimeMillis());
    }

    public static Long decodeId(String hash) {
        return hashids.decode(hash)[1];
    }

    /**
     * markdown转换为html
     *
     * @param markdown
     * @return
     */
    public static String mdToHtml(String markdown) {
        if (StringKit.isBlank(markdown)) {
            return "";
        }

        List<Extension> extensions = Arrays.asList(TablesExtension.create());
        Parser          parser     = Parser.builder().extensions(extensions).build();
        Node            document   = parser.parse(markdown);
        HtmlRenderer    renderer   = HtmlRenderer.builder().extensions(extensions).build();
        String          content    = renderer.render(document);
        content = EmojiParser.parseToUnicode(content);
        return content;
    }

    /**
     * 提取html中的文字
     *
     * @param html
     * @return
     */
    public static String htmlToText(String html) {
        if (StringKit.isNotBlank(html)) {
            return html.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
        }
        return "";
    }

}
